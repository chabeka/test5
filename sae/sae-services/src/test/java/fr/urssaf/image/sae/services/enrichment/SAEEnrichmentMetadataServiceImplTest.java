package fr.urssaf.image.sae.services.enrichment;

import java.io.IOException;
import java.text.ParseException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import fr.urssaf.image.sae.bo.model.bo.SAEDocument;
import fr.urssaf.image.sae.bo.model.bo.SAEMetadata;
import fr.urssaf.image.sae.exception.SAECaptureServiceEx;
import fr.urssaf.image.sae.exception.enrichment.SAEEnrichmentEx;
import fr.urssaf.image.sae.services.CommonsServices;
import fr.urssaf.image.sae.services.enrichment.SAEEnrichmentMetadataService;
import fr.urssaf.image.sae.services.enrichment.xml.model.SAEArchivalMetadatas;

public class SAEEnrichmentMetadataServiceImplTest extends CommonsServices {
   @Autowired
   @Qualifier("saeEnrichmentMetadataService")
   SAEEnrichmentMetadataService saeEnrichmentMetadataService;

   /**
    * @return Le service d'enrichment des metadonnées.
    */
   public final SAEEnrichmentMetadataService getSaeEnrichmentMetadataService() {
      return saeEnrichmentMetadataService;
   }

   /**
    * @param saeEnrichmentMetadataService
    *           the saeEnrichmentMetadataService to set
    */
   public final void setSaeEnrichmentMetadataService(
         SAEEnrichmentMetadataService saeEnrichmentMetadataService) {
      this.saeEnrichmentMetadataService = saeEnrichmentMetadataService;
   }

   /**
    * Test de la méthode
    * {@link fr.urssaf.image.sae.services.enrichment.impl.SAEEnrichmentMetadataServiceImpl#enrichmentMetadata(SAEDocument)}
    * .
    */
   @Test
   public final void enrichmentMetadata() throws SAECaptureServiceEx,
         IOException, ParseException, SAEEnrichmentEx {
      SAEDocument saeDocument = getSAEDocumentMockData();
      saeEnrichmentMetadataService.enrichmentMetadata(saeDocument);
   }

   /**
    * Test de la méthode
    * {@link fr.urssaf.image.sae.services.enrichment.impl.SAEEnrichmentMetadataServiceImpl#enrichmentMetadata(SAEDocument)}
    * .
    */
   @Test(expected = SAEEnrichmentEx.class)
   public final void enrichmentMetadataFailed() throws SAECaptureServiceEx,
         IOException, ParseException, SAEEnrichmentEx {
      SAEDocument saeDocument = getSAEDocumentMockData();
      for (SAEMetadata saeMetadata : saeDocument.getMetadatas()) {
         if (saeMetadata.getLongCode().equals(
               SAEArchivalMetadatas.CODERND.getLongCode())) {
            saeMetadata.setValue("121212");
            break;
         }
      }
      saeEnrichmentMetadataService.enrichmentMetadata(saeDocument);
   }
}
