package fr.urssaf.image.sae.services.consultation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.bo.model.untyped.UntypedDocument;
import fr.urssaf.image.sae.bo.model.untyped.UntypedMetadata;
import fr.urssaf.image.sae.services.SAEServiceTestProvider;
import fr.urssaf.image.sae.services.exception.consultation.SAEConsultationServiceException;
import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-sae-services-test.xml" })
@SuppressWarnings("PMD.MethodNamingConventions")
public class SAEConsultationServiceTest {

   private static final Logger LOG = Logger
         .getLogger(SAEConsultationServiceTest.class);

   @Autowired
   @Qualifier("saeConsultationService")
   private SAEConsultationService service;

   @Autowired
   private SAEServiceTestProvider testProvider;

   private UUID uuid;

   @SuppressWarnings("PMD.NullAssignment")
   @Before
   public void before() {

      // initialisation de l'uuid de l'archive
      uuid = null;
   }

   // FIXME Attente de la nouvelle api DFCE pour pouvoir supprimer.
   @After
   public void after() throws ConnectionServiceEx {

      // suppression de l'insertion
      if (uuid != null) {

         // testProvider.deleteDocument(uuid);
      }
   }

   private UUID capture() throws IOException, ConnectionServiceEx,
         ParseException {
      File srcFile = new File(
            "src/test/resources/doc/attestation_consultation.pdf");

      byte[] content = FileUtils.readFileToByteArray(srcFile);

      String[] parsePatterns = new String[] { "yyyy-MM-dd" };
      List<StorageMetadata> metadatas = new ArrayList<StorageMetadata>();

      metadatas.add(new StorageMetadata("apr", "ADELAIDE"));
      metadatas.add(new StorageMetadata("cop", "CER69"));
      metadatas.add(new StorageMetadata("cog", "UR750"));
      metadatas.add(new StorageMetadata("sm_document_type", "2.3.1.1.12"));
      metadatas.add(new StorageMetadata("vrn", "11.1"));
      metadatas.add(new StorageMetadata("dom", "2"));
      metadatas.add(new StorageMetadata("act", "3"));
      metadatas.add(new StorageMetadata("sm_life_cycle_reference_date",
            DateUtils.parseDate("2013-01-01", parsePatterns)));
      metadatas.add(new StorageMetadata("nbp", "2"));
      metadatas.add(new StorageMetadata("ffi", "fmt/1354"));
      metadatas.add(new StorageMetadata("cse", "ATT_PROD_001"));
      metadatas.add(new StorageMetadata("dre", DateUtils.parseDate(
            "1999-12-30", parsePatterns)));
      metadatas
            .add(new StorageMetadata("sm_title", "Attestation de vigilance"));
      metadatas.add(new StorageMetadata("nfi", "attestation_consultation.pdf"));
      metadatas.add(new StorageMetadata("sm_creation_date", DateUtils
            .parseDate("2012-01-01", parsePatterns)));
      metadatas.add(new StorageMetadata("dfc", DateUtils.parseDate(
            "2012-01-01", parsePatterns)));

      return testProvider.captureDocument(content, metadatas, srcFile);
   }

   @Test
   public void consultation_success() throws IOException,
         SAEConsultationServiceException, ConnectionServiceEx, ParseException {

      uuid = capture();

      LOG.debug("document archivé dans DFCE:" + uuid);

      UntypedDocument untypedDocument = service.consultation(uuid);

      assertNotNull("idArchive '" + uuid + "' doit être consultable",
            untypedDocument);

      List<UntypedMetadata> metadatas = untypedDocument.getUMetadatas();

      assertNotNull("la liste des metadonnées doit être renseignée", metadatas);
      assertEquals(
            "la nombre de métadonnées consultables par défaut est inattendu",
            12, metadatas.size());

      // on trie les métadonnées non typés en fonction de leur code long
      Comparator<UntypedMetadata> comparator = new Comparator<UntypedMetadata>() {
         @Override
         public int compare(UntypedMetadata untypedMetadata1,
               UntypedMetadata untypedMetadata2) {

            return untypedMetadata1.getLongCode().compareTo(
                  untypedMetadata2.getLongCode());

         }
      };
      Collections.sort(metadatas, comparator);

      assertMetadata(metadatas.get(0), "CodeOrganismeGestionnaire", "UR750");
      assertMetadata(metadatas.get(1), "CodeOrganismeProprietaire", "CER69");
      assertMetadata(metadatas.get(2), "CodeRND", "2.3.1.1.12");
      assertMetadata(metadatas.get(3), "ContratDeService", "ATT_PROD_001");

      assertEquals("le code de la metadonnée est inattendue dans cet ordre",
            "DateArchivage", metadatas.get(4).getLongCode());

      assertMetadata(metadatas.get(5), "DateCreation", "2012-01-01");
      assertMetadata(metadatas.get(6), "DateReception", "1999-12-30");
      assertMetadata(metadatas.get(7), "FormatFichier", "fmt/1354");
      assertMetadata(metadatas.get(8), "Hash",
            "4bf2ddbd82d5fd38e821e6aae434ac989972a043");
      assertMetadata(metadatas.get(9), "NomFichier",
            "attestation_consultation.pdf");
      assertMetadata(metadatas.get(10), "TailleFichier", "73791");
      assertMetadata(metadatas.get(11), "Titre", "Attestation de vigilance");

      File expectedContent = new File(
            "src/test/resources/doc/attestation_consultation.pdf");

      assertTrue("le contenu n'est pas attendu", IOUtils.contentEquals(
            FileUtils.openInputStream(expectedContent),
            new ByteArrayInputStream(untypedDocument.getContent())));
   }

   private static void assertMetadata(UntypedMetadata metadata,
         String expectedCode, String expectedValue) {

      assertEquals("le code de la metadonnée est inattendue dans cet ordre",
            expectedCode, metadata.getLongCode());

      assertEquals("la valeur de la metadonnée '" + metadata.getLongCode()
            + "'est inattendue", expectedValue, metadata.getValue());

   }

}
