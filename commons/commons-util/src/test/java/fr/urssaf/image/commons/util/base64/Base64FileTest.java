package fr.urssaf.image.commons.util.base64;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

public class Base64FileTest {

   private static final Logger LOG = Logger.getLogger(Base64FileTest.class);

   private final static String ENCODE = "src/test/resources/encode.txt";
   private final static String DECODE = "src/test/resources/decode.txt";

   private final static String SEPARATOR = System.getProperty("line.separator",
         "\n");

   @Test
   public void encodeFile() throws IOException {

      File file = new File(DECODE);
      String encode = EncodeUtil.encode(file);

      LOG.debug("encodage de :" + DECODE + ":");
      assertEquals("echec encodage en iso du fichier:" + DECODE + ":",
            "w4PCqQ0KYQ==" + SEPARATOR, encode);
   }

   @Test
   public void decodeFile() throws IOException {

      File file = new File(ENCODE);
      String decode = DecodeUtil.decode(file);

      LOG.debug("decodage de :" + ENCODE + ":");
      assertEquals("echec decodage en iso:" + ENCODE + ":", "Ã©" + SEPARATOR
            + "a", decode);

   }

   @Test
   public void decodeFileUTF8() throws IOException {

      File file = new File(ENCODE);
      String decode = DecodeUtil.decode(file, CharEncoding.ISO_8859_1);

      decode = StringUtils.newStringUtf8(StringUtils.getBytesIso8859_1(decode));
      LOG.debug("decodage de :" + ENCODE + ": en utf8");
      assertEquals("echec decodage en utf8:" + ENCODE + ":", "é" + SEPARATOR
            + "a", decode);

   }
}
