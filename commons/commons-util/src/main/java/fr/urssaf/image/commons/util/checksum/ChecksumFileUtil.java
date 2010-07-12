package fr.urssaf.image.commons.util.checksum;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
/**
 * Classe de calcul du checksum sur des fichiers
 * les différents algoritmes sont 
 *       CRC32
 *       MD5
 *       SHA_1
 *       SHA_256
 *       SHA_384
 *       SHA_512
 * Cette surcouche s'appuie sur 
 *       org.apache.commons.codec
 *       org.apache.commons.io
 * @author Bertrand BARAULT
 *
 */
public final class ChecksumFileUtil {
   
   private ChecksumFileUtil(){
      
   }

   /**
    * Renvoie le checksum en hexa avec MD5
    * @param path chemin du fichier
    * @return chaine de caractère en hexa
    * @throws IOException erreur sur le fichier
    */
   public static String md5(String path) throws IOException{
      InputStream data = new FileInputStream(path);
      return DigestUtils.md5Hex(data);
   }
   
   /**
    * Renvoie le checksum en hexa avec SHA_1
    * @param path chemin du fichier
    * @return chaine de caractère en hexa
    * @throws IOException erreur sur le fichier
    */
   public static String sha(String path) throws IOException{
      InputStream data = new FileInputStream(path);
      return DigestUtils.shaHex(data);
   }
   
   /**
    * Renvoie le checksum en hexa avec SHA_256
    * @param path chemin du fichier
    * @return chaine de caractère en hexa
    * @throws IOException erreur sur le fichier
    */
   public static String sha256(String path) throws IOException{
      InputStream data = new FileInputStream(path);
      return DigestUtils.sha256Hex(data);
   }
   
   /**
    * Renvoie le checksum en hexa avec SHA_384
    * @param path chemin du fichier
    * @return chaine de caractère en hexa
    * @throws IOException erreur sur le fichier
    */
   public static String sha384(String path) throws IOException{
      InputStream data = new FileInputStream(path);
      return DigestUtils.sha384Hex(data);
   }
   
   /**
    * Renvoie le checksum en hexa avec SHA_512
    * @param path chemin du fichier
    * @return chaine de caractère en hexa
    * @throws IOException erreur sur le fichier
    */
   public static String sha512(String path) throws IOException{
      InputStream data = new FileInputStream(path);
      return DigestUtils.sha512Hex(data);
   }
   
   /**
    * Renvoie le checksum en hexa avec CRC32
    * @param path chemin du fichier
    * @return chaine de caractère en hexa
    * @throws IOException erreur sur le fichier
    */
   public static String crc32(String path) throws IOException{
      File file = new File(path);
      return   Long.toHexString(FileUtils.checksumCRC32(file));
   }
}
