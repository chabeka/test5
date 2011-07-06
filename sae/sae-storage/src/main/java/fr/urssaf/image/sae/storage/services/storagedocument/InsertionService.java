package fr.urssaf.image.sae.storage.services.storagedocument;

import java.util.UUID;

import fr.urssaf.image.sae.storage.exception.InsertionServiceEx;
import fr.urssaf.image.sae.storage.model.storagedocument.BulkInsertionResults;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocuments;

/**
 * Fournit les services d’insertion de document
 * 
 */
public interface InsertionService {
	// CHECKSTYLE:OFF
	/**
	 * Permet d'insérer un document unique
	 * 
	 * @param storageDocument
	 *            : Le document à stocker
	 * 
	 * @return L'identifiant unique du document
	 * 
	 * @throws InsertionServiceEx
	 *             Runtime exception typée
	 */
	UUID insertStorageDocument(StorageDocument storageDocument)
			throws InsertionServiceEx;

	/**
	 * Permet de réaliser une insertion en masse de documents
	 * 
	 * @param storageDocuments
	 *            : Les documents à stocker
	 * @param allOrNothing
	 *            : boolean qui permet de faire une insertion en masse en mode tout ou rien
	 * @return Le resultat des insertions réussies et échouées
	 */
	BulkInsertionResults bulkInsertStorageDocument(
			StorageDocuments storageDocuments,final boolean allOrNothing);

}
