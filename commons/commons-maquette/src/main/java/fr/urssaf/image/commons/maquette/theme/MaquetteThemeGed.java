package fr.urssaf.image.commons.maquette.theme;

import javax.servlet.FilterConfig;


/**
 * Représente le thème GED
 *
 */
public final class MaquetteThemeGed extends AbstractMaquetteTheme {

   
   private static final long serialVersionUID = -870262983313666444L;
   

   /**
    * Constructeur
    * 
    * @param filterConfig la configuration du filtre
    */
   public MaquetteThemeGed(FilterConfig filterConfig) {
      super(filterConfig);
   }

   
   @Override
   public String getAppLogo() {
      return ConstantesThemeGed.APPLOGO ;
   }

   @Override
   public String getCssContentBackgroundColor() {
      return ConstantesThemeGed.CSSCONTENTBACKGROUNDCOLOR;
   }

   @Override
   public String getCssContentFontColor() {
      return ConstantesThemeGed.CSSCONTENTFONTCOLOR;
   }

   @Override
   public String getCssHeaderBackgroundColor() {
      return ConstantesThemeGed.CSSHEADERBACKGROUNDCOLOR;
   }

   @Override
   public String getCssHeaderBackgroundImg() {
      return ConstantesThemeGed.CSSHEADERBACKGROUNDIMG;
   }

   @Override
   public String getCssInfoboxBackgroundColor() {
      return ConstantesThemeGed.CSSINFOBOXBACKGROUNDCOLOR;
   }

   @Override
   public String getCssLeftcolBackgroundImg() {
      return ConstantesThemeGed.CSSLEFTCOLORBACKGROUNDIMG;
   }

   @Override
   public String getCssMainBackgroundColor() {
      return ConstantesThemeGed.CSSMAINBACKGROUNDCOLOR;
   }

   @Override
   public String getCssMainFontColor() {
      return ConstantesThemeGed.CSSMAINFONTCOLOR;
   }

   @Override
   public String getCssMenuFirstRowFontColor() {
      return ConstantesThemeGed.CSSMENUFIRSTROWFONTCOLOR;
   }

   @Override
   public String getCssMenuLinkFontColor() {
      return ConstantesThemeGed.CSSMENULINKFONTCOLOR;
   }

   @Override
   public String getCssMenuLinkHoverFontColor() {
      return ConstantesThemeGed.CSSMENULINKHOVERFONTCOLOR;
   }

   @Override
   public String getCssSelectedMenuBackgroundColor() {
      return ConstantesThemeGed.CSSSELECTEDMENUBACKGROUNDCOLOR;
   }

   @Override
   public String getMainLogo() {
      return ConstantesThemeGed.MAINLOGO ;
   }
   

}
