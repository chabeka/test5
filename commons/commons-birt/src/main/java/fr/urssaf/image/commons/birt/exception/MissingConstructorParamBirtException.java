package fr.urssaf.image.commons.birt.exception;

public class MissingConstructorParamBirtException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingConstructorParamBirtException() {
		super();
	}

	public MissingConstructorParamBirtException(String message) {
		super("Il manque le paramètre suivant : " + message);
	}

	public MissingConstructorParamBirtException(Throwable cause) {
		super(cause);
	}

	public MissingConstructorParamBirtException(String message,
			Throwable cause) {
		super(message, cause);
	}

}
