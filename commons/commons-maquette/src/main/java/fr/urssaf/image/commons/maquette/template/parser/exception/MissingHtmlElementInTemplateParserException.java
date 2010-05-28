package fr.urssaf.image.commons.maquette.template.parser.exception;

public class MissingHtmlElementInTemplateParserException extends Exception {

	public MissingHtmlElementInTemplateParserException(String id) {
		super( "L'élément suivant n'a pas été trouvé dans le template de la maquette : " + id ) ;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
