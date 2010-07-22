package fr.urssaf.image.commons.util.base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharEncoding;

public final class Base64Decode {

   private Base64Decode() {

   }

   /**
    * Renvoie un texte encodé en ISO8859_1 traduit d'un format en base 64
    * 
    * @param text
    *           texte en base 64
    * @return
    */
   public static String decode(String text) {
      return StringUtils.newStringIso8859_1(Base64.decodeBase64(text));
   }

   /**
    * Renvoie un texte encodé en UTF-8 traduit d'un format en base 64
    * 
    * @param text
    *           texte en base 64
    * @return
    */
   public static String decodeUTF8(String text) {
      return StringUtils.newStringUtf8(Base64.decodeBase64(text));
   }

   /**
    * Renvoie un texte encodé selon charsetName traduit d'un format en base 64
    * 
    * @param text
    *           texte en base 64
    * @param charsetName
    *           encoding
    * @return
    */
   public static String decode(String text, String charsetName) {

      return StringUtils.newString(Base64.decodeBase64(text), charsetName);
   }

   /**
    * decodage d'un fichier en base64
    * 
    * @param encodeFileName
    *           chemin du fichier encodé
    * @param decodeFileName
    *           chemin de sortie du fichier décodé
    * @throws IOException
    *            exception sur les fichiers
    */
   public static void decodeFile(String encodeFileName, String decodeFileName)
         throws IOException {

      Base64InputStream input = new Base64InputStream(new FileInputStream(
            encodeFileName), false);

      FileOutputStream output = new FileOutputStream(decodeFileName);
      try {
         IOUtils.copy(input, output);
      } finally {
         input.close();
         output.close();
      }

   }

   /**
    * Renvoie la chaine décodée d'un fichier en base64
    * 
    * @param encodeFileName
    *           chemin du fichier encodé
    * @param decodeFileName
    *           chemin de sortie du fichier décodé
    * @return chaine décodée en UTF8
    * @throws IOException
    *            exception sur les fichiers
    */
   public static String decodeFileToString(String encodeFileName,
         String decodeFileName) throws IOException {

      decodeFile(encodeFileName, decodeFileName);
      return FileUtils.readFileToString(new File(decodeFileName),CharEncoding.UTF_8);

   }
}
