package fr.urssaf.image.commons.util.base64;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import fr.urssaf.image.commons.file.FileUtil;

public class Base64FileTest {

   private static final Logger LOG = Logger.getLogger(Base64FileTest.class);

   private final static String ENCODE = "src/test/resources/encode.txt";
   private final static String DECODE = "src/test/resources/decode.txt";

   @Test
   public void encodeFile() throws IOException {

      String text = FileUtil.read(DECODE, CharEncoding.UTF_8);
      String encode = EncodeUtil.encode(text);

      LOG.debug("encodage de :" + text + ":");
      assertEquals("echec encodage en iso:" + text + ":", "6Q0KYQ0K", encode);
   }

   @Test
   public void decodeFile() throws IOException {

      String text = FileUtil.read(ENCODE, CharEncoding.UTF_8);
      String decode = DecodeUtil.decode(text);

      LOG.debug("decodage de :" + decode + ":");
      assertEquals("echec decodage en iso:" + text + ":", "é"
            + SystemUtils.LINE_SEPARATOR + "a", decode);

   }
}
