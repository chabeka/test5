package fr.urssaf.image.sae.anais.framework.service.exception;

/**
 * Classe d'exception heritant de {@link IllegalArgumentException}<br>
 * Le port du serveur ANAIS n’est pas renseigné dans les paramètres de connexion
 * transmis
 */
public class PortNonRenseigneException extends IllegalArgumentException {

   private static final long serialVersionUID = 1L;

   /**
    * initialisation du message de l'exception
    */
   public PortNonRenseigneException() {
      super(
            "Le port du serveur ANAIS doit être renseigné dans les paramètres de connexion");
   }

}
