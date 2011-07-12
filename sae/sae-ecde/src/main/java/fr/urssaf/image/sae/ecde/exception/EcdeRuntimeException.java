package fr.urssaf.image.sae.ecde.exception;

/**
 * Encapsulation des exceptions levées non spécifiées
 * <br>
 * L'exception hérite de {@link RuntimeException}
 * */
public class EcdeRuntimeException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   /**
    * implémenation d'un message spécifique
    * 
    * @param message
    *           message de l'exception levée
    */
   public EcdeRuntimeException(String message) {
      super(message);
   }

}
