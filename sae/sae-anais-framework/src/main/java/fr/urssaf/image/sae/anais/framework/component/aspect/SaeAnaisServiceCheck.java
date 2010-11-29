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
 * La classe valide les arguments en entrée des méthode de la classe
 * {@link fr.urssaf.image.sae.anais.framework.service.SaeAnaisService}<br>
 * C'est une classe Aspect qui neccessite une compilation particulière<br>
 * <br>
 * Avec Eclipse il suffit de convertir le projet en projet AspectJ Project<br>
 * Configure --> Convert to AspectJ Project <br>
 * <br>
 * Avec Maven il suffit de configurer le plugin <a
 * href='http://mojo.codehaus.org/aspectj-maven-plugin/'>
 * <code>aspectj-maven-plugin</code></a>
 */
@Aspect
public class SaeAnaisServiceCheck {

   private static final String METHODE = "execution(public * fr.urssaf.image.sae.anais.framework.service.SaeAnaisService.authentifierPourSaeParLoginPassword(..))";

   /**
    * la méthode vérifie les arguments en entrée de la méthode
    * {@link SaeAnaisService#authentifierPourSaeParLoginPassword} <br>
    * Si une des règles n'est pas respectée elle lève une exception spécifique<br>
    * <ul>
    * <li>environnement vide :{@link EnvironnementNonRenseigneException}</li>
    * <li>userLogin vide :{@link UserLoginNonRenseigneException}</li>
    * <li>userPassword vide :{@link UserPasswordNonRenseigneException}</li>
    * <li>serveur non vide et hote vide:{@link HoteNonRenseigneException}</li>
    * <li>serveur non vide et port vide:{@link PortNonRenseigneException}</li>
    * </ul>
    * <br>
    * l'ordre des arguments
    * {@link SaeAnaisService#authentifierPourSaeParLoginPassword} est importante<br>
    * <ul>
    * <li><code>joinPoint.getArgs()[0]</code>:<code>environnement</code></li>
    * <li><code>joinPoint.getArgs()[1]</code>:<code>serveur</code></li>
    * <li><code>joinPoint.getArgs()[2]</code>:<code>userLogin</code></li>
    * <li><code>joinPoint.getArgs()[1]</code>:<code>userPassword</code></li>
    * </ul>
    * 
    * @param joinPoint
    *           joinpoint de la méthode authentifierPourSaeParLoginPassword
    * @throws EnvironnementNonRenseigneException
    * @throws UserLoginNonRenseigneException
    * @throws UserPasswordNonRenseigneException
    * @throws HoteNonRenseigneException
    * @throws PortNonRenseigneException
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
