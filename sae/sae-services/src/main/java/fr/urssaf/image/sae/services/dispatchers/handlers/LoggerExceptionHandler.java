package fr.urssaf.image.sae.services.dispatchers.handlers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import fr.urssaf.image.sae.services.dispatchers.AbstractExceptionHandler;

/**
 * Logger d'exception pour la chaine de responsabilité utilisée par le
 * {@link fr.urssaf.image.sae.services.dispatchers.ExceptionDispatcher
 * dispatcher d'exceptions}.
 */
@Component
public class LoggerExceptionHandler extends AbstractExceptionHandler {

	private static final Logger LOGGER = Logger
			.getLogger(LoggerExceptionHandler.class);

	/**
	 * Log l'exception reçue en paramètre * @param exception Exception à
	 * dispatcher, elle sera traitée par les handlers concrets.
	 * 
	 * @param <T>
	 *            : Le type générique.
	 * @throws T
	 *             exception levée
	 */
	@Override
	public final <T extends Exception> void handleException(T exception)
			throws T {
		LOGGER.error(exception);
	}
}