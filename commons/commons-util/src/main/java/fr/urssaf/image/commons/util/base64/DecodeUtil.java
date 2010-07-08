package fr.urssaf.image.commons.util.base64;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

public final class DecodeUtil {

	private DecodeUtil() {

	}

	/**
	 * Renvoie un texte encodé en ISO8859_1 traduit d'un format en base 64
	 * 
	 * @param text
	 *            texte en base 64
	 * @return
	 */
	public static String decode(String text) {
		return StringUtils.newStringIso8859_1(Base64.decodeBase64(text));
	}

	/**
	 * Renvoie un texte encodé en UTF-8 traduit d'un format en base 64
	 * 
	 * @param text
	 *            texte en base 64
	 * @return
	 */
	public static String decodeUTF8(String text) {
		return StringUtils.newStringUtf8(Base64.decodeBase64(text));
	}

	/**
	 * Renvoie un texte encodé selon charsetName traduit d'un format en base 64
	 * 
	 * @param text
	 *            texte en base 64
	 * @param charsetName
	 *            encoding
	 * @return
	 */
	public static String decode(String text, String charsetName) {

		return StringUtils.newString(Base64.decodeBase64(text), charsetName);
	}
}
