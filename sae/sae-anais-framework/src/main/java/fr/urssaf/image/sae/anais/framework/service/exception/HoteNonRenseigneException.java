package fr.urssaf.image.sae.anais.framework.service.exception;

/**
 * Classe d'exception heritant de {@link IllegalArgumentException}<br>
 * L’adresse IP ou le nom d’hôte du serveur ANAIS n’est pas renseigné dans les
 * paramètres de connexion
 */
public class HoteNonRenseigneException extends IllegalArgumentException {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   /**
    * initialisation du message de l'exception
    */
   public HoteNonRenseigneException() {
      super(
            "L'adresse IP ou le nom d'hôte du serveur ANAIS doit être renseigné dans les paramètres de connexion");
   }

}
