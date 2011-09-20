package fr.urssaf.image.sae.services.document.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;
import fr.urssaf.image.sae.storage.exception.InsertionServiceEx;
import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;
import fr.urssaf.image.sae.storage.services.StorageServiceProvider;

/**
 * Classe d'insertion d'un document dans le SAE pour qu'il soit consultable
 * 
 * 
 */
public final class SAEConsultationServiceData {

   private SAEConsultationServiceData() {

   }

   private static final Logger LOG = Logger
         .getLogger(SAEConsultationServiceData.class);

   private static final StorageDocument CONSULTATION_DOC;

   static {

      CONSULTATION_DOC = new StorageDocument();

      List<StorageMetadata> metadatas = new ArrayList<StorageMetadata>();

      // metadatas.add(new StorageMetadata("_titre",
      // "Attestation de viligance"));

      Calendar calendar = Calendar.getInstance();

      // metadatas.add(new StorageMetadata("_creationDate",
      // calendar.getTime()));

      metadatas.add(new StorageMetadata("APR", "ADELAIDE"));
      metadatas.add(new StorageMetadata("COP", "UR750"));
      metadatas.add(new StorageMetadata("COG", "UR750"));
      metadatas.add(new StorageMetadata("RND", "2.3.1.1.12"));
      metadatas.add(new StorageMetadata("VRN", "11.1"));
      metadatas.add(new StorageMetadata("DOM", "2"));
      metadatas.add(new StorageMetadata("ACT", "3"));
      // metadatas.add(new StorageMetadata("DCO", "1825"));
      metadatas.add(new StorageMetadata("DDC", calendar.getTime()));
      metadatas.add(new StorageMetadata("DFC", calendar.getTime()));
      // metadatas.add(new StorageMetadata("GEL", false));
      metadatas.add(new StorageMetadata("NBP", 2));
      // metadatas.add(new StorageMetadata("NFI", "attestation.pdf"));
      metadatas.add(new StorageMetadata("FFI", "fmt/1354"));
      metadatas.add(new StorageMetadata("_type", "PDF"));
      // metadatas.add(new StorageMetadata("OTY", "autonomous"));
      metadatas.add(new StorageMetadata("CSE", "ATT_PROD_001"));
      // metadatas.add(new StorageMetadata("_archivageDate",
      // calendar.getTime()));

      CONSULTATION_DOC.setTypeDoc("PDF");
      CONSULTATION_DOC.setMetadatas(metadatas);

   }

   /**
    * insertion d'un document
    * 
    * @return uuid de l'archive
    * @throws ConnectionServiceEx
    *            levée lors de l'insertion
    * @throws InsertionServiceEx
    *            levée lors de l'insertion
    * @throws IOException
    *            levée lors de l'insertion
    */
   public static UUID insert() throws ConnectionServiceEx, InsertionServiceEx,
         IOException {

      ApplicationContext ctx = new ClassPathXmlApplicationContext(
            new String[] { "/applicationContext-sae-services-test.xml" });

      StorageServiceProvider provider = ctx
            .getBean(StorageServiceProvider.class);

      StorageConnectionParameter connection = ctx
            .getBean(StorageConnectionParameter.class);

      provider.setStorageServiceProviderParameter(connection);

      provider.getStorageConnectionService().openConnection();

      byte[] content = FileUtils.readFileToByteArray(new File(
            "src/test/resources/doc/attestation_consultation.pdf"));
      CONSULTATION_DOC.setContent(content);
      StorageDocument document = provider.getStorageDocumentService()
            .insertStorageDocument(CONSULTATION_DOC);

      provider.getStorageConnectionService().closeConnexion();

      return document.getUuid();
   }

   /**
    * 
    * @param args
    *           non utilisé
    * @throws ConnectionServiceEx
    *            levée lors de l'insertion
    * @throws InsertionServiceEx
    *            levée lors de l'insertion
    * @throws IOException
    *            levée lors de l'insertion
    */
   public static void main(String[] args) throws ConnectionServiceEx,
         InsertionServiceEx, IOException {

      LOG.info("insertion réussie de l'archive :" + insert());

   }
}
