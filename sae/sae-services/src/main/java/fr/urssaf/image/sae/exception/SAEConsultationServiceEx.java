package fr.urssaf.image.sae.exception;

/**
 * Exception à utiliser pour les erreurs de dans la partie consultation.<BR/>
 */
public class SAEConsultationServiceEx extends SAEServiceException {

   /**
    * L'identifiant unique de l'exception
    */
   private static final long serialVersionUID = -298210295473447438L;

   /**
    * Construit une nouvelle {@link SAEConsultationServiceEx }.
    */
   public SAEConsultationServiceEx() {
      super();
   }

   /**
    * Construit une nouvelle {@link SAEConsultationServiceEx } avec un message et une cause
    * données.
    * 
    * @param message
    *           : Message de l'erreur
    * @param cause
    *           : Cause de l'erreur
    */
   public SAEConsultationServiceEx(final String message, final Throwable cause) {
      super(message, cause);
   }

   /**
    * Construit une nouvelle {@link SAEConsultationServiceEx } avec un message.
    * 
    * @param message
    *           : Le message de l'erreur
    */
   public SAEConsultationServiceEx(final String message) {
      super(message);
   }

   /**
    * Construit une nouvelle {@link SAEConsultationServiceEx } avec un message ,une cause
    * données et un code erreur donné .
    * 
    * @param message
    *           : Le message d'erreur
    * @param cause
    *           : La cause de l'erreur
    * @param codeErreur
    *           : Le code d'erreur
    */
   public SAEConsultationServiceEx(final String codeErreur, final String message,
         final Throwable cause) {
      super(message, cause);
      setCodeError(codeErreur);
   }
}
