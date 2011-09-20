package fr.urssaf.image.sae.services.capture.impl;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.services.capture.SAECaptureService;
import fr.urssaf.image.sae.services.capture.exception.SAECaptureException;
import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;
import fr.urssaf.image.sae.storage.exception.InsertionServiceEx;
import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;
import fr.urssaf.image.sae.storage.services.StorageServiceProvider;

/**
 * Implémentation du service {@link SAECaptureService}
 * 
 */
@Service
public class SAECaptureServiceImpl implements SAECaptureService {

   @Autowired
   @Qualifier("storageServiceProvider")
   private StorageServiceProvider serviceProvider;

   @Autowired
   @Qualifier("storageConnectionParameter")
   private StorageConnectionParameter connection;

   @Autowired
   private ApplicationContext context;

   private static final StorageDocument CONSULTATION_DOC;

   private static final int DATE_YEAR = 2012;

   static {

      CONSULTATION_DOC = new StorageDocument();

      List<StorageMetadata> metadatas = new ArrayList<StorageMetadata>();

      // metadatas.add(new StorageMetadata("_titre",
      // "Attestation de viligance"));

      Calendar calendar = Calendar.getInstance();

      calendar.set(DATE_YEAR, Calendar.JANUARY, 1, 0, 0, 0);

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

      CONSULTATION_DOC.setTypeDoc("PDF");
      CONSULTATION_DOC.setMetadatas(metadatas);

   }

   private UUID insert() throws ConnectionServiceEx, InsertionServiceEx,
         IOException {

      serviceProvider.setStorageServiceProviderParameter(connection);

      serviceProvider.getStorageConnectionService().openConnection();

      byte[] content = IOUtils.toByteArray(context.getResource(
            "classpath:attestation_temp.pdf").getInputStream());

      CONSULTATION_DOC.setContent(content);
      StorageDocument document = serviceProvider.getStorageDocumentService()
            .insertStorageDocument(CONSULTATION_DOC);

      serviceProvider.getStorageConnectionService().closeConnexion();

      return document.getUuid();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final UUID capture(Map<String, String> metadatas, URI uriEcde)
         throws SAECaptureException {

      try {
         return insert();
      } catch (ConnectionServiceEx e) {
         throw new SAECaptureException(e);
      } catch (InsertionServiceEx e) {
         throw new SAECaptureException(e);
      } catch (IOException e) {
         throw new SAECaptureException(e);
      }
   }

}
