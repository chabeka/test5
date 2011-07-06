package fr.urssaf.image.sae.storage.dfce.validation;

import org.apache.commons.lang.Validate;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocuments;

/**
 * 
 * @author akenore
 * 
 */

@Aspect
public class InsertionServiceValidation extends AbstractValidation {
	/**
	 * Valide l'argument de la méthode insertStorageDocument.
	 * 
	 * 
	 * @param storageDocument
	 *            : Le document à insérer
	 */
	@Before(value = "execution( java.util.UUID  fr.urssaf.image.sae.storage.dfce..InsertionServiceImpl.insertStorageDocument(..)) && args(storageDocument)")
	public final void insertStorageDocumentValidation(
			final StorageDocument storageDocument) {
		// ici on valide que le document n'est pas null
		Validate.notNull(storageDocument, getMessageHandler().getMessage(
				"insertion.document.required", "insertion.impact",
				"insertion.action"));
		// ici on vérifie que tous les composants du document ne sont pas null.
		if (storageDocument.getMetadatas() == null
				&& (storageDocument.getContent() == null && storageDocument
						.getFilePath() == null)) {
			Validate.notNull(storageDocument.getMetadatas(), getMessageHandler()
					.getMessage("insertion.document.component.required",
							"insertion.impact", "insertion.action.component"));
		}
	}

	/**
	 * Valide l'argument de la méthode bulkInsertStorageDocument
	 * 
	 * @param storageDocuments
	 *            : La liste des documents
	 * @param allOrNothing
	 *            : Paramètre qui indique si l'insertion sera réalisée en mode
	 *            "Tout ou rien".
	 */
	@Before(value = "execution( fr.urssaf.image..BulkInsertionResults  fr.urssaf.image.sae.storage.dfce..InsertionServiceImpl.bulkInsertStorageDocument(..)) && args(storageDocuments,allOrNothing)")
	public final void bulkInsertStorageDocumentValidation(
			final StorageDocuments storageDocuments, final boolean allOrNothing) {
		// ici on valide que le document n'est pas null
		Validate.notNull(storageDocuments,
				getMessageHandler().getMessage("bulk.insertion.document.required", "bulk.insertion.allOrNothing.impact", "bulk.insertion.allOrNothing.action"));
		// ici on vérifie que tous les composants du document ne sont pas null.
		Validate.notNull(storageDocuments.getListOfStorageDocument(),
				getMessageHandler().getMessage("bulk.insertion.document.component.required",  "bulk.insertion.allOrNothing.impact", "bulk.insertion.allOrNothing.action"));

	}
}
