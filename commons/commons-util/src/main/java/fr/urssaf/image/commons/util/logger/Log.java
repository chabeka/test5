package fr.urssaf.image.commons.util.logger;

import org.apache.log4j.Logger;

public final class Log {
	
	private Log(){
		
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
	
	public static void exception(Logger log,Exception exception){
		log.error(exception.getMessage(), exception);
	}
	
	public static void exception(Logger log,Throwable exception){
		log.error(exception.getMessage(), exception);
	}
	
	public static void throwException(Logger log,Exception exception){
		exception(log, exception);
		throw new RuntimeException(exception);
	}
	
	public static void throwException(Logger log,Throwable exception){
		exception(log, exception);
		throw new RuntimeException(exception);
	}
}
