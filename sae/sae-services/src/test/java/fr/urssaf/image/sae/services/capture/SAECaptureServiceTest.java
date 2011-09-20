package fr.urssaf.image.sae.services.capture;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.docubase.toolkit.model.document.Document;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.services.SAEServiceTestProvider;
import fr.urssaf.image.sae.services.capture.exception.SAECaptureException;
import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-sae-services-test.xml" })
@SuppressWarnings("PMD.MethodNamingConventions")
public class SAECaptureServiceTest {

   @Autowired
   private SAECaptureService service;

   @Autowired
   private SAEServiceTestProvider testProvider;

   private UUID uuid;

   @SuppressWarnings("PMD.NullAssignment")
   @Before
   public void before() {

      // initialisation de l'uuid de l'archive
      uuid = null;
   }

   @After
   public void after() throws ConnectionServiceEx {

      // suppression de l'insertion
      if (uuid != null) {

         testProvider.deleteDocument(uuid);
      }
   }

   @Test
   public void capture_success() throws ConnectionServiceEx,
         SAECaptureException {

      URI ecdeURL = URI
            .create("ecde://cer69-ecde.cer69.recouv/DCL001/19991231/3/documents/attestation.pdf");
      Map<String, String> metadatas = new HashMap<String, String>();

      metadatas.put("code_test_1", "value_test_1");
      metadatas.put("code_test_2", "value_test_2");

      uuid = service.capture(metadatas, ecdeURL);

      Document doc = testProvider.searchDocument(uuid);
      Assert.assertNotNull("UUID non attendue", doc);
   }

}
