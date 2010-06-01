package fr.urssaf.image.commons.maquette.template;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.htmlparser.jericho.Attributes;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.OutputDocument;
import net.htmlparser.jericho.Source;

import org.apache.log4j.Logger;

import fr.urssaf.image.commons.maquette.template.config.exception.MissingContentInfoBoxConfig;
import fr.urssaf.image.commons.maquette.template.config.exception.MissingTitleInfoBoxConfig;
import fr.urssaf.image.commons.maquette.template.config.exception.TemplateConfigNotInitialized;
import fr.urssaf.image.commons.maquette.template.generator.InfoBoxGenerator;
import fr.urssaf.image.commons.maquette.template.generator.MenuGenerator;
import fr.urssaf.image.commons.maquette.template.parser.HeadPartParser;
import fr.urssaf.image.commons.maquette.template.parser.exception.MissingHtmlElementInTemplateParserException;
import fr.urssaf.image.commons.maquette.template.parser.exception.MissingSourceParserException;
import fr.urssaf.image.commons.maquette.template.parser.internal.ContentAppParser;
import fr.urssaf.image.commons.maquette.template.parser.internal.FooterParser;
import fr.urssaf.image.commons.maquette.template.parser.internal.HeaderParser;
import fr.urssaf.image.commons.maquette.template.parser.internal.LeftColParser;
import fr.urssaf.image.commons.maquette.template.parser.internal.PageReminderParser;
import fr.urssaf.image.commons.maquette.tool.InfoBoxItem;
import fr.urssaf.image.commons.maquette.tool.MaquetteConstant;


/**
 * @author CER6990172
 * @desc	g�re l'instanciation de la class Source (jericho) pour le template html et le contenu html de l'application m�tier
 */
public class MaquetteParser {
	
	public static Logger logger = Logger.getLogger( MaquetteParser.class.getName() );
	
	protected OutputDocument odFromTemplate ;
	protected Source scFromTemplate ;
	protected Source scFromApplication ;
	protected HttpServletRequest hsr ;
	protected MaquetteConfig maquetteCfg ;
	
	/**
	 * @param html contenu html g�n�r� par l'application m�tier
	 * @param tpl chemin vers le template html du jar
	 * @param maquetteCfg 
	 * @throws IOException
	 */
	public MaquetteParser( String html, String tpl, HttpServletRequest hsr, MaquetteConfig maquetteCfg ) throws IOException
	{
		scFromTemplate = setSourceFromFile( tpl );
		scFromApplication = setSourceFromString( html );
		odFromTemplate = new OutputDocument( scFromTemplate );
		this.maquetteCfg = maquetteCfg ;
		this.hsr = hsr ;
	}
	
	/**
	 * @desc d�fini l'attribut Source � partir d'une cha�ne Html
	 * @param html
	 * @throws IOException
	 * @return Source
	 */
	protected Source setSourceFromString( String html )throws IOException{
		return new Source( html ) ;
	}
	
	/**
	 * @desc d�fini l'attribut Source depuis un fichier
	 * @param tpl
	 * @throws IOException
	 * @return Source
	 */
	protected Source setSourceFromFile( String tpl ) throws IOException{
		// L'un ou l'autre, aucun impact � priori
		// InputStream is = getClass().getResourceAsStream( tpl ) ;
		InputStreamReader is = new InputStreamReader( this.getClass().getResourceAsStream( tpl ),"ISO-8859-15") ;

		if( is != null )
			return new Source( is ) ;
		else
			throw new IOException( "Le template suivant ne semble pas accessible : " + tpl ) ;
	}
	
	/**
	 * @return outputDocument from template, le document modifi� � afficher
	 */
	public OutputDocument getOutputDocument()
	{
		return odFromTemplate ;
	}
	
	/**
	 * @desc lance les m�thodes de modification du code source
	 */
	public void build() {	

		// 1) Par d�faut on prend ce qu'il y a dans les instances des classes de configuration
		// 2) Ensuite on �crase avec ce que l'on trouve dans le html de l'application m�tier 
		try{
			buildHeadPart();
		}catch( Exception e ){
e.printStackTrace();
logger.warn( "Exception buildHeadPart : " + e.getClass() );			
		}
		
		try{
			buildHeader();
		}catch( Exception e ){
e.printStackTrace();
logger.warn( "Exception buildHeader : " + e.getClass() );
		}
		
		try{
			buildLeftCol();
		}catch( Exception e ){
e.printStackTrace();
logger.warn( "Exception buildLeftCol : " + e.getClass() );
		}
		
		try{
			buildBody();
		}catch( Exception e ){
e.printStackTrace();
logger.warn( "Exception buildBody : " + e.getClass() );			
		}
		
		try{
			buildFooter();
		}catch( Exception e ){
e.printStackTrace();
logger.warn( "Exception buildFooter : " + e.getClass() );			
		}		
	}
	
	/**
	 * @desc modifie le source html
	 * 		 s'occupe uniquement du contenu de la balise <head>
	 * 		 le contenu de la balise head de l'application m�tier est ins�r� � la fin de la balise head du template
	 * @throws MissingSourceHeadPartParserException
	 * @throws MissingHtmlElementInTemplateParserException 
	 * @throws TemplateConfigNotInitialized 
	 */
	protected void buildHeadPart() throws MissingSourceParserException, MissingHtmlElementInTemplateParserException, TemplateConfigNotInitialized
	{
		int start ;
		int end ;
		
		String title = "";
		String cssColorList = "";
		Boolean titleInserted = false ;
				
		// r�cup�ration du head du template et de l'application m�tier
		HeadPartParser hppTpl = new HeadPartParser(scFromTemplate) ;
		HeadPartParser hppApp;
		
		try {
			hppApp = new HeadPartParser(scFromApplication);
			
			// v�rification RGAA sur le titre : trace lanc� dans la console si �a arrive sinon modification de la
			// balise title du template
			// suppression du titre par d�faut
			if( hppApp.getTitleTag() != null )
			{
				start = hppTpl.getHeadTag().getEndTag().getBegin() ;
				title = hppApp.getTitleTag().getContent().toString() ;
				odFromTemplate.remove(hppTpl.getTitleTag()) ;
				odFromTemplate.insert(start, hppApp.getHeadTag().getContent().toString() );
				titleInserted = true ;
			}
			else if( maquetteCfg.getBrowserTitle() == null )
			{
logger.warn("Attention : aucune balise title trouv�e dans l'application m�tier ou dans " + 
				"l'instance HeadPartConfig");
			}

		// Pas de head trouv� dans l'application m�tier 
		} catch (MissingHtmlElementInTemplateParserException e) {		
logger.debug("Pas de head trouv� dans l'application m�tier");
	
		}finally
		{
			if( !titleInserted 
				&& maquetteCfg.getBrowserTitle() != null )
			{
				// On modifie le title et on laisse le reste de la balise head du template tel quel
				start = hppTpl.getTitleTag().getContent().getBegin() ;
				end = hppTpl.getTitleTag().getContent().getEnd() ;
				title = maquetteCfg.getBrowserTitle() ;
				odFromTemplate.replace(start, end, title) ;
			}
			
			// On ajoute les surcharges de couleurs
			if( maquetteCfg.getOverloadColor() )
			{
				start = hppTpl.getHeadTag().getEndTag().getBegin() ;
				cssColorList = "<!-- Surcharge des couleurs et images par d�faut -->" + 
				  "<link href='" + MaquetteConstant.GETRESOURCEURI + "?name=" + MaquetteConstant.CSSMAINCOLORFILE + "&overload=1' rel='stylesheet' type='text/css' />" +
				  "<link href='" + MaquetteConstant.GETRESOURCEURI + "?name=" + MaquetteConstant.CSSMAINFONTCOLORFILE + "&overload=1' rel='stylesheet' type='text/css' />" +
				  "<link href='" + MaquetteConstant.GETRESOURCEURI + "?name=" + MaquetteConstant.CSSMENUCOLORFILE + "&overload=1' rel='stylesheet' type='text/css' />" +
				  "<link href='" + MaquetteConstant.GETRESOURCEURI + "?name=" + MaquetteConstant.CSSMENUFONTCOLORFILE + "&overload=1' rel='stylesheet' type='text/css' />" ;
				odFromTemplate.insert(start, cssColorList);
			}
		}

	}
	
	/**
	 * @throws MissingSourceHeaderParserException 
	 * @throws MissingHtmlElementInTemplateParserException 
	 * @throws TemplateConfigNotInitialized 
	 * @desc modifie le source html
	 * 		 s'occupe uniquement du contenu de la balise <body> mais au niveau de la div header (bandeau 
	 * 		 sup�rieur de la maquette)
	 * 		 proc�de en r�cup�rant l'instance de HeaderConfig et injecte ses param�tres dans le template
	 */
	protected void buildHeader() throws MissingSourceParserException, MissingHtmlElementInTemplateParserException, TemplateConfigNotInitialized
	{
		int start ;
		int end ;
		
		// r�cup�ration du header du template
		HeaderParser hpTpl = new HeaderParser(scFromTemplate) ;
		
		// injection dans le template g�n�r� du header
		// titre : modification du contenu du tag
		start = hpTpl.getTitleTag().getContent().getBegin() ;
		end = hpTpl.getTitleTag().getContent().getEnd() ;
		String appTitle = maquetteCfg.getAppTitle() ;
		odFromTemplate.replace( start, end, appTitle ) ;
		
		// titre : modification du contenu de l'attribut
		Attributes attTitleAttributes = hpTpl.getTitleTag().getAttributes() ;
		start = attTitleAttributes.get("title").getValueSegment().getBegin();
		end = attTitleAttributes.get("title").getValueSegment().getEnd() ;
		String appTitleAttribute = maquetteCfg.getAppTitle() ;
		odFromTemplate.replace( start, end, appTitleAttribute ) ;
		
		// logo Image
		Attributes attMainLogoTag = hpTpl.getMainLogoTag().getAttributes() ;
		start = attMainLogoTag.get("src").getValueSegment().getBegin();
		end = attMainLogoTag.get("src").getValueSegment().getEnd() ;
		String mainLogo = maquetteCfg.getMainLogo() ;
		odFromTemplate.replace( start, end, mainLogo ) ;
		
		// logo Application : modification d'un attribut
		Attributes attLogoTag = hpTpl.getLogoTag().getAttributes() ;
		start = attLogoTag.get("src").getValueSegment().getBegin();
		end = attLogoTag.get("src").getValueSegment().getEnd() ;
		String appLogo = maquetteCfg.getAppLogo() ;
		odFromTemplate.replace( start, end, appLogo ) ;
		// Ajouter la taille de l'image si le navigateur client est sous MSIE6 
		if( maquetteCfg.isIE() )
		{
			// texte � rajouter sur les images suivantes du header
			String heightLogo = " style='height:50px' " ;
			
			// log de l'application
			start = attLogoTag.get("src").getEnd() ;
			odFromTemplate.insert(start, heightLogo ) ;
			
			// image pour la hauteur minimum
			Attributes attMinHeightImg = hpTpl.getMinHeightImg().getAttributes();
			start = attMinHeightImg.get("src").getEnd();
			odFromTemplate.insert(start, heightLogo ) ;
		}
		
		// Menu
		start = hpTpl.getMenuTag().getContent().getBegin() ;
		end = hpTpl.getMenuTag().getContent().getEnd() ;
		String menu = maquetteCfg.getMenu(hsr) ;
		odFromTemplate.replace( start, end, menu ) ;
	}
	
	
	/**
	 * @throws MissingSourceContentAppParserException 
	 * @throws TemplateConfigNotInitialized 
	 * @throws MissingTitleInfoBoxConfig 
	 * @throws MissingContentInfoBoxConfig 
	 * @throws MissingHtmlElementInTemplateParserException 
	 * @desc modifie le source html
	 * 		 s'occupe uniquement de construire la colonne de gauche
	 */
	protected void buildLeftCol() throws MissingSourceParserException, TemplateConfigNotInitialized, MissingContentInfoBoxConfig, MissingTitleInfoBoxConfig, MissingHtmlElementInTemplateParserException
	{
		int start ;
		String html = "" ;
		
		// Modification du source html si l'impl�mentation de ILeftCol est disponible
		if( maquetteCfg.getImplLeftcol() != null )
		{
			// Ajout des bo�tes standards si possible
			if( maquetteCfg.getImplLeftcol().getNomApplication(hsr) != null
				&& maquetteCfg.getImplLeftcol().getVersionApplication(hsr) != null 
				&& (
					maquetteCfg.getImplLeftcol().getNomApplication(hsr).length() > 0 
					|| maquetteCfg.getImplLeftcol().getVersionApplication(hsr).length() > 0 )
				)
			{
				InfoBoxItem appInfo = new InfoBoxItem( "app", "Application", "Informations relatives � l'application courante" ) ;
				appInfo.addSpan( "name", "Nom de l'application", maquetteCfg.getImplLeftcol().getNomApplication(hsr) ) ;
				appInfo.addSpan( "version", "Version de l'application", maquetteCfg.getImplLeftcol().getVersionApplication(hsr) ) ;
				html += InfoBoxGenerator.build( appInfo ) ;
			}
			
			if( maquetteCfg.getImplLeftcol().getNomUtilisateur(hsr) != null
				&& maquetteCfg.getImplLeftcol().getVersionApplication(hsr) != null
				&& maquetteCfg.getImplLeftcol().getLienDeconnexion(hsr) != null 
				&& (
					maquetteCfg.getImplLeftcol().getNomUtilisateur(hsr).length() > 0 
					|| maquetteCfg.getImplLeftcol().getVersionApplication(hsr).length() > 0 )
				)
			{
				InfoBoxItem usrInfo = new InfoBoxItem( "user", "Utilisateur", "Informations relatives � l'utilisateur identifi�" ) ;
				usrInfo.addSpan( "name", "Pr�nom Nom de l'utilisateur", maquetteCfg.getImplLeftcol().getNomUtilisateur(hsr) ) ;
				usrInfo.addSpan( "rights", "Droits affect�s � l'utilisateur", maquetteCfg.getImplLeftcol().getRoleUtilisateur(hsr) ) ;
				html += InfoBoxGenerator.build( usrInfo ) ;
				
				InfoBoxItem logoutInfo = new InfoBoxItem( "logout", "D�connexion", "Boite de d�connexion" ) ;
				logoutInfo.addBtn( "user", "D�connexion", maquetteCfg.getImplLeftcol().getLienDeconnexion(hsr) ) ;
				html += InfoBoxGenerator.build( logoutInfo ) ;
			}
			
			// Ajout des bo�tes sp�cifiques
			List<InfoBoxItem> specificInfoBoxList = maquetteCfg.getImplLeftcol().getInfoBox( hsr ) ;
			if( specificInfoBoxList != null )
				for( InfoBoxItem sibc : specificInfoBoxList )
				{
					html += InfoBoxGenerator.build( sibc ) ;
				}
			
			// Insertion du html
			LeftColParser lcpTpl = new LeftColParser(scFromTemplate) ;
			start = lcpTpl.getLeftColTag().getStartTag().getEnd() ;
			odFromTemplate.insert( start, html ) ;
		}
	}
	
	/**
	 * @throws MissingSourceBodyParserException 
	 * @throws MissingNoScriptTagContentAppParserException 
	 * @throws MissingSourceContentAppParserException 
	 * @throws MissingHtmlElementInTemplateParserException 
	 * @desc modifie le source html
	 * 		 s'occupe uniquement du contenu de la balise <body> mais au niveau de la div content 
	 * 		 (partie central qui doit �tre enrichi par le contenu de l'application m�tier)
	 * 		 proc�de en r�cup�rant le body de l'application m�tier et injecte le contenu dans le template
	 * 		 il v�rifie �galement que 'il y a bien une balise noscript, sinon il utilisera celle du template
	 */
	protected void buildBody() throws MissingSourceParserException, MissingHtmlElementInTemplateParserException
	{
		int start ;
		int end ;
		
		// r�cup�ration du body du template
		ContentAppParser capTpl = new ContentAppParser(scFromTemplate) ;
		PageReminderParser prTpl = new PageReminderParser(scFromTemplate) ;
				
		// mise en place du pageReminder (soit il est retourn� par l'applic cliente, 
		// soit on prend le comportement par d�faut)
		start = prTpl.getPageReminderTag().getContent().getBegin() ;
		end = prTpl.getPageReminderTag().getContent().getEnd() ;
		String breadcrumb = 
			( maquetteCfg.getImplMenu() != null 
				&& maquetteCfg.getImplMenu().getBreadcrumb(hsr) != null ) ?
			maquetteCfg.getImplMenu().getBreadcrumb(hsr) : MenuGenerator.buildBreadcrumb() ;
		odFromTemplate.replace( start, end, breadcrumb + "&nbsp;" ) ;
		
		// remplacement du contenu de content-application par le body de l'application m�tier
		start = capTpl.getContentAppTag().getContent().getBegin() ;
		end = capTpl.getContentAppTag().getContent().getEnd() ;
		List<Element> elBody = scFromApplication.getAllElements("body");
		
		String body ;
		// prise en compte du cas o� l'on a pas de body dans le html de l'application
		if( elBody.size() == 0 )
		{
			body = scFromApplication.toString() ;
		}
		else
			body = elBody.get(0).toString() ;
		
		// rajout de noscript s'il n'est pas pr�sent dans l'application m�tier
		String noScript = "" ;
		List<Element> elNoScriptList = scFromApplication.getAllElements("noscript") ;
		if( elNoScriptList.size() == 0 )
		{
			for( Element el : capTpl.getNoScriptTag() )
				noScript += el.toString() ;
			body += noScript ;
		}
		odFromTemplate.replace( start, end, body ) ;
	}
	
	/**
	 * @throws MissingSourceFooterParserException 
	 * @throws TemplateConfigNotInitialized 
	 * @throws MissingHtmlElementInTemplateParserException 
	 * @desc modifie le source html
	 * 		 s'occupe uniquement du contenu de la balise <body> mais au niveau de la div header (bandeau 
	 * 		 sup�rieur de la maquette)
	 * 		 proc�de en r�cup�rant l'instance de HeaderConfig et injectes ses param�tres dans le template
	 */
	protected void buildFooter() throws MissingSourceParserException, TemplateConfigNotInitialized, MissingHtmlElementInTemplateParserException
	{
		int start ;
		int end ;
		
		// r�cup�ration du footer du template
		FooterParser fpTpl = new FooterParser(scFromTemplate) ;
		
		// suppression du lien providedBy dans template g�n�r� du footer
		start = fpTpl.getProvidedByTag().getContent().getBegin() ;
		end = fpTpl.getProvidedByTag().getContent().getEnd() ;
		if( !maquetteCfg.getAppDisplayStandardsAndNorms() )
			odFromTemplate.replace( start, end, "" ) ;
		
		// suppression du lien providedBy dans template g�n�r� du footer
		start = fpTpl.getCopyrightTag().getContent().getBegin() ;
		end = fpTpl.getCopyrightTag().getContent().getEnd() ;
		String appCopyright = maquetteCfg.getAppCopyright();
		odFromTemplate.replace( start, end, appCopyright ) ;
	}
	
}