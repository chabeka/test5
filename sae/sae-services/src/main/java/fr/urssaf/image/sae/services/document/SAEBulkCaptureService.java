package fr.urssaf.image.sae.services.document;

import fr.urssaf.image.sae.storage.model.jmx.JmxIndicator;

/**
 * Fournit le service :<BR/>
 * <li>Capture en masse.</li> </lu>
 * 
 * @author rhofir.
 */
public interface SAEBulkCaptureService {

	/**
	 * Service pour l'opération <b>capture en Masse</b>
	 * 
	 * @param urlSommaire
	 *            : L’URL ECDE du fichier sommaire.xml décrivant le traitement
	 *            de masse.
	 */
	void bulkCapture(String urlSommaire);

	/**
	 * @param indicator
	 *            : Les indicateurs du service de stockage en masse.
	 * @return Les indicateurs Jmx au niveau du stockage.
	 */
	JmxIndicator retrieveJmxSAEBulkCaptureIndicator();

	/**
	 * Retourne true s'il y'a un thread actif sinon false.
	 * 
	 * @return true s'il y'a un thread actif sinon false.
	 */
	boolean isActive();
}
