package fr.urssaf.image.sae.saml.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXParseException;

import fr.urssaf.image.sae.saml.data.SamlAssertionData;
import fr.urssaf.image.sae.saml.params.SamlAssertionParams;
import fr.urssaf.image.sae.saml.params.SamlCommonsParams;

public class SamlAssertionExtractionServiceTest {

   private static SamlAssertionExtractionService service;

   private static final String FAIL_MESSAGE = "le test doit échouer";

   @BeforeClass
   public static void beforeClass() {
      service = new SamlAssertionExtractionService();
   }

   @Test
   public void extraitDonnees_success() throws IOException {

      File file = new File("src/test/resources/saml/saml_success.xml");
      String assertionSaml = FileUtils.readFileToString(file, "UTF-8");

      SamlAssertionData data = service.extraitDonnees(assertionSaml);

      SamlAssertionParams params = data.getAssertionParams();

      assertEquals("urn:oasis:names:tc:SAML:2.0:ac:classes:Password", params
            .getMethodAuthn2().toASCIIString());
      assertEquals("urn:interops:sp:73282932000074:test", params.getRecipient()
            .toASCIIString());
      assertEquals("urn:oasis:names:tc:SAML:2.0:nameid-format:persistent",
            params.getSubjectFormat2().toASCIIString());
      assertEquals("subjectId2", params.getSubjectId2());

      SamlCommonsParams commons = params.getCommonsParams();
      assertEquals("http://rniam.cnav.fr", commons.getAudience()
            .toASCIIString());
      assertDate("2006-03-21T12:50:01.152Z", commons.getAuthnInstant());
      assertEquals(UUID.fromString("f81d4fae-7dec-11d0-a765-00a0c91e6bf6"),
            commons.getId());
      assertDate("2007-03-21T12:50:01.152Z", commons.getIssueInstant());
      assertEquals("urn:interops:73282932000074:idp:test:version", commons
            .getIssuer());
      assertDate("2009-03-21T12:50:01.137Z", commons.getNotOnBefore());
      assertDate("2010-03-21T12:50:01.152Z", commons.getNotOnOrAfter());
      assertEquals("ROLE_USER,ROLE_ADMIN", StringUtils.join(commons.getPagm(),
            ","));

   }

   @Test
   public void extraitDonnees_failure_SAXException() {

      try {
         service.extraitDonnees("aaa");
         fail(FAIL_MESSAGE);
      } catch (IllegalStateException e) {

         assertEquals(SAXParseException.class, e.getCause().getClass());
      }

   }

   @Test
   public void extraitDonnees_failure_ID() throws IOException {

      File file = new File("src/test/resources/saml/saml_failure_ID.xml");
      String assertionSaml = FileUtils.readFileToString(file, "UTF-8");
      try {
         service.extraitDonnees(assertionSaml);
         fail(FAIL_MESSAGE);
      } catch (IllegalArgumentException e) {

         assertEquals("Invalid UUID string: bad id", e.getMessage());
      }

   }

   private void assertDate(String expected, Date actual) {

      DateTime dateTime = new DateTime(actual);

      assertEquals(new DateTime(expected), dateTime);
   }
}
