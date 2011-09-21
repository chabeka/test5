package fr.urssaf.image.sae.services.exception.capture;

import fr.urssaf.image.sae.exception.SAEServiceException;

/**
 * Exception à utiliser pour les erreurs de dans la partie capture.<BR/>
 */
public class SAECaptureServiceEx extends SAEServiceException {

   /**
    * L'identifiant unique de l'exception
    */
   private static final long serialVersionUID = -298210295473447438L;

   /**
    * Construit une nouvelle {@link SAECaptureServiceEx }.
    */
   public SAECaptureServiceEx() {
      super();
   }

   /**
    * Construit une nouvelle {@link SAECaptureServiceEx } avec un message et une cause
    * données.
    * 
    * @param message
    *           : Message de l'erreur
    * @param cause
    *           : Cause de l'erreur
    */
   public SAECaptureServiceEx(final String message, final Throwable cause) {
      super(message, cause);
   }

   /**
    * Construit une nouvelle {@link SAECaptureServiceEx } avec un message.
    * 
    * @param message
    *           : Le message de l'erreur
    */
   public SAECaptureServiceEx(final String message) {
      super(message);
   }

   /**
    * Construit une nouvelle {@link SAECaptureServiceEx } avec un message ,une cause
    * données et un code erreur donné .
    * 
    * @param message
    *           : Le message d'erreur
    * @param cause
    *           : La cause de l'erreur
    * @param codeErreur
    *           : Le code d'erreur
    */
   public SAECaptureServiceEx(final String codeErreur, final String message,
         final Throwable cause) {
      super(message, cause);
      setCodeError(codeErreur);
   }
}
