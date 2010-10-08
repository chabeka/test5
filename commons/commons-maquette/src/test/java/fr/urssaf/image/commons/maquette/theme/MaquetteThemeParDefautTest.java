package fr.urssaf.image.commons.maquette.theme;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.mock.web.MockFilterConfig;

import fr.urssaf.image.commons.maquette.constantes.ConstantesConfigFiltre;


/**
 * Test de la classe {@link MaquetteThemeGed}
 *
 */
@SuppressWarnings("PMD")
public class MaquetteThemeParDefautTest {

   
   @Test
   public void testValeurParDefaut() {
      
      MaquetteThemeParDefaut theme = new MaquetteThemeParDefaut(null);
      
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeParDefaut.APPLOGO,
            theme.getAppLogo());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeParDefaut.CSSCONTENTBACKGROUNDCOLOR,
            theme.getCssContentBackgroundColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeParDefaut.CSSCONTENTFONTCOLOR,
            theme.getCssContentFontColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeParDefaut.CSSHEADERBACKGROUNDCOLOR,
            theme.getCssHeaderBackgroundColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeParDefaut.CSSHEADERBACKGROUNDIMG,
            theme.getCssHeaderBackgroundImg());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeParDefaut.CSSINFOBOXBACKGROUNDCOLOR,
            theme.getCssInfoboxBackgroundColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeParDefaut.CSSLEFTCOLORBACKGROUNDIMG,
            theme.getCssLeftcolBackgroundImg());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeParDefaut.CSSMAINBACKGROUNDCOLOR,
            theme.getCssMainBackgroundColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeParDefaut.CSSMAINFONTCOLOR,
            theme.getCssMainFontColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeParDefaut.CSSMENUFIRSTROWFONTCOLOR,
            theme.getCssMenuFirstRowFontColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeParDefaut.CSSMENULINKFONTCOLOR,
            theme.getCssMenuLinkFontColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeParDefaut.CSSMENULINKHOVERFONTCOLOR,
            theme.getCssMenuLinkHoverFontColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeParDefaut.CSSSELECTEDMENUBACKGROUNDCOLOR,
            theme.getCssSelectedMenuBackgroundColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeParDefaut.MAINLOGO,
            theme.getMainLogo());
      
   }
   
   
   @Test
   public void testValeurDansConfigFiltre() {
      
      MockFilterConfig filterConfig = new MockFilterConfig(); 
      filterConfig.addInitParameter(ConstantesConfigFiltre.APPLOGO, "APPLOGO");
      filterConfig.addInitParameter(ConstantesConfigFiltre.CSSCONTENTBACKGROUNDCOLOR, "CSSCONTENTBACKGROUNDCOLOR");
      filterConfig.addInitParameter(ConstantesConfigFiltre.CSSCONTENTFONTCOLOR, "CSSCONTENTFONTCOLOR");
      filterConfig.addInitParameter(ConstantesConfigFiltre.CSSHEADERBACKGROUNDCOLOR, "CSSHEADERBACKGROUNDCOLOR");
      filterConfig.addInitParameter(ConstantesConfigFiltre.CSSHEADERBACKGROUNDIMG, "CSSHEADERBACKGROUNDIMG");
      filterConfig.addInitParameter(ConstantesConfigFiltre.CSSINFOBOXBACKGROUNDCOLOR, "CSSINFOBOXBACKGROUNDCOLOR");
      filterConfig.addInitParameter(ConstantesConfigFiltre.CSSLEFTCOLORBACKGROUNDIMG, "CSSLEFTCOLORBACKGROUNDIMG");
      filterConfig.addInitParameter(ConstantesConfigFiltre.CSSMAINBACKGROUNDCOLOR, "CSSMAINBACKGROUNDCOLOR");
      filterConfig.addInitParameter(ConstantesConfigFiltre.CSSMAINFONTCOLOR, "CSSMAINFONTCOLOR");
      filterConfig.addInitParameter(ConstantesConfigFiltre.CSSMENUFIRSTROWFONTCOLOR, "CSSMENUFIRSTROWFONTCOLOR");
      filterConfig.addInitParameter(ConstantesConfigFiltre.CSSMENULINKFONTCOLOR, "CSSMENULINKFONTCOLOR");
      filterConfig.addInitParameter(ConstantesConfigFiltre.CSSMENULINKHOVERFONTCOLOR, "CSSMENULINKHOVERFONTCOLOR");
      filterConfig.addInitParameter(ConstantesConfigFiltre.CSSSELECTEDMENUBACKGROUNDCOLOR, "CSSSELECTEDMENUBACKGROUNDCOLOR");
      filterConfig.addInitParameter(ConstantesConfigFiltre.MAINLOGO, "MAINLOGO");
      
      MaquetteThemeParDefaut theme = new MaquetteThemeParDefaut(filterConfig);
      
      assertEquals(
            "Erreur dans le thème",
            "APPLOGO",
            theme.getAppLogo());
      assertEquals(
            "Erreur dans le thème",
            "CSSCONTENTBACKGROUNDCOLOR",
            theme.getCssContentBackgroundColor());
      assertEquals(
            "Erreur dans le thème",
            "CSSCONTENTFONTCOLOR",
            theme.getCssContentFontColor());
      assertEquals(
            "Erreur dans le thème",
            "CSSHEADERBACKGROUNDCOLOR",
            theme.getCssHeaderBackgroundColor());
      assertEquals(
            "Erreur dans le thème",
            "CSSHEADERBACKGROUNDIMG",
            theme.getCssHeaderBackgroundImg());
      assertEquals(
            "Erreur dans le thème",
            "CSSINFOBOXBACKGROUNDCOLOR",
            theme.getCssInfoboxBackgroundColor());
      assertEquals(
            "Erreur dans le thème",
            "CSSLEFTCOLORBACKGROUNDIMG",
            theme.getCssLeftcolBackgroundImg());
      assertEquals(
            "Erreur dans le thème",
            "CSSMAINBACKGROUNDCOLOR",
            theme.getCssMainBackgroundColor());
      assertEquals(
            "Erreur dans le thème",
            "CSSMAINFONTCOLOR",
            theme.getCssMainFontColor());
      assertEquals(
            "Erreur dans le thème",
            "CSSMENUFIRSTROWFONTCOLOR",
            theme.getCssMenuFirstRowFontColor());
      assertEquals(
            "Erreur dans le thème",
            "CSSMENULINKFONTCOLOR",
            theme.getCssMenuLinkFontColor());
      assertEquals(
            "Erreur dans le thème",
            "CSSMENULINKHOVERFONTCOLOR",
            theme.getCssMenuLinkHoverFontColor());
      assertEquals(
            "Erreur dans le thème",
            "CSSSELECTEDMENUBACKGROUNDCOLOR",
            theme.getCssSelectedMenuBackgroundColor());
      assertEquals(
            "Erreur dans le thème",
            "MAINLOGO",
            theme.getMainLogo());
      
   }
   
   
}
