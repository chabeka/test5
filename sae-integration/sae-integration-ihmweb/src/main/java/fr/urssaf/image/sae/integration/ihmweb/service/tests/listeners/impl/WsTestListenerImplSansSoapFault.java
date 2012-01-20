package fr.urssaf.image.sae.integration.ihmweb.service.tests.listeners.impl;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import fr.urssaf.image.sae.integration.ihmweb.modele.ResultatTest;
import fr.urssaf.image.sae.integration.ihmweb.modele.ResultatTestLog;
import fr.urssaf.image.sae.integration.ihmweb.modele.TestStatusEnum;
import fr.urssaf.image.sae.integration.ihmweb.service.tests.listeners.WsTestListener;
import fr.urssaf.image.sae.integration.ihmweb.utils.LogUtils;

/**
 * Comportement d'un test d'appel à une opération de service web, pour le cas où
 * on ne s'attend pas à un comportement particulier
 */
public final class WsTestListenerImplSansSoapFault implements WsTestListener {

   @Override
   public void onSetStatusInitialResultatTest(ResultatTest resultatTest) {
      resultatTest.setStatus(TestStatusEnum.SansStatus);
   }

   @Override
   public void onRetourWsSansErreur(ResultatTest resultatTest) {
      resultatTest.setStatus(TestStatusEnum.Succes);
   }

   @Override
   public void onSoapFault(ResultatTest resultatTest, AxisFault faultObtenue) {

      // On loggue simplement la SoapFault
      ResultatTestLog log = resultatTest.getLog();
      log.appendLogLn("On a obtenu une SoapFault :");
      log.appendLogNewLine();
      LogUtils.logSoapFault(log, faultObtenue);
      resultatTest.setStatus(TestStatusEnum.Echec);

   }

   @Override
   public void onRemoteException(ResultatTest resultatTest,
         RemoteException exception) {

      // On loggue simplement l'exception
      ResultatTestLog log = resultatTest.getLog();
      log.appendLogLn("Une exception non SoapFault a été levée :");
      log.appendLogNewLine();
      log.appendLogLn(exception.toString());
      resultatTest.setStatus(TestStatusEnum.Echec);
   }

}
