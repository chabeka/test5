package fr.urssaf.image.sae.saml.exception;

/**
 * La vérification de l'assertion SAML a échoué.
 * 
 * 
 */
public class SamlVerificationException extends Exception {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   /**
    * 
    * @param exception
    *           exception levée
    */
   protected SamlVerificationException(Exception exception) {
      super(exception);
   }

}
