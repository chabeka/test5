package fr.urssaf.image.commons.cassandra.serializer.exception;

/**
 * Exception envoyée lorsqu'un problème est rencontré lors de la sérialisation
 * ou dé-sérialisation
 *
 */
public class SerializerException extends RuntimeException {

   /**
    * @see java.lang.RuntimeException
    */
   public SerializerException(String message) {
      super(message);
   }

   private static final long serialVersionUID = 3407921347858626769L;

}
