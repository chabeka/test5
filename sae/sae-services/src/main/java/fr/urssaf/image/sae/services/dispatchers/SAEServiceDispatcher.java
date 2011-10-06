package fr.urssaf.image.sae.services.dispatchers;
/**
 * Interface du dispatcher
 * @author akenore
 *
 */
public interface SAEServiceDispatcher {
	/**
	 * Dispatcher d'exception du SAE.
	 * 
	 * @param exception
	 *            Exception à dispatcher, elle sera traitée par les handlers
	 *            concrets.
	 *             @param <T> : Le type générique.
	 * @throws T exception levée
	 */
	 <T extends Exception> void dispatch(final T exception) throws T;
}
