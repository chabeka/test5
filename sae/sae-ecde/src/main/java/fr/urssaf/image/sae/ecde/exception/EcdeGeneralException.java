package fr.urssaf.image.sae.ecde.exception;

import org.apache.log4j.Logger;

/**
 * 
 * Classe mère des exceptions non runtime levées par sae-ecde
 * <br>
 * {@link Exception}
 * 
 * */

public class EcdeGeneralException extends Exception {

   private static final long serialVersionUID = 1L;
   
   //logger
   public static final Logger LOG = Logger.getLogger(EcdeGeneralException.class);

   /**
    * implémentation du message par défaut {@value #MESSAGE}
    * 
    * @param cause
    *           raison de l'exception levée
    */
   public EcdeGeneralException(String cause) {
      super(cause);
   }
   
   /**
    * implémentation du message par défaut {@value #MESSAGE}
    * 
    * @param message
    *           message de l'exception levée
    * @param cause
    *           cause de l'exception levée
    */
   public EcdeGeneralException(String message, Throwable cause) {
      super(message, cause);
   }
   
}
