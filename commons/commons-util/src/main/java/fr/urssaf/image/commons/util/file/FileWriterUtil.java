package fr.urssaf.image.commons.util.file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.output.FileWriterWithEncoding;

/**
 * Fonctions de manipulations de fichier
 * Cette classe est une surcouche de  org.apache.commons.io
 */
public final class FileWriterUtil {

   private FileWriterUtil() {

   }

   /**
    * Ecrit une ligne à la fin d'un fichier
    * 
    * @param text
    *           la ligne à écrire
    * @param file
    *           le chemin du fichier
    * @throws IOException
    */
   public static void write(String text, String file) throws IOException {
      write(text, new File(file));
   }

   /**
    * Ecrit une ligne à la fin d'un fichier
    * 
    * @param text
    *           la ligne à écrire
    * @param file
    *           le chemin du fichier
    * @param encoding
    *           l'encodage à utiliser (par exemple : "UTF-8")
    * @throws IOException
    */
   public static void write(String text, String file, String encoding)
         throws IOException {
      write(text, new File(file), encoding);
   }

   /**
    * Ecrit une ligne à la fin d'un fichier
    * 
    * @param text
    *           la ligne à écrire
    * @param file
    *           le fichier dans lequel écrire
    * @throws IOException
    */
   public static void write(String text, File file) throws IOException {
      write(text, file, Charset.defaultCharset().name());
   }

   /**
    * Ecrit une ligne à la fin d'un fichier
    * 
    * @param text
    *           la ligne à écrire
    * @param file
    *           le fichier dans lequel écrire
    * @param encoding
    *           l'encodage à utiliser (par exemple : "UTF-8")
    * @throws IOException
    */
   public static void write(String text, File file, String encoding)
         throws IOException {

      FileWriterWithEncoding writer = new FileWriterWithEncoding(file,
            encoding, true);
      try {
         writer.write(text);
      } finally {
         writer.close();
      }

   }

}