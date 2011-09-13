package fr.urssaf.image.sae.storage.services.connection;

import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;
import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;

/**
 * Fournit le service de connexion à la base de stockage.
 */
public interface StorageConnectionService {

	/**
	 * Permet d'ouvrir une connexion
	 * 
	 * @throws ConnectionServiceEx
	 *             Exception liée à la connection.
	 */

	void openConnection() throws ConnectionServiceEx;

	/**
	 * Permet la fermeture d'une connection
	 */
	void closeConnexion();

	/**
	 * Permet vérifier si la connection est active.
	 *  	
	 * @return True si la connection est active sinon false
	 */
	boolean isActive();

	/**
	 * Permet d'initialiser les paramètres dont le service aura besoin.
	 * 
	 * @param storageConnectionParameter
	 *            : Les paramètres de connexion à la base de stockage.
	 */
	@SuppressWarnings("PMD.LongVariable")
	void setStorageConnectionServiceParameter(
			final StorageConnectionParameter storageConnectionParameter);

}
