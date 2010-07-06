package fr.urssaf.image.commons.birt.exception;

public class MissingConstructorParamBirtRenderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingConstructorParamBirtRenderException() {
		super();
	}

	public MissingConstructorParamBirtRenderException(String message) {
		super("Il manque le paramètre suivant : " + message);
	}

	public MissingConstructorParamBirtRenderException(Throwable cause) {
		super(cause);
	}

	public MissingConstructorParamBirtRenderException(String message,
			Throwable cause) {
		super(message, cause);
	}

}
