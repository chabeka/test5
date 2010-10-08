package fr.urssaf.image.commons.maquette.theme;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.mock.web.MockFilterConfig;

/**
 * Tests unitaires de la classe {@link AbstractMaquetteTheme}
 *
 */
@SuppressWarnings("PMD")
public class AbstractMaquetteThemeTest {

   
   /**
    * Tests unitaires de la méthode {@link AbstractMaquetteTheme#getFilterValue(String)}
    */
   @Test
   public void getFilterValue() {
      
      MockFilterConfig filterConfig;
      AbstractMaquetteTheme theme;
      String valeur;
      
      // Test avec un FilterConfig à null
      filterConfig = null;
      theme = new MaquetteThemeParDefaut(filterConfig);
      
      valeur = theme.getFilterValue(null);
      assertEquals("",valeur);
      
      valeur = theme.getFilterValue("");
      assertEquals("",valeur);
      
      valeur = theme.getFilterValue("toto");
      assertEquals("",valeur);
      
      // Tests avec un FilterConfig non null
      
      filterConfig = new MockFilterConfig();
      filterConfig.addInitParameter("toto", "tata");
      theme = new MaquetteThemeParDefaut(filterConfig);
      
      valeur = theme.getFilterValue(null);
      assertEquals("",valeur);
      
      valeur = theme.getFilterValue("");
      assertEquals("",valeur);
      
      valeur = theme.getFilterValue("toto");
      assertEquals("tata",valeur);
      
   }
   
}
