package fr.urssaf.image.sae.webservices.component;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;

/**
 * Classe d'instance des répertoires des dépôts des AC, CRL...
 * 
 * 
 */
public final class TempDirectoryFactory {

   public static final File DIRECTORY;

   public static final File CRL;

   public static final File AC_RACINE;

   static {
      DIRECTORY = new File(FilenameUtils.concat(SystemUtils.getJavaIoTmpDir()
            .getAbsolutePath(), "sae_webservices"));

      CRL = new File(DIRECTORY + "/CRL/");
      AC_RACINE = new File(DIRECTORY + "/ACRacine/");
   }

   private TempDirectoryFactory() {

   }

   /**
    * création et nettoyage du répertoire principal {@value #DIRECTORY}
    * 
    */
   public static void createDirectory() {

      try {
         FileUtils.forceMkdir(DIRECTORY);
         cleanDirectory();
      } catch (IOException e) {
         throw new IllegalArgumentException(e);
      }

   }

   /**
    * nettoyage du répertoire principal {@value #DIRECTORY}
    */
   public static void cleanDirectory() {

      try {
         FileUtils.cleanDirectory(DIRECTORY);
      } catch (IOException e) {
         throw new IllegalArgumentException(e);
      }
   }

   /**
    * création du répertoire de dépôt de {@value #CRL}
    */
   public static void createCRLDirectory() {

      try {
         FileUtils.forceMkdir(CRL);
      } catch (IOException e) {
         throw new IllegalArgumentException(e);
      }
   }

   /**
    * nettoyage de {@value #CRL}
    */
   public static void cleanCRLDirectory() {

      try {
         FileUtils.cleanDirectory(CRL);
      } catch (IOException e) {
         throw new IllegalArgumentException(e);
      }
   }

   /**
    * création du répertoire de dépôt des AC racine {@value #AC_RACINE}
    * 
    */
   public static void createACDirectory() {

      try {
         FileUtils.forceMkdir(AC_RACINE);
      } catch (IOException e) {
         throw new IllegalArgumentException(e);
      }
   }

}
