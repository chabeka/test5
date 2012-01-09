package fr.urssaf.image.sae.integration.ihmweb.service.tests.listeners.impl;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.commons.lang.ArrayUtils;

import fr.urssaf.image.sae.integration.ihmweb.modele.ResultatTest;
import fr.urssaf.image.sae.integration.ihmweb.modele.ResultatTestLog;
import fr.urssaf.image.sae.integration.ihmweb.modele.SoapFault;
import fr.urssaf.image.sae.integration.ihmweb.modele.TestStatusEnum;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.service.SaeServiceTestService;
import fr.urssaf.image.sae.integration.ihmweb.service.tests.listeners.WsTestListener;

/**
 * Comportement d'un test d'appel à une opération de service web, 
 * pour le cas où on s'attend à obtenir une SoapFault
 */
public final class WsTestListenerImplSoapFault implements WsTestListener {

   
   private final SoapFault faultAttendue;
   private final Object[] argsMsgSoapFault;
   
   
   /**
    * Constructeur
    * 
    * @param faultAttendue la SoapFault attendu
    * @param argsMsgSoapFault les arguments pour le String.format du message de la SoapFault attendue
    */
   @SuppressWarnings("PMD.ArrayIsStoredDirectly")
   public WsTestListenerImplSoapFault(
         SoapFault faultAttendue,
         final Object[] argsMsgSoapFault) {
      
      this.faultAttendue = faultAttendue;
      
      if (ArrayUtils.isNotEmpty(argsMsgSoapFault)) {
         this.argsMsgSoapFault = ArrayUtils.clone(argsMsgSoapFault);
      } else {
         this.argsMsgSoapFault = null; // NOPMD (le champ privé argsMsgSoapFault est final, il faut
                                       // l'assigner dans le constructeur
      }
      
   }
   
      
   
   @Override
   public void onSetStatusInitialResultatTest(ResultatTest resultatTest) {
      resultatTest.setStatus(TestStatusEnum.NonLance);
   }
   
   
   @Override
   public void onRetourWsSansErreur(ResultatTest resultatTest) {

      // Le test est en échec
      resultatTest.setStatus(TestStatusEnum.Echec);
      
      // Log
      ResultatTestLog log = resultatTest.getLog();
      log.appendLog("Echec : On aurait dû recevoir une SoapFault ");
      log.appendLog(this.faultAttendue.codeToString());
      log.appendLog(" avec le message \"");
      log.appendLog(String.format(this.faultAttendue.getMessage(),this.argsMsgSoapFault));
      log.appendLogLn("\".");
      
   }
   
   
   @Override
   public void onSoapFault(
         ResultatTest resultatTest, 
         AxisFault faultObtenue) {
      
      // Vérification que la SoapFault obtenue est bien celle attendue
      SaeServiceTestService.checkSoapFault(
            faultObtenue,
            resultatTest,
            this.faultAttendue,
            this.argsMsgSoapFault);
      
   }
   
   
   @Override
   public void onRemoteException(ResultatTest resultatTest, RemoteException exception) {
      
      // Le test a échoué
      // On met le statut du test à Echec, et on log l'exception
      SaeServiceTestService.exceptionNonPrevue(exception,resultatTest);
      
   }
   
}
