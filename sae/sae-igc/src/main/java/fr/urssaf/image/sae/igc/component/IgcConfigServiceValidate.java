package fr.urssaf.image.sae.igc.component;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import fr.urssaf.image.sae.igc.exception.IgcConfigException;
import fr.urssaf.image.sae.igc.util.FileUtils;
import fr.urssaf.image.sae.igc.util.TextUtils;

/**
 * Classe de validation des méthodes contenues dans la classe
 * <code>fr.urssaf.image.sae.igc.service.IgcConfigService</code>
 * 
 * 
 */
@Aspect
public class IgcConfigServiceValidate {

   @SuppressWarnings("PMD.LongVariable")
   public static final String IGC_CONFIG_NOTEXIST = "Le fichier de configuration IGC est introuvable (${0})";

   private static final String METHODE = "execution(* fr.urssaf.image.sae.igc.service.IgcConfigService.loadConfig(..))";

   public static final String ARG_EMPTY = "Le paramètre [${0}] n'est pas renseigné alors qu'il est obligatoire";

   /**
    * Vérification de la validité des arguments
    * <ul>
    * <li><code>pathConfigFile</code> doit être renseigné</li>
    * <li>le fichier <code>pathConfigFile</code> doit exister</li>
    * </ul>
    * 
    * 
    * @param joinPoint
    *           jointure de la méthode <code>loadConfig</code>
    * @throws IgcConfigException
    *            une exception est levée sur les arguments en entrée
    */
   @Before(METHODE)
   public final void loadConfig(JoinPoint joinPoint) throws IgcConfigException {

      String pathConfig = (String) joinPoint.getArgs()[0];

      if (!StringUtils.isNotBlank(pathConfig)) {

         throw new IllegalArgumentException(TextUtils.getMessage(ARG_EMPTY,
               "pathConfigFile"));
      }

      if (!FileUtils.isFile(pathConfig)) {

         throw new IgcConfigException(TextUtils.getMessage(IGC_CONFIG_NOTEXIST,
               pathConfig));
      }
   }

}
