package fr.urssaf.hectotest;

/**
 * Utilitaires de conversion
 *
 */
public class ConvertHelper 
{
    /**
     * Convert the byte array to an int starting from the given offset.
     *
     * @param b The byte array
     * @param offset The array offset
     * @return The integer
     */
    public static int byteArrayToInt(byte[] b, int offset) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            if (b.length > (i + offset)) value += (b[i + offset] & 0x000000FF) << shift;
        }
        return value;
    }
    
    public static long byteArrayToLong(byte[] b) {
    	long value = 0;
    	for (int i = 0; i < b.length; i++)
    	{
    	   value = (value << 8) + (b[i] & 0xff);
    	}
        return value;
    }
    
	public static String getReadableUTF8String(byte[] bytes) throws Exception {
		String result = "";
		// Cf http://fr.wikipedia.org/wiki/UTF-8
		
		int i = 0;
		while (i < bytes.length) {
			byte b = bytes[i];
			// 128 = 10000000
			if ((b & 128) == 0) {				
				// On est dans le cas d'un caractère à 7 bits : 0xxxxxxx
				if (b < 32) {
					// Caractère non imprimable
					result += "\\x" + getHexString(b);
				}
				else {
					result += (char) b;
				}
				i++;
				continue;
			}
			else {
				// Est-on dans cette forme là ? 110xxxxx 10xxxxxx
				// 192 = 11000000
				// 32 = 00100000
				if (((b & 192) == 192) && ((b & 32) == 0)) {
					if (i < bytes.length - 1) {
						byte b2 = bytes[i+1];
						// 64 = 01000000
						if (((b2 & 128) == 128) && ((b2 & 64) == 0)) {
							byte[] myBytes = new byte[2];
							myBytes[0] = b;
							myBytes[1] = b2;
							result += new String(myBytes, "UTF-8");
							i+=2;
							continue;						
						}
					}
				}
			}
			// Ce n'est pas un caractère UTF8
			result += "\\x" + getHexString(b);
			i++;
		}
		return result;
	}

	public static String getHexString(byte b) throws Exception {
		byte[] bytes = new byte[1];
		bytes[0] = b;
		return getHexString(bytes);
	}

	/**
	 * Renvoie la représentation hexadécimale d'un tableau de bytes
	 * @param bytes tableau de bytes
	 * @return
	 * @throws Exception
	 */
	public static String getHexString(byte[] bytes) throws Exception {
		  String result = "";
		  for (int i=0; i < bytes.length; i++) {
		    result +=
		          Integer.toString( ( bytes[i] & 0xff ) + 0x100, 16).substring( 1 );
		  }
		  return result;
	}
	
	/**
	 * Encode une chaîne de caractères en hexadécimal
	 * @param str chaîne à encoder
	 * @return chaîne encodée
	 */
	static String stringToHex(String str) {
		char[] chars = str.toCharArray();
		StringBuffer strBuffer = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			strBuffer.append(Integer.toHexString((int) chars[i]));
		}
		return strBuffer.toString();
	}
}
