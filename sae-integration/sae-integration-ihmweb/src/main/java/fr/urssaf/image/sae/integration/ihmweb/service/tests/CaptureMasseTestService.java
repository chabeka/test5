package fr.urssaf.image.sae.integration.ihmweb.service.tests;

import java.io.File;
import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.integration.ihmweb.constantes.SaeIntegrationConstantes;
import fr.urssaf.image.sae.integration.ihmweb.formulaire.CaptureMasseFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.formulaire.CaptureMasseResultatFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.modele.ResultatTest;
import fr.urssaf.image.sae.integration.ihmweb.modele.ResultatTestLog;
import fr.urssaf.image.sae.integration.ihmweb.modele.SoapFault;
import fr.urssaf.image.sae.integration.ihmweb.modele.TestStatusEnum;
import fr.urssaf.image.sae.integration.ihmweb.modele.somres.resultats.ResultatsType;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.modele.SaeServiceStub;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.modele.SaeServiceStub.ArchivageMasse;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.utils.SaeServiceLogUtils;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.utils.SaeServiceObjectFactory;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.utils.SaeServiceStubUtils;
import fr.urssaf.image.sae.integration.ihmweb.service.ecde.EcdeService;
import fr.urssaf.image.sae.integration.ihmweb.service.referentiels.ReferentielSoapFaultService;
import fr.urssaf.image.sae.integration.ihmweb.service.tests.listeners.WsTestListener;
import fr.urssaf.image.sae.integration.ihmweb.service.tests.listeners.impl.WsTestListenerImplLibre;
import fr.urssaf.image.sae.integration.ihmweb.service.tests.listeners.impl.WsTestListenerImplSoapFault;
import fr.urssaf.image.sae.integration.ihmweb.utils.ViUtils;


/**
 * Service pour les tests de la fonctionnalité "Capture de masse".<br>
 * <br>
 * Cette fonctionnalité fait intervenir :<br>
 * <ul>
 *    <li>le service web SaeService, et son opération "archivageMasse"</li>
 *    <li>
 *       l'ECDE, avec :
 *       <ul>
 *          <li>la présence ou non du fichier flag de fin de traitement</li>
 *          <li>la lecture du fichier resultats.xml</li>
 *       </ul>
 *    </li>
 * </ul>
 */
@Service
public class CaptureMasseTestService {

   
   @Autowired
   private ReferentielSoapFaultService refSoapFault;
   
   
   @Autowired
   private EcdeService ecdeService;
   
   
   private void appelWsOpArchiMasse(
         String urlServiceWeb,
         String ficRessourceVi,
         CaptureMasseFormulaire formulaire,
         WsTestListener wsListener) {
   
      // Vide le résultat du test précédent
      ResultatTest resultatTest = formulaire.getResultats();
      resultatTest.clear();
      ResultatTestLog log = resultatTest.getLog();
      wsListener.onSetStatusInitialResultatTest(resultatTest);
      
      // Ajout d'un log de résultat
      SaeServiceLogUtils.logAppelArchivageMasse(log,formulaire);
      
      // Récupération du stub du service web
      SaeServiceStub service = SaeServiceStubUtils.getServiceStub(
            urlServiceWeb, ficRessourceVi);
      
      // Appel du service web et gestion de erreurs
      try 
      {
         
         // Construction du paramètre d'entrée de l'opération
         ArchivageMasse paramsService = SaeServiceObjectFactory.buildArchivageMasseRequest(
               formulaire.getUrlSommaire());
         
         // Appel du service web
         service.archivageMasse(paramsService); 

         // Appel du listener
         wsListener.onRetourWsSansErreur(resultatTest);
         
         // Log de la réponse obtenue
         log.appendLogLn("Détails de la réponse obtenue de l'opération \"archivageMasse\" :");
         log.appendLogLn("Le service n'a pas renvoyé d'erreur");
         
      } catch (AxisFault fault) {
        
         // Appel du listener
         wsListener.onSoapFault(resultatTest,fault);
         
      } catch (RemoteException e) {
         
         // Appel du listener
         wsListener.onRemoteException(resultatTest,e);
         
      }
      
      // Ajoute le timestamp en 1ère ligne du log
      log.insertTimestamp();
      
   }
   
   

   /**
    * Test libre de l'appel à l'opération "archivageMasse" du service web SaeService.<br>
    * 
    * @param urlServiceWeb l'URL du service web SaeService
    * @param formulaire le formulaire
    */
   public final void appelWsOpArchiMasseTestLibre(
         String urlServiceWeb,
         CaptureMasseFormulaire formulaire) {
   
      // Création de l'objet qui implémente l'interface WsTestListener
      // et qui ne s'attend pas à un quelconque résultat (test libre)
      WsTestListener testLibre = new WsTestListenerImplLibre();
      
      // Appel de la méthode "générique" de test
      appelWsOpArchiMasse(
            urlServiceWeb,
            ViUtils.FIC_VI_OK,
            formulaire,
            testLibre);
      
   }
   
   
   /**
    * Test d'appel à l'opération "archivageMasse" du service web SaeService.<br>
    * On vérifie que l'authentification applicative est activée sur l'opération
    * 
    * @param urlServiceWeb l'URL du service web SaeService
    * @param formulaire le formulaire
    */
   public final void appelWsOpArchiMasseSoapFaultAuth(
         String urlServiceWeb,
         CaptureMasseFormulaire formulaire) {
      
      // Création de l'objet qui implémente l'interface WsTestListener
      // et qui s'attend à recevoir une certaine SoapFault
      SoapFault faultAttendue = refSoapFault.findSoapFault("wsse_SecurityTokenUnavailable") ;
      WsTestListener testAuth = new WsTestListenerImplSoapFault(faultAttendue,null);
      
      // Appel de la méthode "générique" de test
      appelWsOpArchiMasse(
            urlServiceWeb,
            ViUtils.FIC_VI_SANS_VI,
            formulaire,
            testAuth);
      
   }
   
   
   /**
    * Regarde les résultats d'un traitement de masse
    * 
    * @param formulaire le formulaire
    */
   public final void regardeResultatsTdm(
         CaptureMasseResultatFormulaire formulaire) {
      
      // Vide le résultat du test précédent
      ResultatTest resultatTest = formulaire.getResultats();
      resultatTest.clear();
      ResultatTestLog log = resultatTest.getLog();
      resultatTest.setStatus(TestStatusEnum.SansStatus);
            
      // Récupère l'URL ECDE du fichier sommaire.xml
      String urlEcdeSommaire = formulaire.getUrlSommaire();
      
      // Récupère le chemin complet du fichier flag
      String cheminFichierFlag = getCheminFichierFlag(urlEcdeSommaire);
      
      // On regarde si le fichier flag est présent ou non
      log.appendLog("Présence du fichier flag " + cheminFichierFlag + " : ");
      File file = new File(cheminFichierFlag);
      boolean fichierFlagPresent = file.exists();
      if (fichierFlagPresent) {
         log.appendLogLn("OUI");
         log.appendLogLn("=> Le traitement de masse est considéré comme terminé");
      } else {
         log.appendLogLn("NON");
         log.appendLogLn("=> Le traitement de masse n'est pas considéré comme terminé (pas encore commencé ou en cours)");
      }
         
      // Si le fichier flag est présent, on jette un coup d'oeil au resultats.xml
      if (fichierFlagPresent) {
         
         // Récupère le chemin complet du fichier resultats.xml
         String cheminFichierResultatsXml = getCheminFichierResultatsXml(urlEcdeSommaire);
         
         // On vérifie dans un premier temps que le fichier resultats existe
         log.appendLogNewLine();
         log.appendLog("Présence du fichier résultats " + cheminFichierResultatsXml + " : ");
         file = new File(cheminFichierResultatsXml);
         boolean fichierResultatsPresent = file.exists();
         if (fichierResultatsPresent) {
            
            // Le fichier resultats.xml est présent
            log.appendLogLn("OUI");
            log.appendLogNewLine();
            
            // Charge le fichier resultats.xml
            ResultatsType objResultatXml = ecdeService.chargeResultatsXml(cheminFichierResultatsXml);
            
            // Affiche dans le log un résumé du fichier resultats.xml
            SaeServiceLogUtils.logResultatsXml(
                  resultatTest, 
                  objResultatXml, 
                  cheminFichierResultatsXml);
            
            
         } else {
            
            // Test en échec : le fichier flag est présent, mais pas le fichier résultats
            resultatTest.setStatus(TestStatusEnum.Echec);
            log.appendLogLn("NON");
            log.appendLogNewLine();
            log.appendLogLn("Erreur : le fichier flag est présent, on devrait trouver un fichier résultats");

         }
         
         
      }
      
   }
   
   
//   private String getRepertoireTraitement(String repDocuments) {
//      
//      // Le répertoire documents correspond à :
//      //  => racineEcde/AAAAMMJJ/idTraitements/documents/
//      // On cherche à retirer le répertoire documents pour obtenir :
//      //  => racineEcde/AAAAMMJJ/idTraitements/
//      
//      String repDocumentsOk = FilenameUtils.normalizeNoEndSeparator(repDocuments);
//      int idx = FilenameUtils.indexOfLastSeparator(repDocumentsOk);
//      String repTraitement = repDocumentsOk.substring(0,idx);
//      repTraitement = FilenameUtils.normalizeNoEndSeparator(repTraitement);
//      
//      return repTraitement; 
//      
//   }
   
   
//   private String getRepertoireDocuments(String cheminFicSommaire) {
//      
//      String repDocuments = FilenameUtils.getFullPath(cheminFicSommaire);
//      repDocuments = FilenameUtils.normalizeNoEndSeparator(repDocuments);
//      return repDocuments; 
//      
//   }
   
   
   private String getRepertoireTraitement(String cheminFicSommaire) {
    
    String repTraitement = FilenameUtils.getFullPath(cheminFicSommaire);
    
    return repTraitement; 
    
 }
   
   
   private String getCheminFichierFlag(String urlEcdeSommaire) {
      
      String cheminFicSommaire = ecdeService.convertUrlEcdeToPath(urlEcdeSommaire);
      
      String repTraitement = getRepertoireTraitement(cheminFicSommaire);
      
      String cheminFichierFlag = FilenameUtils.concat(
            repTraitement, 
            SaeIntegrationConstantes.NOM_FIC_FLAG_TDM);
      
      return cheminFichierFlag;
      
   }
   
   
   private String getCheminFichierResultatsXml(String urlEcdeSommaire) {
      
      String cheminFicSommaire = ecdeService.convertUrlEcdeToPath(urlEcdeSommaire);
      
      String repTraitement = getRepertoireTraitement(cheminFicSommaire);
      
      String cheminFichierResultatsXml = FilenameUtils.concat(
            repTraitement, 
            SaeIntegrationConstantes.NOM_FIC_RESULTATS);
      
      return cheminFichierResultatsXml;
      
   }
   
   

}
