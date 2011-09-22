package fr.urssaf.image.sae.services.capture;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.docubase.toolkit.model.document.Document;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
         UnknownHashCodeEx, NotArchivableMetadataEx, ReferentialRndException, UnknownCodeRndEx {

      File srcFile = new File(
            "src/test/resources/doc/attestation_consultation.pdf");
      File destFile = new File(ecdeRepertory.getAbsolutePath(),
            "DCL001/19991231/3/documents/attestation.pdf");
      FileUtils.copyFile(srcFile, destFile);

      URI ecdeURL = URI
            .create("ecde://ecde.cer69.recouv/DCL001/19991231/3/documents/attestation.pdf");

      Map<String, String> metadatas = new HashMap<String, String>();

      metadatas.put("ApplicationProductrice", "ADELAIDE");
      metadatas.put("CodeOrganismeProprietaire", "UR750");
      metadatas.put("CodeOrganismeGestionnaire", "UR750");
      metadatas.put("CodeRND", "2.3.1.1.12");
      metadatas.put("VersionRND", "11.1");
      // metadatas.put("CodeFonction", "2");
      // metadatas.put("CodeActivite", "3");
      // metadatas.put("DureeConservation", "1825");
      //metadatas.put("DateDebutConservation", "2012-01-01");
      // metadatas.put("DateFinConservation", "2012-01-01");
      // metadatas.put("Gel", "false");
      metadatas.put("NbPages", "2");
      metadatas.put("NomFichier", "attestation.pdf");
      metadatas.put("FormatFichier", "fmt/1354");
      metadatas.put("DateCreation", "2012-01-01");
      metadatas.put("Titre", "Attestion de vigilance");
      metadatas.put("TypeHash", "SHA-1");

      metadatas.put("Hash", DigestUtils.shaHex(FileUtils
            .readFileToByteArray(srcFile)));
      // metadatas.put("type", "PDF");
      // metadatas.put("ObjectType", "autonomous");
      // metadatas.put("ContratDeService", "ATT_PROD_001");

      uuid = service.capture(metadatas, ecdeURL);

      Document doc = testProvider.searchDocument(uuid);
      Assert.assertNotNull("UUID non attendue", doc);
   }

}
