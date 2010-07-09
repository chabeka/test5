package fr.urssaf.image.commons.util.base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.StringUtils;

public final class EncodeUtil {

   private EncodeUtil() {

   }

   /**
    * renvoie le text en base 64 au codage ISO8859_1
    * 
    * @param text
    *           texte à encoder
    * @return chaine de caractères en base 64
    */
   public static String encode(String text) {

      byte[] iso = StringUtils.getBytesIso8859_1(text);
      return encode(iso);
   }

   /**
    * renvoie le text en base 64 au codage UTF-8
    * 
    * @param text
    *           texte à encoder
    * @return chaines de caractères en base 64
    */
   public static String encodeUTF8(String text) {

      byte[] utf8 = StringUtils.getBytesUtf8(text);
      return encode(utf8);
   }

   /**
    * renvoie le text en base 64 au codage spécifié par charsetName
    * 
    * @param text
    *           texte à encoder
    * @param charsetName
    *           codage
    * @return
    */
   public static String encode(String text, String charsetName) {

      byte[] encodage = StringUtils.getBytesUnchecked(text, charsetName);
      return encode(encodage);
   }

   private static String encode(byte[] text) {
      return StringUtils.newStringUtf8(Base64.encodeBase64(text, false));
   }

   /**
    * Renvoie la chaine de caractère en base64 d'un fichier
    * @param file fichier
    * @return chaine de caractère
    * @throws IOException exception sur le fichier
    */
   public static String encode(File file) throws IOException {

      Base64InputStream base64InputStream = new Base64InputStream(
            new FileInputStream(file), true);

      int encodage = base64InputStream.read();
      StringBuffer value = new StringBuffer();

      while (encodage != -1) {

         byte encodageByte = (byte) encodage;
         @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
         String valueAscii = StringUtils
               .newStringUsAscii(new byte[] { encodageByte });
         value.append(valueAscii);

         encodage = base64InputStream.read();

      }

      return value.toString();
   }
}
