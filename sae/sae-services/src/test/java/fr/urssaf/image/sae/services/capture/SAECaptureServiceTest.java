package fr.urssaf.image.sae.services.capture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.docubase.toolkit.model.document.Criterion;
import net.docubase.toolkit.model.document.Document;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.bo.model.untyped.UntypedMetadata;
import fr.urssaf.image.sae.mapping.exception.InvalidSAETypeException;
import fr.urssaf.image.sae.mapping.exception.MappingFromReferentialException;
import fr.urssaf.image.sae.services.SAEServiceTestProvider;
import fr.urssaf.image.sae.services.exception.capture.DuplicatedMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.EmptyDocumentEx;
import fr.urssaf.image.sae.services.exception.capture.InvalidValueTypeAndFormatMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.NotArchivableMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.NotSpecifiableMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.RequiredArchivableMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.RequiredStorageMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.SAECaptureServiceEx;
import fr.urssaf.image.sae.services.exception.capture.UnknownHashCodeEx;
import fr.urssaf.image.sae.services.exception.capture.UnknownMetadataEx;
import fr.urssaf.image.sae.services.exception.enrichment.ReferentialRndException;
import fr.urssaf.image.sae.services.exception.enrichment.SAEEnrichmentEx;
import fr.urssaf.image.sae.services.exception.enrichment.UnknownCodeRndEx;
import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-sae-services-test.xml" })
@SuppressWarnings( { "PMD.MethodNamingConventions", "PMD.ExcessiveImports" })
public class SAECaptureServiceTest {

   private static final Logger LOG = Logger
         .getLogger(SAECaptureServiceTest.class);

   @Autowired
   private SAECaptureService service;

   @Autowired
   private SAEServiceTestProvider testProvider;

   private UUID uuid;

   private static File ecdeRepertory;

   @BeforeClass
   public static void beforeClass() throws IOException {

      // mise en place du point dans le répertoire temporaire

      ecdeRepertory = new File(FilenameUtils.concat(SystemUtils
            .getJavaIoTmpDir().getAbsolutePath(), "ecde"));

      FileUtils.forceMkdir(ecdeRepertory);
      FileUtils.cleanDirectory(ecdeRepertory);
   }

   @AfterClass
   public static void afterClass() throws IOException {

      FileUtils.cleanDirectory(ecdeRepertory);
   }

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
         SAECaptureServiceEx, IOException, SAEEnrichmentEx,
         RequiredStorageMetadataEx, InvalidValueTypeAndFormatMetadataEx,
         UnknownMetadataEx, DuplicatedMetadataEx, NotSpecifiableMetadataEx,
         EmptyDocumentEx, RequiredArchivableMetadataEx,
         MappingFromReferentialException, InvalidSAETypeException,
         UnknownHashCodeEx, NotArchivableMetadataEx, ReferentialRndException,
         UnknownCodeRndEx {

      File srcFile = new File(
            "src/test/resources/doc/attestation_consultation.pdf");
      File destFile = new File(ecdeRepertory.getAbsolutePath(),
            "DCL001/19991231/3/documents/attestation.pdf");
      FileUtils.copyFile(srcFile, destFile);

      URI ecdeURL = URI
            .create("ecde://ecde.cer69.recouv/DCL001/19991231/3/documents/attestation.pdf");

      List<UntypedMetadata> metadatas = new ArrayList<UntypedMetadata>();

      metadatas.add(new UntypedMetadata("ApplicationProductrice", "ADELAIDE"));

      metadatas.add(new UntypedMetadata("CodeOrganismeProprietaire", "CER69"));
      metadatas.add(new UntypedMetadata("CodeOrganismeGestionnaire", "UR750"));
      metadatas.add(new UntypedMetadata("CodeRND", "2.3.1.1.12"));
      metadatas.add(new UntypedMetadata("VersionRND", "11.1"));

      // metadatas.put("CodeFonction", "2");
      // metadatas.put("CodeActivite", "3");
      // metadatas.put("DureeConservation", "1825");
      // metadatas.put("DateDebutConservation", "2012-01-01");
      // metadatas.put("DateFinConservation", "2012-01-01");
      // metadatas.put("Gel", "false");

      metadatas.add(new UntypedMetadata("NbPages", "2"));
      metadatas.add(new UntypedMetadata("NomFichier", "attestation.pdf"));
      metadatas.add(new UntypedMetadata("FormatFichier", "fmt/1354"));
      metadatas.add(new UntypedMetadata("DateCreation", "2012-01-01"));
      metadatas.add(new UntypedMetadata("Titre", "Attestation de vigilance"));
      metadatas.add(new UntypedMetadata("TypeHash", "SHA-1"));

      String hash = DigestUtils.shaHex(new FileInputStream(srcFile));
      metadatas.add(new UntypedMetadata("Hash", hash));

      // metadatas.put("type", "PDF");
      // metadatas.put("ObjectType", "autonomous");
      // metadatas.put("ContratDeService", "ATT_PROD_001");

      uuid = service.capture(metadatas, ecdeURL);

      LOG.debug("document archivé dans DFCE:" + uuid);

      Document doc = testProvider.searchDocument(uuid);

      Assert.assertNotNull("l'UUID '" + uuid + "' doit exister dans le SAE",
            doc);

      // TEST sur les métadonnées
      
      assertDocument(doc, hash);
      assertCriterions(doc.getAllCriterions());

      // TEST sur le contenu du document
      
      assertTrue("le contenu n'est pas attendu", IOUtils.contentEquals(
            FileUtils.openInputStream(srcFile), testProvider
                  .loadDocumentFile(doc)));

   }

   private static void assertDocument(Document doc, String hash) {

      // TEST sur métadonnée : Titre
      Assert.assertEquals("la métadonnée 'Titre(_titre)' est incorrecte",
            "Attestation de vigilance", doc.getTitle());

      // TEST sur métadonnée : DateCreation
      Calendar creationDate = Calendar.getInstance();
      creationDate.setTime(doc.getCreationDate());

      Assert
            .assertEquals(
                  "la métadonnée 'DateCreation(_creationDate)' est incorrecte pour l'année",
                  2012, creationDate.get(Calendar.YEAR));
      Assert
            .assertEquals(
                  "la métadonnée 'DateCreation(_creationDate)' est incorrecte pour le mois",
                  Calendar.JANUARY, creationDate.get(Calendar.MONTH));
      Assert
            .assertEquals(
                  "la métadonnée 'DateCreation(_creationDate)' est incorrecte pour le jour",
                  1, creationDate.get(Calendar.DATE));

      // TEST sur métadonnée : TypeHash
      Assert
            .assertEquals(
                  "la métadonnée 'TypeHash(version.1.digest.algorithm)' est incorrecte",
                  "SHA-1", doc.getDigestAlgorithm());

      // TEST sur métadonnée : Hash
      Assert.assertEquals(
            "la métadonnée 'Hash(version.1.digest)' est incorrecte", hash, doc
                  .getDigest());
   }

   private static void assertCriterions(List<Criterion> criterions) {

      Map<String, Object> expectedMetadatas = new HashMap<String, Object>();

      // TEST sur métadonnée : ApplicationProductrice
      expectedMetadatas.put("APR", "ADELAIDE");
      // TEST sur métadonnée : CodeOrganismeProprietaire
      expectedMetadatas.put("COP", "CER69");
      // TEST sur métadonnée : CodeOrganismeGestionnaire
      expectedMetadatas.put("COG", "UR750");
      // TEST sur métadonnée : CodeRND
      expectedMetadatas.put("RND", "2.3.1.1.12");
      // TEST sur métadonnée : VersionRND
      expectedMetadatas.put("VRN", "11.1");
      // TEST sur métadonnée : CodeFonction
      expectedMetadatas.put("DOM", "2");
      // TEST sur métadonnée : CodeActivite
      expectedMetadatas.put("ACT", "3");

      // TODO effectuer un test sur la métadonnée DureeConservation(DCO) lors du
      // test du service capture
      // TEST sur métadonnée : DureeConservation
      // expectedMetadatas.put("DCO", "1825");

      // TODO effectuer un test sur la métadonnée Gel(GEL) lors du
      // test du service capture
      // TEST sur métadonnée : Gel
      // expectedMetadatas.put("GEL", false);

      // TEST sur métadonnée : NbPages
      expectedMetadatas.put("NBP", 2);

      // TEST sur métadonnée : NomFichier
      // TODO effectuer un test sur la métadonnée NomFichier(NFI) lors du
      // test du service capture
      // expectedMetadatas.put("NFI", "attestion.pdf");

      // TEST sur métadonnée : FormatFichier
      expectedMetadatas.put("FFI", "fmt/1354");

      // TODO effectuer un test sur la métadonnée Type(_type) lors du
      // test du service capture
      // TEST sur métadonnée : type
      // expectedMetadatas.put("type", "pdf");

      // TEST sur métadonnée : ObjectType
      // TODO effectuer un test sur la métadonnée ObjectType(OTY) lors du
      // test du service capture
      // expectedMetadatas.put("OTY", "autonomous");

      // TEST sur métadonnée : ContratDeService
      // TODO effectuer un test sur la métadonnée ContratDeService(CSE) lors du
      // test du service capture
      expectedMetadatas.put("CSE", "ATT_PROD_001");

      // TEST sur métadonnée : DateArchivage
      // TODO effectuer un test sur la métadonnée DateArchivage(_archivageDate)
      // lors du
      // test du service capture
      // expectedMetadatas.put("_archivageDate", "1999-12-31");

      for (Criterion criterion : criterions) {

         if (!"DDC".contains(criterion.getCategoryName())
               && !"DFC".contains(criterion.getCategoryName())) {

            assertMetadata(criterion, expectedMetadatas);
         }

      }

      assertTrue("Les métadonnées " + expectedMetadatas.keySet()
            + " sont attendues", expectedMetadatas.isEmpty());

   }

   private static void assertMetadata(Criterion criterion,
         Map<String, Object> expectedMetadatas) {

      assertTrue("la metadonnée '" + criterion.getCategoryName()
            + "' est inattendue", expectedMetadatas.containsKey(criterion
            .getCategoryName()));

      assertEquals("la valeur de la metadonnée " + criterion.getCategoryName()
            + " est incorrecte", expectedMetadatas.get(criterion
            .getCategoryName()), criterion.getWord());

      expectedMetadatas.remove(criterion.getCategoryName());
   }
}
