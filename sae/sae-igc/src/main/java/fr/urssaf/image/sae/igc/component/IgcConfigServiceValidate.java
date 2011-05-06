package fr.urssaf.image.sae.igc.component;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import fr.urssaf.image.sae.igc.exception.IgcConfigException;
import fr.urssaf.image.sae.igc.modele.IgcConfig;
import fr.urssaf.image.sae.igc.service.IgcConfigService;
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
    * Vérification de la validité des arguments entrée de la méthode
    * {@link IgcConfigService#loadConfig}
    * <ul>
    * <li><code>pathConfigFile</code> doit être renseigné</li>
    * <li>le fichier <code>pathConfigFile</code> doit exister</li>
    * </ul>
    * 
    * 
    * @param joinPoint
    *           jointure de la méthode {@link IgcConfigService#loadConfig}
    * @throws IgcConfigException
    *            une exception est levée sur les arguments en entrée
    */
   @Before(METHODE)
   public final void loadConfigBefore(JoinPoint joinPoint)
         throws IgcConfigException {

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

   /**
    * Vérification des propriétés de l'instance de {@link IgcConfig}
    * 
    * <ul>
    * 
    * <li>{@link IgcConfig#getRepertoireACRacines()} doit être renseigné</li>
    * <li>{@link IgcConfig#getRepertoireACRacines()} le chemin du répertoire
    * doit exister</li>
    * <li>{@link IgcConfig##getRepertoireCRLs()} doit être renseigné</li>
    * <li>{@link IgcConfig#getRepertoireCRLs()} le chemin du répertoire doit
    * exister</li>
    * <li>{@link IgcConfig#getUrlsTelechargementCRLs()} doit contenir au moins
    * une URL</li>
    * 
    * </ul>
    * 
    * @param joinPoint
    *           jointure de la méthode {@link IgcConfigService#loadConfig}
    * @param igcConfig
    *           instance de de {@link IgcConfig} en sortie de la méthode
    * @throws IgcConfigException
    *            une exception est levée sur l'instance de {@link IgcConfig} en
    *            sortie
    */
   @AfterReturning(pointcut = "execution(* fr.urssaf.image.sae.igc.service.IgcConfigService.loadConfig(..))", returning = "igcConfig")
   public final void loadConfigAfter(JoinPoint joinPoint, IgcConfig igcConfig)
         throws IgcConfigException {

      String pathConfig = (String) joinPoint.getArgs()[0];

      if (!StringUtils.isNotBlank(igcConfig.getRepertoireACRacines())) {

         throw new IgcConfigException(TextUtils.getMessage(
               IgcConfigService.AC_RACINES_REQUIRED, pathConfig));
      }

      if (!FileUtils.isDirectory(igcConfig.getRepertoireACRacines())
            || !FileUtils.isAbsolute(igcConfig.getRepertoireACRacines())) {

         throw new IgcConfigException(TextUtils.getMessage(
               IgcConfigService.AC_RACINES_NOTEXIST, igcConfig
                     .getRepertoireACRacines(), pathConfig));
      }

      if (!StringUtils.isNotBlank(igcConfig.getRepertoireCRLs())) {
         throw new IgcConfigException(TextUtils.getMessage(
               IgcConfigService.CRLS_REQUIRED, pathConfig));
      }

      if (!FileUtils.isDirectory(igcConfig.getRepertoireCRLs())
            || !FileUtils.isAbsolute(igcConfig.getRepertoireCRLs())) {

         throw new IgcConfigException(TextUtils.getMessage(
               IgcConfigService.CRLS_NOTEXIST, igcConfig.getRepertoireCRLs(),
               pathConfig));
      }

      if (CollectionUtils.isEmpty(igcConfig.getUrlsTelechargementCRLs())) {

         throw new IgcConfigException(TextUtils.getMessage(
               IgcConfigService.URLS_CRL_REQUIRED, pathConfig));
      }
   }

}
