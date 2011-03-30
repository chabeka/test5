package fr.urssaf.image.sae.vi.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URI;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CRLException;
import java.security.cert.X509CRL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.urssaf.image.sae.saml.util.ConverterUtils;
import fr.urssaf.image.sae.vi.exception.VIAppliClientException;
import fr.urssaf.image.sae.vi.exception.VIFormatTechniqueException;
import fr.urssaf.image.sae.vi.exception.VIInvalideException;
import fr.urssaf.image.sae.vi.exception.VINivAuthException;
import fr.urssaf.image.sae.vi.exception.VIPagmIncorrectException;
import fr.urssaf.image.sae.vi.exception.VIServiceIncorrectException;
import fr.urssaf.image.sae.vi.exception.VISignatureException;
import fr.urssaf.image.sae.vi.service.CRLFactory;
import fr.urssaf.image.sae.vi.service.WebServiceVIService;

public class WebServiceVIServiceValidateTest {
   
   private static final String FAIL_MESSAGE = "le test doit échouer";

   private static final String ISSUER = "issuer";

   private static final String ID_UTILISATEUR = "id_utilisateur";

   private static final String ID_APPLI = "id_appli";

   private static final String IDENTIFICATION = "identification";

   private static final String ALIAS = "alias";

   private static final String PASSWORD = "password";

   private static final URI SERVICE_VISE = ConverterUtils
         .uri("http://sae.urssaf.fr");

   private static List<X509CRL> crl = new ArrayList<X509CRL>();

   private static WebServiceVIService service;

   @BeforeClass
   public static void beforeClass() {

      service = EasyMock.createMock(WebServiceVIService.class);

      try {
         crl.add(CRLFactory
               .createCRL("src/test/resources/CRL/Pseudo_ACOSS.crl"));
      } catch (CRLException e) {
         throw new IllegalStateException(e);
      } catch (IOException e) {
         throw new IllegalStateException(e);
      }
   }

   private KeyStore keystore;

   private List<String> pagm;

   @Before
   public void before() throws KeyStoreException {

      keystore = KeyStore.getInstance(KeyStore.getDefaultType());
      pagm = Arrays.asList("PAGM_1", "", "   ", "PAGM_2", null);
   }

   @Test
   public void creerVIpourServiceWebFailure_pagm() {

      assertCreerVIpourServiceWebFailure_pagm(null);
      assertCreerVIpourServiceWebFailure_pagm(Arrays.asList("", " ", null));

   }

   private void assertCreerVIpourServiceWebFailure_pagm(List<String> pagm) {

      try {
         service.creerVIpourServiceWeb(pagm, ISSUER, ID_UTILISATEUR, keystore,
               ALIAS, PASSWORD);
         fail(FAIL_MESSAGE);
      } catch (IllegalArgumentException e) {

         assertEquals("Il faut spécifier au moins un PAGM", e.getMessage());
      }

   }

   @Test
   public void creerVIpourServiceWebFailure_issuer() {

      assertCreerVIpourServiceWebFailure("issuer", pagm, null, ID_UTILISATEUR,
            keystore, ALIAS, PASSWORD);

   }

   @Test
   public void creerVIpourServiceWebFailure_keystore() {

      assertCreerVIpourServiceWebFailure("keystore", pagm, ISSUER, ID_UTILISATEUR,
            null, ALIAS, PASSWORD);

   }

   @Test
   public void creerVIpourServiceWebFailure_alias() {

      assertCreerVIpourServiceWebFailure("alias", pagm, ISSUER, ID_UTILISATEUR,
            keystore, null, PASSWORD);

   }

   @Test
   public void creerVIpourServiceWebFailure_password() {

      assertCreerVIpourServiceWebFailure("password", pagm, ISSUER, ID_UTILISATEUR,
            keystore, ALIAS, null);

   }

   private void assertCreerVIpourServiceWebFailure(String param, List<String> pagm,
         String issuer, String idUtilisateur, KeyStore keystore, String alias,
         String password) {

      try {
         service.creerVIpourServiceWeb(pagm, issuer, idUtilisateur, keystore,
               alias, password);
         fail(FAIL_MESSAGE);
      } catch (IllegalArgumentException e) {

         assertEquals("Le paramètre [" + param
               + "] n'est pas renseigné alors qu'il est obligatoire", e
               .getMessage());
      }

   }

   @Test
   public void verifierVIdeServiceWebFailure_identification()
         throws VIFormatTechniqueException, VISignatureException,
         VIInvalideException, VIAppliClientException, VINivAuthException,
         VIPagmIncorrectException, VIServiceIncorrectException {

      assertVerifierVIdeServiceWeb("identification", null, SERVICE_VISE, ID_APPLI,
            keystore, ALIAS, PASSWORD, crl);

   }

   @Test
   public void verifierVIdeServiceWebFailure_serviceVise()
         throws VIFormatTechniqueException, VISignatureException,
         VIInvalideException, VIAppliClientException, VINivAuthException,
         VIPagmIncorrectException, VIServiceIncorrectException {

      assertVerifierVIdeServiceWeb("serviceVise", IDENTIFICATION, null, ID_APPLI,
            keystore, ALIAS, PASSWORD, crl);

   }

   @Test
   public void verifierVIdeServiceWebFailure_application()
         throws VIFormatTechniqueException, VISignatureException,
         VIInvalideException, VIAppliClientException, VINivAuthException,
         VIPagmIncorrectException, VIServiceIncorrectException {

      assertVerifierVIdeServiceWeb("idAppliClient", IDENTIFICATION, SERVICE_VISE,
            null, keystore, ALIAS, PASSWORD, crl);

   }

   @Test
   public void verifierVIdeServiceWebFailure_keystore()
         throws VIFormatTechniqueException, VISignatureException,
         VIInvalideException, VIAppliClientException, VINivAuthException,
         VIPagmIncorrectException, VIServiceIncorrectException {

      assertVerifierVIdeServiceWeb("keystore", IDENTIFICATION, SERVICE_VISE,
            ID_APPLI, null, ALIAS, PASSWORD, crl);

   }

   @Test
   public void verifierVIdeServiceWebFailure_alias()
         throws VIFormatTechniqueException, VISignatureException,
         VIInvalideException, VIAppliClientException, VINivAuthException,
         VIPagmIncorrectException, VIServiceIncorrectException {

      assertVerifierVIdeServiceWeb("alias", IDENTIFICATION, SERVICE_VISE, ID_APPLI,
            keystore, null, PASSWORD, crl);

   }

   @Test
   public void verifierVIdeServiceWebFailure_password()
         throws VIFormatTechniqueException, VISignatureException,
         VIInvalideException, VIAppliClientException, VINivAuthException,
         VIPagmIncorrectException, VIServiceIncorrectException {

      assertVerifierVIdeServiceWeb("password", IDENTIFICATION, SERVICE_VISE,
            ID_APPLI, keystore, ALIAS, null, crl);

   }

   private void assertVerifierVIdeServiceWeb(String param, String identification,
         URI serviceVise, String idAppliClient, KeyStore keystore,
         String alias, String password, List<X509CRL> crl)
         throws VIFormatTechniqueException, VISignatureException,
         VIInvalideException, VIAppliClientException, VINivAuthException,
         VIPagmIncorrectException, VIServiceIncorrectException {

      try {
         service.verifierVIdeServiceWeb(identification, serviceVise,
               idAppliClient, keystore, alias, password, crl);
         fail(FAIL_MESSAGE);
      } catch (IllegalArgumentException e) {

         assertEquals("Le paramètre [" + param
               + "] n'est pas renseigné alors qu'il est obligatoire", e
               .getMessage());
      }

   }

   @Test
   public void verifierVIdeServiceWebFailure_crl()
         throws VIFormatTechniqueException, VISignatureException, VIInvalideException, VIAppliClientException, VINivAuthException, VIPagmIncorrectException, VIServiceIncorrectException {

      assertVerifierVIdeServiceWebFailure_crl(null);
      List<X509CRL> crl = new ArrayList<X509CRL>();
      crl.add(null);
      crl.add(null);
      assertVerifierVIdeServiceWebFailure_crl(crl);
      assertVerifierVIdeServiceWebFailure_crl(new ArrayList<X509CRL>());

   }

   private void assertVerifierVIdeServiceWebFailure_crl(List<X509CRL> crl)
         throws VIFormatTechniqueException, VISignatureException, VIInvalideException, VIAppliClientException, VINivAuthException, VIPagmIncorrectException, VIServiceIncorrectException {

      try {
         service.verifierVIdeServiceWeb(IDENTIFICATION, SERVICE_VISE,
               ID_APPLI, keystore, ALIAS, PASSWORD, crl);
         fail(FAIL_MESSAGE);
      } catch (IllegalArgumentException e) {

         assertEquals("Il faut spécifier au moins un CRL", e.getMessage());
      }

   }

}
