package fr.urssaf.image.commons.maquette.template.parser.exception;

public class MissingSourceParserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingSourceParserException() {
		// TODO Auto-generated constructor stub
	}

	public MissingSourceParserException(String parserName) {
		super("Le parser " + parserName + " ne dipose pas d'instance Source");
		// TODO Auto-generated constructor stub
	}

	public MissingSourceParserException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public MissingSourceParserException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
