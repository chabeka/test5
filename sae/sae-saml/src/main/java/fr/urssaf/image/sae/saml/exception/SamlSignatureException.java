package fr.urssaf.image.sae.saml.exception;

/**
 * La signature de l'assertion n'est pas valide
 * 
 * 
 */
public class SamlSignatureException extends SamlVerificationException {

   private static final long serialVersionUID = 1L;

   /**
    * 
    * @param exception
    *           exception levée
    */
   public SamlSignatureException(Exception exception) {
      super(exception);
   }

}
