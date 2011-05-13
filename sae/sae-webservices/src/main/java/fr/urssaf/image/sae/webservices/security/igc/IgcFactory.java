package fr.urssaf.image.sae.webservices.security.igc;

import org.springframework.core.io.FileSystemResource;

import fr.urssaf.image.sae.igc.IgcServiceFactory;
import fr.urssaf.image.sae.igc.exception.IgcConfigException;
import fr.urssaf.image.sae.igc.modele.IgcConfig;
import fr.urssaf.image.sae.igc.service.IgcConfigService;

/**
 * Classe d'instanciation de {@link IgcFactory}
 * 
 * 
 */
public final class IgcFactory {

   private IgcFactory() {

   }

   private static final String ICG_CONFIG_ERROR = "Une erreur s'est produite lors du chargement du fichier de configuration IGC";

   /**
    * initialisation des répertoires des fichiers AC racine et des CRL à partir
    * d'un fichier de configuration
    * 
    * <pre>
    * &lt;?xml version="1.0" encoding="UTF-8"?>
    * &lt;IgcConfig>
    * 
    *    &lt;repertoireACRacines>
    *       /appl/sae/certificats/ACRacine
    *    &lt;/repertoireACRacines>
    *    
    *    &lt;repertoireCRL>
    *       /appl/sae/certificats/CRL
    *    &lt;/repertoireCRL>
    *     
    *    &lt;URLTelechargementCRL>
    *       &lt;url>http://cer69idxpkival1.cer69.recouv/*.crl&lt;/url>
    *    &lt;/URLTelechargementCRL>
    *       
    * &lt;/IgcConfig>
    * </pre>
    * 
    * Une exception {@link IllegalArgumentException} avec le message
    * {@value #ICG_CONFIG_ERROR} est levée si une exception
    * {@link IgcConfigException} est levée
    * 
    * @param igcConfigResource
    *           fichier de configuration de l'IGC
    * @return instance de {@link IgcConfig}
    * 
    */
   public static IgcConfig createIgcConfig(FileSystemResource igcConfigResource) {

      IgcConfigService service = IgcServiceFactory.createIgcConfigService();

      try {
         return service.loadConfig(igcConfigResource.getPath());
      } catch (IgcConfigException e) {
         throw new IllegalArgumentException(ICG_CONFIG_ERROR, e);
      }
   }
}
