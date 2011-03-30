package fr.urssaf.image.sae.vi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.X509CRL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import fr.urssaf.image.sae.saml.data.SamlAssertionData;
import fr.urssaf.image.sae.saml.service.SamlAssertionExtractionService;
import fr.urssaf.image.sae.saml.util.ConverterUtils;
import fr.urssaf.image.sae.saml.util.XMLUtils;
import fr.urssaf.image.sae.vi.exception.VIAppliClientException;
import fr.urssaf.image.sae.vi.exception.VIFormatTechniqueException;
import fr.urssaf.image.sae.vi.exception.VIInvalideException;
import fr.urssaf.image.sae.vi.exception.VINivAuthException;
import fr.urssaf.image.sae.vi.exception.VIPagmIncorrectException;
import fr.urssaf.image.sae.vi.exception.VIServiceIncorrectException;
import fr.urssaf.image.sae.vi.exception.VISignatureException;
import fr.urssaf.image.sae.vi.exception.VIVerificationException;
import fr.urssaf.image.sae.vi.modele.VIContenuExtrait;

public class WebServiceVIServiceTest {

   private static final Logger LOG = Logger
         .getLogger(WebServiceVIServiceTest.class);

   private static WebServiceVIService service;

   private static SamlAssertionExtractionService extraction;

   private static final String FAIL_MESSAGE = "le test doit échouer";

   private static final String ID_UTILISATEUR = "idUtilisateur_test";

   private static final String ISSUER = "issuer_test";

   private static final URI SERVICE_VISE = ConverterUtils
         .uri("http://sae.urssaf.fr");

   private static List<X509CRL> crl = new ArrayList<X509CRL>();

   private static Date system_date;

   @BeforeClass
   public static void beforeClass() {

      service = new WebServiceVIService();
      extraction = new SamlAssertionExtractionService();

      try {
         crl.add(CRLFactory
               .createCRL("src/test/resources/CRL/Pseudo_ACOSS.crl"));
      } catch (CRLException e) {
         throw new IllegalStateException(e);
      } catch (IOException e) {
         throw new IllegalStateException(e);
      }

      try {
         system_date = DateUtils.parseDate("12/12/1999 01:00:00",
               new String[] { "dd/MM/yyyy HH:mm:ss" });
      } catch (ParseException e) {
         throw new IllegalStateException(e);
      }
   }

   private KeyStore keystore;

   private String alias;

   private String password;

   @Before
   public void before() throws KeyStoreException, NoSuchAlgorithmException,
         CertificateException, IOException {

      keystore = KeyStoreFactory.createKeystore();
      alias = keystore.aliases().nextElement();
      password = "hiUnk6O3QnRN";
   }

   @Test
   public void creerVIpourServiceWeb_success_idNotEmpty() throws SAXException {

      assertCreerVIpourServiceWeb(ID_UTILISATEUR, ID_UTILISATEUR);
   }

   @Test
   public void creerVIpourServiceWeb_success_idEmpty() throws SAXException {

      assertCreerVIpourServiceWeb("NON_RENSEIGNE", null);

   }

   private void assertCreerVIpourServiceWeb(String idExpected, String idActual)
         throws SAXException {

      List<String> pagm = Arrays.asList("ROLE_USER", "ROLE_ADMIN");

      String password = "hiUnk6O3QnRN";

      String assertion = service.creerVIpourServiceWeb(pagm, ISSUER, idActual,
            keystore, alias, password);

      display(assertion);

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

   private void display(String assertion) throws SAXException {

      Element element = XMLUtils.parse(assertion);
      Transformer transformer = XMLUtils.initTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty(
            "{http://xml.apache.org/xslt}indent-amount", "4");

      LOG.debug("\n" + assertion);
      LOG.debug("\n" + XMLUtils.print(element, "UTF-8", transformer));
   }

   private String getVecteurIdentification(String identification)
         throws IOException {

      File file = new File(identification);
      return FileUtils.readFileToString(file, "UTF-8");
   }

   @Test
   public void verifierVIdeServiceWeb_success() throws IOException,
         VIFormatTechniqueException, VISignatureException, VIInvalideException,
         VIAppliClientException, VINivAuthException, VIPagmIncorrectException,
         VIServiceIncorrectException {

      String identification = getVecteurIdentification("src/test/resources/webservice/vi_success.xml");

      VIContenuExtrait extrait = service.verifierVIdeServiceWeb(identification,
            SERVICE_VISE, ISSUER, keystore, alias, password, crl);

      assertEquals(ID_UTILISATEUR, extrait.getIdUtilisateur());
      assertEquals("ROLE_USER,ROLE_ADMIN", StringUtils.join(extrait.getPagm(),
            ","));

   }

   @Test
   public void verifierVIdeServiceWeb_failure_format() throws IOException,
         VISignatureException, VIInvalideException, VIAppliClientException,
         VINivAuthException, VIPagmIncorrectException,
         VIServiceIncorrectException {

      String identification = getVecteurIdentification("src/test/resources/webservice/vi_failure_format.xml");

      try {
         service.verifierVIdeServiceWeb(identification, SERVICE_VISE, ISSUER,
               keystore, alias, password, crl);
         fail(FAIL_MESSAGE);
      } catch (VIFormatTechniqueException e) {

         assertVIVerificationException("wsse:InvalidSecurityToken",
               "Le jeton de sécurité fourni est invalide", e);
      }

   }

   @Test
   public void verifierVIdeServiceWeb_failure_sign() throws IOException,
         VIFormatTechniqueException, VIInvalideException,
         VIAppliClientException, VINivAuthException, VIPagmIncorrectException,
         VIServiceIncorrectException {

      String identification = getVecteurIdentification("src/test/resources/webservice/vi_failure_sign.xml");

      try {
         service.verifierVIdeServiceWeb(identification, SERVICE_VISE, ISSUER,
               keystore, alias, password, crl);
         fail(FAIL_MESSAGE);
      } catch (VISignatureException exception) {

         assertVIVerificationException("wsse:FailedCheck",
               "La signature ou le chiffrement n'est pas valide", exception);
      }

   }

   private void assertVIVerificationException(String faultCode,
         String faultMessage, VIVerificationException exception) {

      assertEquals(faultCode, exception.getSoapFaultCode());
      assertEquals(faultMessage, exception.getSoapFaultMessage());
   }

   @Test
   public void verifierVIdeServiceWeb_failure_datebefore()
         throws VIAppliClientException, VINivAuthException,
         VIPagmIncorrectException, IOException, VIServiceIncorrectException {

      String identification = getVecteurIdentification("src/test/resources/webservice/vi_failure_datebefore.xml");
      SamlAssertionData data = extraction.extraitDonnees(identification);

      try {
         service.validate(data, SERVICE_VISE, ISSUER, system_date);
         fail(FAIL_MESSAGE);
      } catch (VIInvalideException exception) {
         assertEquals(
               "L'assertion n'est pas encore valable: elle ne sera active qu'à partir de 31/12/1999 02:00:00 alors que nous sommes le 12/12/1999 01:00:00",
               exception.getMessage());

         assertVIVerificationException("vi:InvalidVI", "Le VI est invalide",
               exception);
      }

   }

   @Test
   public void verifierVIdeServiceWeb_failure_dateafter()
         throws VIAppliClientException, VINivAuthException,
         VIPagmIncorrectException, IOException, VIServiceIncorrectException {

      String identification = getVecteurIdentification("src/test/resources/webservice/vi_failure_dateafter.xml");
      SamlAssertionData data = extraction.extraitDonnees(identification);

      try {
         service.validate(data, SERVICE_VISE, ISSUER, system_date);
         fail(FAIL_MESSAGE);
      } catch (VIInvalideException exception) {
         assertEquals(
               "L'assertion a expirée : elle n'était valable que jusqu’au 01/12/1999 02:00:00, hors nous sommes le 12/12/1999 01:00:00",
               exception.getMessage());

         assertVIVerificationException("vi:InvalidVI", "Le VI est invalide",
               exception);
      }

   }

   @Test
   public void verifierVIdeServiceWeb_failure_serviceVise()
         throws VIAppliClientException, VINivAuthException,
         VIPagmIncorrectException, IOException, VIInvalideException {

      String identification = getVecteurIdentification("src/test/resources/webservice/vi_failure_servicevise.xml");
      SamlAssertionData data = extraction.extraitDonnees(identification);

      try {
         service.validate(data, SERVICE_VISE, ISSUER, system_date);
         fail(FAIL_MESSAGE);
      } catch (VIServiceIncorrectException exception) {
         assertEquals(
               "Le service visé '"
                     + SERVICE_VISE
                     + "' ne correspond pas à celui indiqué dans le VI 'http://service_test.fr'",
               exception.getMessage());

         assertVIVerificationException("vi:InvalidService",
               "Le service visé par le VI n'existe pas ou est invalide",
               exception);
      }

   }

   @Test
   public void verifierVIdeServiceWeb_failure_idapplication()
         throws VINivAuthException, VIPagmIncorrectException, IOException,
         VIInvalideException, VIServiceIncorrectException {

      String identification = getVecteurIdentification("src/test/resources/webservice/vi_failure_idapplication.xml");
      SamlAssertionData data = extraction.extraitDonnees(identification);

      try {
         service.validate(data, SERVICE_VISE, ISSUER, system_date);
         fail(FAIL_MESSAGE);
      } catch (VIAppliClientException exception) {
         assertEquals(
               "L'identifiant de l'organisme client présent dans le VI (service_failure) est invalide ou inconnu",
               exception.getMessage());

         assertVIVerificationException(
               "vi:InvalidIssuer",
               "L'identifiant de l'organisme client présent dans le VI est invalide ou inconnu",
               exception);
      }

   }

   @Test
   public void verifierVIdeServiceWeb_failure_methodauth()
         throws VIPagmIncorrectException, IOException, VIInvalideException,
         VIAppliClientException, VIServiceIncorrectException {

      String identification = getVecteurIdentification("src/test/resources/webservice/vi_failure_methodauthn.xml");
      SamlAssertionData data = extraction.extraitDonnees(identification);

      try {
         service.validate(data, SERVICE_VISE, ISSUER, system_date);
         fail(FAIL_MESSAGE);
      } catch (VINivAuthException exception) {
         assertEquals(
               "Le niveau d'authentification 'method_failure' est incorrect",
               exception.getMessage());

         assertVIVerificationException(
               "vi:InvalidAuthLevel",
               "Le niveau d'authentification initial n'est pas conforme au contrat d'interopérabilité",
               exception);
      }

   }

   @Test
   public void verifierVIdeServiceWeb_failure_pagm() throws IOException,
         VIInvalideException, VIAppliClientException, VINivAuthException,
         VIServiceIncorrectException {

      String identification = getVecteurIdentification("src/test/resources/webservice/vi_failure_pagm.xml");
      SamlAssertionData data = extraction.extraitDonnees(identification);

      try {
         service.validate(data, SERVICE_VISE, ISSUER, system_date);
         fail(FAIL_MESSAGE);
      } catch (VIPagmIncorrectException exception) {
         assertEquals("Le ou les PAGM présents dans le VI sont invalides",
               exception.getMessage());

         assertVIVerificationException("vi:InvalidPagm",
               "Le ou les PAGM présents dans le VI sont invalides", exception);
      }

   }

}
