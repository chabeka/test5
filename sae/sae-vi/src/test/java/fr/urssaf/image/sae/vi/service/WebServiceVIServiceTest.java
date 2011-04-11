package fr.urssaf.image.sae.vi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URI;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import fr.urssaf.image.sae.saml.data.SamlAssertionData;
import fr.urssaf.image.sae.saml.service.SamlAssertionExtractionService;
import fr.urssaf.image.sae.saml.util.ConverterUtils;
import fr.urssaf.image.sae.vi.exception.VIFormatTechniqueException;
import fr.urssaf.image.sae.vi.exception.VISignatureException;
import fr.urssaf.image.sae.vi.exception.VIVerificationException;
import fr.urssaf.image.sae.vi.modele.VIContenuExtrait;
import fr.urssaf.image.sae.vi.modele.VISignVerifParams;
import fr.urssaf.image.sae.vi.testutils.TuUtils;
import fr.urssaf.image.sae.vi.util.XMLUtils;

@SuppressWarnings("PMD.MethodNamingConventions")
public class WebServiceVIServiceTest {

   private static final Logger LOG = Logger
         .getLogger(WebServiceVIServiceTest.class);

   private static WebServiceVIService service;

   private static SamlAssertionExtractionService extraction;

   private static final String ID_UTILISATEUR = "idUtilisateur_test";

   private static final String ISSUER = "issuer_test";

   private static final URI SERVICE_VISE = ConverterUtils
         .uri("http://sae.urssaf.fr");

   @BeforeClass
   public static void beforeClass() {

      service = new WebServiceVIService();
      extraction = new SamlAssertionExtractionService();

   }

   private KeyStore keystore;

   private String alias;


   @Before
   public void before() throws KeyStoreException, NoSuchAlgorithmException,
         CertificateException, IOException {

      keystore = KeyStoreFactory.createKeystore();
      alias = keystore.aliases().nextElement();
   }

   @Test
   public void creerVIpourServiceWeb_success_idNotEmpty() throws SAXException {

      assertCreerVIpourServiceWeb(ID_UTILISATEUR, ID_UTILISATEUR);
   }

   @Test
   public void creerVIpourServiceWeb_success_idEmpty() throws SAXException {

      assertCreerVIpourServiceWeb("NON_RENSEIGNE", null);

   }

   @SuppressWarnings("PMD.JUnitAssertionsShouldIncludeMessage")
   private void assertCreerVIpourServiceWeb(String idExpected, String idActual)
         throws SAXException {

      List<String> pagm = Arrays.asList("ROLE_USER", "ROLE_ADMIN");

      String password = "hiUnk6O3QnRN";

      Element assertion = service.creerVIpourServiceWeb(pagm, ISSUER, idActual,
            keystore, alias, password);

      LOG.debug("\n" + XMLUtils.print(assertion));

      SamlAssertionData data = extraction.extraitDonnees(assertion);

      assertNotNull(data.getAssertionParams().getCommonsParams().getId());
      assertNotNull(data.getAssertionParams().getCommonsParams()
            .getIssueInstant());
      assertEquals(ISSUER, data.getAssertionParams().getCommonsParams()
            .getIssuer());

      long diff = data.getAssertionParams().getCommonsParams()
            .getNotOnOrAfter().getTime()
            - data.getAssertionParams().getCommonsParams().getNotOnBefore()
                  .getTime();

      assertEquals(Long.valueOf(2 * 3600 * 1000), Long.valueOf(diff));
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

   @Test
   @SuppressWarnings("PMD.JUnitAssertionsShouldIncludeMessage")
   public void verifierVIdeServiceWeb_success() throws IOException,
         SAXException, VIVerificationException {

      Element identification = XMLUtils
            .parse("src/test/resources/webservice/vi_success.xml");

      VIContenuExtrait extrait = service.verifierVIdeServiceWeb(identification,
            SERVICE_VISE, ISSUER, TuUtils.buildSignVerifParamsOK());

      assertEquals(ID_UTILISATEUR, extrait.getIdUtilisateur());
      assertEquals("ROLE_USER,ROLE_ADMIN", StringUtils.join(extrait.getPagm(),
            ","));
      assertEquals("Portail Image", extrait.getCodeAppli());

   }

   @Test(expected = VIFormatTechniqueException.class)
   public void verifierVIdeServiceWeb_failure_format() throws IOException,
         SAXException, VIVerificationException {

      Element identification = XMLUtils
            .parse("src/test/resources/webservice/vi_failure_format.xml");

      service.verifierVIdeServiceWeb(identification, SERVICE_VISE, ISSUER,
            new VISignVerifParams());

   }

   @Test(expected = VISignatureException.class)
   public void verifierVIdeServiceWeb_failure_sign() throws IOException,
         SAXException, VIVerificationException {

      Element identification = XMLUtils
            .parse("src/test/resources/webservice/vi_failure_sign.xml");

      service.verifierVIdeServiceWeb(identification, SERVICE_VISE, ISSUER,
            new VISignVerifParams());

   }

}
