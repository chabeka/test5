package fr.urssaf.image.sae.ecde.exception;

import org.apache.log4j.Logger;

/**
 * Classe EcdeBadURLException
 * 
 * Exception levée lorsqu'une URL ECDE n'appartient à aucun ECDE connu
 * 
 * 
 * */

public class EcdeBadURLException extends EcdeGeneralException {
   
   
   private static final long serialVersionUID = 1L;
   
   //logger
   public static final Logger LOG = Logger.getLogger(EcdeBadURLException.class);
   
   /**
    * Constructor
    * 
    * @param message cause
    */
   public EcdeBadURLException(String message) {
      super(message);
   }
   
}
