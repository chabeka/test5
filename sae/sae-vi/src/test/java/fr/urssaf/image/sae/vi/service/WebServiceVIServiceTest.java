package fr.urssaf.image.sae.vi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import fr.urssaf.image.sae.saml.data.SamlAssertionData;
import fr.urssaf.image.sae.saml.service.SamlAssertionExtractionService;
import fr.urssaf.image.sae.saml.util.XMLUtils;

public class WebServiceVIServiceTest {

   private static final Logger LOG = Logger
         .getLogger(WebServiceVIServiceTest.class);

   private static WebServiceVIService service;

   private static SamlAssertionExtractionService extraction;

   @BeforeClass
   public static void beforeClass() {

      service = new WebServiceVIService();
      extraction = new SamlAssertionExtractionService();
   }

   @Test
   public void creerVIpourServiceWeb() throws KeyStoreException,
         NoSuchAlgorithmException, CertificateException, IOException,
         SAXException {

      creerVIpourServiceWeb("idUtilisateur_test", "idUtilisateur_test");
      creerVIpourServiceWeb("NON_RENSEIGNE", null);

   }

   private void creerVIpourServiceWeb(String idExpected, String idActual)
         throws KeyStoreException, NoSuchAlgorithmException,
         CertificateException, IOException, SAXException {

      List<String> pagm = Arrays.asList("ROLE_USER", "ROLE_ADMIN");
      KeyStore keystore = KeyStoreFactory.createKeystore();
      String alias = keystore.aliases().nextElement();
      String issuer = "issuer_test";
      String password = "hiUnk6O3QnRN";

      String assertion = service.creerVIpourServiceWeb(pagm, issuer, idActual,
            keystore, alias, password);

      display(assertion);

      SamlAssertionData data = extraction.extraitDonnees(assertion);

      assertNotNull(data.getAssertionParams().getCommonsParams().getId());
      assertNotNull(data.getAssertionParams().getCommonsParams()
            .getIssueInstant());
      assertEquals(issuer, data.getAssertionParams().getCommonsParams()
            .getIssuer());

      long diff = data.getAssertionParams().getCommonsParams()
            .getNotOnOrAfter().getTime()
            - data.getAssertionParams().getCommonsParams().getNotOnBefore()
                  .getTime();

      assertEquals(new Long(2 * 3600 * 1000), new Long(diff));
      assertEquals("http://sae.urssaf.fr", data.getAssertionParams()
            .getCommonsParams().getAudience().toASCIIString());
      assertNotNull(data.getAssertionParams().getCommonsParams()
            .getAuthnInstant());
      assertEquals("urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified",
            data.getAssertionParams().getSubjectFormat2().toASCIIString());
      assertEquals(idExpected, data.getAssertionParams().getSubjectId2());
      assertEquals("urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified", data
            .getAssertionParams().getMethodAuthn2().toASCIIString());
      assertEquals("urn:URSSAF", data.getAssertionParams().getRecipient()
            .toASCIIString());

   }

   private void display(String assertion) throws SAXException {

      Element element = XMLUtils.parse(assertion);
      Transformer transformer = XMLUtils.initTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty(
            "{http://xml.apache.org/xslt}indent-amount", "4");

      LOG.debug("\n" + XMLUtils.print(element, "UTF-8", transformer));
   }
}
