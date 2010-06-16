package fr.urssaf.image.testHudson;

import static org.junit.Assert.*;

import org.junit.Test;



public class UneClasseTest {

   @Test
   public void uneMethodeTest()
   {
      UneClasse laClasse = new UneClasse();
      String actual = laClasse.uneMethode();
      String expected = "test de compilation Hudson";
      assertEquals(expected, actual);
   }
   
   
}
