package fr.urssaf.image.sae.anais.framework.service.exception;

/**
 * Classe d'exception heritant de {@link IllegalArgumentException}<br>
 * Le mot de passe de l’utilisateur n’est pas renseigné
 * 
 */
public class UserPasswordNonRenseigneException extends IllegalArgumentException {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   /**
    * initialisation du message de l'exception
    */
   public UserPasswordNonRenseigneException() {
      super("Le mot de passe de l'utilisateur doit être renseigné");

   }

}
