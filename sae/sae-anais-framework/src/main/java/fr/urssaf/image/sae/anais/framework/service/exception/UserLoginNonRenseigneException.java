package fr.urssaf.image.sae.anais.framework.service.exception;

/**
 * Classe d'exception heritant de {@link IllegalArgumentException}<br>
 * L’identifiant de l’utilisateur n’est pas renseigné
 */
public class UserLoginNonRenseigneException extends IllegalArgumentException {

   
   private static final long serialVersionUID = 1L;

   /**
    * initialisation du message de l'exception
    */
   public UserLoginNonRenseigneException() {
      super("L’identifiant de l’utilisateur doit être renseigné");
   }

}
