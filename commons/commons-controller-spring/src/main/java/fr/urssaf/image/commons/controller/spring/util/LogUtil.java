package fr.urssaf.image.commons.controller.spring.util;



/**
 * Fonctions de traces diverses 
 *
 */
public final class LogUtil {
	
	private LogUtil(){
		
	}

	
	public static String getLogger(String[] log, char separator) {
		StringBuffer info = new StringBuffer();

		if (log != null) {
			info.append(log[0]);
			for (int i = 1; i < log.length; i++) {
				info.append(separator);
				info.append(log[i]);
			}

		}

		return info.toString();

	}
	
}
