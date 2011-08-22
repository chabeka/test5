package fr.urssaf.image.sae.services.document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.bo.model.untyped.UntypedDocument;
import fr.urssaf.image.sae.bo.model.untyped.UntypedMetadata;
import fr.urssaf.image.sae.services.document.exception.SAEConsultationServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-sae-services-consultation-test.xml" })
@SuppressWarnings("PMD.MethodNamingConventions")
public class SAEConsultationServiceTest {

   @Autowired
   @Qualifier("saeConsultationService")
   private SAEConsultationService service;

   @Test
   @Ignore("dans l'attente d'une base stable! de tests unitaire pour la consultation")
   public void consultation_success() throws IOException,
         SAEConsultationServiceException {

      UUID idArchive = UUID.fromString("1261362f-c87c-4e48-a06a-bc6b69f514e4");

      UntypedDocument untypedDocument = service.consultation(idArchive);

      assertNotNull("idArchive '" + idArchive + "' doit être consultable",
            untypedDocument);

      List<UntypedMetadata> metadatas = untypedDocument.getUMetadatas();

      assertNotNull("la liste des metadonnées doit être renseignée", metadatas);
      assertEquals("nombre de metadatas inattendu", 10, metadatas.size());

      Map<String, Object> expectedMetadatas = new HashMap<String, Object>();

      expectedMetadatas.put("ASO", "GED");

      expectedMetadatas.put("ACT", "2");
      expectedMetadatas.put("OTY", "autonome");
      expectedMetadatas.put("CSE", "CS1");
      expectedMetadatas.put("DCO", "12");
      expectedMetadatas.put("DFC", "2015/12/01");
      expectedMetadatas.put("COP", "UR030");
      expectedMetadatas.put("DOM", "2");
      expectedMetadatas.put("RND", "2.2.3.2.2");
      expectedMetadatas.put("FFI", "fmt/18");

      assertMetadata(metadatas.get(0), expectedMetadatas);
      assertMetadata(metadatas.get(1), expectedMetadatas);
      assertMetadata(metadatas.get(2), expectedMetadatas);
      assertMetadata(metadatas.get(3), expectedMetadatas);
      assertMetadata(metadatas.get(4), expectedMetadatas);
      assertMetadata(metadatas.get(5), expectedMetadatas);
      assertMetadata(metadatas.get(6), expectedMetadatas);
      assertMetadata(metadatas.get(7), expectedMetadatas);
      assertMetadata(metadatas.get(8), expectedMetadatas);
      assertMetadata(metadatas.get(9), expectedMetadatas);

      File expectedContent = new File(
            "src/test/resources/doc/attestation_consultation.pdf");

      assertTrue("le contenu n'est pas attendu", IOUtils.contentEquals(
            FileUtils.openInputStream(expectedContent),
            new ByteArrayInputStream(untypedDocument.getContent())));
   }

   private static void assertMetadata(UntypedMetadata metadata,
         Map<String, Object> expectedMetadatas) {

      assertTrue("la metadonnée '" + metadata.getLongCode()
            + "' est inattendue", expectedMetadatas.containsKey(metadata
            .getLongCode()));

      assertEquals("la valeur de la metadonnée est inattendue",
            expectedMetadatas.get(metadata.getLongCode()), metadata.getValue());

      expectedMetadatas.remove(metadata.getLongCode());
   }

}
