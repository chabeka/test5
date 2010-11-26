package fr.urssaf.image.sae.anais.framework.component.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisAdresseServeur;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisEnumCodesEnvironnement;

/**
 * Classe de validation des arguments en entrée des méthode de la classe
 * {@link fr.urssaf.image.sae.anais.framework.service.SaeAnaisService}<br>
 */
@Aspect
public class SaeAnaisServiceCheck {

   private static final String METHODE = "execution(public * fr.urssaf.image.sae.anais.framework.service.SaeAnaisService.authentifierPourSaeParLoginPassword(..))";

   /**
    * la méthode vérifie les arguments en entrée de la méthode
    * authentifierPourSaeParLoginPassword
    * @param joinPoint
    *           joinpoint de la méthode authentifierPourSaeParLoginPassword
    */
   @Before(METHODE)
   public final void authentifierPourSaeParLoginPasswordCheck(
         JoinPoint joinPoint) {

      SaeAnaisEnumCodesEnvironnement environnement = (SaeAnaisEnumCodesEnvironnement) joinPoint
            .getArgs()[0];
      SaeAnaisAdresseServeur serveur = (SaeAnaisAdresseServeur) joinPoint
            .getArgs()[1];
      String userLogin = (String) joinPoint.getArgs()[2];

      String userPassword = (String) joinPoint.getArgs()[3];

      // TODO SPECIFIER LES EXCEPTIONS

      if (environnement == null) {
         throw new IllegalArgumentException(
               "L’environnement (Développement / Validation  / Production) doit être renseigné");
      }

      if (userLogin == null) {
         throw new IllegalArgumentException(
               "L’identifiant de l’utilisateur doit être renseigné");
      }

      if (userPassword == null) {
         throw new IllegalArgumentException(
               "Le mot de passe de l’utilisateur doit être renseigné");
      }

      if (serveur != null) {

         if (serveur.getHote() == null) {
            throw new IllegalArgumentException(
                  "L’adresse IP ou le nom d’hôte du serveur ANAIS doit être renseigné dans les paramètres de connexion");
         }

         if (serveur.getPort() == null) {
            throw new IllegalArgumentException(
                  "Le port du serveur ANAIS doit être renseigné dans les paramètres de connexion");
         }

      }

   }

}
