package fr.urssaf.image.sae.webservices;

import static org.junit.Assert.fail;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.OperationClient;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.MessageContext;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.xml.security.Init;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import fr.urssaf.image.sae.webservices.util.SoapTestUtils;

@SuppressWarnings("PMD.MethodNamingConventions")
public class ArchivageUnitaireSoapTest {


   private static Configuration config;
   
   private MessageContext msgctx;
   
   private OperationClient opClient;
   
   private ServiceClient client;
   
   
   @BeforeClass
   public static void beforeClass() throws ConfigurationException, AxisFault {
      config = new PropertiesConfiguration("sae-webservices-test.properties");
      Init.init();
   }
   
   
   @Before
   public void before() throws AxisFault {

      client = new ServiceClient();
      Options options = new Options();
      options.setAction("archivageUnitaire");
      client.setTargetEPR(new EndpointReference(config
            .getString("urlServiceWeb")));

      msgctx = new MessageContext();
      opClient = client.createClient(ServiceClient.ANON_OUT_IN_OP);
      opClient.addMessageContext(msgctx);

   }
   
   @After
   public void after() throws AxisFault {
      client.cleanupTransport();
   }
   
   
   @Test
   public void archivageUnitaire_failure_wsse_securityTokenUnavailable() {

      try {

         SoapTestUtils.execute(
               "src/test/resources/soap/request/archivageUnitaire_SoapFault_wsse_SecurityTokenUnavailable.xml",
               msgctx,
               opClient);

         fail(SoapTestUtils.FAIL_MSG);
         
      } catch (AxisFault fault) {

         SoapTestUtils.assertAxisFault(
            fault,
            "La référence au jeton de sécurité est introuvable",
            SoapTestUtils.FAULT_CODE_STU,
            SoapTestUtils.WSSE_NAMESPACE, 
            SoapTestUtils.WSSE_PREFIX);
         
      }
   }
   
   
   @Test
   @Ignore("Test temporaire")
   public void archivageUnitaire_failure_sae_ServiceNonImplemente() {

      try {

         SoapTestUtils.execute(
               "src/test/resources/soap/request/archivageUnitaire_SoapFault_sae_ServiceNonImplemente.xml",
               msgctx,
               opClient);

         fail(SoapTestUtils.FAIL_MSG);
         
      } catch (AxisFault fault) {

         SoapTestUtils.assertAxisFault(
            fault,
            "Le service d'archivage unitaire n'est pas encore disponible",
            SoapTestUtils.FAULT_CODE_SNI, 
            SoapTestUtils.SAE_NAMESPACE, 
            SoapTestUtils.SAE_PREFIX);
         
      }
   }
   
}
