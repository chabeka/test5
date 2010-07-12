package fr.urssaf.image.commons.util.checksum;

import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Classe de calcul du checksum sur des chaines de caractères
 * les différents algorithmes sont
 *       MD5
 *       SHA_1
 *       SHA_256
 *       SHA_384
 *       SHA_512
 * Cette surcouche s'appuie sur 
 *       org.apache.commons.codec
 * @author Bertrand BARAULT
 *
 */
public final class ChecksumStringUtil {

   private ChecksumStringUtil() {

   }

   /**
    * Renvoie le checksum en hexa avec MD5
    * 
    * @param data
    *           chaine de caractère
    * @return chaine de caractère en hexa
    */
   public static String md5(String data) {
      return DigestUtils.md5Hex(data);
   }

   /**
    * Renvoie le checksum en hexa avec SHA_1
    * 
    * @param data
    *           chaine de caractère
    * @return chaine de caractère en hexa
    */
   public static String sha(String data) {
      return DigestUtils.shaHex(data);
   }

   /**
    * Renvoie le checksum en hexa avec SHA_256
    * 
    * @param data
    *           chaine de caractère
    * @return chaine de caractère en hexa
    */
   public static String sha256(String data) {
      return DigestUtils.sha256Hex(data);
   }

   /**
    * Renvoie le checksum en hexa avec SHA_384
    * 
    * @param data
    *           chaine de caractère
    * @return chaine de caractère en hexa
    */
   public static String sha384(String data) throws IOException {
      return DigestUtils.sha384Hex(data);
   }

   /**
    * Renvoie le checksum en hexa avec SHA_512
    * 
    * @param data
    *           chaine de caractère
    * @return chaine de caractère en hexa
    */
   public static String sha512(String data) throws IOException {
      return DigestUtils.sha512Hex(data);
   }

}
