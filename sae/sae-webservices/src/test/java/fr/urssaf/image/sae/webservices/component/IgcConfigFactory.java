package fr.urssaf.image.sae.webservices.component;

import java.io.IOException;

import org.springframework.core.io.FileSystemResource;

import fr.urssaf.image.sae.igc.modele.IgcConfig;
import fr.urssaf.image.sae.webservices.security.igc.IgcFactory;
import fr.urssaf.image.sae.webservices.security.igc.util.IgcUtils;

/**
 * Classe d'instance pour IGC
 * 
 * 
 */
public final class IgcConfigFactory {

   public static final String DOWNLOAD_URL = "http://cer69idxpkival1.cer69.recouv/";

   private IgcConfigFactory() {

   }

   /**
    * création d'un fichier igcConfig.xml dépose dans le répertoire temporaire
    * 
    * @param configFileName
    *           nom du fichier de configuration
    */
   public static void createIgcConfigXML(String configFileName) {

      createIgcConfigXML(configFileName,
            new String[] { DOWNLOAD_URL + "*.crl" });

   }

   /**
    * création d'un fichier igcConfig.xml dépose dans le répertoire temporaire
    * 
    * @param configFileName
    *           nom du fichier de configuration
    * @param urls
    *           urls de téléchargement
    */
   public static void createIgcConfigXML(String configFileName, String... urls) {

      // création du fichier igcConfig.xml dépose dans le répertoire temporaire
      try {
         IgcUtils.loadConfig(TempDirectoryFactory.DIRECTORY + "/"
               + configFileName, TempDirectoryFactory.AC_RACINE
               .getCanonicalPath(),
               TempDirectoryFactory.CRL.getCanonicalPath(), urls);
      } catch (IOException e) {
         throw new IllegalArgumentException(e);
      }

   }

   /**
    * création d'un objet igcConfig
    * 
    * @param igcConfig
    *           nom du fichier igcConfig
    * @return instance de IgcConfig
    */
   public static IgcConfig createIgcConfig(String igcConfig) {

      createIgcConfigXML(igcConfig);

      FileSystemResource igcConfigResource = new FileSystemResource(
            TempDirectoryFactory.DIRECTORY + "/" + igcConfig);
      return IgcFactory.createIgcConfig(igcConfigResource);
   }
}
