package fr.urssaf.image.commons.util.logger;

import org.apache.log4j.Logger;


/**
 * Fonctions de traces diverses 
 *
 */
public final class Log {
	
	private Log(){
		
	}

	public static void exception(Logger log,Exception exception){
		log.error(exception.getMessage(), exception);
	}
	
	public static void exception(Logger log,Throwable exception){
		log.error(exception.getMessage(), exception);
	}
	
	public static void throwException(Logger log,Exception exception){
		exception(log, exception);
		throw new IllegalArgumentException(exception);
	}
	
	public static void throwException(Logger log,Throwable exception){
		exception(log, exception);
		throw new IllegalArgumentException(exception);
	}
}
