package fr.urssaf.image.sae.anais.framework.service.exception;

/**
 * Exception d'encapsulation pour les exceptions ANAIS
 * 
 *
 */
public class SaeAnaisApiException extends RuntimeException {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   /**
    * 
    * @param exception exception ANAIS
    */
   public SaeAnaisApiException(Exception exception) {
      super(exception);
   }

}
