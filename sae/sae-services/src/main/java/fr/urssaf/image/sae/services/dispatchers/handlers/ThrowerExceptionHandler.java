package fr.urssaf.image.sae.services.dispatchers.handlers;

import org.springframework.stereotype.Component;

import fr.urssaf.image.sae.services.dispatchers.AbstractExceptionHandler;

/**
 * Handler utilisé par la chaine de responsabilité du dispatcher d'exception.
 * 
 * Le but de cet handler est uniquement de lever une exception. C'est le dernier
 * maillon de la chaine car l'exception provoque l'arrêt du traitement.
 */
@Component
public class ThrowerExceptionHandler extends AbstractExceptionHandler {

	/**
	 * Lève l'exception reçue en paramètre. Utile en fin de chaine.
	 * 
	 * @param exception
	 *            Exception à dispatcher, elle sera traitée par les handlers
	 *            concrets.
	 * @param <T>
	 *            : Le type générique.
	 * @throws T
	 *             exception levée
	 */
	@Override
	public final <T extends Exception> void handleException(T exception)
			throws T {
		throw exception;
	}
}
