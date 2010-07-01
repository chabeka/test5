/**
 * 
 */
package fr.urssaf.image.commons.maquette.tool;

/**
 * @author CER6990172
 *
 */
public class MaquetteConstant {

	// chemin vers les resources, et nom de la servlet utilisé dans le web.xml
	public static final String RESOURCEFOLDER = "/resource" ;
	public static final String GETRESOURCEURI = "getResourceImageMaquette.do" ;
	
	public static final String CSSMAINCOLORFILE = "/css/main-color.css" ;
	public static final String CSSMAINFONTCOLORFILE = "/css/main-font-color.css" ;
	public static final String CSSMENUCOLORFILE = "/css/menu-color.css" ;
	public static final String CSSMENUFONTCOLORFILE = "/css/menu-font-color.css" ;
	
	public static final String SESSION_MAQUETTECONFIG = "maquetteConfig" ;
	
	// TODO pas terrible, passer par une collection
	public static final int CSSMAINBACKGROUNDCOLOR_INDEX = 0 ;
	public static final int CSSCONTENTBACKGROUNDCOLOR_INDEX = 1 ;
	public static final int CSSLEFTCOLORBACKGROUNDIMG_INDEX = 2 ;
	public static final int CSSINFOBOXBACKGROUNDCOLOR_INDEX = 3 ;
	public static final int CSSHEADERBACKGROUNDCOLOR_INDEX = 4 ;
	public static final int CSSHEADERBACKGROUNDIMG_INDEX = 5 ;
	public static final int CSSSELECTEDMENUBACKGROUNDCOLOR_INDEX = 6 ;
	public static final int CSSMAINFONTCOLOR_INDEX = 7 ;
	public static final int CSSCONTENTFONTCOLOR_INDEX = 8 ;
	public static final int CSSMENUFIRSTROWFONTCOLOR_INDEX = 9 ;
	public static final int CSSMENULINKFONTCOLOR_INDEX = 10 ;
	public static final int CSSMENULINKHOVERFONTCOLOR_INDEX = 11 ;
	
	// Couleur par défaut (pour l'instant, même que AED)
	public static final String CSSMAINBACKGROUNDCOLOR_DEFAULT = "#A6A9CA" ;
	public static final String CSSCONTENTBACKGROUNDCOLOR_DEFAULT = "#fff" ;
	public static final String CSSLEFTCOLORBACKGROUNDIMG_DEFAULT = GETRESOURCEURI + "?name=/img/leftcol_aed.png" ;
	public static final String CSSINFOBOXBACKGROUNDCOLOR_DEFAULT = "#EAEAEF" ;
	public static final String CSSHEADERBACKGROUNDCOLOR_DEFAULT = "#051A7D" ;
	public static final String CSSHEADERBACKGROUNDIMG_DEFAULT = GETRESOURCEURI + "?name=/img/degrade_h_aed.png" ;
	public static final String CSSSELECTEDMENUBACKGROUNDCOLOR_DEFAULT = "#05577D" ;
	public static final String CSSMAINFONTCOLOR_DEFAULT = "#fff" ;
	public static final String CSSCONTENTFONTCOLOR_DEFAULT = "#000" ;
	public static final String CSSMENUFIRSTROWFONTCOLOR_DEFAULT = "#000" ;
	public static final String CSSMENULINKFONTCOLOR_DEFAULT = "#000" ;
	public static final String CSSMENULINKHOVERFONTCOLOR_DEFAULT = "#000" ;
	public static final String MAINLOGO_DEFAULT = GETRESOURCEURI + "?name=/img/logo_image_aed.png" ;
	public static final String APPLOGO_DEFAULT = GETRESOURCEURI + "?name=/img/logo_aed.png" ;
	
	// Couleur de l'AED, et logo
	public static final String CSSMAINBACKGROUNDCOLOR_AED = "#A6A9CA" ;
	public static final String CSSCONTENTBACKGROUNDCOLOR_AED = "#fff" ;
	public static final String CSSLEFTCOLORBACKGROUNDIMG_AED = GETRESOURCEURI + "?name=/img/leftcol_aed.png" ;
	public static final String CSSINFOBOXBACKGROUNDCOLOR_AED = "#EAEAEF" ;
	public static final String CSSHEADERBACKGROUNDCOLOR_AED = "#051A7D" ;
	public static final String CSSHEADERBACKGROUNDIMG_AED = GETRESOURCEURI + "?name=/img/degrade_h_aed.png" ;
	public static final String CSSSELECTEDMENUBACKGROUNDCOLOR_AED = "#05577D" ;
	public static final String CSSMAINFONTCOLOR_AED = "#fff" ;
	public static final String CSSCONTENTFONTCOLOR_AED = "#000" ;
	public static final String CSSMENUFIRSTROWFONTCOLOR_AED = "#fff" ;
	public static final String CSSMENULINKFONTCOLOR_AED = "#fff" ;
	public static final String CSSMENULINKHOVERFONTCOLOR_AED = "#000" ;
	public static final String MAINLOGO_AED = GETRESOURCEURI + "?name=/img/logo_image_aed.png" ;
	public static final String APPLOGO_AED = GETRESOURCEURI + "?name=/img/logo_aed.png" ;
	
	// Couleur de la GED, et logo
	public static final String CSSMAINBACKGROUNDCOLOR_GED = "#AAB9BD" ;
	public static final String CSSCONTENTBACKGROUNDCOLOR_GED = "#fff" ;
	public static final String CSSLEFTCOLORBACKGROUNDIMG_GED = GETRESOURCEURI + "?name=/img/leftcol_ged.png" ;
	public static final String CSSINFOBOXBACKGROUNDCOLOR_GED = "#EAEAEF" ;
	public static final String CSSHEADERBACKGROUNDCOLOR_GED = "#044a6e" ;
	public static final String CSSHEADERBACKGROUNDIMG_GED = GETRESOURCEURI + "?name=/img/degrade_h_ged.png" ;
	public static final String CSSSELECTEDMENUBACKGROUNDCOLOR_GED = "#05577D" ;
	public static final String CSSMAINFONTCOLOR_GED = "#fff" ;
	public static final String CSSCONTENTFONTCOLOR_GED = "#000" ;
	public static final String CSSMENUFIRSTROWFONTCOLOR_GED = "#CEEDFF" ;
	public static final String CSSMENULINKFONTCOLOR_GED = "#fff" ;
	public static final String CSSMENULINKHOVERFONTCOLOR_GED = "#000" ;
	public static final String MAINLOGO_GED = GETRESOURCEURI + "?name=/img/logo_image_ged.png" ;
	public static final String APPLOGO_GED = GETRESOURCEURI + "?name=/img/logo_ged.png" ;
	
	// Nom des paramètres css dans le web.xml
	public static final String P_CSSMAINBACKGROUNDCOLOR = "cssMainBackgroundColor" ;
	public static final String P_CSSCONTENTBACKGROUNDCOLOR = "cssContentBackgroundColor" ;
	public static final String P_CSSLEFTCOLORBACKGROUNDIMG = "cssLeftcolBackgroundImg" ;
	public static final String P_CSSINFOBOXBACKGROUNDCOLOR = "cssInfoboxBackgroundColor" ;
	public static final String P_CSSHEADERBACKGROUNDCOLOR = "cssHeaderBackgroundColor" ;
	public static final String P_CSSHEADERBACKGROUNDIMG = "cssHeaderBackgroundImg" ;
	public static final String P_CSSSELECTEDMENUBACKGROUNDCOLOR = "cssSelectedMenuBackgroundColor" ;
	public static final String P_CSSMAINFONTCOLOR = "cssMainFontColor" ;
	public static final String P_CSSCONTENTFONTCOLOR = "cssContentFontColor" ;
	public static final String P_CSSMENUFIRSTROWFONTCOLOR = "cssMenuFirstRowFontColor" ;
	public static final String P_CSSMENULINKFONTCOLOR = "cssMenuLinkFontColor" ;
	public static final String P_CSSMENULINKHOVERFONTCOLOR = "cssMenuLinkHoverFontColor" ;
	
	// Nom des paramètres standards dans le web.xml
	public static final String P_IMPL_LEFTCOL = "implementationILeftCol" ;
	public static final String P_IMPL_MENU = "implementationIMenu" ;
	public static final String P_COPYRIGHT = "appCopyright";
	public static final String P_STANDARD_N_NORM = "appDisplayStandardsAndNorms";
	public static final String P_MAINLOGO = "logoPrincipal";
	public static final String P_APPTITLE = "appTitle";
	public static final String P_APPLOGO = "appLogo";
	public static final String P_THEME = "theme";
	
	public static final String THEME_AED = "aed";
	public static final String THEME_GED = "ged";
}
