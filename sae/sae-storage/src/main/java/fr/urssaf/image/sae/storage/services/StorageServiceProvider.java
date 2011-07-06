package fr.urssaf.image.sae.storage.services;

import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;
import fr.urssaf.image.sae.storage.services.connection.StorageConnectionService;
import fr.urssaf.image.sae.storage.services.storagedocument.StorageDocumentService;

/**
 * 
 * Fournit l’ensemble des services pour Manipuler les Objets DFCE
 * 
 */
public interface StorageServiceProvider {
	/**
	 * Permet d'initialiser les paramètres dont le service aura besoin
	 * 
	 * @param storageConnectionParameter
	 *            : Les paramètres de connexion à la base de stockage
	 */
	@SuppressWarnings("PMD.LongVariable")
	void setStorageServiceProviderParameter(
			final StorageConnectionParameter storageConnectionParameter);

	/**
	 * 
	 * @return les services d'insertion ,de recherche,récupération
	 */
	StorageDocumentService getStorageDocumentService();

	/**
	 * 
	 * @return l'interface des services de connexion
	 */
	StorageConnectionService getStorageConnectionService();
}
