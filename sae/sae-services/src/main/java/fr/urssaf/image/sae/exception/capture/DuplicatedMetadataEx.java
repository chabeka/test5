/**
 * 
 */
package fr.urssaf.image.sae.exception.capture;


/**
 * Exception est levée lors de la vérification de duplication des métadonnées.
 * 
 * @author rhofir.
 */
public class DuplicatedMetadataEx extends Exception {
   private static final long serialVersionUID = 1L;

   /**
    * Construit une nouvelle {@link DuplicatedMetadataEx } avec un message et
    * une cause données.
    * 
    * @param message
    *           : Le message d'erreur
    * @param cause
    *           : La cause de l'erreur
    */
   public DuplicatedMetadataEx(final String message, final Throwable cause) {
      super(message, cause);
   }

   /**
    * Construit une nouvelle {@link DuplicatedMetadataEx }avec un message.
    * 
    * @param message
    *           : Le message de l'erreur
    */
   public DuplicatedMetadataEx(final String message) {
      super(message);
   }
}