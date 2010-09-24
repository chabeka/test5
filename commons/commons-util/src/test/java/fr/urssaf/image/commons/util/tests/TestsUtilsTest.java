package fr.urssaf.image.commons.util.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests unitaires de la classe TestsUtils
 *
 */
@SuppressWarnings("PMD")
public class TestsUtilsTest {

   
   /**
    * Test de la méthode {@link TestsUtils#getTemporaryFileName}<br>
    * Version sans argument 
    */
   @Test
   public void getTemporaryFileName_Test1() {
      String resultat = TestsUtils.getTemporaryFileName();
      assertNotNull("Vérifie que le résultat n'est pas nul",resultat);
      assertTrue("Vérifie que le résultat n'est pas vide",resultat.length()>0);
   }
   
   
   /**
    * Test de la méthode {@link TestsUtils#getTemporaryFileName(String)}<br>
    * Version avec 1 argument (préfixe)<br>
    * Cas de test : le préfixe est null
    */
   @Test
   public void getTemporaryFileName_Test2() {
      String prefixe = null;
      String resultat = TestsUtils.getTemporaryFileName(prefixe);
      assertNotNull("Vérifie que le résultat n'est pas nul",resultat);
      assertTrue("Vérifie que le résultat n'est pas vide",resultat.length()>0);
   }
   
   
   /**
    * Test de la méthode {@link TestsUtils#getTemporaryFileName(String)}<br>
    * Version avec 1 argument (préfixe)<br>
    * Cas de test : le préfixe est vide
    */
   @Test
   public void getTemporaryFileName_Test3() {
      String prefixe = "";
      String resultat = TestsUtils.getTemporaryFileName(prefixe);
      assertNotNull("Vérifie que le résultat n'est pas nul",resultat);
      assertTrue("Vérifie que le résultat n'est pas vide",resultat.length()>0);
   }
   
   
   /**
    * Test de la méthode {@link TestsUtils#getTemporaryFileName(String)}<br>
    * Version avec 1 argument (préfixe)<br>
    * Cas de test : le préfixe est renseigné
    */
   @Test
   public void getTemporaryFileName_Test4() {
      String prefixe = "le_prefixe";
      String resultat = TestsUtils.getTemporaryFileName(prefixe);
      assertNotNull("Vérifie que le résultat n'est pas nul",resultat);
      assertTrue("Vérifie que le résultat n'est pas vide",resultat.length()>prefixe.length());
      assertTrue("Vérifie que le résultat commence par le préfixe demandé",resultat.startsWith(prefixe));
   }
   
   
   /**
    * Test de la méthode {@link TestsUtils#getTemporaryFileName(String,String)}<br>
    * Version avec 2 arguments (préfixe + suffixe)<br>
    * Cas de test : préfixe et suffixe non renseignés
    */
   @Test
   public void getTemporaryFileName_Test5() {
      String prefixe = null;
      String suffixe = null;
      String resultat = TestsUtils.getTemporaryFileName(prefixe,suffixe);
      assertNotNull("Vérifie que le résultat n'est pas nul",resultat);
      assertTrue("Vérifie que le résultat n'est pas vide",resultat.length()>0);
   }
   
   
   /**
    * Test de la méthode {@link TestsUtils#getTemporaryFileName(String,String)}<br>
    * Version avec 2 arguments (préfixe + suffixe)<br>
    * Cas de test : préfixe et suffixe vides
    */
   @Test
   public void getTemporaryFileName_Test6() {
      String prefixe = "";
      String suffixe = "";
      String resultat = TestsUtils.getTemporaryFileName(prefixe,suffixe);
      assertNotNull("Vérifie que le résultat n'est pas nul",resultat);
      assertTrue("Vérifie que le résultat n'est pas vide",resultat.length()>0);
   }
   
   
   /**
    * Test de la méthode {@link TestsUtils#getTemporaryFileName(String,String)}<br>
    * Version avec 2 arguments (préfixe + suffixe)<br>
    * Cas de test : préfixe et suffixe renseignés
    */
   @Test
   public void getTemporaryFileName_Test7() {
      String prefixe = "le_prefixe";
      String suffixe = "le_suffixe";
      String resultat = TestsUtils.getTemporaryFileName(prefixe,suffixe);
      assertNotNull("Vérifie que le résultat n'est pas nul",resultat);
      assertTrue("Vérifie que le résultat n'est pas vide",resultat.length()>prefixe.length());
      assertTrue("Vérifie que le résultat commence par le préfixe demandé",resultat.startsWith(prefixe));
      assertTrue("Vérifie que le résultat se termine par le suffixe demandé",resultat.endsWith(suffixe));
   }
   
}
