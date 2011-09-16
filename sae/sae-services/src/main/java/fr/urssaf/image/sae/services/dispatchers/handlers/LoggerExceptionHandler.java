package fr.urssaf.image.sae.services.dispatchers.handlers;

import org.apache.log4j.Logger;

import fr.urssaf.image.sae.services.dispatchers.ExceptionHandler;

/**
 * Logger d'exception pour la chaine de responsabilité utilisée
 * par le {@link fr.urssaf.image.sae.services.dispatchers.ExceptionDispatcher dispatcher d'exception}.
 */
public class LoggerExceptionHandler extends ExceptionHandler {
   
   private Logger logger = Logger.getLogger(getClass());
   
   public LoggerExceptionHandler() {

   }

   /**
    * 
    * @param exception
    */
   public <T extends Exception> void handleException(T exception) throws T {
      logger.error(exception);
   }
}