package fr.urssaf.image.sae.storage.exception;

/**
 * Exception levée par une méthode de la l’interface SearchingService
 * 
 * <li>
 * Attribute serialVersionUID L'indentifiant unique de l'exception
 */
public class SearchingServiceEx extends StorageException {

   /**
    * L'identifiant unique de l'exception
    */
   private static final long serialVersionUID = -298210295473447438L;

   /**
    * Constructeur simple
    */
   public SearchingServiceEx() {
      super();
   }

   /**
    * Constructeur
    * 
    * @param message
    *           : Message de l'erreur
    * @param cause
    *           : Cause de l'erreur
    */
   public SearchingServiceEx(final String message, final Throwable cause) {
      super(message, cause);
   }

   /**
    * Constructeur
    * 
    * @param message
    *           : Le message de l'erreur
    */
   public SearchingServiceEx(final String message) {
      super(message);
   }

}
