package fr.urssaf.image.sae.saml.service;

import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.urssaf.image.sae.saml.params.SamlAssertionParams;
import fr.urssaf.image.sae.saml.params.SamlCommonsParams;

public class SamlAssertionCreationServiceTest {

   private static final Logger LOG = Logger
         .getLogger(SamlAssertionCreationServiceTest.class);

   private static SamlAssertionCreationService service;

   private static KeyStore keystore;

   @BeforeClass
   public static void beforeClass() throws KeyStoreException {
      service = new SamlAssertionCreationService();
      keystore = KeyStore.getInstance("PKCS12");

      try {
         FileInputStream in = new FileInputStream(
               "src/test/resources/Portail_Image.p12");
         keystore.load(in, "hiUnk6O3QnRN".toCharArray());
         in.close();
      } catch (Exception ex) {
         System.out.println("Failed to read keystore:");
         ex.printStackTrace();
      }

      Enumeration<String> aliases = keystore.aliases();
      while(aliases.hasMoreElements()){
         
         LOG.debug("alias:["+aliases.nextElement()+"]");
      }
   }

   private SamlAssertionParams params;

   @Before
   public void before() throws URISyntaxException {

      params = new SamlAssertionParams();

      URI subjectFormat2 = new URI(
            "urn:oasis:names:tc:SAML:2.0:nameid-format:persistent");
      params.setSubjectFormat2(subjectFormat2);
      params.setSubjectId2("subjectId2");
      URI methodAuthn2 = new URI(
            "urn:oasis:names:tc:SAML:2.0:ac:classes:Password");
      params.setMethodAuthn2(methodAuthn2);
      URI recipient = new URI("urn:interops:sp:73282932000074:test");
      params.setRecipient(recipient);

      SamlCommonsParams commonsParams = new SamlCommonsParams();
      URI audience = new URI("http://rniam.cnav.fr");
      commonsParams.setAudience(audience);

      commonsParams.setIssuer("urn:interops:73282932000074:idp:test:version");
      Date notOnBefore = new Date();
      commonsParams.setNotOnBefore(notOnBefore);
      Date notOnOrAfter = new Date();
      commonsParams.setNotOnOrAfter(notOnOrAfter);
      commonsParams.setPagm(Arrays.asList("ROLE_USER", "", " ", "ROLE_ADMIN",
            null));
      params.setCommonsParams(commonsParams);
   }

   /**
    * cas où id,issueInstant & authnInstant ne sont pas renseignés
    * 
    */
   @Test
   public void genererAssertion() {

      SamlCommonsParams commonsParams = params.getCommonsParams();
      Date authnInstant = new Date();
      commonsParams.setAuthnInstant(authnInstant);
      UUID id = UUID.fromString("f81d4fae-7dec-11d0-a765-00a0c91e6bf6");
      commonsParams.setId(id);
      Date issueInstant = new Date();
      commonsParams.setIssueInstant(issueInstant);

      String assertion = service.genererAssertion(params, keystore);

      LOG.debug("\n" + assertion);
   }

   /**
    * cas où id,issueInstant & authnInstant ne sont pas renseignés
    * 
    */
   @Test
   public void genererAssertion_empty() {

      String assertion = service.genererAssertion(params, keystore);

      LOG.debug("\n" + assertion);
   }

}
