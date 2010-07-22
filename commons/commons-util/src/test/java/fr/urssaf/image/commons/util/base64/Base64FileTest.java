package fr.urssaf.image.commons.util.base64;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings({"PMD.ConsecutiveLiteralAppends","PMD.AvoidDuplicateLiterals"})
public class Base64FileTest {

   private static final Logger LOG = Logger.getLogger(Base64FileTest.class);

   private final static String ENCODE = "src/test/resources/encoding/encode.txt";

   private final static String DIRECTORY;

   static {
      DIRECTORY = FilenameUtils.concat(SystemUtils.getJavaIoTmpDir()
            .getAbsolutePath(), "base64");
   }

   @BeforeClass
   public static void init() throws IOException {
      File directory = new File(DIRECTORY);
      FileUtils.forceMkdir(directory);

      FileUtils.cleanDirectory(directory);
   }

   
   @Test
   public void decodeFile() throws IOException {

      String decode = FilenameUtils.concat(DIRECTORY, "encode.txt");
      String decodeString = Base64Decode.decodeFileToString(ENCODE, decode);

      LOG.debug("decodage de :" + ENCODE + ":" + decodeString);
      assertEquals("echec decodage en iso:" + ENCODE + ":", "éa\r\naz\r\n\r\n",
            decodeString);

   }
   
   
   @Test
   public void encodeFileToStringTest1() throws IOException {

      // Cas de test : fichier vide
      
      String cheminFichier = "src/test/resources/base64/fichier_vide";
      
      String base64 = Base64Encode.encodeFileToString(cheminFichier);
      
      StringBuffer expected = new StringBuffer();
      
      assertEquals(
            "echec encodage en base64 du fichier:" + cheminFichier + ":",
            expected.toString(),
            base64);
   }
   
   
   @Test
   public void encodeFileToStringTest2() throws IOException {

      // Cas de test : petit fichier texte, avec caractères accentués
      // (fichier utilisé pour les tests de la 1ère implémentation base64)
      
      String cheminFichier = "src/test/resources/base64/decode.txt";
      
      String base64 = Base64Encode.encodeFileToString(cheminFichier);
      
      StringBuffer expected = new StringBuffer(20);
      expected.append("w4PCqWENCmF6DQoNCg==");
      
      assertEquals(
            "echec encodage en base64 du fichier:" + cheminFichier + ":",
            expected.toString(),
            base64);
   }
   
   
   @Test
   public void encodeFileToStringTest3() throws IOException {

      // Cas de test : petit fichier texte
      
      String cheminFichier = "src/test/resources/base64/fichier_texte_60.txt";
      
      String base64 = Base64Encode.encodeFileToString(cheminFichier);
      
      StringBuffer expected = new StringBuffer(80);
      expected.append("MDEyMzQ1Njc4OQ0KMDEyMzQ1Njc4OQ0KMDEyMzQ1Njc4OQ0KMDEyMzQ1Njc4OQ0K");
      expected.append("MDEyMzQ1Njc4");
      
      assertEquals(
            "echec encodage en base64 du fichier:" + cheminFichier + ":",
            expected.toString(),
            base64);
   }
   
   
   @Test
   public void encodeFileToStringTest4() throws IOException {

      // Cas de test : fichier texte
      // (qui a généré un échec du test de la 1ère implémentation base64)
      
      String cheminFichier = "src/test/resources/base64/fichier_texte_65.txt";
      
      String base64 = Base64Encode.encodeFileToString(cheminFichier);
      
      StringBuffer expected = new StringBuffer(100);
      expected.append("MDEyMzQ1Njc4OQ0KMDEyMzQ1Njc4OQ0KMDEyMzQ1Njc4OQ0KMDEyMzQ1Njc4OQ0K");
      expected.append("MDEyMzQ1Njc4OQ0KMDEy");
      
      assertEquals(
            "echec encodage en base64 du fichier:" + cheminFichier + ":",
            expected.toString(),
            base64);
   }
   
   
   @Test
   public void encodeFileTest5() throws IOException {

      // Cas de test : fichier binaire
      
      String cheminFichier = "src/test/resources/base64/fichier_zip.zip";
      
      String base64 = Base64Encode.encodeFileToString(cheminFichier);
      
      StringBuffer expected = new StringBuffer(200);
      expected.append("UEsDBAoAAAAAAE+B9TwWdPdQEQAAABEAAAARAAAAZmljaGllcl90ZXh0ZS50eHRi");
      expected.append("bGENCmJsYQ0K6ehA5yYNClBLAQIUAAoAAAAAAE+B9TwWdPdQEQAAABEAAAARAAAA");
      expected.append("AAAAAAAAIAAAAAAAAABmaWNoaWVyX3RleHRlLnR4dFBLBQYAAAAAAQABAD8AAABA");
      expected.append("AAAAAAA=");
      
      assertEquals(
            "echec encodage en base64 du fichier:" + cheminFichier + ":",
            expected.toString(),
            base64);
   }

}
