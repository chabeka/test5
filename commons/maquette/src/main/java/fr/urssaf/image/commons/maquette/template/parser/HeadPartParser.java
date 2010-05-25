package fr.urssaf.image.commons.maquette.template.parser;

import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import fr.urssaf.image.commons.maquette.template.parser.exception.MissingHtmlElementInTemplateParserException;
import fr.urssaf.image.commons.maquette.template.parser.exception.MissingSourceParserException;

/**
 * @author CER6990172
 * @desc parse la balise head de toute chaîne ou fichier de template
 * @TODO tester les cas suivants :
 * 			pas de head dans la source
 * 			pas de title
 * 			pas de meta
 * 			pas de link
 * 			pas de style
 * 			pas de script
 * 			balise link non fermée
 * 			balise links mal formatée
 * 			comment est interprété la section particulière à IE6 ?
 */
public class HeadPartParser
{
	private Element headTag ;
	private Element titleTag ;
	private List<Element> metaTag ;
	private List<Element> linkTag ;
	private List<Element> styleTag ;
	private List<Element> scriptTag ;
	
	/**
	 * @desc default constructor
	 */
	public HeadPartParser() {
		
	}
	
	/**
	 * @desc exécute le doParse dans la foulée
	 * @param sc
	 * @throws MissingSourceParserException
	 * @throws MissingHtmlElementInTemplateParserException 
	 */
	public HeadPartParser( Source sc ) throws MissingSourceParserException, MissingHtmlElementInTemplateParserException {
		doParse(sc) ;
	}

	/**
	 * @return the headTag
	 */
	public Element getHeadTag() {
		return headTag;
	}

	/**
	 * @return the titleTag
	 */
	public Element getTitleTag() {
		return titleTag;
	}

	/**
	 * @return the metaTag
	 */
	public List<Element> getMetaTag() {
		return metaTag;
	}

	/**
	 * @return the linkTag
	 */
	public List<Element> getLinkTag() {
		return linkTag;
	}

	/**
	 * @return the styleTag
	 */
	public List<Element> getStyleTag() {
		return styleTag;
	}

	/**
	 * @return the scriptTag
	 */
	public List<Element> getScriptTag() {
		return scriptTag;
	}

	/**
	 * @desc lance le parsing des éléments de la balise head contenu dans l'attribut Source
	 * @param sc
	 * @throws MissingSourceHeadPartParserException
	 * @throws MissingHeadTagHeadPartParserException 
	 */
	public void doParse( Source sc ) throws MissingSourceParserException, MissingHtmlElementInTemplateParserException
	{	
		if( sc != null )
		{
			headTag = doGetHeadTag( sc );
			titleTag = doGetTitleTag( sc );
			metaTag = doGetMetaTag() ;
			linkTag = doGetLinkTag() ;
			styleTag = doGetStyleTag() ;
			scriptTag = doGetScriptTag() ;
		}
		else
			throw new MissingSourceParserException("HeadPart") ;
	}
	
	/**
	 * @desc	retourne le titre affiché dans le navigateur
	 * @param sc
	 * @throws MissingHeadTagHeadPartParserException 
	 */
	private Element doGetHeadTag( Source sc ) throws MissingHtmlElementInTemplateParserException {
		List<Element> elHead = sc.getAllElements("head");
		if( elHead.size() == 0 )
			throw new MissingHtmlElementInTemplateParserException("head tag");
		
		return elHead.get(0) ;
	}
	
	/**
	 * @desc	retourne le titre affiché dans le navigateur
	 * @param sc
	 */
	private Element doGetTitleTag( Source sc ) {
		Element titleElement = sc.getFirstElement(HTMLElementName.TITLE);
		return titleElement ;
	}
	
	/**
	 * @desc	retourne les metas
	 * @throws MissingHtmlElementInTemplateParserException 
	 */
	private List<Element> doGetMetaTag() throws MissingHtmlElementInTemplateParserException {
		return doGetListTagFromHead("meta");
	}
	
	/**
	 * @desc	retourne les metas
	 * @throws MissingHtmlElementInTemplateParserException 
	 */
	private List<Element> doGetLinkTag() throws MissingHtmlElementInTemplateParserException {
		return doGetListTagFromHead("link");
	}
	
	/**
	 * @desc	retourne les styles incorporés au document html
	 * @throws MissingHeadTagHeadPartParserException 
	 */
	private List<Element> doGetStyleTag() throws MissingHtmlElementInTemplateParserException {
		return doGetListTagFromHead("style");
	}
	
	/**
	 * @desc	retourne les styles incorporés au document html
	 * @param sc
	 * @throws MissingHtmlElementInTemplateParserException 
	 */
	private List<Element> doGetScriptTag() throws MissingHtmlElementInTemplateParserException {
		return doGetListTagFromHead( "script");
	}
	
	/**
	 * @desc retourne la liste complète des tags d'un type donné présent dans la balise head
	 * @param sc
	 * @param tagName
	 * @return elList
	 * @throws MissingHtmlElementInTemplateParserException 
	 */
	private List<Element> doGetListTagFromHead( String tagName ) throws MissingHtmlElementInTemplateParserException
	{
		if( headTag == null )
			throw new MissingHtmlElementInTemplateParserException("head tag");
		
		List<Element> elList = headTag.getAllElements( tagName ) ;
		
		return elList ;
	}
	
}
