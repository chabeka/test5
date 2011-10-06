package fr.urssaf.image.sae.services.dispatchers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.services.dispatchers.ExceptionDispatcher;
import fr.urssaf.image.sae.services.dispatchers.SAEServiceDispatcher;
import fr.urssaf.image.sae.services.dispatchers.handlers.LoggerExceptionHandler;
import fr.urssaf.image.sae.services.dispatchers.handlers.ThrowerExceptionHandler;

/**
 * Cette classe implémente l'interface {@link SAEServiceDispatcher }
 * 
 * @author akenore
 * 
 */
@Service
@Qualifier("saeServiceDispatcher")
@SuppressWarnings("PMD.LongVariable")
public class SAEServiceDispatcherImpl implements SAEServiceDispatcher {

	private ExceptionDispatcher exceptionDispatcher;
	private ThrowerExceptionHandler throwerException;
	private LoggerExceptionHandler loggerExceptionHandler;

	/**
	 * @return L'exception dispatcher
	 */
	public final ExceptionDispatcher getExceptionDispatcher() {
		return exceptionDispatcher;
	}

	/**
	 * @param exceptionDispatcher
	 *            : L'exception dispatcher
	 */
	public final void setExceptionDispatcher(
			ExceptionDispatcher exceptionDispatcher) {
		this.exceptionDispatcher = exceptionDispatcher;
	}

	/**
	 * @return L'exception throwerException
	 */
	public final ThrowerExceptionHandler getThrowerException() {
		return throwerException;
	}

	/**
	 * @param throwerException
	 *            : L'exception throwerException
	 */
	public final void setThrowerException(
			ThrowerExceptionHandler throwerException) {
		this.throwerException = throwerException;
	}

	/**
	 * @return Le gestionnaire de log
	 */
	public final LoggerExceptionHandler getLoggerExceptionHandler() {
		return loggerExceptionHandler;
	}

	/**
	 * @param loggerExceptionHandler
	 *            : Le gestionnaire de log
	 */
	public final void setLoggerExceptionHandler(
			LoggerExceptionHandler loggerExceptionHandler) {
		this.loggerExceptionHandler = loggerExceptionHandler;
	}

	/**
	 * {@inheritDoc}
	 */
	public final <T extends Exception> void dispatch(T exception) throws T {
		exceptionDispatcher.dispatch(exception);
	}

	/**
	 * Construit un objet de type {@link SAEServiceDispatcherImpl }
	 * 
	 * @param exceptionDispatcher
	 *            : L'exception dispatcher.
	 * @param throwerException
	 *            : L'exception throwerException
	 * @param loggerExceptionHandler
	 *            : Le gestionnaire de log
	 */
	@Autowired
	public SAEServiceDispatcherImpl(
			 ExceptionDispatcher exceptionDispatcher,
			 ThrowerExceptionHandler throwerException,
			 LoggerExceptionHandler loggerExceptionHandler) {
		this.exceptionDispatcher = exceptionDispatcher;
		this.loggerExceptionHandler = loggerExceptionHandler;
		this.throwerException = throwerException;
		// à l'initialisation du service on définit la chaîne de responsabilité.
		exceptionDispatcher.setHandler(loggerExceptionHandler
				.setSuccessor(throwerException));
	}

}
