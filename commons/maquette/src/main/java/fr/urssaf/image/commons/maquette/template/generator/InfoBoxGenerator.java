package fr.urssaf.image.commons.maquette.template.generator;

import fr.urssaf.image.commons.maquette.template.config.exception.MissingContentInfoBoxConfig;
import fr.urssaf.image.commons.maquette.template.config.exception.MissingTitleInfoBoxConfig;
import fr.urssaf.image.commons.maquette.tool.InfoBoxItem;

/**
 * @author 	CER6990172
 * @desc	cette classe gère les boites utilisés par la section <leftcol> de la maquette
 * 			
 */
public final class InfoBoxGenerator
{		
	public static String build( InfoBoxItem ibi ) throws MissingContentInfoBoxConfig, MissingTitleInfoBoxConfig
	{
		if( ibi.getContent().length() == 0 )
			throw new MissingContentInfoBoxConfig();
		
		if( ibi.getTitle().length() == 0 )
			throw new MissingTitleInfoBoxConfig();
		
		if( ibi.getBoxDesc().length() == 0 )
			throw new MissingTitleInfoBoxConfig();
		
		String html = "<h3 class=\"boxTitle\" id=\"" + ibi.getShortIdentifier() + "-title\">" + ibi.getTitle() + "</h3>\n"
					+ "<p class=\"boxContent\" id=\"" + ibi.getShortIdentifier() + "\" title=\"" + ibi.getBoxDesc() + "\">\n"
					+ ibi.getContent() + "</p>\n" ;
		
		return html ;
	}
}
