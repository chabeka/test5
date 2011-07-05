package fr.urssaf.image.sae.ecde.exception;

import org.apache.log4j.Logger;

/**
 * Classe EcdeRuntimeException
 * 
 * Encapsulation des exceptions levées non spécifiées
 * 
 * 
 * */
public class EcdeRuntimeException extends RuntimeException {

   private static final long serialVersionUID = 1L;
   
   //logger
   public static final Logger LOG = Logger.getLogger(EcdeRuntimeException.class);

   /**
    * Constructor
    * 
    * @param message cause
    */
   public EcdeRuntimeException(String message) {
      super(message);
      
   }
   
}
