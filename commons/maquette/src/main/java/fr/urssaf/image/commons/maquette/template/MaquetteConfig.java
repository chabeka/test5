package fr.urssaf.image.commons.maquette.template;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import fr.urssaf.image.commons.maquette.definition.ILeftCol;
import fr.urssaf.image.commons.maquette.definition.IMenu;
import fr.urssaf.image.commons.maquette.template.config.exception.UnkownThemeForMaquetteConfig;
import fr.urssaf.image.commons.maquette.template.generator.MenuGenerator;
import fr.urssaf.image.commons.maquette.tool.MaquetteConstant;
import fr.urssaf.image.commons.maquette.tool.MenuItem;

// TODO	doit remplacer les différents xxxConfig : bien différencier ceux qui doivent être 
//	obligatoirement instancié via le singleton et qui vont lancer une exception (Footer, 
public class MaquetteConfig 
{
	
	public static Logger logger = Logger.getLogger( MaquetteConfig.class.getName() );
	
	/**
	 * @desc	instance du singleton
	 */
	// private static MaquetteConfig instance ;
	
	/**
	 * @desc 	head_part
	 */
	// liste de paramètre propre au html métier
	private String browserTitle ;
	private List<String> cssList ;
	private List<String> metaList ;
	// liste de paramètre pour la surcharge des couleurs
	private Boolean overloadColor ;
	private List<String> cssListParam ; 
	// gestion du thème
	private String theme ;
	
	/**
	 * @desc	header
	 */
	private Boolean isFuckingIE ;
	private String mainLogo ;
	private String appTitle ;
	private String appLogo ;
	private String appLogoTitle ;
	
	/**
	 * @desc	leftcol
	 */
	private ILeftCol implLeftCol ;
	
	/**
	 * @desc	menu
	 */
	private IMenu implMenu ;
	private String requestUrl ;
	
	/**
	 * @desc	footer
	 */
	private String appCopyright ;
	private Boolean appDisplayStandardsAndNorms ;
	
	/**
	 * @desc	initialise les valeurs par défaut des attributs
	 */
	private void initAttribute() {
		//Head_part
		cssList = new ArrayList<String>();
		metaList = new ArrayList<String>();
		overloadColor = false ;
		cssListParam = new LinkedList<String>();
		theme = null ;
		
		//Header
		mainLogo = "" ;
		appTitle = "" ;
		appLogo = "" ;
		appLogoTitle = "" ;
		isFuckingIE = true ;
		
		//LeftCol
		implLeftCol = null ;
		
		//Menu
		implMenu = null ;
		
		//Footer
		setAppCopyright( "" );
		appDisplayStandardsAndNorms = false ;
	}
	
	/**
	 * @desc	constructeur vide, utile pour les développeurs souhaitant configurer eux même cet objet
	 * 			par défaut l'attribut appDisplayStandardsAndNorms est positionné à false
	 */
	public MaquetteConfig( FilterConfig fc, HttpServletRequest hsr )throws SecurityException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, UnkownThemeForMaquetteConfig
	{
		initAttribute() ;
		initConfig( fc, hsr ) ;
		isIE( hsr ) ; 
		// stock en session pour réutilisation par la servlet
		hsr.getSession().setAttribute( MaquetteConstant.SESSION_MAQUETTECONFIG , this) ;
	}

	/**
	 * @param	fc
	 * @throws UnkownThemeForMaquetteConfig 
	 * @desc	initialise les attributs de l'objet
	 */
	private void initConfig( FilterConfig fc, HttpServletRequest hsr )throws SecurityException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, UnkownThemeForMaquetteConfig
	{
		// Head_part
		// gestion du theme
		theme = fc.getInitParameter( MaquetteConstant.P_THEME ) ;
logger.debug( "valeur du theme : (" + theme + ")" ) ;		
		
		// valeur par défaut
		cssListParam = new LinkedList<String>() ;
		cssListParam.add(MaquetteConstant.CSSMAINBACKGROUNDCOLOR_INDEX, MaquetteConstant.CSSMAINBACKGROUNDCOLOR_DEFAULT );
		cssListParam.add(MaquetteConstant.CSSCONTENTBACKGROUNDCOLOR_INDEX, MaquetteConstant.CSSCONTENTBACKGROUNDCOLOR_DEFAULT );
		cssListParam.add(MaquetteConstant.CSSLEFTCOLORBACKGROUNDIMG_INDEX, MaquetteConstant.CSSLEFTCOLORBACKGROUNDIMG_DEFAULT );
		cssListParam.add(MaquetteConstant.CSSINFOBOXBACKGROUNDCOLOR_INDEX, MaquetteConstant.CSSINFOBOXBACKGROUNDCOLOR_DEFAULT );
		cssListParam.add(MaquetteConstant.CSSHEADERBACKGROUNDCOLOR_INDEX, MaquetteConstant.CSSHEADERBACKGROUNDCOLOR_DEFAULT );
		cssListParam.add(MaquetteConstant.CSSHEADERBACKGROUNDIMG_INDEX, MaquetteConstant.CSSHEADERBACKGROUNDIMG_DEFAULT );
		cssListParam.add(MaquetteConstant.CSSSELECTEDMENUBACKGROUNDCOLOR_INDEX, MaquetteConstant.CSSSELECTEDMENUBACKGROUNDCOLOR_DEFAULT );
		cssListParam.add(MaquetteConstant.CSSMAINFONTCOLOR_INDEX, MaquetteConstant.CSSMAINFONTCOLOR_DEFAULT );
		cssListParam.add(MaquetteConstant.CSSCONTENTFONTCOLOR_INDEX, MaquetteConstant.CSSCONTENTFONTCOLOR_DEFAULT );
		cssListParam.add(MaquetteConstant.CSSMENUFIRSTROWFONTCOLOR_INDEX, MaquetteConstant.CSSMENUFIRSTROWFONTCOLOR_DEFAULT );
		cssListParam.add(MaquetteConstant.CSSMENULINKFONTCOLOR_INDEX, MaquetteConstant.CSSMENULINKFONTCOLOR_DEFAULT );
		cssListParam.add(MaquetteConstant.CSSMENULINKHOVERFONTCOLOR_INDEX, MaquetteConstant.CSSMENULINKHOVERFONTCOLOR_DEFAULT );
		
		mainLogo = MaquetteConstant.MAINLOGO_DEFAULT ; // Header
		appLogo = MaquetteConstant.APPLOGO_DEFAULT ; // Header
		
		// Liste des paramètres de couleur
		if( theme != null )
		{
			if( theme.compareTo( MaquetteConstant.THEME_AED ) == 0 )
			{
				cssListParam = new LinkedList<String>() ;
				cssListParam.add(MaquetteConstant.CSSMAINBACKGROUNDCOLOR_INDEX, MaquetteConstant.CSSMAINBACKGROUNDCOLOR_AED );
				cssListParam.add(MaquetteConstant.CSSCONTENTBACKGROUNDCOLOR_INDEX, MaquetteConstant.CSSCONTENTBACKGROUNDCOLOR_AED );
				cssListParam.add(MaquetteConstant.CSSLEFTCOLORBACKGROUNDIMG_INDEX, MaquetteConstant.CSSLEFTCOLORBACKGROUNDIMG_AED );
				cssListParam.add(MaquetteConstant.CSSINFOBOXBACKGROUNDCOLOR_INDEX, MaquetteConstant.CSSINFOBOXBACKGROUNDCOLOR_AED );
				cssListParam.add(MaquetteConstant.CSSHEADERBACKGROUNDCOLOR_INDEX, MaquetteConstant.CSSHEADERBACKGROUNDCOLOR_AED );
				cssListParam.add(MaquetteConstant.CSSHEADERBACKGROUNDIMG_INDEX, MaquetteConstant.CSSHEADERBACKGROUNDIMG_AED );
				cssListParam.add(MaquetteConstant.CSSSELECTEDMENUBACKGROUNDCOLOR_INDEX, MaquetteConstant.CSSSELECTEDMENUBACKGROUNDCOLOR_AED );
				cssListParam.add(MaquetteConstant.CSSMAINFONTCOLOR_INDEX, MaquetteConstant.CSSMAINFONTCOLOR_AED );
				cssListParam.add(MaquetteConstant.CSSCONTENTFONTCOLOR_INDEX, MaquetteConstant.CSSCONTENTFONTCOLOR_AED );
				cssListParam.add(MaquetteConstant.CSSMENUFIRSTROWFONTCOLOR_INDEX, MaquetteConstant.CSSMENUFIRSTROWFONTCOLOR_AED );
				cssListParam.add(MaquetteConstant.CSSMENULINKFONTCOLOR_INDEX, MaquetteConstant.CSSMENULINKFONTCOLOR_AED );
				cssListParam.add(MaquetteConstant.CSSMENULINKHOVERFONTCOLOR_INDEX, MaquetteConstant.CSSMENULINKHOVERFONTCOLOR_AED );
				
				mainLogo = MaquetteConstant.MAINLOGO_AED ; // Header
				appLogo = MaquetteConstant.APPLOGO_AED ; // Header
				
			}else if( theme.compareTo( MaquetteConstant.THEME_GED ) == 0 )
			{
				cssListParam = new LinkedList<String>() ;
				cssListParam.add(MaquetteConstant.CSSMAINBACKGROUNDCOLOR_INDEX, MaquetteConstant.CSSMAINBACKGROUNDCOLOR_GED );
				cssListParam.add(MaquetteConstant.CSSCONTENTBACKGROUNDCOLOR_INDEX, MaquetteConstant.CSSCONTENTBACKGROUNDCOLOR_GED );
				cssListParam.add(MaquetteConstant.CSSLEFTCOLORBACKGROUNDIMG_INDEX, MaquetteConstant.CSSLEFTCOLORBACKGROUNDIMG_GED );
				cssListParam.add(MaquetteConstant.CSSINFOBOXBACKGROUNDCOLOR_INDEX, MaquetteConstant.CSSINFOBOXBACKGROUNDCOLOR_GED );
				cssListParam.add(MaquetteConstant.CSSHEADERBACKGROUNDCOLOR_INDEX, MaquetteConstant.CSSHEADERBACKGROUNDCOLOR_GED );
				cssListParam.add(MaquetteConstant.CSSHEADERBACKGROUNDIMG_INDEX, MaquetteConstant.CSSHEADERBACKGROUNDIMG_GED );
				cssListParam.add(MaquetteConstant.CSSSELECTEDMENUBACKGROUNDCOLOR_INDEX, MaquetteConstant.CSSSELECTEDMENUBACKGROUNDCOLOR_GED );
				cssListParam.add(MaquetteConstant.CSSMAINFONTCOLOR_INDEX, MaquetteConstant.CSSMAINFONTCOLOR_GED );
				cssListParam.add(MaquetteConstant.CSSCONTENTFONTCOLOR_INDEX, MaquetteConstant.CSSCONTENTFONTCOLOR_GED );
				cssListParam.add(MaquetteConstant.CSSMENUFIRSTROWFONTCOLOR_INDEX, MaquetteConstant.CSSMENUFIRSTROWFONTCOLOR_GED );
				cssListParam.add(MaquetteConstant.CSSMENULINKFONTCOLOR_INDEX, MaquetteConstant.CSSMENULINKFONTCOLOR_GED );
				cssListParam.add(MaquetteConstant.CSSMENULINKHOVERFONTCOLOR_INDEX, MaquetteConstant.CSSMENULINKHOVERFONTCOLOR_GED );
				
				mainLogo = MaquetteConstant.MAINLOGO_GED ; // Header
				appLogo = MaquetteConstant.APPLOGO_GED ; // Header
				
			}else
				throw new UnkownThemeForMaquetteConfig( theme );
		}
		else
		{
			cssListParam = new LinkedList<String>() ;
			cssListParam.add(MaquetteConstant.CSSMAINBACKGROUNDCOLOR_INDEX, fc.getInitParameter( MaquetteConstant.P_CSSMAINBACKGROUNDCOLOR ));
			cssListParam.add(MaquetteConstant.CSSCONTENTBACKGROUNDCOLOR_INDEX, fc.getInitParameter( MaquetteConstant.P_CSSCONTENTBACKGROUNDCOLOR ));
			cssListParam.add(MaquetteConstant.CSSLEFTCOLORBACKGROUNDIMG_INDEX, fc.getInitParameter( MaquetteConstant.P_CSSLEFTCOLORBACKGROUNDIMG ));
			cssListParam.add(MaquetteConstant.CSSINFOBOXBACKGROUNDCOLOR_INDEX, fc.getInitParameter( MaquetteConstant.P_CSSINFOBOXBACKGROUNDCOLOR ));
			cssListParam.add(MaquetteConstant.CSSHEADERBACKGROUNDCOLOR_INDEX, fc.getInitParameter( MaquetteConstant.P_CSSHEADERBACKGROUNDCOLOR ));
			cssListParam.add(MaquetteConstant.CSSHEADERBACKGROUNDIMG_INDEX, fc.getInitParameter( MaquetteConstant.P_CSSHEADERBACKGROUNDIMG ));
			cssListParam.add(MaquetteConstant.CSSSELECTEDMENUBACKGROUNDCOLOR_INDEX, fc.getInitParameter( MaquetteConstant.P_CSSSELECTEDMENUBACKGROUNDCOLOR ));
			cssListParam.add(MaquetteConstant.CSSMAINFONTCOLOR_INDEX, fc.getInitParameter( MaquetteConstant.P_CSSMAINFONTCOLOR ));
			cssListParam.add(MaquetteConstant.CSSCONTENTFONTCOLOR_INDEX, fc.getInitParameter( MaquetteConstant.P_CSSCONTENTFONTCOLOR ));
			cssListParam.add(MaquetteConstant.CSSMENUFIRSTROWFONTCOLOR_INDEX, fc.getInitParameter( MaquetteConstant.P_CSSMENUFIRSTROWFONTCOLOR ));
			cssListParam.add(MaquetteConstant.CSSMENULINKFONTCOLOR_INDEX, fc.getInitParameter( MaquetteConstant.P_CSSMENULINKFONTCOLOR ));
			cssListParam.add(MaquetteConstant.CSSMENULINKHOVERFONTCOLOR_INDEX, fc.getInitParameter( MaquetteConstant.P_CSSMENULINKHOVERFONTCOLOR ));
			
			mainLogo = fc.getInitParameter( MaquetteConstant.P_MAINLOGO ); // Header
			appLogo = fc.getInitParameter( MaquetteConstant.P_APPLOGO ); // Header
		}
		
		for( String cssEl : cssListParam)
		{
			// test sur null utile dans le cas où un ou plusieurs paramètres sont inexistants
			if( cssEl != null && cssEl.length() > 0 )
				overloadColor = true ;
		}
logger.debug( "nb de cssListParam : " + cssListParam.size() ) ;

		// Header
		appTitle = fc.getInitParameter( MaquetteConstant.P_APPTITLE );
		appLogoTitle = "Titre de l'application" + fc.getInitParameter( MaquetteConstant.P_APPTITLE );
		
		// Menu
		requestUrl = hsr.getRequestURI() ;
		String menuImplementationPath = fc.getInitParameter( MaquetteConstant.P_IMPL_MENU );
				
		// Utilisation de la reflexion pour charger l'implémentation de IMenu	
		if( menuImplementationPath != null 
			&& menuImplementationPath.length() > 0 )
		{
			Class<IMenu> classImplMenu;
			try {
				classImplMenu = (Class<IMenu>) Class.forName( menuImplementationPath );
				
				Constructor constructor = classImplMenu.getConstructor() ;
				implMenu = (IMenu) constructor.newInstance();
			} catch (Exception e) {
logger.debug("Aucun menu récupérable : ("  + e.getClass() + ")" + e.getMessage() );
				e.printStackTrace();
			}
		}

		// LeftCol
		// Utilisation de la reflexion pour charger l'implémentation de ILeftCol
		String implementationPath = fc.getInitParameter( MaquetteConstant.P_IMPL_LEFTCOL ) ;
		
		if( implementationPath != null 
			&& implementationPath.length() > 0 )
		{
			Class<ILeftCol> classImplLeftCol = (Class<ILeftCol>) Class.forName( implementationPath ) ;
			
			Constructor constructor = classImplLeftCol.getConstructor() ;
			implLeftCol = (ILeftCol) constructor.newInstance(); // écrase la précédente version de leftColInfo
		}
		
		// Footer
		setAppCopyright( fc.getInitParameter( MaquetteConstant.P_COPYRIGHT ) ) ;
		setAppDisplayStandardsAndNorms( fc.getInitParameter( MaquetteConstant.P_STANDARD_N_NORM ) ) ;
	}
	
	public void addMeta( String meta ){
		metaList.add(meta) ;
	}
	public List<String> getMetaList() {
		return metaList;
	}
	protected void setMetaList(List<String> metaList) {
		this.metaList = cssList;
	}
	
	/**
	 * @desc ajoute un élément à l'attribut cssList
	 * @param cssFileName
	 */
	public void addCss( String cssFileName ){
		cssList.add(cssFileName) ;
	}
	
	/**
	 * @return la liste des css
	 */
	public List<String> getCssList() {
		return cssList;
	}
	
	/**
	 * @desc créé la liste des css
	 * @param cssList
	 */
	@SuppressWarnings("unused")
	private void setCssList(List<String> cssList) {
		this.cssList = cssList;
	}

	
	/**
	 * @return le titre du navigateur
	 */
	public String getBrowserTitle() {
		return browserTitle;
	}
	
	/**
	 * @param défini le titre du navigateur
	 * @desc  permet de définir le titre du navigateur pour chaque page : doit être public
	 */
	public void setBrowserTitle(String browserTitle) {
		this.browserTitle = browserTitle;
	}
	
	/**
	 * @return l'attribut overloadColor permettant de savoir si on veut charger les css spécifiques à la couleur
	 */
	public Boolean getOverloadColor() {
		return overloadColor ;
	}
	
	/**
	 * @param positionne l'attribut overloadColor
	 */
	@SuppressWarnings("unused")
	private void setOverloadColor(Boolean overloadColor) {
		this.overloadColor = overloadColor;
	}
	
	/**
	 * @return la liste des couleurs personnalisées
	 */
	public List<String> getCssListParam()
	{
		return cssListParam ;
	}
	
	/**
	 * @return si le navigateur est sous MSIE 6
	 */
	public Boolean isIE() {
		return isFuckingIE;
	}
	
	/**
	 * @param hsr
	 * @return si le navigateur est sous MSIE 6
	 */
	public Boolean isIE( HttpServletRequest hsr ) {
		isFuckingIE = hsr.getHeader("User-Agent").contains("MSIE 6.0") ;
		return isFuckingIE ;
	}
	
	/**
	 * @return logo "Image" positionné en haut à gauche
	 */
	public String getMainLogo() {
		return mainLogo;
	}
	
	/**
	 * @param mainLogo
	 */
	@SuppressWarnings("unused")
	private void setMainLogo(String mainLogo) {
		this.mainLogo = mainLogo;
	}
	
	/**
	 * @return Titre de l'application, affichable notamment dans la fenêtre du navigateur
	 */
	public String getAppTitle() {
		return appTitle;
	}
	
	/**
	 * @param appTitle
	 */
	@SuppressWarnings("unused")
	private void setAppTitle(String appTitle) {
		this.appTitle = appTitle;
	}

	/**
	 * @return chemin absolu vers le logo
	 */
	public String getAppLogo() {
		return appLogo;
	}
	
	/**
	 * @param appLogo chemin absolu vers le logo, accessible via le navigateur au sein d'une balise <img src="appLogo" />
	 */
	@SuppressWarnings("unused")
	private void setAppLogo(String appLogo) {
		this.appLogo = appLogo;
	}
	
	/**
	 * @return contenu de l'attribut <title> de la balise img contenant le log de l'application
	 */
	public String getAppLogoTitle() {
		return appLogoTitle;
	}
	
	/**
	 * @param appLogo
	 */
	@SuppressWarnings("unused")
	private void setAppLogoTitle(String appLogoTitle) {
		this.appLogoTitle = appLogoTitle;
	}
		
	/**
	 * @return 	appCopyright
	 */
	public String getAppCopyright() {
		return appCopyright;
	}

	/**
	 * @param appCopyright, période du copyright, affichable dans la section <footer> de la maquette
	 */
	private void setAppCopyright(String appCopyright) {
		this.appCopyright = appCopyright;
	}
	
	/**
	 * @return the appDisplayStandardsAndNorms
	 */
	public Boolean getAppDisplayStandardsAndNorms() {
		return appDisplayStandardsAndNorms;
	}

	/**
	 * @param appDisplayStandardsAndNorms the appDisplayStandardsAndNorms to set
	 */
	private void setAppDisplayStandardsAndNorms(String appDisplayStandardsAndNorms) {
		this.appDisplayStandardsAndNorms = ( appDisplayStandardsAndNorms == "1" ? true : false );
	}

	/**
	 * @desc	récupère l'implémentation de ILeftCol
	 */
	public ILeftCol getImplLeftcol() {
		if( implLeftCol == null )
logger.warn( "L'implémentation de l'interface ILeftCol n'est pas disponible" ) ;
		
		return implLeftCol;
	}
	
	/**
	 * @desc	récupère l'implémentation de IMenu
	 */
	public IMenu getImplMenu() {
		if( implMenu == null )
logger.warn( "L'implémentation de l'interface IMenu n'est pas disponible" ) ;
		
		return implMenu;
	}
	
	/**
	 * @return la chaîne html du menu
	 */
	public String getMenu( HttpServletRequest hsr )
	{
		String html = "" ;
		if( implMenu != null )
		{
			List<MenuItem> listMenuItem = implMenu.getMenu(hsr) ;
			if( listMenuItem != null )
				html = MenuGenerator.buildMenu(listMenuItem, requestUrl).toString() ;
			else
logger.warn( "La méthode getMenuItem de l'implémentation de l'interface IMenu retourne null, aucun menu n'est affichable" ) ;
		}
		else
logger.warn( "L'implémentation de l'interface IMenu n'est pas disponible" ) ;
	
		return html ;
	}
	
	/**
	 * @return l'URL
	 */
	public String getRequestUrl()
	{
		return requestUrl ;
	}
}
