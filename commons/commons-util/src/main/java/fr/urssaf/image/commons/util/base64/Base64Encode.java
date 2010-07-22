package fr.urssaf.image.commons.util.base64;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.compress.utils.IOUtils;


public final class Base64Encode {

   private Base64Encode() {

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
    * Renvoie le contenu d'un fichier, en l'encodant en base 64
    * 
    * @param cheminFichier Le chemin du fichier
    * @return contenu du fichier en base64
    * @throws IOException
    *  
    */
   public static String encodeFileToString(String cheminFichier) throws IOException {
      FileInputStream fis = new FileInputStream(cheminFichier); 
      Base64InputStream base64inputStream = new Base64InputStream(fis,true,-1,null);
      StringBuffer base64 = new StringBuffer();
      byte[] buffer = new byte[1024];
      int lus;
      do
      {
         lus = base64inputStream.read(buffer);
         if (lus>0)
         {
            for(int i=0;i<lus;i++)
            {
               base64.append((char)buffer[i]);
            }
         }
      }
      while (lus>0);
      return base64.toString();
   }
}
