package fr.urssaf.image.sae.anais.framework.component.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisAdresseServeur;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisEnumCodesEnvironnement;
import fr.urssaf.image.sae.anais.framework.service.exception.EnvironnementNonRenseigneException;
import fr.urssaf.image.sae.anais.framework.service.exception.HoteNonRenseigneException;
import fr.urssaf.image.sae.anais.framework.service.exception.PortNonRenseigneException;
import fr.urssaf.image.sae.anais.framework.service.exception.UserLoginNonRenseigneException;
import fr.urssaf.image.sae.anais.framework.service.exception.UserPasswordNonRenseigneException;

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
    * 
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

      if (environnement == null) {
         throw new EnvironnementNonRenseigneException();
      }

      if (userLogin == null) {
         throw new UserLoginNonRenseigneException();
      }

      if (userPassword == null) {
         throw new UserPasswordNonRenseigneException();
      }

      if (serveur != null) {

         if (serveur.getHote() == null) {
            throw new HoteNonRenseigneException();
         }

         if (serveur.getPort() == null) {
            throw new PortNonRenseigneException();
         }

      }

   }

}
