package fr.urssaf.image.sae.integration.ihmweb.service.tests.listeners.impl;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;

import fr.urssaf.image.sae.integration.ihmweb.formulaire.TestWsParentFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.modele.ResultatTest;
import fr.urssaf.image.sae.integration.ihmweb.modele.ResultatTestLog;
import fr.urssaf.image.sae.integration.ihmweb.modele.TestStatusEnum;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.security.LogInMessageHandler;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.security.VIHandler;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.service.SaeServiceTestService;
import fr.urssaf.image.sae.integration.ihmweb.service.tests.listeners.WsTestListener;
import fr.urssaf.image.sae.integration.ihmweb.utils.LogUtils;

/**
 * Comportement d'un test d'appel à une opération de service web, pour le cas où
 * on s'attend à obtenir une réponse (pas de SoapFault)
 */
public final class WsTestListenerImplReponseAttendue implements WsTestListener {

   @Override
   public void onSetStatusInitialResultatTest(ResultatTest resultatTest) {
      resultatTest.setStatus(TestStatusEnum.NonLance);
   }

   @Override
   public void onRetourWsSansErreur(ResultatTest resultatTest,
         ConfigurationContext configurationContext,
         TestWsParentFormulaire testWsParentFormulaire) {

      String messageIn = (String) configurationContext
            .getProperty(LogInMessageHandler.PROP_MESSAGE_IN);
      String messageOut = (String) configurationContext
            .getProperty(VIHandler.PROP_MESSAGE_OUT);

      testWsParentFormulaire.getSoapFormulaire().setMessageIn(messageIn);
      testWsParentFormulaire.getSoapFormulaire().setMessageOut(messageOut);

   }

   @Override
   public void onSoapFault(ResultatTest resultatTest, AxisFault faultObtenue,
         ConfigurationContext configurationContext,
         TestWsParentFormulaire testWsParentFormulaire) {

      // Le test est en échec
      resultatTest.setStatus(TestStatusEnum.Echec);

      // Log
      ResultatTestLog log = resultatTest.getLog();
      log
            .appendLogLn("On s'attendait à une réponse sans erreur du service web, hors on a obtenu une SoapFault :");
      log.appendLogNewLine();
      LogUtils.logSoapFault(log, faultObtenue);

      String messageIn = (String) configurationContext
            .getProperty(LogInMessageHandler.PROP_MESSAGE_IN);
      String messageOut = (String) configurationContext
            .getProperty(VIHandler.PROP_MESSAGE_OUT);

      testWsParentFormulaire.getSoapFormulaire().setMessageIn(messageIn);
      testWsParentFormulaire.getSoapFormulaire().setMessageOut(messageOut);

   }

   @Override
   public void onRemoteException(ResultatTest resultatTest,
         RemoteException exception, ConfigurationContext configurationContext,
         TestWsParentFormulaire testWsParentFormulaire) {

      // Le test est en échec
      // On met le statut du test à Echec, et on log l'exception
      SaeServiceTestService.exceptionNonPrevue(exception, resultatTest);

      String messageIn = (String) configurationContext
            .getProperty(LogInMessageHandler.PROP_MESSAGE_IN);
      String messageOut = (String) configurationContext
            .getProperty(VIHandler.PROP_MESSAGE_OUT);

      testWsParentFormulaire.getSoapFormulaire().setMessageIn(messageIn);
      testWsParentFormulaire.getSoapFormulaire().setMessageOut(messageOut);

   }

}
