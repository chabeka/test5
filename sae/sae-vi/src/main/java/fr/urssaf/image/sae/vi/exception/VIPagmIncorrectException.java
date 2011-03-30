package fr.urssaf.image.sae.vi.exception;

/**
 * Le ou les PAGM présents dans le VI sont invalides
 * 
 * 
 */
public class VIPagmIncorrectException extends VIVerificationException {

   private static final long serialVersionUID = 1L;

   /**
    * instancie le message d'exception 'Le ou les PAGM présents dans le VI sont
    * invalides'
    */
   public VIPagmIncorrectException() {
      super("Le ou les PAGM présents dans le VI sont invalides");
   }

   /**
    * 
    * @return "vi:InvalidPagm"
    */
   @Override
   public final String getSoapFaultCode() {

      return "vi:InvalidPagm";
   }

   /**
    * 
    * @return "Le ou les PAGM présents dans le VI sont invalides"
    */
   @Override
   public final String getSoapFaultMessage() {

      return "Le ou les PAGM présents dans le VI sont invalides";
   }

}
