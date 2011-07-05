package fr.urssaf.image.sae.ecde.exception;

import org.apache.log4j.Logger;

/**
 * Classe EcdeGeneralException
 * 
 * Classe mère des exceptions non runtime levées par sae-ecde
 * Elle hérite de la classe Exceptions
 * 
 * 
 * */

public class EcdeGeneralException extends Exception {

   private static final long serialVersionUID = 1L;
   
   //logger
   public static final Logger LOG = Logger.getLogger(EcdeGeneralException.class);

   /**
    * Constructor
    * 
    * @param message cause
    */
   public EcdeGeneralException(String message) {
      super(message);
   }
   
}
