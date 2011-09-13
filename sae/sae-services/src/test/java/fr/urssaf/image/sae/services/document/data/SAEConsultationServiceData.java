package fr.urssaf.image.sae.services.document.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

      metadatas.add(new StorageMetadata("ACT", "2"));
      metadatas.add(new StorageMetadata("ASO", "GED"));
      metadatas.add(new StorageMetadata("OTY", "autonome"));
      metadatas.add(new StorageMetadata("CSE", "CS1"));
      metadatas.add(new StorageMetadata("DCO", "12"));
      metadatas.add(new StorageMetadata("DFC", "2015/12/01"));
      metadatas.add(new StorageMetadata("COP", "UR030"));
      metadatas.add(new StorageMetadata("DOM", "2"));
      metadatas.add(new StorageMetadata("RND", "2.2.3.2.2"));
      metadatas.add(new StorageMetadata("FFI", "fmt/18"));

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
            new String[] { "/applicationContext-sae-services-consultation-test.xml" });

      StorageServiceProvider provider = ctx
            .getBean(StorageServiceProvider.class);

      StorageConnectionParameter connection = ctx
            .getBean(StorageConnectionParameter.class);

      provider.setStorageServiceProviderParameter(connection);

      provider.getStorageConnectionService().openConnection();

      byte[] content = FileUtils.readFileToByteArray(new File(
            "src/test/resources/doc/attestation_consultation.pdf"));
      CONSULTATION_DOC.setContent(content);
      StorageDocument document = provider.getStorageDocumentService().insertStorageDocument(
            CONSULTATION_DOC);

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
