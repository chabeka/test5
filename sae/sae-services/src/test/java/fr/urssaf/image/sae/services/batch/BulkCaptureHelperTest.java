package fr.urssaf.image.sae.services.batch;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import fr.urssaf.image.sae.bo.model.untyped.UntypedDocument;
import fr.urssaf.image.sae.bo.model.untyped.UntypedDocumentOnError;
import fr.urssaf.image.sae.services.CommonsServices;
import fr.urssaf.image.sae.storage.model.storagedocument.BulkInsertionResults;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocuments;

@SuppressWarnings("all")
public class BulkCaptureHelperTest extends CommonsServices {
   @Autowired
   @Qualifier("bulkCaptureHelper")
   BulkCaptureHelper bulkCaptureHelper;

   @Test
   @Ignore
   public final void addIdTreatementToStorageDoc() {
      fail("Not yet implemented"); // TODO
      List<StorageDocument> storageDocs = null;
      bulkCaptureHelper.addIdTreatementToStorageDoc(storageDocs);
   }

   @Test
   @Ignore
   public final void buildStorageDocuments() {
      fail("Not yet implemented"); // TODO
      List<UntypedDocument> untypedDocs = null;
      bulkCaptureHelper.buildStorageDocuments(untypedDocs);
   }

   @Test
   @Ignore
   public final void buildErrors() {
      fail("Not yet implemented"); // TODO
      StorageDocuments storageDocuments = null;
      Exception messageException = null;
      bulkCaptureHelper.buildTechnicalErrors(storageDocuments, messageException);
   }

   @Test
   @Ignore
   public final void buildResultatsSuccess() {
      fail("Not yet implemented"); // TODO
      BulkInsertionResults bulkInsertionResults = null;
      int initialDocumentsCount = -1;
      String ecdeDirectory = null;
//      bulkCaptureHelper.buildResultatsSuccess(bulkInsertionResults,
//            initialDocumentsCount, ecdeDirectory);
   }

   @Test
   @Ignore
   public final void buildResultatsError() {
      fail("Not yet implemented"); // TODO
      int initialDocumentsCount = -1;
      String ecdeDirectory = null;
      List<UntypedDocumentOnError> untypedDocumentsOnError = null;
//      bulkCaptureHelper.buildResultatsError(initialDocumentsCount,
//            ecdeDirectory, untypedDocumentsOnError);
   }

}
