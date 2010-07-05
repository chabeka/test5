package fr.urssaf.image.commons.util.string;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


/**
 * Tests unitaires de la classe StringUtil
 *
 */
public class StringUtilTest {

   
   /**
    * Tests unitaires de la méthode StringUtil.isEmpty()
    */
   @Test
   public void isEmptyTest()
   {
      assertFalse("Test d'une chaîne non vide",StringUtil.isEmpty("toto"));
      assertTrue("Test d'une chaîne null",StringUtil.isEmpty(null));
      assertTrue("Test d'une chaîne avec un seul espace",StringUtil.isEmpty(" "));
      assertTrue("Test d'une chaîne avec uniquement des espaces",StringUtil.isEmpty("   "));
      assertFalse("Test d'une chaîne avec quelques espaces",StringUtil.isEmpty(" toto  "));
   }
   
   
   /**
    * Tests unitaires de la méthode StringUtil.notEmpty()
    */
   @Test
   public void notEmptyTest()
   {
      assertTrue("Test d'une chaîne non vide",StringUtil.notEmpty("toto"));
      assertFalse("Test d'une chaîne null",StringUtil.notEmpty(null));
      assertFalse("Test d'une chaîne avec un seul espace",StringUtil.notEmpty(" "));
      assertFalse("Test d'une chaîne avec uniquement des espaces",StringUtil.notEmpty("   "));
      assertTrue("Test d'une chaîne avec quelques espaces",StringUtil.notEmpty(" toto  "));
   }
   
   
   
   /**
    * Tests unitaires de la méthode StringUtil.trim()
    */
   @Test
   public void trimTest()
   {
      assertEquals("Test d'une chaîne null",null,StringUtil.trim(null));
      assertEquals("Test d'une chaîne sans espaces","toto",StringUtil.trim("toto"));
      assertEquals("Test d'une chaîne avec un espace","",StringUtil.trim(" "));
      assertEquals("Test d'une chaîne avec plusieurs espaces","",StringUtil.trim("    "));
      assertEquals("Test d'une chaîne avec des espaces devant","test",StringUtil.trim("  test"));
      assertEquals("Test d'une chaîne avec des espaces derrière","test",StringUtil.trim("test   "));
      assertEquals("Test d'une chaîne avec des espaces autour","test",StringUtil.trim("   test   "));
   }
   
}
