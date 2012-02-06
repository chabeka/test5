package fr.urssaf.image.sae.integration.ihmweb.service.tests;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.activation.DataHandler;

import org.apache.axis2.AxisFault;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.integration.ihmweb.exception.IntegrationRuntimeException;
import fr.urssaf.image.sae.integration.ihmweb.formulaire.ConsultationFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.modele.ResultatTest;
import fr.urssaf.image.sae.integration.ihmweb.modele.ResultatTestLog;
import fr.urssaf.image.sae.integration.ihmweb.modele.SoapFault;
import fr.urssaf.image.sae.integration.ihmweb.modele.TestStatusEnum;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.modele.SaeServiceStub;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.modele.SaeServiceStub.Consultation;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.modele.SaeServiceStub.ConsultationResponse;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.modele.SaeServiceStub.ObjetNumeriqueConsultationType;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.utils.SaeServiceLogUtils;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.utils.SaeServiceObjectFactory;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.utils.SaeServiceStubUtils;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.utils.SaeServiceTypeUtils;
import fr.urssaf.image.sae.integration.ihmweb.service.referentiels.ReferentielSoapFaultService;
import fr.urssaf.image.sae.integration.ihmweb.service.tests.listeners.WsTestListener;
import fr.urssaf.image.sae.integration.ihmweb.service.tests.listeners.impl.WsTestListenerImplLibre;
import fr.urssaf.image.sae.integration.ihmweb.service.tests.listeners.impl.WsTestListenerImplReponseAttendue;
import fr.urssaf.image.sae.integration.ihmweb.service.tests.listeners.impl.WsTestListenerImplSoapFault;
import fr.urssaf.image.sae.integration.ihmweb.service.tests.utils.TestsMetadonneesService;
import fr.urssaf.image.sae.integration.ihmweb.utils.ChecksumUtils;
import fr.urssaf.image.sae.integration.ihmweb.utils.ViUtils;

/**
 * Service de test de l'opération "consultation" du service web SaeService
 */
@SuppressWarnings( { "PMD.CyclomaticComplexity", "PMD.NPathComplexity",
      "PMD.ExcessiveMethodLength" })
@Service
public class ConsultationTestService {

   @Autowired
   private ReferentielSoapFaultService refSoapFault;

   @Autowired
   private TestsMetadonneesService testsMetasService;

   private ConsultationResponse appelWsOpConsultation(String urlServiceWeb,
         String ficRessourceVi, ConsultationFormulaire formulaire,
         WsTestListener wsListener) {

      // Initialise le résultat
      ConsultationResponse result = null;

      // Vide le résultat du test précédent
      ResultatTest resultatTest = formulaire.getResultats();
      resultatTest.clear();
      ResultatTestLog log = resultatTest.getLog();
      wsListener.onSetStatusInitialResultatTest(resultatTest);

      // Ajout d'un log de résultat
      SaeServiceLogUtils.logAppelConsultation(log, formulaire);

      // Récupération du stub du service web
      SaeServiceStub service = SaeServiceStubUtils.getServiceStub(
            urlServiceWeb, ficRessourceVi);

      // Appel du service web et gestion de erreurs
      try {

         // Construction du paramètre d'entrée de l'opération
         Consultation paramsService = SaeServiceObjectFactory
               .buildConsultationRequest(formulaire.getIdArchivage(),
                     formulaire.getCodeMetadonnees());

         // Appel du service web
         ConsultationResponse response = service.consultation(paramsService);

         // Mémorise la réponse pour le retour de la méthode
         result = response;

         // Appel du listener
         wsListener.onRetourWsSansErreur(resultatTest, service
               ._getServiceClient().getServiceContext()
               .getConfigurationContext(), formulaire.getParent());

         // Log de la réponse obtenue
         log
               .appendLogLn("Détails de la réponse obtenue de l'opération \"consultation\" :");
         SaeServiceLogUtils.logResultatConsultation(resultatTest, response
               .getConsultationResponse());

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
    * Test libre de l'appel à l'opération "consultation" du service web
    * SaeService.<br>
    * 
    * @param urlServiceWeb
    *           l'URL du service web SaeService
    * @param formulaire
    *           le formulaire
    */
   public final void appelWsOpConsultationTestLibre(String urlServiceWeb,
         ConsultationFormulaire formulaire) {

      // Création de l'objet qui implémente l'interface WsTestListener
      // et qui ne s'attend pas à un quelconque résultat (test libre)
      WsTestListener testLibre = new WsTestListenerImplLibre();

      // Appel de la méthode "générique" de test
      appelWsOpConsultation(urlServiceWeb, ViUtils.FIC_VI_OK, formulaire,
            testLibre);

   }

   /**
    * Test d'appel à l'opération "consultation" du service web SaeService.<br>
    * On s'attend à récupérer une SoapFault
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
   public final void appelWsOpConsultationSoapFault(String urlServiceWeb,
         ConsultationFormulaire formulaire, String ficVi,
         String idSoapFaultAttendu, final Object[] argsMsgSoapFault) {

      // Création de l'objet qui implémente l'interface WsTestListener
      // et qui s'attend à recevoir une certaine SoapFault
      SoapFault faultAttendue = refSoapFault.findSoapFault(idSoapFaultAttendu);
      WsTestListener testAuth = new WsTestListenerImplSoapFault(faultAttendue,
            argsMsgSoapFault);

      // Appel de la méthode "générique" de test
      appelWsOpConsultation(urlServiceWeb, ficVi, formulaire, testAuth);

   }

   private void wsVerifieRetour(ConsultationFormulaire formulaire,
         ConsultationResponse response, String sha1attendu) {

      // Initialise
      ResultatTest resultatTest = formulaire.getResultats();
      ResultatTestLog log = resultatTest.getLog();

      // Initialise un flag pour suivre l'état du test
      boolean testKo = false;

      // Récupère l'objet numérique dans le bon type, pour des besoins
      // techniques
      ObjetNumeriqueConsultationType objNum = response
            .getConsultationResponse().getObjetNumerique();

      // Fait le point sur ce qu'on a demandé par rapport à ce qu'on a récupéré
      boolean urlConsultDemandee = false;
      boolean urlConsultRecuperee = SaeServiceTypeUtils
            .isObjetNumeriqueUrlConsultDirecte(objNum);
      boolean contenuBase64recupere = SaeServiceTypeUtils
            .isObjetNumeriqueContenuBase64(objNum);

      // Vérification que ce qu'on a reçu correspond à ce qu'on a demandé
      boolean testKoTemp = wsIsRecuConformeAdemande(urlConsultDemandee,
            urlConsultRecuperee, contenuBase64recupere, log);
      if (testKoTemp) {
         testKo = true;
      }

      // Vérification du contenu (SHA-1) si fourni en paramètre + contenu base
      // 64
      if (sha1attendu != null) {
         testKoTemp = wsVerifSha1(urlConsultDemandee, contenuBase64recupere,
               sha1attendu, log, objNum);
         if (testKoTemp) {
            testKo = true;
         }
      }

      // Vérification des métadonnes
      String erreur = testsMetasService
            .verifieMetadonneesConsulteeParDefaut(response
                  .getConsultationResponse().getMetadonnees());
      if (StringUtils.isNotBlank(erreur)) {
         testKo = true;
         log.appendLogNewLine();
         log.appendLogLn(erreur);
      }

      // Etat du test
      if (testKo) {
         resultatTest.setStatus(TestStatusEnum.Echec);
      } else {
         resultatTest.setStatus(TestStatusEnum.AControler);
      }

   }

   private boolean wsIsRecuConformeAdemande(boolean urlConsultDemandee,
         boolean urlConsultRecuperee, boolean contenuBase64recupere,
         ResultatTestLog log) {

      boolean testKo = false;

      if (urlConsultDemandee) {

         // On a demandé une URL de consultation directe

         if (!urlConsultRecuperee) {
            // Pourtant, on ne l'a pas reçu
            testKo = true;
            log
                  .appendLogLn("On a demandé au service web de consultation une URL de consultation directe, hors on ne l'a pas reçu");
         }

         if (contenuBase64recupere) {
            // Pourtant, on a reçu un contenu en base64
            testKo = true;
            log
                  .appendLogLn("On a demandé au service web de consultation une URL de consultation directe, pourtant on a reçu un contenu en base64");
         }

      } else {

         // On n'a PAS demandé d'URL de consultation directe

         if (urlConsultRecuperee) {
            // Pourtant, on en a reçu une
            testKo = true;
            log
                  .appendLogLn("On n'a PAS demandé au service web de recevoir une URL de consultation directe, pourtant on en a reçu une");
         }

         if (!contenuBase64recupere) {
            // Pourtant, on a reçu un contenu en base64
            testKo = true;
            log
                  .appendLogLn("On n'a PAS demandé au service web de recevoir une URL de consultation directe, pourtant on n'a pas reçu de contenu en base64");
         }

      }

      return testKo;

   }

   private boolean wsVerifSha1(boolean urlConsultDemandee,
         boolean contenuBase64recupere, String sha1attendu,
         ResultatTestLog log, ObjetNumeriqueConsultationType objNum) {

      // Initialise le résultat
      boolean testKo = false;

      // Vérification du contenu (SHA-1) si fourni en paramètre + contenu base
      // 64
      if ((!urlConsultDemandee) && (contenuBase64recupere)
            && (StringUtils.isNotBlank(sha1attendu))) {

         // Récupère le contenu du document renvoyé dans la réponse de
         // consultation
         DataHandler contenu = objNum
               .getObjetNumeriqueConsultationTypeChoice_type0().getContenu();

         // Calcul du SHA-1
         String sha1obtenu = null;
         try {
            sha1obtenu = ChecksumUtils.sha1(contenu.getInputStream());
         } catch (IOException e) {
            throw new IntegrationRuntimeException(e);
         }

         // Comparaison des SHA-1
         if (!StringUtils.equalsIgnoreCase(sha1obtenu, sha1attendu)) {
            testKo = true;
            log
                  .appendLogLn("Le SHA-1 du contenu de l'archive renvoyée ("
                        + sha1obtenu
                        + ") n'est pas identique au SHA-1 du document que l'on attendait ("
                        + sha1attendu + ")");
         }

      }

      // Renvoie des résultat
      return testKo;

   }

   /**
    * Test d'appel à l'opération "consultation" du service web SaeService.<br>
    * On s'attend à récupérer une réponse correcte
    * 
    * @param urlServiceWeb
    *           l'URL du service web SaeService
    * @param formulaire
    *           le formulaire
    * @param sha1attendu
    *           le SHA-1 attendu, ou null si pas de SHA-1
    * @return la réponse de l'opération "consultation", ou null si une exception
    *         s'est produite
    */
   public final ConsultationResponse appelWsOpConsultationReponseCorrecteAttendue(
         String urlServiceWeb, ConsultationFormulaire formulaire,
         String sha1attendu) {

      // Création de l'objet qui implémente l'interface WsTestListener
      // et qui s'attend à recevoir une réponse
      WsTestListener testAvecReponse = new WsTestListenerImplReponseAttendue();

      // Appel de la méthode "générique" de test
      ConsultationResponse response = appelWsOpConsultation(urlServiceWeb,
            ViUtils.FIC_VI_OK, formulaire, testAvecReponse);

      // On vérifie le résultat obtenu (si le test n'a pas échoué dans les
      // étapes préalables)
      ResultatTest resultatTest = formulaire.getResultats();
      if (!TestStatusEnum.Echec.equals(resultatTest.getStatus())) {

         // Vérifie que l'on ait reçu une réponse
         if (response == null) {

            // Echec bizarre
            resultatTest.setStatus(TestStatusEnum.Echec);
            ResultatTestLog log = resultatTest.getLog();
            log
                  .appendLogLn("On s'attendait à une réponse sans erreur du service web, hors on a obtenu une réponse null");

         } else {

            // Vérification de la réponse par rapport aux paramètres envoyés au
            // service web
            wsVerifieRetour(formulaire, response, sha1attendu);

         }

      }

      // Renvoie la réponse du service web
      return response;

   }

}
