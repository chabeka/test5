package fr.urssaf.image.sae.services.document.commons.impl;

import java.io.IOException;
import java.text.ParseException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import fr.urssaf.image.sae.bo.model.untyped.UntypedDocument;
import fr.urssaf.image.sae.exception.SAECaptureServiceEx;
import fr.urssaf.image.sae.exception.capture.DuplicatedMetadataEx;
import fr.urssaf.image.sae.exception.capture.EmptyDocumentEx;
import fr.urssaf.image.sae.exception.capture.InvalidValueTypeAndFormatMetadataEx;
import fr.urssaf.image.sae.exception.capture.NotArchivableMetadataEx;
import fr.urssaf.image.sae.exception.capture.NotSpecifiableMetadataEx;
import fr.urssaf.image.sae.exception.capture.RequiredArchivableMetadataEx;
import fr.urssaf.image.sae.exception.capture.RequiredStorageMetadataEx;
import fr.urssaf.image.sae.exception.capture.UnknownHashCodeEx;
import fr.urssaf.image.sae.exception.capture.UnknownMetadataEx;
import fr.urssaf.image.sae.exception.enrichment.SAEEnrichmentEx;
import fr.urssaf.image.sae.mapping.exception.InvalidSAETypeException;
import fr.urssaf.image.sae.mapping.exception.MappingFromReferentialException;
import fr.urssaf.image.sae.services.CommonsServices;
import fr.urssaf.image.sae.services.document.commons.SAECommonCaptureService;

/***
 * Classe de test pour le service commun de capture en masse et capture
 * unitaire.
 * 
 * @author Rhofir
 */
public class SAECommonCaptureServiceImplTest extends CommonsServices {
   @Autowired
   @Qualifier("saeCommonCaptureService")
   SAECommonCaptureService saeCommonCaptureService;

   /**
    * @return Le service saeCommonCaptureService
    */
   public final SAECommonCaptureService getSaeCommonCaptureService() {
      return saeCommonCaptureService;
   }

   /**
    * @param saeCommonCaptureService
    *           : Le service saeCommonCaptureService.
    */
   public final void setSaeCommonCaptureService(
         SAECommonCaptureService saeCommonCaptureService) {
      this.saeCommonCaptureService = saeCommonCaptureService;
   }

   @Test
   public final void testBuildStorageDocumentForCapture()
         throws SAECaptureServiceEx, IOException, ParseException,
         SAEEnrichmentEx, RequiredStorageMetadataEx,
         InvalidValueTypeAndFormatMetadataEx, UnknownMetadataEx,
         DuplicatedMetadataEx, NotArchivableMetadataEx,
         NotSpecifiableMetadataEx, EmptyDocumentEx,
         RequiredArchivableMetadataEx, MappingFromReferentialException,
         InvalidSAETypeException, UnknownHashCodeEx {
      UntypedDocument untypedDocument = getUntypedDocumentMockData();
      saeCommonCaptureService.buildStorageDocumentForCapture(untypedDocument);
   }

}
