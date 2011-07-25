package fr.urssaf.image.sae.storage.services.storagedocument;

import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;

/**
 * Fournit l’ensemble des services : <BR />
 * <ul>
 * <li>
 * {@link InsertionService } : services d’insertion.</li>
 * <li>
 * {@link SearchingService} : services de recherche.</li>
 * <li>
 * {@link RetrievalService} : services de récupération.</li>
 * <li>
 * {@link DeletionService} : services de suppression.</li>
 * </ul>
 */
public interface StorageDocumentService extends InsertionService,
		SearchingService, RetrievalService, DeletionService {
	/**
	 * Permet d'initialiser les paramètres dont le service aura besoin
	 * 
	 * @param storageConnectionParameter
	 *            : Les paramètres de connexion à la base de stockage
	 */
	@SuppressWarnings("PMD.LongVariable")
	void setStorageDocumentServiceParameter(
			final StorageConnectionParameter storageConnectionParameter);

}
