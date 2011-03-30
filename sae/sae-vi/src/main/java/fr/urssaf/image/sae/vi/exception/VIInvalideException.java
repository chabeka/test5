package fr.urssaf.image.sae.vi.exception;

/**
 * Le VI est invalide
 * 
 * 
 */
public class VIInvalideException extends VIVerificationException {

   private static final long serialVersionUID = 1L;

   /**
    * 
    * @param msg
    *           message de l'exception
    */
   public VIInvalideException(String msg) {
      super(msg);
   }

   /**
    * 
    * @return "vi:InvalidVI"
    */
   @Override
   public final String getSoapFaultCode() {

      return "vi:InvalidVI";
   }

   /**
    * 
    * @return "Le VI est invalide"
    */
   @Override
   public final String getSoapFaultMessage() {

      return "Le VI est invalide";
   }

}
