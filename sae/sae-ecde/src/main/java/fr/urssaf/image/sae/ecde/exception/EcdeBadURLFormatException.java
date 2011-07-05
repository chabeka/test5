package fr.urssaf.image.sae.ecde.exception;

import org.apache.log4j.Logger;

/**
 * Classe EcdeBadURLFormatException
 * 
 * Exception levée lorsqu'une URL ECDE ne respecte pas le format d'une URL ECDE
 * 
 * 
 * */


public class EcdeBadURLFormatException extends EcdeGeneralException {
   
   private static final long serialVersionUID = 1L;
   
   //logger
   public static final Logger LOG = Logger.getLogger(EcdeBadURLFormatException.class);

   
   /**
    * Constructor
    * 
    * @param message cause
    */
   public EcdeBadURLFormatException(String message) {
      super(message);
   }
}
