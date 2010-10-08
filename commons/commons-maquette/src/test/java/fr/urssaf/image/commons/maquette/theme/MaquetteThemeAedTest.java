package fr.urssaf.image.commons.maquette.theme;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * Test de la classe {@link MaquetteThemeAed}
 *
 */
@SuppressWarnings("PMD")
public class MaquetteThemeAedTest {

   
   @Test
   public void testTheme() {
      
      MaquetteThemeAed theme = new MaquetteThemeAed(null);
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeAed.APPLOGO,
            theme.getAppLogo());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeAed.CSSCONTENTBACKGROUNDCOLOR,
            theme.getCssContentBackgroundColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeAed.CSSCONTENTFONTCOLOR,
            theme.getCssContentFontColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeAed.CSSHEADERBACKGROUNDCOLOR,
            theme.getCssHeaderBackgroundColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeAed.CSSHEADERBACKGROUNDIMG,
            theme.getCssHeaderBackgroundImg());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeAed.CSSINFOBOXBACKGROUNDCOLOR,
            theme.getCssInfoboxBackgroundColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeAed.CSSLEFTCOLORBACKGROUNDIMG,
            theme.getCssLeftcolBackgroundImg());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeAed.CSSMAINBACKGROUNDCOLOR,
            theme.getCssMainBackgroundColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeAed.CSSMAINFONTCOLOR,
            theme.getCssMainFontColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeAed.CSSMENUFIRSTROWFONTCOLOR,
            theme.getCssMenuFirstRowFontColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeAed.CSSMENULINKFONTCOLOR,
            theme.getCssMenuLinkFontColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeAed.CSSMENULINKHOVERFONTCOLOR,
            theme.getCssMenuLinkHoverFontColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeAed.CSSSELECTEDMENUBACKGROUNDCOLOR,
            theme.getCssSelectedMenuBackgroundColor());
      assertEquals(
            "Erreur dans le thème",
            ConstantesThemeAed.MAINLOGO,
            theme.getMainLogo());
      
   }
   
   
}
