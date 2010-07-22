package fr.urssaf.image.commons.util.base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;

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
    * Encodage d'un fichier en base64
    * 
    * @param decodeFileName
    *           chemin du fichier décodé
    * @param encodeFileName
    *           chemin de sortie du fichier encodé
    * @throws IOException
    *            exception sur les fichiers
    */
   public static void encodeFile(String decodeFileName, String encodeFileName)
         throws IOException {

      FileInputStream input = new FileInputStream(decodeFileName);

      Base64OutputStream output = new Base64OutputStream(new FileOutputStream(
            encodeFileName), true);

      try {
         IOUtils.copy(input, output);
      } finally {
         input.close();
         output.close();
      }

   }

   /**
    * Renvoie la chaine encodée en base64 d'un fichier
    * 
    * @param decodeFileName
    *           chemin du fichier décodé
    * @param encodeFileName
    *           chemin de sortie du fichier encodé
    * @return chaine en base64
    * @throws IOException
    *            exception sur les fichiers
    */
   public static String encodeFileToString(String decodeFileName,
         String encodeFileName) throws IOException {
      encodeFile(decodeFileName, encodeFileName);
      return FileUtils.readFileToString(new File(encodeFileName));

   }
}
