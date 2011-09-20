package fr.urssaf.image.sae.services.capture.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.services.capture.SAECaptureService;
import fr.urssaf.image.sae.services.capture.exception.SAECaptureException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-sae-services-test.xml" })
@SuppressWarnings("PMD.MethodNamingConventions")
public class SAECaptureServiceValidationTest {

   private SAECaptureService service;

   private static Map<String, String> metadatas;

   private static URI ecdeURL;

   @BeforeClass
   public static void beforeClass() {

      ecdeURL = URI
            .create("ecde://cer69-ecde.cer69.recouv/DCL001/19991231/3/documents/attestation.pdf");

      metadatas = new HashMap<String, String>();
      metadatas.put("test", "test");

   }

   @Before
   public void before() {

      service = new SAECaptureService() {

         @Override
         public UUID capture(Map<String, String> metadatas, URI ecdeURL) {

            return null;
         }
      };
   }

   @Test
   public void capture_success() throws SAECaptureException {

      try {
         service.capture(metadatas, ecdeURL);

      } catch (IllegalArgumentException e) {
         fail("les arguments en entrée doivent être valides");
      }

   }

   @Test
   public void capture_failure_metadatas_null() throws SAECaptureException {

      assertCapture_failure_metadatas(service, null);
      assertCapture_failure_metadatas(service, new HashMap<String, String>());

   }

   private static void assertCapture_failure_metadatas(
         SAECaptureService service, Map<String, String> metadatas)
         throws SAECaptureException {

      try {

         service.capture(metadatas, ecdeURL);

         fail("l'argument metadatas ne doit pas être renseigné");
      } catch (IllegalArgumentException e) {
         assertEquals("message d'exception non attendu",
               "L'argument 'metadatas' doit être renseigné", e.getMessage());
      }

   }

   @Test
   public void capture_failure_ecdeUrl_null() throws SAECaptureException {

      try {

         service.capture(metadatas, null);

         fail("l'argument ecdeURL ne doit pas être renseigné");
      } catch (IllegalArgumentException e) {
         assertEquals("message d'exception non attendu",
               "L'argument 'ecdeURL' doit être renseigné", e.getMessage());
      }

   }

}
