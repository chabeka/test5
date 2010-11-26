package fr.urssaf.image.sae.anais.framework.service.exception;

/**
 * Classe d'exception heritant de {@link IllegalArgumentException}<br>
 * L’environnement (Production / Validation / Développement) n’est pas renseigné
 */
public class EnvironnementNonRenseigneException extends
      IllegalArgumentException {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   /**
    * initialisation du message de l'exception
    */
   public EnvironnementNonRenseigneException() {
      super(
            "L'environnement (Développement / Validation  / Production) doit être renseigné");
   }

}
