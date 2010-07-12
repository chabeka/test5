package fr.urssaf.image.commons.util.file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

/**
 * Fonctions de manipulations de fichier
 * Cette classe est une surcouche de  org.apache.commons.io
 */
public final class FileReaderUtil {

   private FileReaderUtil() {

   }

   /**
    * Lit intégralement un fichier et le restitue dans une chaîne de caractères
    * 
    * @param file
    *           le chemin du fichier à lire
    * @return la chaîne de caractères contenant l'ensemble du fichier
    * @throws IOException
    */
   public static String read(String file) throws IOException {
      return read(new File(file));
   }

   /**
    * Lit intégralement un fichier et le restitue dans une chaîne de caractères
    * 
    * @param file
    *           le chemin du fichier à lire
    * @param encoding
    *           l'encodage à utiliser pour lire le fichier (par exemple :
    *           "UTF-8")
    * @return la chaîne de caractères contenant l'ensemble du fichier
    * @throws IOException
    */
   public static String read(String file, String encoding) throws IOException {
      return read(new File(file), encoding);
   }

   /**
    * Lit intégralement un fichier et le restitue dans une chaîne de caractères
    * 
    * @param file
    *           le fichier à lire
    * @return la chaîne de caractères contenant l'ensemble du fichier
    * @throws IOException
    */
   public static String read(File file) throws IOException {
      return read(file, Charset.defaultCharset().name());
   }

   /**
    * Lit intégralement un fichier et le restitue dans une chaîne de caractères
    * 
    * @param file
    *           le fichier à lire
    * @param encoding
    *           l'encodage à utiliser pour lire le fichier (par exemple :
    *           "UTF-8")
    * @return la chaîne de caractères contenant l'ensemble du fichier
    * @throws IOException
    */
   public static String read(File file, String encoding) throws IOException {
      return  FileUtils.readFileToString(file, encoding);
   }

 

}
