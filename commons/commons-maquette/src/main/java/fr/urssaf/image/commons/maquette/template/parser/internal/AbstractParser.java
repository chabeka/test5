package fr.urssaf.image.commons.maquette.template.parser.internal;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import fr.urssaf.image.commons.maquette.template.parser.exception.MissingHtmlElementInTemplateParserException;

/**
 * @author CER6990172
 * 
 */
public abstract class AbstractParser
{
	/**
	 * @param sc
	 * @param hmlt identifier
	 * @throws MissingHtmlElementInTemplateParserException 
	 */
	protected Element getElementById( Source sc, String id ) throws MissingHtmlElementInTemplateParserException
	{
		Element el = sc.getElementById( id ) ;
		if( el == null )
			throw new MissingHtmlElementInTemplateParserException( id ) ;
		return el ;
	}
	
}
