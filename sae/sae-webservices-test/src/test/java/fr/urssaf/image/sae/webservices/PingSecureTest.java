package fr.urssaf.image.sae.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.context.SecurityContextHolder;

import fr.urssaf.image.sae.webservices.modele.SaeServiceStub;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.PingSecureRequest;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.PingSecureResponse;
import fr.urssaf.image.sae.webservices.util.AuthenticateUtils;

@SuppressWarnings("PMD.MethodNamingConventions")
public class PingSecureTest {

   private SaeServiceStub service;

   private static final Logger LOG = Logger.getLogger(PingSecureTest.class);

   private static final String SECURITY_PATH = "src/main/resources/META-INF";

   @Before
   public void before() throws AxisFault, ConfigurationException {

      Configuration config = new PropertiesConfiguration(
            "sae-webservices-test.properties");

      ConfigurationContext ctx = ConfigurationContextFactory
            .createConfigurationContextFromFileSystem(SECURITY_PATH,
                  SECURITY_PATH + "/axis2.xml");

      service = new SaeServiceStub(ctx, config.getString("urlServiceWeb"));
   }

   @After
   public void after() {

      SecurityContextHolder.getContext().setAuthentication(null);
   }

   
   private String getSoapFaultInfos(AxisFault fault) {
      StringBuilder messageFailure = new StringBuilder();
      messageFailure.append("SoapFault :");
      messageFailure.append(
            "\r\n" + 
            "FaultCode = " + 
            fault.getFaultCode().getPrefix() + 
            ":" + 
            fault.getFaultCode().getLocalPart());
      messageFailure.append(
            "\r\n" + 
            "FaultString = " + 
            fault.getMessage());
      return messageFailure.toString();
   }
   
   
   @Test
   public void pingSecureAvecViOk_success() throws RemoteException {

      try {
         
         AuthenticateUtils.authenticate("ROLE_TOUS");
   
         PingSecureRequest request = new PingSecureRequest();
   
         PingSecureResponse response = service.pingSecure(request);
   
         LOG.debug(response.getPingString());
   
         assertEquals("Test du ping securisé",
               "Les services du SAE sécurisés par authentification sont en ligne",
               response.getPingString());
      
      } catch(AxisFault fault) {
         fail(getSoapFaultInfos(fault));
      }
      
   }

   @Test
   public void pingSecureAvecViOk_failure() throws RemoteException {

      AuthenticateUtils.authenticate("OTHER_ROLE");

      PingSecureRequest request = new PingSecureRequest();

      try {
         service.pingSecure(request);
         fail("le test doit échouer");
      } catch (AxisFault fault) {
         LOG.debug(getSoapFaultInfos(fault));
         assertEquals(
               "Le message de la SoapFault n'est pas celui attendu",
               "Access is denied", 
               fault.getMessage());
      }

   }

   @Test
   public void pingSecureSansVI() throws RemoteException {

      PingSecureRequest request = new PingSecureRequest();

      try {
         service.pingSecure(request);
         fail("le test doit échouer");
      } catch (AxisFault fault) {
         LOG.debug(getSoapFaultInfos(fault));
         assertEquals(
               "Le message de la SoapFault n'est pas celui attendu",
               "La référence au jeton de sécurité est introuvable",
               fault.getMessage());

      }
   }
}
