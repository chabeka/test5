package fr.urssaf.image.commons.util.base64;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

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

		
		LOG.debug(":"+FileUtil.read(DECODE)+":");
		String encode = EncodeUtil.encode(FileUtil.read(DECODE));

		//assertEquals(BASE64_ISO.length(), encode.length());
		assertEquals("mauvaise encodage","6Q0KYQ0K", encode);
	}

	@Test
	public void decodeFile() throws IOException {

		String decode = DecodeUtil.decode(EncodeUtil
				.encode(FileUtil.read(ENCODE)));
		LOG.debug("decode ISO8859_1:" + decode);
		LOG.debug("size ISO8859_1:" + decode.length());

		//assertEquals(BASE64_ISO.length(), decode.length());
		assertEquals("mauvaise décodage","6Q0KYQ=="+SystemUtils.LINE_SEPARATOR, decode);

	}
}
