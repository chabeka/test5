package fr.urssaf.image.sae.services.batch;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

import fr.urssaf.image.sae.services.CommonsServices;
import fr.urssaf.image.sae.services.exception.capture.DuplicatedMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.EmptyDocumentEx;
import fr.urssaf.image.sae.services.exception.capture.InvalidValueTypeAndFormatMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.NotSpecifiableMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.RequiredArchivableMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.RequiredStorageMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.UnknownHashCodeEx;
import fr.urssaf.image.sae.services.exception.capture.UnknownMetadataEx;
import fr.urssaf.image.sae.services.exception.enrichment.UnknownCodeRndEx;

@SuppressWarnings("all")
public class BulkCaptureJobTest extends CommonsServices{

   @Test
   @Ignore
   public final void bulkCapture() {
      fail("Not yet implemented"); // TODO
   }

   @Test(expected = UnknownCodeRndEx.class)
   @Ignore
   public final void enrichmentMetadataFailed() {
      fail("Not yet implemented"); // TODO
   }

   @Test(expected = EmptyDocumentEx.class)
   @Ignore
   public final void checkEmptyUntypedDocumentFailed() {
      fail("Not yet implemented"); // TODO
   }

   @Test(expected = DuplicatedMetadataEx.class)
   @Ignore
   public final void checkDuplicatedMetadataFailed() {
      fail("Not yet implemented"); // TODO
   }

   @Test(expected = UnknownMetadataEx.class)
   @Ignore
   public final void checkUnknownMetadataFailed() {
      fail("Not yet implemented"); // TODO
   }

   @Test(expected = InvalidValueTypeAndFormatMetadataEx.class)
   @Ignore
   public final void checkInvalidValueTypeAndFormatMetadataFailed() {
      fail("Not yet implemented"); // TODO
   }

   @Test(expected = NotSpecifiableMetadataEx.class)
   @Ignore
   public final void notSpecifiableMetadataFailed() {

   }

   @Test(expected = RequiredArchivableMetadataEx.class)
   @Ignore
   public final void requiredArchivableMetadataFailed() {

   }

   @Test(expected = IllegalArgumentException.class)
   @Ignore
   public final void saeCaptureFailed() {

   }

   @Test(expected = UnknownHashCodeEx.class)
   @Ignore
   public final void checkHashCodeMetadataForStorageFailed() {

   }

   @Test(expected = RequiredStorageMetadataEx.class)
   @Ignore
   public final void requiredStorageMetadataFailed() {

   }
}
