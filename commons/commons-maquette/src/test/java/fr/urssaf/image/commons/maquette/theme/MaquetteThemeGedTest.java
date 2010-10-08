package fr.urssaf.image.commons.maquette.theme;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * Test de la classe {@link MaquetteThemeGed}
 *
 */
@SuppressWarnings("PMD")
public class MaquetteThemeGedTest {

   
   @Test
   public void testTheme() {
      
      MaquetteThemeGed theme = new MaquetteThemeGed(null);
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeGed.APPLOGO,
            theme.getAppLogo());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeGed.CSSCONTENTBACKGROUNDCOLOR,
            theme.getCssContentBackgroundColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeGed.CSSCONTENTFONTCOLOR,
            theme.getCssContentFontColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeGed.CSSHEADERBACKGROUNDCOLOR,
            theme.getCssHeaderBackgroundColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeGed.CSSHEADERBACKGROUNDIMG,
            theme.getCssHeaderBackgroundImg());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeGed.CSSINFOBOXBACKGROUNDCOLOR,
            theme.getCssInfoboxBackgroundColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeGed.CSSLEFTCOLORBACKGROUNDIMG,
            theme.getCssLeftcolBackgroundImg());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeGed.CSSMAINBACKGROUNDCOLOR,
            theme.getCssMainBackgroundColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeGed.CSSMAINFONTCOLOR,
            theme.getCssMainFontColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeGed.CSSMENUFIRSTROWFONTCOLOR,
            theme.getCssMenuFirstRowFontColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeGed.CSSMENULINKFONTCOLOR,
            theme.getCssMenuLinkFontColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeGed.CSSMENULINKHOVERFONTCOLOR,
            theme.getCssMenuLinkHoverFontColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeGed.CSSSELECTEDMENUBACKGROUNDCOLOR,
            theme.getCssSelectedMenuBackgroundColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeGed.MAINLOGO,
            theme.getMainLogo());
      
   }
   
   
}
