package fr.urssaf.image.sae.services.document.exception;

import fr.urssaf.image.sae.exception.SAEServiceException;

/**
 * Exception levée dans le service de consultation
 * 
 * 
 */
public class SAEConsultationServiceException extends SAEServiceException {

   private static final long serialVersionUID = 1L;

   private static final String MSG_EXCEPTION = "une exception a eu lieu dans la consultation";

   /**
    * le message de l'exception est {@value #MSG_EXCEPTION}
    * 
    * @param cause
    *           cause de l'exception
    */
   public SAEConsultationServiceException(Throwable cause) {
      super(MSG_EXCEPTION, cause);
   }

}
