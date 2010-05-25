package fr.urssaf.image.commons.util.string;


public final class StringUtil {

	private StringUtil(){
		
	}
	
	public static boolean notEmpty(String value){
		
		if (value != null) {
			return !"".equals(value.trim());
		} 
			
		return false;
		
	}

	public static String trim(String value) {
		if (value != null) {
			return value.trim();
		} 
		return null;
	}
}
