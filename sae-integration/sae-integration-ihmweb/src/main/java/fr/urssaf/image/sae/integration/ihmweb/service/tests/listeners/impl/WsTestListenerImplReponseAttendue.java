package fr.urssaf.image.sae.integration.ihmweb.service.tests.listeners.impl;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import fr.urssaf.image.sae.integration.ihmweb.modele.ResultatTest;
import fr.urssaf.image.sae.integration.ihmweb.modele.ResultatTestLog;
import fr.urssaf.image.sae.integration.ihmweb.modele.TestStatusEnum;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.service.SaeServiceTestService;
import fr.urssaf.image.sae.integration.ihmweb.service.tests.listeners.WsTestListener;
import fr.urssaf.image.sae.integration.ihmweb.utils.LogUtils;


/**
 * Comportement d'un test d'appel à une opération de service web, 
 * pour le cas où on s'attend à obtenir une réponse (pas de SoapFault)
 */
public final class WsTestListenerImplReponseAttendue implements WsTestListener {

   @Override
   public void onSetStatusInitialResultatTest(ResultatTest resultatTest) {
      resultatTest.setStatus(TestStatusEnum.NonLance);
   }
   
   
   @Override
   public void onRetourWsSansErreur(ResultatTest resultatTest) {

      // Rien à faire ici
      
   }
   
   
   @Override
   public void onSoapFault(
         ResultatTest resultatTest, 
         AxisFault faultObtenue) {
      
      // Le test est en échec
      resultatTest.setStatus(TestStatusEnum.Echec);
      
      // Log
      ResultatTestLog log = resultatTest.getLog();
      log.appendLogLn("On s'attendait à une réponse sans erreur du service web, hors on a obtenu une SoapFault :");
      log.appendLogNewLine();
      LogUtils.logSoapFault(log, faultObtenue);
            
   }
   
   
   @Override
   public void onRemoteException(ResultatTest resultatTest, RemoteException exception) {
      
      // Le test est en échec
      // On met le statut du test à Echec, et on log l'exception
      SaeServiceTestService.exceptionNonPrevue(exception,resultatTest);
      
   }
   
}
