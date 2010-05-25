package fr.urssaf.image.commons.maquette.richard.benjamin.school.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import fr.urssaf.image.commons.maquette.urssaf.image.maquette.fixture.SimpleClass;


public class ReflexionTest{
	public static Logger log = Logger.getLogger( ReflexionTest.class );
	
	@Ignore
	@Test
	public void reflexion() throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		String simpeClass = "fr.urssaf.image.maquette.SimpleClass" ;
		String simpeInterface = "fr.urssaf.image.maquette.SimpleInterface" ;
		
		try {
			Class<SimpleClass> classSimpeClass = (Class<SimpleClass>) Class.forName( simpeClass ) ;
			Class[] interfList = classSimpeClass.getInterfaces();
			
			assertEquals( 1, interfList.length );
			assertEquals( simpeInterface, interfList[0].getName() );
			
			Constructor constructor = classSimpeClass.getConstructor() ;
			SimpleClass myObject = (SimpleClass) constructor.newInstance();

			assertEquals( "Hello", myObject.sayHello() ) ;

			
		} catch (ClassNotFoundException e) {
			fail(e.getMessage());
		}
	}
	
}
