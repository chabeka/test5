package fr.urssaf.image.sae.integration.ihmweb.service.tests;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.integration.ihmweb.formulaire.CaptureUnitaireFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.modele.CaptureUnitaireResultat;
import fr.urssaf.image.sae.integration.ihmweb.modele.ResultatTest;
import fr.urssaf.image.sae.integration.ihmweb.modele.ResultatTestLog;
import fr.urssaf.image.sae.integration.ihmweb.modele.SoapFault;
import fr.urssaf.image.sae.integration.ihmweb.modele.TestStatusEnum;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.modele.SaeServiceStub;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.modele.SaeServiceStub.ArchivageUnitaire;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.modele.SaeServiceStub.ArchivageUnitaireResponse;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.utils.SaeServiceLogUtils;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.utils.SaeServiceObjectFactory;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.utils.SaeServiceStubUtils;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.utils.SaeServiceTypeUtils;
import fr.urssaf.image.sae.integration.ihmweb.service.ecde.EcdeService;
import fr.urssaf.image.sae.integration.ihmweb.service.referentiels.ReferentielSoapFaultService;
import fr.urssaf.image.sae.integration.ihmweb.service.tests.listeners.WsTestListener;
import fr.urssaf.image.sae.integration.ihmweb.service.tests.listeners.impl.WsTestListenerImplLibre;
import fr.urssaf.image.sae.integration.ihmweb.service.tests.listeners.impl.WsTestListenerImplReponseAttendue;
import fr.urssaf.image.sae.integration.ihmweb.service.tests.listeners.impl.WsTestListenerImplSoapFault;
import fr.urssaf.image.sae.integration.ihmweb.utils.ViUtils;

/**
 * Service de tests de l'opération "archivageUnitaire" du service SaeService
 */
@Service
public class CaptureUnitaireTestService {

   @Autowired
   private EcdeService ecdeService;

   @Autowired
   private ReferentielSoapFaultService refSoapFault;

   private CaptureUnitaireResultat appelWsOpCaptureUnitaire(
         String urlServiceWeb, String ficRessourceVi,
         CaptureUnitaireFormulaire formulaire, WsTestListener wsListener) {

      // Initialise la valeur de retour
      CaptureUnitaireResultat result = new CaptureUnitaireResultat();

      // Vide le résultat du test précédent
      ResultatTest resultatTest = formulaire.getResultats();
      resultatTest.clear();
      ResultatTestLog log = resultatTest.getLog();
      wsListener.onSetStatusInitialResultatTest(resultatTest);

      // Ajout d'un log
      SaeServiceLogUtils.logAppelArchivageUnitaire(log, formulaire);

      // Récupération du stub du service web
      SaeServiceStub service = SaeServiceStubUtils.getServiceStub(
            urlServiceWeb, ficRessourceVi);

      // Appel du service web et gestion de erreurs
      try {

         // Construction du paramètre d'entrée de l'opération
         ArchivageUnitaire paramsService = SaeServiceObjectFactory
               .buildArchivageUnitaireRequest(formulaire.getUrlEcde(),
                     formulaire.getMetadonnees());

         // Appel du service web
         ArchivageUnitaireResponse response = service
               .archivageUnitaire(paramsService);

         // Appel du listener
         wsListener.onRetourWsSansErreur(resultatTest, service
               ._getServiceClient().getServiceContext()
               .getConfigurationContext(), formulaire.getParent());

         // Récupère l'identifiant d'archivage renvoyé
         String idArchivage = SaeServiceTypeUtils.extractUuid(response
               .getArchivageUnitaireResponse().getIdArchive());

         // Log de la réponse obtenue
         log.appendLogNewLine();
         log
               .appendLogLn("Détails de la réponse obtenue de l'opération \"archivageUnitaire\" :");
         SaeServiceLogUtils.logResultatCaptureUnitaire(resultatTest, response
               .getArchivageUnitaireResponse());

         // Affecte l'identifiant d'archivage à l'objet de réponse de la méthode
         result.setIdArchivage(idArchivage);

         // Calcul du SHA-1 du fichier envoyé, et affectation à l'objet de
         // réponse
         String sha1 = ecdeService.sha1(formulaire.getUrlEcde());
         result.setSha1(sha1);
         log.appendLogNewLine();
         log
               .appendLogLn("Pour information, le SHA-1 du fichier envoyé à la capture est : ");
         log.appendLogLn(sha1);

      } catch (AxisFault fault) {

         // Appel du listener
         wsListener.onSoapFault(resultatTest, fault, service
               ._getServiceClient().getServiceContext()
               .getConfigurationContext(), formulaire.getParent());

      } catch (RemoteException e) {

         // Appel du listener
         wsListener.onRemoteException(resultatTest, e, service
               ._getServiceClient().getServiceContext()
               .getConfigurationContext(), formulaire.getParent());

      }

      // Ajoute le timestamp en 1ère ligne du log
      log.insertTimestamp();

      // Renvoie du résultat
      return result;

   }

   /**
    * Test libre de l'appel à l'opération "archivageUnitaire" du service web
    * SaeService.<br>
    * 
    * @param urlServiceWeb
    *           l'URL du service web SaeService
    * @param formulaire
    *           le formulaire
    */
   public final void appelWsOpCaptureUnitaireUrlEcdeTestLibre(
         String urlServiceWeb, CaptureUnitaireFormulaire formulaire) {

      // Création de l'objet qui implémente l'interface WsTestListener
      // et qui ne s'attend pas à un quelconque résultat (test libre)
      WsTestListener testLibre = new WsTestListenerImplLibre();

      // Appel de la méthode "générique" de test
      appelWsOpCaptureUnitaire(urlServiceWeb, ViUtils.FIC_VI_OK, formulaire,
            testLibre);

   }

   /**
    * Test d'appel à l'opération "archivageUnitaire" du service web SaeService.<br>
    * <br>
    * On s'attend à obtenir une SoapFault.
    * 
    * @param urlServiceWeb
    *           l'URL du service web SaeService
    * @param formulaire
    *           le formulaire
    * @param ficVi
    *           le fichier de VI à utiliser parmi les constantes des ViUtils
    * @param idSoapFaultAttendu
    *           l'identifiant de la SoapFault attendu dans le référentiel des
    *           SoapFault
    * @param argsMsgSoapFault
    *           les arguments pour le String.format du message de la SoapFault
    *           attendue
    */
   public final void appelWsOpCaptureUnitaireSoapFault(String urlServiceWeb,
         CaptureUnitaireFormulaire formulaire, String ficVi,
         String idSoapFaultAttendu, final Object[] argsMsgSoapFault) {

      // Création de l'objet qui implémente l'interface WsTestListener
      // et qui s'attend à recevoir une certaine SoapFault
      SoapFault faultAttendue = refSoapFault.findSoapFault(idSoapFaultAttendu);
      WsTestListener listener = new WsTestListenerImplSoapFault(faultAttendue,
            argsMsgSoapFault);

      // Appel de la méthode "générique" de test
      appelWsOpCaptureUnitaire(urlServiceWeb, ficVi, formulaire, listener);

   }

   /**
    * Test d'appel à l'opération "archivageUnitaire" du service web SaeService.<br>
    * On s'attend à récupérer une réponse sans erreur
    * 
    * @param urlServiceWeb
    *           l'URL du service web SaeService
    * @param formulaire
    *           le formulaire
    * @return les informations résultant de la capture unitaire
    */
   public final CaptureUnitaireResultat appelWsOpCaptureUnitaireReponseAttendue(
         String urlServiceWeb, CaptureUnitaireFormulaire formulaire) {

      // Création de l'objet qui implémente l'interface WsTestListener
      // et qui s'attend à recevoir une réponse
      WsTestListener testAvecReponse = new WsTestListenerImplReponseAttendue();

      // Appel de la méthode "générique" de test
      CaptureUnitaireResultat resultat = appelWsOpCaptureUnitaire(
            urlServiceWeb, ViUtils.FIC_VI_OK, formulaire, testAvecReponse);

      // On considère que le test est en succès si aucune erreur renvoyé
      ResultatTest resultatTest = formulaire.getResultats();
      if ((resultat != null)
            && (TestStatusEnum.NonLance.equals(resultatTest.getStatus()))) {
         resultatTest.setStatus(TestStatusEnum.Succes);
      } else {
         resultatTest.setStatus(TestStatusEnum.Echec);
      }

      // Renvoie le résultat
      return resultat;

   }

}
