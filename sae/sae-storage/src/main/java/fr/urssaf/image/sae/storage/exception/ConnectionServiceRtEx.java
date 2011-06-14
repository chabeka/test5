package fr.urssaf.image.sae.storage.exception;

/**
 * Exception levée par une méthode de la l’interface StorageConnectionService <BR />
 * 
 *<li>
 * Attribut serialVersionUID : Identifiant Unique de l'erreur</li>
 */
public class ConnectionServiceRtEx extends StorageRuntimeException {

   /**
    * L'identifiant unique de l'erreur
    */
   private static final long serialVersionUID = -7786562625725866505L;

 
   /**
    * Constructeur simple
    */
   public ConnectionServiceRtEx() {
      super();
   }

   /**
    * Constructeur
    * 
    * @param message : 
    *           Le message d'erreur
    */
   public ConnectionServiceRtEx(final String message) {
      super(message);
   }

   /**
    * Constructeur
    * 
    * @param message : 
    *           Le message d'erreur
    * @param cause : 
    *           La cause de l'erreur
    */
   public ConnectionServiceRtEx(final String message, final Throwable cause) {
      super(message, cause);
   }

}
