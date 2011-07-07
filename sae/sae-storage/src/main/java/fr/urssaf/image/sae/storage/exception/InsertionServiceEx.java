package fr.urssaf.image.sae.storage.exception;

/**
 * Exception levée par une méthode de la l’interface InsertionService
 * 
 * <li>
 * Attribut serialVersionUID Identifiant unique de l'exception</li>
 */
public class InsertionServiceEx extends StorageException {

   /**
    * L'indentifiant unique de l'exception
    */
   private static final long serialVersionUID = 2518811610910367626L;

   /**
    * Constructeur simple
    */
   public InsertionServiceEx() {
      super();
   }

   /**
    * Constructeur
    * 
    * @param message
    *           : Le message d'erreur
    * @param cause
    *           : La cause de l'erreur
    */
   public InsertionServiceEx(final String message, final Throwable cause) {
      super(message, cause);
   }

   /**
    * Constructeur
    * 
    * @param message
    *           : Le message de l'erreur
    */
   public InsertionServiceEx(final String message) {
      super(message);
   }

}