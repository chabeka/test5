package fr.urssaf.image.sae.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.urssaf.image.sae.webservices.util.AxiomUtils;
import fr.urssaf.image.sae.webservices.util.SoapTestUtils;

@SuppressWarnings("PMD.MethodNamingConventions")
public class RechercheSoapTest {

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
      options.setAction("recherche");
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
   public void recherche_failure_wsse_securityTokenUnavailable() {

      try {

         SoapTestUtils
               .execute(
                     "src/test/resources/soap/request/recherche_SoapFault_wsse_SecurityTokenUnavailable.xml",
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

   // private static final String UUID_META = "UUID";

   private static final String CODE_RND_META = "CodeRND";

   private static final String COTISANT_META = "NumeroCotisant";

   private static final String SIRET_META = "Siret";

   private static final String COMPTE_META = "DenominationCompte";

   private static final String ORGANISME_META = "CodeOrganisme";

   @Test
   public void recherche_success() throws AxisFault {

      SoapTestUtils.execute(
            "src/test/resources/soap/request/recherche_success.xml", msgctx,
            opClient);

      Document response = AxiomUtils.loadDocumentResponse(client);

      NodeList resultats = (NodeList) response.getElementsByTagNameNS(
            NAMESPACE_URI, "resultats").item(0);

      assertEquals("nombre de resultats inattendu", 3, resultats.getLength());

      Map<String, String> metas1 = new HashMap<String, String>();
      // metas1.put(UUID_META, "110E8400-E29B-11D4-A716-446655440000");
      metas1.put(CODE_RND_META, "3.1.3.1.1");
      metas1.put(COTISANT_META, "704815");
      metas1.put(SIRET_META, "49980055500017");
      metas1.put(COMPTE_META, "SPOHN ERWAN MARIE MAX");
      metas1.put(ORGANISME_META, "UR030");

      assertResultat((NodeList) resultats.item(0),
            "110E8400-E29B-11D4-A716-446655440000", metas1);

      Map<String, String> metas2 = new HashMap<String, String>();
      // metas2.put(UUID_META, "510E8200-E29B-18C4-A716-446677440120");
      metas2.put(CODE_RND_META, "1.A.X.X.X");
      metas2.put(COTISANT_META, "723804");
      metas2.put(SIRET_META, "07413151710009");
      metas2.put(COMPTE_META, "CHEVENIER ANDRE");
      metas2.put(ORGANISME_META, "UR030");

      assertResultat((NodeList) resultats.item(1),
            "510E8200-E29B-18C4-A716-446677440120", metas2);

      Map<String, String> metas3 = new HashMap<String, String>();
      // metas3.put(UUID_META, "48758200-A29B-18C4-B616-455677840120");
      metas3.put(CODE_RND_META, "1.2.3.3.1");
      metas3.put(COTISANT_META, "719900");
      metas3.put(SIRET_META, "07412723410007");
      metas3.put(COMPTE_META, "COUTURIER GINETTE");
      metas3.put(ORGANISME_META, "UR030");

      assertResultat((NodeList) resultats.item(2),
            "48758200-A29B-18C4-B616-455677840120", metas3);

   }

   private static void assertResultat(NodeList resultat, String uuid,
         Map<String, String> metadonnees) {

      assertUniciteCodeMetadonnee(resultat, metadonnees.size(), uuid);

      assertEquals("nombre de resultats inattendu", uuid, resultat.item(0)
            .getTextContent());

      NodeList metas = (NodeList) resultat.item(1);

      // test sur le contenu des méta-données
      for (int index = 0; index < metadonnees.size(); index++) {

         assertMetadonnee((NodeList) metas.item(index), uuid, metadonnees);
      }

      // test sur le nombre de méta-données
      assertNull("nombre de metadonnees inattendu", metas.item(metadonnees
            .size()));

   }

   private static void assertUniciteCodeMetadonnee(NodeList resultat,
         int codeNumber, String uuid) {

      Set<String> codes = new HashSet<String>();

      NodeList metas = (NodeList) resultat.item(1);

      for (int index = 0; index < codeNumber; index++) {

         Node metadonnee = ((NodeList) metas.item(index)).item(0);

         assertEquals("mauvaise balise dans les métadonnées " + uuid, "code",
               metadonnee.getLocalName());

         codes.add(metadonnee.getTextContent());
      }

      assertEquals("les codes des métadonnées de '" + uuid
            + "' ne sont pas uniques entre eux", codeNumber, codes.size());

   }

   private static void assertMetadonnee(NodeList metadonnee, String uuid,
         Map<String, String> metadonnees) {

      assertEquals("mauvaise balise dans les métadonnées " + uuid, "code",
            metadonnee.item(0).getLocalName());
      assertEquals("mauvaise balise dans les métadonnées " + uuid, "valeur",
            metadonnee.item(1).getLocalName());

      String code = metadonnee.item(0).getTextContent();
      String valeur = metadonnee.item(1).getTextContent();

      assertTrue(
            "la métadonnée '" + code + "' n'est pas présente dans " + uuid,
            metadonnees.containsKey(code));
      assertEquals("la valeur de la métadonnée '" + code
            + "' est incorrecte dans " + uuid, metadonnees.get(code), valeur);

   }

}
