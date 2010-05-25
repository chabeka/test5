package fr.urssaf.image.commons.maquette.richard.benjamin.school.java;

import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;


public class Log4jTest{
	public static Logger log = Logger.getLogger( Log4jTest.class );
	
	@Test
	public void log()
	{
		try{
			log.info("test") ;
		}catch(Exception e){
			fail( e.getMessage() ) ;
		}
	}
	
}
