package fr.urssaf.image.commons.maquette.template.config.exception;

public class UnkownThemeForMaquetteConfig extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnkownThemeForMaquetteConfig() {
		super();
	}

	public UnkownThemeForMaquetteConfig(String theme) {
		super("Le theme spécifié est inconnu : " + theme);
	}

	public UnkownThemeForMaquetteConfig(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public UnkownThemeForMaquetteConfig(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
