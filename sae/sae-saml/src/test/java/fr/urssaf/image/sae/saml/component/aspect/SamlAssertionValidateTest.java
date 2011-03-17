package fr.urssaf.image.sae.saml.component.aspect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.urssaf.image.sae.saml.params.SamlAssertionParams;
import fr.urssaf.image.sae.saml.params.SamlCommonsParams;
import fr.urssaf.image.sae.saml.service.SamlAssertionCreationService;

import static org.easymock.EasyMock.*;

public class SamlAssertionValidateTest {

   private static SamlAssertionCreationService service;

   @BeforeClass
   public static void beforeClass() {
      service = createMock(SamlAssertionCreationService.class);
   }

   private SamlAssertionParams assertionParams;

   private SamlCommonsParams commonsParams;

   private KeyStore keystore;
  
   @Before
   public void before() throws KeyStoreException {

      assertionParams = new SamlAssertionParams();
      commonsParams = new SamlCommonsParams();
      keystore = KeyStore.getInstance(KeyStore.getDefaultType());
   }

   private void assertException(String message) {

      try {
         service.genererAssertion(assertionParams, keystore);
         fail("IllegalArgumentException attendue");
      } catch (IllegalArgumentException e) {

         assertEquals(message, e.getMessage());
      }
   }

   private String getMessage(String arg) {

      return "Il faut renseigner [" + arg + "]";

   }

   private String getEmptyMessage(String arg) {

      return "Le paramètre [" + arg
            + "] n'est pas renseigné alors qu'il est obligatoire";

   }

   @Test
   public void genererAssertionFailure_assertionParams() {

      try {
         service.genererAssertion(null, keystore);
         fail("IllegalArgumentException attendue");
      } catch (IllegalArgumentException e) {

         assertEquals(getEmptyMessage("assertionParams"), e.getMessage());
      }

   }

   @Test
   public void genererAssertionFailure_keystore() {

      try {
         service.genererAssertion(assertionParams, null);
         fail("IllegalArgumentException attendue");
      } catch (IllegalArgumentException e) {

         assertEquals(getEmptyMessage("keyStore"), e.getMessage());
      }

   }

   @Test
   public void genererAssertionFailure_commonsParams() {

      assertException(getEmptyMessage("assertionParams.commonsParams"));

   }

   @Test
   public void genererAssertionFailure_issuer() {

      assertionParams.setCommonsParams(commonsParams);

      assertException(getMessage("assertionParams.commonsParams.issuer"));

   }

   @Test
   public void genererAssertionFailure_notOnOrAfter() {

      assertionParams.setCommonsParams(commonsParams);
      commonsParams.setIssuer("issuer");

      assertException(getMessage("assertionParams.commonsParams.notOnOrAfter"));
   }

   @Test
   public void genererAssertionFailure_notOnBefore() {

      assertionParams.setCommonsParams(commonsParams);
      commonsParams.setIssuer("issuer");
      commonsParams.setNotOnOrAfter(new Date());

      assertException(getMessage("assertionParams.commonsParams.notOnBefore"));
   }

   @Test
   public void genererAssertionFailure_audience() {

      assertionParams.setCommonsParams(commonsParams);
      commonsParams.setIssuer("issuer");
      commonsParams.setNotOnOrAfter(new Date());
      commonsParams.setNotOnBefore(new Date());

      assertException(getMessage("assertionParams.commonsParams.audience"));
   }

   @Test
   public void genererAssertionFailure_PAGM_null() throws URISyntaxException {

      assertionParams.setCommonsParams(commonsParams);
      commonsParams.setIssuer("issuer");
      commonsParams.setNotOnOrAfter(new Date());
      commonsParams.setNotOnBefore(new Date());
      commonsParams.setAudience(new URI("http://audience"));

      assertException("Il faut spécifier au moins un PAGM");
   }

   @Test
   public void genererAssertionFailure_PAGM_empty() throws URISyntaxException {

      assertionParams.setCommonsParams(commonsParams);
      commonsParams.setIssuer("issuer");
      commonsParams.setNotOnOrAfter(new Date());
      commonsParams.setNotOnBefore(new Date());
      commonsParams.setAudience(new URI("http://audience"));

      List<String> pagm = Collections.emptyList();

      commonsParams.setPagm(pagm);

      assertException("Il faut spécifier au moins un PAGM");
   }

   @Test
   public void genererAssertionFailure_subjectFormat2()
         throws URISyntaxException {

      assertionParams.setCommonsParams(commonsParams);
      commonsParams.setIssuer("issuer");
      commonsParams.setNotOnOrAfter(new Date());
      commonsParams.setNotOnBefore(new Date());
      commonsParams.setAudience(new URI("http://audience"));
      commonsParams.setPagm(Arrays.asList("PAGM"));

      assertException(getMessage("assertionParams.subjectFormat2"));
   }

   @Test
   public void genererAssertionFailure_subjectId2() throws URISyntaxException {

      assertionParams.setCommonsParams(commonsParams);
      commonsParams.setIssuer("issuer");
      commonsParams.setNotOnOrAfter(new Date());
      commonsParams.setNotOnBefore(new Date());
      commonsParams.setAudience(new URI("http://audience"));
      commonsParams.setPagm(Arrays.asList("PAGM"));

      assertionParams.setSubjectFormat2(new URI("http://format"));

      assertException(getMessage("assertionParams.subjectId2"));
   }

   @Test
   public void genererAssertionFailure_methodAuthn2() throws URISyntaxException {

      assertionParams.setCommonsParams(commonsParams);
      commonsParams.setIssuer("issuer");
      commonsParams.setNotOnOrAfter(new Date());
      commonsParams.setNotOnBefore(new Date());
      commonsParams.setAudience(new URI("http://audience"));
      commonsParams.setPagm(Arrays.asList("PAGM"));

      assertionParams.setSubjectFormat2(new URI("http://format"));
      assertionParams.setSubjectId2("subjectId2");

      assertException(getMessage("assertionParams.methodAuthn2"));
   }

   @Test
   public void genererAssertionFailure_recipient() throws URISyntaxException {

      assertionParams.setCommonsParams(commonsParams);
      commonsParams.setIssuer("issuer");
      commonsParams.setNotOnOrAfter(new Date());
      commonsParams.setNotOnBefore(new Date());
      commonsParams.setAudience(new URI("http://audience"));
      commonsParams.setPagm(Arrays.asList("PAGM"));

      assertionParams.setSubjectFormat2(new URI("http://format"));
      assertionParams.setSubjectId2("subjectId2");
      assertionParams.setMethodAuthn2(new URI("http://methodAuthn2"));

      assertException(getMessage("assertionParams.recipient"));
   }

}
