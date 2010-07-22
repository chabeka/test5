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

public class Base64FileTest {

   private static final Logger LOG = Logger.getLogger(Base64FileTest.class);

   private final static String ENCODE = "src/test/resources/encoding/encode.txt";
   private final static String DECODE = "src/test/resources/encoding/decode.txt";

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
   public void encodeFile() throws IOException {

      String encode = FilenameUtils.concat(DIRECTORY, "decode.txt");

      EncodeUtil.encodeFile(DECODE, encode);
      String encodeString = EncodeUtil.encodeFileToString(DECODE, encode);

      LOG.debug("encodage de :" + DECODE + ":" + encodeString + ":");
      assertEquals("echec encodage en iso du fichier:" + DECODE + ":",
            "w6lhDQpheg0KDQo=\r\n", encodeString);
   }

   @Test
   public void decodeFile() throws IOException {

      String decode = FilenameUtils.concat(DIRECTORY, "encode.txt");
      String decodeString = DecodeUtil.decodeFileToString(ENCODE, decode);

      LOG.debug("decodage de :" + ENCODE + ":" + decodeString);
      assertEquals("echec decodage en iso:" + ENCODE + ":", "éa\r\naz\r\n\r\n",
            decodeString);

   }

}
