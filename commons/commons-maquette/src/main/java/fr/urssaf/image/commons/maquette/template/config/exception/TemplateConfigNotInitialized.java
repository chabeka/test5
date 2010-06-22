package fr.urssaf.image.commons.maquette.template.config.exception;

public class TemplateConfigNotInitialized extends Exception {

	/**
	 * 
	 */
	public TemplateConfigNotInitialized() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param message
	 */
	public TemplateConfigNotInitialized(String zone) {
		super("La configuration du template de la zone suivante n'a pas été initialisée : " + zone);
	}

	/**
	 * @param cause
	 */
	public TemplateConfigNotInitialized(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public TemplateConfigNotInitialized(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
