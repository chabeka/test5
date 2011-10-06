package fr.urssaf.image.sae.services.batch;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import fr.urssaf.image.sae.bo.model.untyped.UntypedDocument;
import fr.urssaf.image.sae.bo.model.untyped.UntypedDocumentOnError;
import fr.urssaf.image.sae.mapping.services.MappingDocumentService;
import fr.urssaf.image.sae.services.CommonsServices;
import fr.urssaf.image.sae.services.exception.capture.SAECaptureServiceEx;
import fr.urssaf.image.sae.storage.model.storagedocument.BulkInsertionResults;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocuments;

@SuppressWarnings("all")
public class BulkCaptureHelperTest extends CommonsServices {
   @Autowired
   @Qualifier("bulkCaptureHelper")
   BulkCaptureHelper bulkCaptureHelper;
   @Autowired
   @Qualifier("mappingDocumentService")
   MappingDocumentService mapping;

   @Test
   @Ignore
   public final void addIdTreatementToStorageDoc() throws SAECaptureServiceEx, IOException, ParseException {
      List<StorageDocument> storageDocs = null;
      getUntypedDocumentMockData();
      bulkCaptureHelper.addIdTreatementToStorageDoc(storageDocs);
   }

   @Test
   @Ignore
   public final void buildStorageDocuments() {
      List<UntypedDocument> untypedDocs = null;
      bulkCaptureHelper.buildStorageDocuments(untypedDocs);
   }

   @Test
   @Ignore
   public final void buildErrors() {
      StorageDocuments storageDocuments = null;
      Exception messageException = null;
      bulkCaptureHelper.buildTechnicalErrors(storageDocuments, messageException);
   }

   @Test
   @Ignore
   public final void buildResultatsSuccess() {
      BulkInsertionResults bulkInsertionResults = null;
      int initialDocumentsCount = -1;
      String ecdeDirectory = null;
//      bulkCaptureHelper.buildResultatsSuccess(bulkInsertionResults,
//            initialDocumentsCount, ecdeDirectory);
   }

   @Test
   @Ignore
   public final void buildResultatsError() {
      int initialDocumentsCount = -1;
      String ecdeDirectory = null;
      List<UntypedDocumentOnError> untypedDocumentsOnError = null;
//      bulkCaptureHelper.buildResultatsError(initialDocumentsCount,
//            ecdeDirectory, untypedDocumentsOnError);
   }

}
