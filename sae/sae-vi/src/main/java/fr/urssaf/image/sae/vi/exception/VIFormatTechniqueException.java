package fr.urssaf.image.sae.vi.exception;

import fr.urssaf.image.sae.saml.exception.SamlFormatException;

/**
 * Une erreur technique sur le format du VI a été détectée
 * 
 * 
 */
public class VIFormatTechniqueException extends VIVerificationException {

   private static final long serialVersionUID = 1L;

   /**
    * @param exception
    *           assertion SAML non conforme
    */
   public VIFormatTechniqueException(SamlFormatException exception) {
      super(exception);
   }

   /**
    * 
    * @return "wsse:InvalidSecurityToken"
    */
   @Override
   public final String getSoapFaultCode() {

      return "wsse:InvalidSecurityToken";
   }

   /**
    * 
    * @return "Le jeton de sécurité fourni est invalide"
    */
   @Override
   public final String getSoapFaultMessage() {

      return "Le jeton de sécurité fourni est invalide";
   }

}
