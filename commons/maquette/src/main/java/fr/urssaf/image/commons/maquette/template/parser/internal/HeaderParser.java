package fr.urssaf.image.commons.maquette.template.parser.internal;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import fr.urssaf.image.commons.maquette.template.parser.exception.MissingHtmlElementInTemplateParserException;
import fr.urssaf.image.commons.maquette.template.parser.exception.MissingSourceParserException;

/**
 * @author CER6990172
 * @desc parse la balise body de toute chaîne ou fichier de template pour en récupérer les éléments de la balise <div id="header">
 */
public class HeaderParser extends AbstractParser
{
	private Element mainLogoTag ;
	private Element titleTag ;
	private Element logoTag ;
	private Element menuTag ;
	private Element minHeightImg ;
	
	/**
	 * @desc default constructor
	 */
	public HeaderParser() {
		
	}
	
	/**
	 * @desc exécute le doParse dans la foulée
	 * @param sc
	 * @throws MissingSourceParserException 
	 * @throws MissingHtmlElementInTemplateParserException 
	 */
	public HeaderParser( Source sc ) throws MissingSourceParserException, MissingHtmlElementInTemplateParserException {
		doParse(sc) ;
	}

	/**
	 * @return the mainLogoTag
	 */
	public Element getMainLogoTag() {
		return mainLogoTag;
	}

	/**
	 * @return the titleTag
	 */
	public Element getTitleTag() {
		return titleTag;
	}

	/**
	 * @return the logoTag
	 */
	public Element getLogoTag() {
		return logoTag;
	}

	/**
	 * @return the menuTag
	 */
	public Element getMenuTag() {
		return menuTag;
	}
	
	/**
	 * @return the image identified by the id minheight
	 */
	public Element getMinHeightImg() {
		return minHeightImg;
	}

	/**
	 * @desc lance le parsing des éléments de la balise body contenu dans l'attribut Source
	 * @param sc
	 * @throws MissingSourceParserException
	 * @throws MissingHtmlElementInTemplateParserException 
	 */
	public void doParse( Source sc ) throws MissingSourceParserException, MissingHtmlElementInTemplateParserException
	{	
		if( sc != null )
		{
			mainLogoTag = doGetMainLogoTag( sc );
			titleTag = doGetTitleTag( sc );
			logoTag = doGetLogoTag( sc ) ;
			menuTag = doGetMenuTag( sc ) ;
			minHeightImg = doGetMinHeightImg( sc ) ;
		}
		else
			throw new MissingSourceParserException("Header") ;
	}
	
	/**
	 * @desc	retourne le chemin vers le logo Image
	 * @param sc
	 * @throws MissingHtmlElementInTemplateParserException 
	 */
	public Element doGetMainLogoTag( Source sc ) throws MissingHtmlElementInTemplateParserException {
		return getElementById( sc, "logoimage" ) ;
	}
	
	/**
	 * @desc	retourne le titre affiché dans le navigateur
	 * @param sc
	 * @throws MissingHtmlElementInTemplateParserException 
	 */
	public Element doGetTitleTag( Source sc ) throws MissingHtmlElementInTemplateParserException {
		return getElementById( sc, "title-app" ) ;
	}
	
	/**
	 * @desc	retourne le chemin vers le logo affiché dans le navigateur
	 * @param sc
	 * @throws MissingHtmlElementInTemplateParserException 
	 */
	public Element doGetLogoTag( Source sc ) throws MissingHtmlElementInTemplateParserException {
		return getElementById( sc, "logoappli" ) ;
	}
	
	/**
	 * @desc	retourne la zone du menu
	 * @param sc
	 * @throws MissingHtmlElementInTemplateParserException 
	 */
	public Element doGetMenuTag( Source sc ) throws MissingHtmlElementInTemplateParserException {
		return getElementById( sc, "menu" ) ;
	}
	
	/**
	 * @desc	retourne l'image minheight
	 * @param sc
	 * @throws MissingHtmlElementInTemplateParserException 
	 */
	public Element doGetMinHeightImg( Source sc ) throws MissingHtmlElementInTemplateParserException {
		return getElementById( sc, "minheight" ) ;
	}
	
}
