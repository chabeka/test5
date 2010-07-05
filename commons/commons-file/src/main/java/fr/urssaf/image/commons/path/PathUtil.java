package fr.urssaf.image.commons.path;

import java.io.File;

/**
 * Bibliothèque de fonctions utilitaires pour manipuler les chemins de fichiers
 *
 */
public final class PathUtil {

   
   private PathUtil()
   {
      
   }
   
   /**
    * Concatène parent et child en utilisant les bons séparateurs de répertoire selon l'OS
    * @param parent The parent pathname string
    * @param child The child pathname string
    * @return le chemin concaténé
    */
   public static String combine(String parent, String child)
   {
      File file = new File(parent,child);
      return file.getPath();
   }
   
}
