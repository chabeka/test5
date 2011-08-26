package fr.urssaf.image.sae.mapping.model;



/**
 * Énumération contenant la listes des codes long des métadonnées techniques
 * considéré comme attributs.<br/>
 * Ces métadonnées sont à exclure des la liste des métadonnées du
 * {@link SAEDocument}.
 * 
 * @author akenore
 * 
 */
public enum TechnicalMetadatas {
	// Date creation
	DATECREATION("DateCreation"),
	// Titre du document
	TITRE("Titre"),
	// type
	TYPE("type"),
	// pas de valeur
	NOVALUE("");
	// Le code long de la métadonnée.
	private String longCode;
	// Le code court de la métadonnée.
	private String shortCode;

	/**
	 * @param longCode
	 *            : Le code long de la métadonnée.
	 */
	public void setLongCode(final String longCode) {
		this.longCode = longCode;
	}

	/**
	 * @return : Le code long de la métadonnée.
	 */
	public String getLongCode() {
		return longCode;
	}

	TechnicalMetadatas(final String longCode) {
		this.longCode = longCode;
	}

	/**
	 * @param shortCode
	 *            : Le code court de la métadonnée.
	 */
	public final void setShortCode(final String shortCode) {
		this.shortCode = shortCode;
	}

	/**
	 * @return Le code court de la métadonnée.
	 */
	public String getShortCode() {
		return shortCode;
	}
}
