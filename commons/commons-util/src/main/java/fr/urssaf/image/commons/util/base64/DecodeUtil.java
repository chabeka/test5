package fr.urssaf.image.commons.util.base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.StringUtils;

public final class DecodeUtil {

   private DecodeUtil() {

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
    * @param file
    *           fichier codé en base64
    * @return une chaine de caractère au format ISO8859_1
    * @throws IOException
    */
   public static String decode(File file) throws IOException {

      return decode(file, CharEncoding.ISO_8859_1);
   }

   /**
    * decodage d'un fichier en base64
    * 
    * @param file
    *           ile fichier codé en base64
    * @param charsetName
    *           encoding
    * @return une chaine de caractère au format du charsetName
    * @throws IOException exception sur le fichier
    */
   public static String decode(File file, String charsetName)
         throws IOException {

      Base64InputStream base64InputStream = new Base64InputStream(
            new FileInputStream(file), false);

      int encodage = base64InputStream.read();
      StringBuffer value = new StringBuffer();
      while (encodage != -1) {

         byte encodageByte = (byte) encodage;
         @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
         String val = StringUtils.newString(new byte[] { encodageByte },
               charsetName);
         value.append(val);

         encodage = base64InputStream.read();

      }

      return value.toString();
   }
}
