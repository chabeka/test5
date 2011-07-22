package fr.urssaf.image.sae.dfce.admin.services.connection;

import fr.urssaf.image.sae.dfce.admin.model.ConnectionParameter;
import fr.urssaf.image.sae.dfce.admin.services.exceptions.ConnectionServiceEx;

/**
 * Fournit les services :
 * <ul>
 * <li>
 * openConnection : Service de connection à la base.</li>
 * <li>
 * closeConnection : Service de déconnection à la base.</li>
 * </ul>
 * 
 * @author akenore
 * 
 */
public interface ConnectionService {
	/**
	 * Ouvre la connection
	 * 
	 * @throws ConnectionServiceEx
	 *             Lorsqu'un problème survient lors de la connxion
	 */
	void openConnection() throws ConnectionServiceEx;

	/**
	 * Ferme la connection
	 */
	void closeConnection();
	/**
	 * Initialise les paramètres de connexion
	 * @param connectionParameter : Les paramètres de connection
	 */
	@SuppressWarnings("PMD.LongVariable")
	void setConnectionParameter(final ConnectionParameter connectionParameter);
}
