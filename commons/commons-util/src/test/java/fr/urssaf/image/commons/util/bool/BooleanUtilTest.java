package fr.urssaf.image.commons.util.bool;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


/**
 * Tests unitaires de la classe BooleanUtil
 *
 */
public class BooleanUtilTest {

	
   /**
    * Tests unitaires de la méthode BooleanUtil.getBool()
    */
   @Test
	public void getBool() {

      assertTrue("Test de true",BooleanUtil.getBool(true));
	   assertTrue("Test de true",BooleanUtil.getBool(Boolean.TRUE));
	   
		assertFalse("Test de false",BooleanUtil.getBool(false));
		assertFalse("Test de false",BooleanUtil.getBool(Boolean.FALSE));
		
		assertFalse("Test de null",BooleanUtil.getBool(null));

	}

	
}
