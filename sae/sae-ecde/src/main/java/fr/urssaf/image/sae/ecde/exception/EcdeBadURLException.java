package fr.urssaf.image.sae.ecde.exception;

/**
 * 
 * Exception levée lorsqu'une URL ECDE n'appartient à aucun ECDE connu
 * <br>
 * @see EcdeGeneralException
 *  
 * */

public class EcdeBadURLException extends EcdeGeneralException {
   
   private static final long serialVersionUID = 1L;
   
   /**
    * implémentation du message par défaut {@value #MESSAGE}
    * 
    * @param cause
    *           raison de l'exception levée
    */
   public EcdeBadURLException(String cause) {
      super(cause);
   }
   
   /**
    * implémentation du message par défaut {@value #MESSAGE}
    * 
    * @param message
    *           message de l'exception levée
    * @param cause
    *           raison de l'exception levée          
    */
   public EcdeBadURLException(String message, Throwable cause ) {
      super(message, cause);
   }   
}
