package fr.urssaf.image.sae.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.axiom.soap.SOAPEnvelope;
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
import org.junit.Test;
import org.w3c.dom.Document;

import fr.urssaf.image.sae.webservices.util.AxiomUtils;

/**
 * Classe tests unitaires pour tester les différents SoapFault<br>
 * Les message soap de test se trouvent dans le répertoire
 * <code>src/test/resources/soap/request</code>
 * 
 * 
 */
@SuppressWarnings( { "PMD.MethodNamingConventions", "PMD.TooManyMethods" })
public class PingSecureSoapTest {

   private static final String FAIL_MSG = "le test doit échouer";

   private static final String VI_PREFIX = "vi";

   private static final String VI_NAMESPACE = "urn:iops:vi:faultcodes";

   private static final String SAE_PREFIX = "sae";

   private static final String SAE_NAMESPACE = "urn:sae:faultcodes";

   private static final String WSSE_PREFIX = "wsse";

   private static final String WSSE_NAMESPACE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";

   private static Configuration config;

   @BeforeClass
   public static void beforeClass() throws ConfigurationException, AxisFault {

      config = new PropertiesConfiguration("sae-webservices-test.properties");
      Init.init();
   }

   private MessageContext msgctx;

   private OperationClient opClient;

   private ServiceClient client;

   @Before
   public void before() throws AxisFault {

      client = new ServiceClient();
      Options options = new Options();
      options.setAction("PingSecure");
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

   private void execute(String soapFile) throws AxisFault {

      SOAPEnvelope soapEnvelope = AxiomUtils.parse(soapFile);

      msgctx.setEnvelope(soapEnvelope);
      opClient.execute(true);

   }

   @Test
   public void pingSecure_success() throws AxisFault {

      this.execute("src/test/resources/soap/request/pingSecure_ok.xml");

      Document response = AxiomUtils.loadDocumentResponse(client);

      assertEquals("test sur le message retour du ping",
            "Les services du SAE sécurisés par authentification sont en ligne",
            response.getElementsByTagNameNS("http://www.cirtil.fr/saeService",
                  "pingString").item(0).getTextContent());

   }

   private void assertAxisFault(AxisFault fault, String message,
         String localPart, String namespaceURI, String prefix) {

      assertEquals("le message du soapFault est incorrect", message, fault
            .getMessage());
      assertEquals("le code du soapFault est incorrect", localPart, fault
            .getFaultCode().getLocalPart());
      assertEquals("le namespaceURI du soapFault", namespaceURI, fault
            .getFaultCode().getNamespaceURI());
      assertEquals("le prefix du soapFault", prefix, fault.getFaultCode()
            .getPrefix());
   }

   @Test
   public void pingSecure_failure_sae_droitsInsuffisants() {

      try {

         this
               .execute("src/test/resources/soap/request/pingSecure_SoapFault_sae_DroitsInsuffisants.xml");

         fail(FAIL_MSG);
      } catch (AxisFault fault) {

         this
               .assertAxisFault(
                     fault,
                     "Les droits présents dans le vecteur d'identification sont insuffisants pour effectuer l'action demandée",
                     "DroitsInsuffisants", SAE_NAMESPACE, SAE_PREFIX);

      }
   }

   @Test
   public void pingSecure_failure_vi_invalidAuthLevel() {

      try {

         this
               .execute("src/test/resources/soap/request/pingSecure_SoapFault_vi_InvalidAuthLevel.xml");

         fail(FAIL_MSG);
      } catch (AxisFault fault) {

         this
               .assertAxisFault(
                     fault,
                     "Le niveau d'authentification initial n'est pas conforme au contrat d'interopérabilité",
                     "InvalidAuthLevel", VI_NAMESPACE, VI_PREFIX);
      }
   }

   @Test
   public void pingSecure_failure_vi_invalidPagm() {

      try {

         this
               .execute("src/test/resources/soap/request/pingSecure_SoapFault_vi_InvalidPagm.xml");

         fail(FAIL_MSG);
      } catch (AxisFault fault) {

         this
               .assertAxisFault(
                     fault,
                     "Les droits présents dans le vecteur d'identification sont insuffisants pour effectuer l'action demandée",
                     "DroitsInsuffisants", SAE_NAMESPACE, SAE_PREFIX);
      }
   }

   @Test
   public void pingSecure_failure_vi_invalidService() {

      try {

         this
               .execute("src/test/resources/soap/request/pingSecure_SoapFault_vi_InvalidService.xml");

         fail(FAIL_MSG);
      } catch (AxisFault fault) {

         this.assertAxisFault(fault,
               "Le service visé par le VI n'existe pas ou est invalide",
               "InvalidService", VI_NAMESPACE, VI_PREFIX);
      }
   }

   @Test
   public void pingSecure_failure_vi_invalidVI() {

      try {

         this
               .execute("src/test/resources/soap/request/pingSecure_SoapFault_vi_InvalidVI.xml");

         fail(FAIL_MSG);
      } catch (AxisFault fault) {

         this.assertAxisFault(fault, "Le VI est invalide", "InvalidVI",
               VI_NAMESPACE, VI_PREFIX);
      }
   }

   @Test
   public void pingSecure_failure_wsse_failedCheck() {

      try {

         this
               .execute("src/test/resources/soap/request/pingSecure_SoapFault_wsse_FailedCheck.xml");

         fail(FAIL_MSG);
      } catch (AxisFault fault) {

         this.assertAxisFault(fault,
               "La signature ou le chiffrement n'est pas valide",
               "FailedCheck", WSSE_NAMESPACE, WSSE_PREFIX);
      }
   }

   @Test
   public void pingSecure_failure_wsse_invalidSecurityToken() {

      try {

         this
               .execute("src/test/resources/soap/request/pingSecure_SoapFault_wsse_InvalidSecurityToken.xml");

         fail(FAIL_MSG);
      } catch (AxisFault fault) {

         this.assertAxisFault(fault,
               "Le jeton de sécurité fourni est invalide",
               "InvalidSecurityToken", WSSE_NAMESPACE, WSSE_PREFIX);
      }
   }

   @Test
   public void pingSecure_failure_wsse_securityTokenUnavailable() {

      try {

         this
               .execute("src/test/resources/soap/request/pingSecure_SoapFault_wsse_SecurityTokenUnavailable.xml");

         fail(FAIL_MSG);
      } catch (AxisFault fault) {

         this.assertAxisFault(fault,
               "La référence au jeton de sécurité est introuvable",
               "SecurityTokenUnavailable", WSSE_NAMESPACE, WSSE_PREFIX);
      }
   }

}
