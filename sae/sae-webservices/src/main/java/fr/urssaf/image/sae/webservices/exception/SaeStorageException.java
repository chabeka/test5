package fr.urssaf.image.sae.webservices.exception;

import fr.urssaf.image.sae.storage.exception.StorageException;

/**
 * Exception pour le package {@link fr.urssaf.image.sae.storage}
 * 
 * 
 */
public class SaeStorageException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   /**
    * 
    * @param cause
    *           exception levée de type {@link StorageException}
    */
   public SaeStorageException(Exception cause) {
      super(cause);
   }

   /**
    * 
    * @param cause
    *           message de l'exception
    */
   public SaeStorageException(String cause) {
      super(cause);
   }

}
