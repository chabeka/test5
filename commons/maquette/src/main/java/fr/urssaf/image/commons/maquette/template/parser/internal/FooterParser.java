package fr.urssaf.image.commons.maquette.template.parser.internal;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import fr.urssaf.image.commons.maquette.template.parser.exception.MissingHtmlElementInTemplateParserException;
import fr.urssaf.image.commons.maquette.template.parser.exception.MissingSourceParserException;

/**
 * @author CER6990172
 * @desc parse la balise body de toute chaîne ou fichier de template pour en récupérer les éléments de la balise <div id="header">
 */
public class FooterParser extends AbstractParser
{
	private Element providedByTag ;
	private Element copyrightTag ;
	
	/**
	 * @desc default constructor
	 */
	public FooterParser() {
		
	}
	
	/**
	 * @desc exécute le doParse dans la foulée
	 * @param sc
	 * @throws MissingSourceParserException 
	 * @throws MissingHtmlElementInTemplateParserException 
	 */
	public FooterParser( Source sc ) throws MissingSourceParserException, MissingHtmlElementInTemplateParserException {
		doParse(sc) ;
	}

	/**
	 * @return the providedByTag
	 */
	public Element getProvidedByTag() {
		return providedByTag;
	}

	/**
	 * @return the copyrightTag
	 */
	public Element getCopyrightTag() {
		return copyrightTag;
	}

	/**
	 * @desc lance le parsing des éléments de la balise body contenu dans l'attribut Source
	 * @param sc
	 * @throws MissingSourceFooterParserException
	 * @throws MissingHtmlElementInTemplateParserException 
	 */
	public void doParse( Source sc ) throws MissingSourceParserException, MissingHtmlElementInTemplateParserException
	{	
		if( sc != null )
		{
			providedByTag = doGetProvidedByTag( sc );
			copyrightTag = doGetCopyrightTag( sc ) ;
		}
		else
			throw new MissingSourceParserException("Footer") ;
	}
	
	/**
	 * @desc	retourne le lien providedBy
	 * @param sc
	 * @throws MissingHtmlElementInTemplateParserException 
	 */
	public Element doGetProvidedByTag( Source sc ) throws MissingHtmlElementInTemplateParserException {
		return getElementById( sc, "providedby" ) ;
	}
	
	/**
	 * @desc	retourne le chemin vers le logo affiché dans le navigateur
	 * @param sc
	 * @throws MissingHtmlElementInTemplateParserException 
	 */
	public Element doGetCopyrightTag( Source sc ) throws MissingHtmlElementInTemplateParserException {
		return getElementById( sc, "copyright" ) ;
	}
	
}
