package fr.urssaf.image.commons.util.base64;

import static org.junit.Assert.assertEquals;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

@SuppressWarnings("PMD.JUnitAssertionsShouldIncludeMessage")
public class Base64Test {

	private static final Logger LOG = Logger.getLogger(Base64Test.class);

	private final static String TEST = "é" + SystemUtils.LINE_SEPARATOR + "a";
	private final static String BASE64_UTF8 = "w6kNCmE=";
	private final static String BASE64_ISO = "6Q0KYQ==";

	@SuppressWarnings("PMD.MethodNamingConventions")
	@Test
	public void encodeISO8859_1() {

		String encode = EncodeUtil.encode(TEST);

		assertEquals(BASE64_ISO.length(), encode.length());
		assertEquals(BASE64_ISO, encode);
	}

	@SuppressWarnings("PMD.MethodNamingConventions")
	@Test
	public void decodeISO8859_1() {

		String decode = DecodeUtil.decode(BASE64_ISO);
		LOG.debug("decode ISO8859_1:" + decode);
		LOG.debug("size ISO8859_1:" + decode.length());

		assertEquals(TEST.length(), decode.length());
		assertEquals(TEST, decode);

	}

	@Test
	public void encodeUTF8() {

		String encode = EncodeUtil.encodeUTF8(TEST);

		assertEquals(BASE64_UTF8.length(), encode.length());
		assertEquals(BASE64_UTF8, encode);
	}

	@Test
	public void decodeUTF8() {

		String decode = DecodeUtil.decodeUTF8(BASE64_UTF8);
		LOG.debug("decode UTF8:" + decode);
		LOG.debug("size UTF8:" + decode.length());

		assertEquals(TEST.length(), decode.length());
		assertEquals(TEST, decode);
	}
	
	@Test
	public void encode() {
		
		String TEST = "à@"+SystemUtils.LINE_SEPARATOR+"ç"+SystemUtils.LINE_SEPARATOR+"ù";

		String encode = EncodeUtil.encode(TEST, CharEncoding.ISO_8859_1);

		assertEquals("4EANCucNCvk=", encode);
	}

}
