package fr.urssaf.image.commons.maquette;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.mock.web.MockFilterConfig;

import fr.urssaf.image.commons.maquette.config.MaquetteFilterConfig;
import fr.urssaf.image.commons.maquette.constantes.ConstantesConfigFiltre;
import fr.urssaf.image.commons.maquette.exception.MaquetteThemeException;
import fr.urssaf.image.commons.maquette.tool.MaquetteConstant;


/**
 * Tests unitaires de la classe {@link MaquetteFilterConfig}
 *
 */
@SuppressWarnings("PMD")
public class MaquetteFilterConfigTest {
   
   
   /**
    * Test du constructeur avec tous les paramètres de la configuration renseignés
    * et corrects.<br>
    * <br>
    * Résultat attendu : aucune erreur, les implémentations du menu et des boîtes de
    * gauche sont instantiées.
    * 
    * @throws MaquetteThemeException
    */
   @Test
   public void testCasNormal()
   throws
   MaquetteThemeException {
      
      // Remplissage du FilterConfig
      MockFilterConfig filterConfig = new MockFilterConfig();
      filterConfig.addInitParameter(ConstantesConfigFiltre.EXCLUDEFILES, "THE_EXCLUDEFILES");
      filterConfig.addInitParameter(ConstantesConfigFiltre.APPTITLE, "THE_APPTITLE");
      filterConfig.addInitParameter(ConstantesConfigFiltre.COPYRIGHT, "THE_COPYRIGHT");
      filterConfig.addInitParameter(ConstantesConfigFiltre.STANDARD_AND_NORM, "1");
      filterConfig.addInitParameter(ConstantesConfigFiltre.IMPL_MENU, "fr.urssaf.image.commons.maquette.fixture.FixtureMenu");
      filterConfig.addInitParameter(ConstantesConfigFiltre.IMPL_LEFTCOL, "fr.urssaf.image.commons.maquette.fixture.FixtureBoitesDeGauche");
      filterConfig.addInitParameter(ConstantesConfigFiltre.THEME, MaquetteConstant.THEME_AED);
      
      // Instantiation de l'objet à tester
      MaquetteFilterConfig maqFilterConfig = new MaquetteFilterConfig(filterConfig);
      
      // Vérifications
      
      assertEquals(
            "Erreur sur excludeFiles",
            "THE_EXCLUDEFILES",
            maqFilterConfig.getExcludeFiles());
      
      assertEquals(
            "Erreur sur appTitle",
            "THE_APPTITLE",
            maqFilterConfig.getAppTitle());
      
      assertEquals(
            "Erreur sur appCopyright",
            "THE_COPYRIGHT",
            maqFilterConfig.getAppCopyright());
      
      assertTrue(
            "Erreur sur appDisplayStandardsAndNorms",
            maqFilterConfig.getAppDispStdNorms());
      
      assertEquals(
            "Erreur sur implementationIMenu",
            "fr.urssaf.image.commons.maquette.fixture.FixtureMenu",
            maqFilterConfig.getImplMenu());
      
      assertEquals(
            "Erreur sur implementationILeftCol",
            "fr.urssaf.image.commons.maquette.fixture.FixtureBoitesDeGauche",
            maqFilterConfig.getImplLeftCol());
      
      assertNotNull(
            "L'objet représentant le thème est null",
            maqFilterConfig.getTheme());
      
      assertEquals(
            "Erreur sur theme",
            "fr.urssaf.image.commons.maquette.theme.MaquetteThemeAed",
            maqFilterConfig.getTheme().getClass().getName());

   }
   
   
   /**
    * Test supplémentaire à {@link #testCasNormal()} pour le cas où 
    * le paramètre appDisplayStandardsAndNorms est à false.
    * 
    * @throws MaquetteThemeException
    */
   @Test
   public void testCasNormal2()
   throws
   MaquetteThemeException {
      
      // Remplissage du FilterConfig
      MockFilterConfig filterConfig = new MockFilterConfig();
      filterConfig.addInitParameter(ConstantesConfigFiltre.STANDARD_AND_NORM, "0");
      
      // Instantiation de l'objet à tester
      MaquetteFilterConfig maqFilterConfig = new MaquetteFilterConfig(filterConfig);
      
      // Vérification
      
      assertFalse(
            "Erreur sur appDisplayStandardsAndNorms",
            maqFilterConfig.getAppDispStdNorms());
      
   }
   
   
   /**
    * Test du constructeur sans spécifier d'implémentation pour le menu ou
    * les boîtes de gauche<br>
    * <br>
    * Résultat attendu : pas d'instantiation du menu ou des boîtes de gauche,
    * pas d'erreur ni d'exception
    * 
    * @throws MaquetteThemeException
    */
   @Test
   public void testCasSansMenuEtSansBoitesGauche()
   throws
   MaquetteThemeException{
      
      // Remplissage du FilterConfig
      MockFilterConfig filterConfig = new MockFilterConfig();
      
      // Instantiation de l'objet à tester
      MaquetteFilterConfig maqFilterConfig = new MaquetteFilterConfig(filterConfig);
      
      // Vérification du résultat
      
      assertNull(
            "L'implémentation du menu devrait être null",
            maqFilterConfig.getImplMenu());
      
      assertNull(
            "L'implémentation des boîtes de gauche devrait être null",
            maqFilterConfig.getImplMenu());
      
   }
   

}
