package fr.urssaf.image.commons.util.number;


public final class NumberUtil {

	private NumberUtil(){
		
	}
	
	public static String toString(Number num) {
		return num != null ? num.toString() : null;
	}

	public static boolean positif(Number num) {
		return num != null ? IntegerUtil.positif(num.intValue()) : true;
	}

	public static boolean negatif(Number num) {
		return num != null ? IntegerUtil.negatif(num.intValue()) : true;
	}

}
