package fr.urssaf.image.sae.igc.exception;

/**
 * Une erreur s'est produite lors du téléchargement des éléments de l'IGC.
 * 
 * 
 */
public class IgcDownloadException extends Exception {

   private static final long serialVersionUID = 1L;

   /**
    * implémentation d'un message d'exception spécifique
    */
   public IgcDownloadException() {
      super(
            "Une erreur s'est produite lors du téléchargement des éléments de l'IGC");

   }
}
