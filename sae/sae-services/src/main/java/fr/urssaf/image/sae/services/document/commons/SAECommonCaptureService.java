package fr.urssaf.image.sae.services.document.commons;

import fr.urssaf.image.sae.bo.model.untyped.UntypedDocument;
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
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;

/**
 * Service commun pour l’archivage unitaire et l’archivage en masse.
 * 
 *@author rhofir.
 */
public interface SAECommonCaptureService {

   /**
    * Cette méthode permet de construire un StorageDocument à partir d’un
    * UntypedDocument. Cette méthode fait appel dans l’ordre à :<br>
    * <lu><br>
    * <li>checkUntypedDocument</li><br>
    * <li>checkUntypedMetadata</li><br>
    * <li>checkSaeMetadataForCapture</li><br>
    * <li>enrichmentMetadata</li><br>
    * <li>checkSaeMetadataForStorage</li><br>
    * </lu>
    * 
    * @param untypedDocument
    *           : Classe représentant un document non typé
    *           {@link StorageDocument}
    * @return Classe représentant un document archivable.
    * @throws RequiredStorageMetadataEx
    *            {@link RequiredStorageMetadataEx}.
    * @throws InvalidValueTypeAndFormatMetadataEx
    *            {@link InvalidValueTypeAndFormatMetadataEx}.
    * @throws UnknownMetadataEx
    *            {@link UnknownMetadataEx}.
    * @throws DuplicatedMetadataEx
    *            {@link DuplicatedMetadataEx}.
    * @throws NotArchivableMetadataEx
    *            {@link NotArchivableMetadataEx}.
    * @throws NotSpecifiableMetadataEx
    *            {@link NotSpecifiableMetadataEx}.
    *@throws EmptyDocumentEx
    *            {@link EmptyDocumentEx}.
    *@throws RequiredArchivableMetadataEx
    *            {@link RequiredArchivableMetadataEx}.
    * @throws SAEEnrichmentEx
    *            {@link SAEEnrichmentEx}.
    * @throws MappingFromReferentialException
    *            {@link MappingFromReferentialException}.
    * @throws InvalidSAETypeException
    *            {@link InvalidSAETypeException}.
    * @throws UnknownHashCodeEx
    *            {@link UnknownHashCodeEx}.
    */
   StorageDocument buildStorageDocumentForCapture(
         UntypedDocument untypedDocument) throws RequiredStorageMetadataEx,
         InvalidValueTypeAndFormatMetadataEx, UnknownMetadataEx,
         DuplicatedMetadataEx, NotArchivableMetadataEx,
         NotSpecifiableMetadataEx, EmptyDocumentEx,
         RequiredArchivableMetadataEx, SAEEnrichmentEx,
         MappingFromReferentialException, InvalidSAETypeException,
         UnknownHashCodeEx;
}
