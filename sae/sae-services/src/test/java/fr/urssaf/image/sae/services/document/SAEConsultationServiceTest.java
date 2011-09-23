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
@ContextConfiguration(locations = { "/applicationContext-sae-services-test.xml" })
@SuppressWarnings("PMD.MethodNamingConventions")
public class SAEConsultationServiceTest {

   @Autowired
   @Qualifier("saeConsultationService")
   private SAEConsultationService service;

   @Test
   //@Ignore("dans l'attente d'une base stable! de tests unitaire pour la consultation")
   public void consultation_success() throws IOException,
         SAEConsultationServiceException {

      UUID idArchive = UUID.fromString("f1815255-d860-4fc5-8327-e041f14e598a");

      UntypedDocument untypedDocument = service.consultation(idArchive);

      assertNotNull("idArchive '" + idArchive + "' doit être consultable",
            untypedDocument);

      List<UntypedMetadata> metadatas = untypedDocument.getUMetadatas();

      assertNotNull("la liste des metadonnées doit être renseignée", metadatas);
      
      Map<String, Object> expectedMetadatas = new HashMap<String, Object>();

      expectedMetadatas.put("Titre", "Attestation de vigilance");
      expectedMetadatas.put("DateCreation", "2012-01-01");
      expectedMetadatas.put("DateReception", "1999-12-30");
      expectedMetadatas.put("CodeOrganismeGestionnaire", "UR750");
      expectedMetadatas.put("CodeOrganismeProprietaire", "CER69");
      expectedMetadatas.put("CodeRND", "2.3.1.1.12");
      expectedMetadatas.put("NomFichier", "");
      expectedMetadatas.put("FormatFichier", "fmt/1354");
      expectedMetadatas.put("ContratDeService", "ATT_PROD_001");
      expectedMetadatas.put("DateArchivage", "2012-01-01");

      for (UntypedMetadata metadata : metadatas) {
         assertMetadata(metadata, expectedMetadatas);
      }

      assertTrue("Des métadonnées '" + expectedMetadatas.keySet()
            + "' sont attendues", expectedMetadatas.isEmpty());

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

      assertEquals("la valeur de la metadonnée '" + metadata.getLongCode()
            + "'est inattendue", expectedMetadatas.get(metadata.getLongCode()),
            metadata.getValue());

      expectedMetadatas.remove(metadata.getLongCode());
   }

}
