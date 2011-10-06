/**
 * 
 */
package fr.urssaf.image.sae.services.batch;

import java.net.URI;

import fr.urssaf.image.sae.ecde.modele.resultats.Resultats;
import fr.urssaf.image.sae.ecde.modele.sommaire.Sommaire;
import fr.urssaf.image.sae.ecde.service.EcdeServices;
import fr.urssaf.image.sae.services.dispatchers.SAEServiceDispatcher;

/**
 * Wrapper pour les jobs de capture en masse.
 * 
 * @author Rhofir
 */
public class BulkCaptureJobWrapper implements Runnable {

	private String ecdeUrl;
	private final EcdeServices ecdeServices;
	private final BulkCaptureJob bulkCaptureJob;
	private final SAEServiceDispatcher serviceDispatcher;

	/**
	 * Constructeur.
	 * 
	 * @param urlEcde
	 *            : Url du somaire.xml
	 * @param ecdeServices
	 *            : Le service ECDE.
	 * @param bulkCaptureJob
	 *            : Le Job de capture en masse.
	 * @param serviceDispatcher
	 *            : Dispatcher.
	 */
	public BulkCaptureJobWrapper(final String urlEcde,
			final EcdeServices ecdeServices,
			final BulkCaptureJob bulkCaptureJob,
			final SAEServiceDispatcher serviceDispatcher) {
		this.ecdeUrl = urlEcde;
		this.ecdeServices = ecdeServices;
		this.bulkCaptureJob = bulkCaptureJob;
		this.serviceDispatcher = serviceDispatcher;
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	@SuppressWarnings("PMD.EmptyCatchBlock")
	public final void run() {
		// Appeler le service ECDE de récupération du sommaire.xml à partir de
		// l'URL
		try {
			URI ecdeUri = new URI(ecdeUrl);
			Sommaire sommaireEcde = ecdeServices.fetchSommaireByUri(ecdeUri);
			// Début traitement par BOB
			Resultats resultatEcde = bulkCaptureJob.bulkCapture(sommaireEcde);
			// Fin traitement par BOB
			// Appeler le service ECDE de persistance du résultat
			ecdeServices.persistResultat(resultatEcde);
		} catch (Exception except) {

			try {
				serviceDispatcher.dispatch(except);
			} catch (Exception e) {
				// ici on ne lève aucune exception
			}
		}
	}

	/**
	 * @param ecdeUrl
	 *            : URL du sommaire.
	 */
	public final void setEcdeUrl(final String ecdeUrl) {
		this.ecdeUrl = ecdeUrl;
	}

	/**
	 * @return URL du sommaire.
	 */
	public final String getEcdeUrl() {
		return ecdeUrl;
	}

}
