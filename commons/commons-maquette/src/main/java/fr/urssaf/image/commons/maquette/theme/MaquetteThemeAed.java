package fr.urssaf.image.commons.maquette.theme;

import javax.servlet.FilterConfig;


/**
 * Représente le thème AED
 *
 */
public final class MaquetteThemeAed extends AbstractMaquetteTheme {

   
   private static final long serialVersionUID = -6898249833760590030L;
   

   /**
    * Constructeur
    * 
    * @param filterConfig la configuration du filtre
    */
   public MaquetteThemeAed(FilterConfig filterConfig) {
      super(filterConfig);
   }

   
   @Override
   public String getAppLogo() {
      return ConstantesThemeAed.APPLOGO ;
   }

   @Override
   public String getCssContentBackgroundColor() {
      return ConstantesThemeAed.CSSCONTENTBACKGROUNDCOLOR;
   }

   @Override
   public String getCssContentFontColor() {
      return ConstantesThemeAed.CSSCONTENTFONTCOLOR;
   }

   @Override
   public String getCssHeaderBackgroundColor() {
      return ConstantesThemeAed.CSSHEADERBACKGROUNDCOLOR;
   }

   @Override
   public String getCssHeaderBackgroundImg() {
      return ConstantesThemeAed.CSSHEADERBACKGROUNDIMG;
   }

   @Override
   public String getCssInfoboxBackgroundColor() {
      return ConstantesThemeAed.CSSINFOBOXBACKGROUNDCOLOR;
   }

   @Override
   public String getCssLeftcolBackgroundImg() {
      return ConstantesThemeAed.CSSLEFTCOLORBACKGROUNDIMG;
   }

   @Override
   public String getCssMainBackgroundColor() {
      return ConstantesThemeAed.CSSMAINBACKGROUNDCOLOR;
   }

   @Override
   public String getCssMainFontColor() {
      return ConstantesThemeAed.CSSMAINFONTCOLOR;
   }

   @Override
   public String getCssMenuFirstRowFontColor() {
      return ConstantesThemeAed.CSSMENUFIRSTROWFONTCOLOR;
   }

   @Override
   public String getCssMenuLinkFontColor() {
      return ConstantesThemeAed.CSSMENULINKFONTCOLOR;
   }

   @Override
   public String getCssMenuLinkHoverFontColor() {
      return ConstantesThemeAed.CSSMENULINKHOVERFONTCOLOR;
   }

   @Override
   public String getCssSelectedMenuBackgroundColor() {
      return ConstantesThemeAed.CSSSELECTEDMENUBACKGROUNDCOLOR;
   }

   @Override
   public String getMainLogo() {
      return ConstantesThemeAed.MAINLOGO ;
   }

}
