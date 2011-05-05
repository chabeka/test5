package fr.urssaf.image.sae.igc.util;

import java.io.File;

/**
 * Classe utilitaire sur la manipulation des fichiers
 * 
 * 
 */
public final class FileUtils {

   private FileUtils() {

   }

   /**
    * 
    * @param path
    *           chemin du répertoire
    * @return true si c'est un répertoire false sinon
    */
   public static boolean isDirectory(String path) {

      File file = new File(path);
      return file.isDirectory();
   }

   /**
    * 
    * @param path
    *           chemin du fichier
    * @return true si c'est un fichier false sinon
    */
   public static boolean isFile(String path) {

      File file = new File(path);
      return file.isFile();
   }
}
