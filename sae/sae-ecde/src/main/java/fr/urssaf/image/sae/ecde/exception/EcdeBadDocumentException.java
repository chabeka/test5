package fr.urssaf.image.sae.ecde.exception;
/**
 * Classe exception appellée par la stratégie SommaireUnserializerStrategy
 * <br> 
 * Un document référencé dans le sommaire n'est pas valide : fichier absent, problème
 * <br>de droit, etc
 *
 */
public class EcdeBadDocumentException extends EcdeGeneralException {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * Constructor
    * 
    * @param message cause
    */
   public EcdeBadDocumentException(String message) {
      super(message);
   }

}
