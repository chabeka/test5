package fr.urssaf.image.commons.birt.exception;

public class MissingConstructorParamBirtRenderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingConstructorParamBirtRenderException() {
		// TODO Auto-generated constructor stub
	}

	public MissingConstructorParamBirtRenderException(String message) {
		super("Il manque le paramètre suivant : " + message);
		// TODO Auto-generated constructor stub
	}

	public MissingConstructorParamBirtRenderException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public MissingConstructorParamBirtRenderException(String message,
			Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
