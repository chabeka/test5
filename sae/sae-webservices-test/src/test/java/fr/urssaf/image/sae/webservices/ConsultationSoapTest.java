package fr.urssaf.image.sae.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.axiom.util.base64.Base64Utils;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.OperationClient;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.MessageContext;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.xml.security.Init;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.urssaf.image.sae.webservices.util.AxiomUtils;
import fr.urssaf.image.sae.webservices.util.SoapTestUtils;

@SuppressWarnings("PMD.MethodNamingConventions")
public class ConsultationSoapTest {

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
      options.setAction("consultation");
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
   public void consultation_failure_wsse_securityTokenUnavailable() {

      try {

         SoapTestUtils
               .execute(
                     "src/test/resources/soap/request/consultation_SoapFault_wsse_SecurityTokenUnavailable.xml",
                     msgctx, opClient);

         fail(SoapTestUtils.FAIL_MSG);

      } catch (AxisFault fault) {

         SoapTestUtils.assertAxisFault(fault,
               "La référence au jeton de sécurité est introuvable",
               SoapTestUtils.FAULT_CODE_STU, SoapTestUtils.WSSE_NAMESPACE,
               SoapTestUtils.WSSE_PREFIX);

      }
   }

   private static final String NAMESPACE_URI = "http://www.cirtil.fr/saeService";

   @Test
   public void consultation_success() throws DOMException, IOException {

      SoapTestUtils.execute(
            "src/test/resources/soap/request/consultation_success.xml", msgctx,
            opClient);

      Document response = AxiomUtils.loadDocumentResponse(client);

      // test sur le contenu

      File expectedContent = new File(
            "src/test/resources/storage/attestation.pdf");

      assertEquals("le contenu n'est pas attendu en base64", Base64Utils
            .encode(FileUtils.readFileToByteArray(expectedContent)), response
            .getElementsByTagNameNS(NAMESPACE_URI, "objetNumerique").item(0)
            .getTextContent());

      // test sur les metadonnees

      NodeList metadonnees = (NodeList) response.getElementsByTagNameNS(
            NAMESPACE_URI, "metadonnees").item(0);

      assertNotNull("la liste des metadonnées doit être renseignée",
            metadonnees);
      assertEquals("nombre de metadatas inattendu", 10, metadonnees.getLength());

      Map<String, Object> expectedMetadatas = new HashMap<String, Object>();

      expectedMetadatas.put("ASO", "GED");
      expectedMetadatas.put("ACT", "2");
      expectedMetadatas.put("OTY", "autonome");
      expectedMetadatas.put("CSE", "CS1");
      expectedMetadatas.put("DCO", "12");
      expectedMetadatas.put("DFC", "2015/12/01");
      expectedMetadatas.put("COP", "UR030");
      expectedMetadatas.put("DOM", "2");
      expectedMetadatas.put("RND", "2.2.3.2.2");
      expectedMetadatas.put("FFI", "fmt/18");

      assertMetadata(metadonnees.item(0), expectedMetadatas);
      assertMetadata(metadonnees.item(1), expectedMetadatas);
      assertMetadata(metadonnees.item(2), expectedMetadatas);
      assertMetadata(metadonnees.item(3), expectedMetadatas);
      assertMetadata(metadonnees.item(4), expectedMetadatas);
      assertMetadata(metadonnees.item(5), expectedMetadatas);
      assertMetadata(metadonnees.item(6), expectedMetadatas);
      assertMetadata(metadonnees.item(7), expectedMetadatas);
      assertMetadata(metadonnees.item(8), expectedMetadatas);
      assertMetadata(metadonnees.item(9), expectedMetadatas);

   }

   private static void assertMetadata(Node metadonnee,
         Map<String, Object> expectedMetadatas) {

      String code = metadonnee.getChildNodes().item(0).getTextContent();
      String value = metadonnee.getChildNodes().item(1).getTextContent();

      assertTrue("la metadonnée '" + code + "' est inattendue",
            expectedMetadatas.containsKey(code));

      assertEquals("la valeur de la metadonnée est inattendue",
            expectedMetadatas.get(code), value);

      expectedMetadatas.remove(code);
   }

}
