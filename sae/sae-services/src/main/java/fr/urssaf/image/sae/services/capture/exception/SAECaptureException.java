package fr.urssaf.image.sae.services.capture.exception;


/**
 * Exception levée dans le service de capture
 * 
 * 
 */
public class SAECaptureException extends Exception {

   private static final long serialVersionUID = 1L;

   private static final String MSG_EXCEPTION = "Une exception a eu lieu dans la consultation";

   /**
    * le message de l'exception est {@value #MSG_EXCEPTION}
    * 
    * @param cause
    *           cause de l'exception
    */
   public SAECaptureException(Throwable cause) {
      super(MSG_EXCEPTION, cause);
   }

}
