package fr.urssaf.image.sae.webservices.component;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import fr.urssaf.image.sae.igc.IgcServiceFactory;
import fr.urssaf.image.sae.igc.exception.IgcConfigException;
import fr.urssaf.image.sae.igc.exception.IgcDownloadException;
import fr.urssaf.image.sae.igc.modele.IgcConfig;

/**
 * Classe d'instance pour les ceetificats, crl ect...
 * 
 * 
 */
public final class SecurityFactory {

   private SecurityFactory() {

   }

   /**
    * Téléchargement des crls à partir d'un fichier de igcConfig
    * 
    * @param configFileName
    *           nom fichier de igcConfig dans le répertoire temporaire
    */
   public static void downloadCRLs(String configFileName) {

      IgcConfig igcConfig;
      try {
         igcConfig = IgcServiceFactory.createIgcConfigService().loadConfig(
               TempDirectoryFactory.DIRECTORY + "/" + configFileName);
      } catch (IgcConfigException e) {
         throw new IllegalArgumentException(e);
      }
      downloadCRLs(igcConfig);
   }

   /**
    * Téléchargement des crls à partir d'une instance d'igcConfig
    * 
    * @param igcConfig
    *           instance de igcConfig
    */
   public static void downloadCRLs(IgcConfig igcConfig) {

      try {
         IgcServiceFactory.createIgcDownloadService().telechargeCRLs(igcConfig);
      } catch (IgcDownloadException e) {
         throw new IllegalArgumentException(e);
      }
   }

   /**
    * Téléchargement d'un CRL dans le répertoire temporaire
    * {@value TempDirectoryFactory#CRL}
    * 
    * @param crl
    *           nom du CRL
    */
   public static void downloadCRL(String crl) {

      download(crl, FilenameUtils.concat(TempDirectoryFactory.CRL
            .getAbsolutePath(), crl));
   }

   /**
    * Téléchargement d'un CRL dans le répertoire temporaire
    * {@value TempDirectoryFactory#CRL}
    * 
    * @param crl
    *           nom du CRL
    * @param destination
    *           nom du fichier destination dans le repertoire
    *           {@value TempDirectoryFactory#CRL}
    */
   public static void downloadCRL(String crl, String destination) {

      download(crl, FilenameUtils.concat(TempDirectoryFactory.CRL
            .getAbsolutePath(), destination));
   }

   /**
    * Téléchargement du certificat dans le répertoire temporaire
    * {@value TempDirectoryFactory#AC_RACINE}
    * 
    * @param certificat
    *           nom du certificat
    * 
    */
   public static void downloadCertificat(String certificat) {

      downloadCertificat(certificat, certificat);
   }

   /**
    * Téléchargement d'un certificat dans le répertoire temporaire
    * {@value TempDirectoryFactory#AC_RACINE}
    * 
    * @param certificat
    *           nom du certificat
    * @param destination
    *           nom du fichier destination dans le repertoire
    *           {@value TempDirectoryFactory#AC_RACINE}
    */
   public static void downloadCertificat(String certificat, String destination) {

      download(certificat, FilenameUtils.concat(TempDirectoryFactory.AC_RACINE
            .getAbsolutePath(), destination));
   }

   /**
    * Téléchargement d'un fichier vers une destination
    * 
    * @param file
    *           fichier à télécharger
    * @param destination
    *           fichier téléchargé
    */
   public static void download(String file, String destination) {

      try {

         FileUtils.copyURLToFile(new URL(IgcConfigFactory.DOWNLOAD_URL + file),
               new File(destination));

      } catch (IOException e) {
         throw new IllegalArgumentException(e);
      }
   }

}
