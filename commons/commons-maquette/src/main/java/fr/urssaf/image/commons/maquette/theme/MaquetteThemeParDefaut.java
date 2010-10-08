package fr.urssaf.image.commons.maquette.theme;

import javax.servlet.FilterConfig;

import org.apache.commons.lang.StringUtils;

import fr.urssaf.image.commons.maquette.constantes.ConstantesConfigFiltre;


/**
 * Représente le thème par défaut<br>
 * <br>
 * Utilise les valeurs spécifiées dans la configuration
 * du filtre (web.xml) ainsi que les valeurs par défaut
 *
 */
public final class MaquetteThemeParDefaut extends AbstractMaquetteTheme {

   
   private static final long serialVersionUID = 5262508270477499371L;

   
   /**
    * Constructeur
    * 
    * @param filterConfig la configuration du filtre
    */
   public MaquetteThemeParDefaut(FilterConfig filterConfig) {
      super(filterConfig);
   }

   
   @Override
   public String getAppLogo() {
      String valeurFiltre = getFilterValue(ConstantesConfigFiltre.APPLOGO) ; 
      return StringUtils.isEmpty(valeurFiltre) ? ConstantesThemeParDefaut.APPLOGO : valeurFiltre;
   }

   @Override
   public String getCssContentBackgroundColor() {
      String valeurFiltre = getFilterValue(ConstantesConfigFiltre.CSSCONTENTBACKGROUNDCOLOR) ; 
      return StringUtils.isEmpty(valeurFiltre) ? ConstantesThemeParDefaut.CSSCONTENTBACKGROUNDCOLOR : valeurFiltre;
   }

   @Override
   public String getCssContentFontColor() {
      String valeurFiltre = getFilterValue(ConstantesConfigFiltre.CSSCONTENTFONTCOLOR) ; 
      return StringUtils.isEmpty(valeurFiltre) ? ConstantesThemeParDefaut.CSSCONTENTFONTCOLOR : valeurFiltre;
   }

   @Override
   public String getCssHeaderBackgroundColor() {
      String valeurFiltre = getFilterValue(ConstantesConfigFiltre.CSSHEADERBACKGROUNDCOLOR) ; 
      return StringUtils.isEmpty(valeurFiltre) ? ConstantesThemeParDefaut.CSSHEADERBACKGROUNDCOLOR : valeurFiltre;
   }

   @Override
   public String getCssHeaderBackgroundImg() {
      String valeurFiltre = getFilterValue(ConstantesConfigFiltre.CSSHEADERBACKGROUNDIMG) ; 
      return StringUtils.isEmpty(valeurFiltre) ? ConstantesThemeParDefaut.CSSHEADERBACKGROUNDIMG : valeurFiltre;
   }

   @Override
   public String getCssInfoboxBackgroundColor() {
      String valeurFiltre = getFilterValue(ConstantesConfigFiltre.CSSINFOBOXBACKGROUNDCOLOR) ; 
      return StringUtils.isEmpty(valeurFiltre) ? ConstantesThemeParDefaut.CSSINFOBOXBACKGROUNDCOLOR : valeurFiltre;
   }

   @Override
   public String getCssLeftcolBackgroundImg() {
      String valeurFiltre = getFilterValue(ConstantesConfigFiltre.CSSLEFTCOLORBACKGROUNDIMG) ; 
      return StringUtils.isEmpty(valeurFiltre) ? ConstantesThemeParDefaut.CSSLEFTCOLORBACKGROUNDIMG : valeurFiltre;
   }

   @Override
   public String getCssMainBackgroundColor() {
      String valeurFiltre = getFilterValue(ConstantesConfigFiltre.CSSMAINBACKGROUNDCOLOR) ; 
      return StringUtils.isEmpty(valeurFiltre) ? ConstantesThemeParDefaut.CSSMAINBACKGROUNDCOLOR : valeurFiltre;
   }

   @Override
   public String getCssMainFontColor() {
      String valeurFiltre = getFilterValue(ConstantesConfigFiltre.CSSMAINFONTCOLOR) ; 
      return StringUtils.isEmpty(valeurFiltre) ? ConstantesThemeParDefaut.CSSMAINFONTCOLOR : valeurFiltre;
   }

   @Override
   public String getCssMenuFirstRowFontColor() {
      String valeurFiltre = getFilterValue(ConstantesConfigFiltre.CSSMENUFIRSTROWFONTCOLOR) ; 
      return StringUtils.isEmpty(valeurFiltre) ? ConstantesThemeParDefaut.CSSMENUFIRSTROWFONTCOLOR : valeurFiltre;
   }

   @Override
   public String getCssMenuLinkFontColor() {
      String valeurFiltre = getFilterValue(ConstantesConfigFiltre.CSSMENULINKFONTCOLOR) ; 
      return StringUtils.isEmpty(valeurFiltre) ? ConstantesThemeParDefaut.CSSMENULINKFONTCOLOR : valeurFiltre;
   }

   @Override
   public String getCssMenuLinkHoverFontColor() {
      String valeurFiltre = getFilterValue(ConstantesConfigFiltre.CSSMENULINKHOVERFONTCOLOR) ; 
      return StringUtils.isEmpty(valeurFiltre) ? ConstantesThemeParDefaut.CSSMENULINKHOVERFONTCOLOR : valeurFiltre;
   }

   @Override
   public String getCssSelectedMenuBackgroundColor() {
      String valeurFiltre = getFilterValue(ConstantesConfigFiltre.CSSSELECTEDMENUBACKGROUNDCOLOR) ; 
      return StringUtils.isEmpty(valeurFiltre) ? ConstantesThemeParDefaut.CSSSELECTEDMENUBACKGROUNDCOLOR : valeurFiltre;
   }

   @Override
   public String getMainLogo() {
      String valeurFiltre = getFilterValue(ConstantesConfigFiltre.MAINLOGO) ; 
      return StringUtils.isEmpty(valeurFiltre) ? ConstantesThemeParDefaut.MAINLOGO : valeurFiltre;
   }

}
