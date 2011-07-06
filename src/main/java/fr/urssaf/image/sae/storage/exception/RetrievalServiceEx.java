package fr.urssaf.image.sae.storage.exception;

/**
 * Exception levée par une méthode de la l’interface RetrievalService
 * 
 * <li>
 * A1ttribut serialVersionUID L'identifiant unique de l'exception</li>
 */
public class RetrievalServiceEx extends StorageException {
   /**
    * L'indentifiant unique de l'exception
    */
   private static final long serialVersionUID = 537491939481933226L;

   /**
    * Constructeur simple
    */
   public RetrievalServiceEx() {
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
   public RetrievalServiceEx(final String message, final Throwable cause) {
      super(message, cause);
   }

   /**
    * Constructeur
    * 
    * @param message
    *           : Le message d'erreur
    */
   public RetrievalServiceEx(final String message) {
      super(message);
   }

}
