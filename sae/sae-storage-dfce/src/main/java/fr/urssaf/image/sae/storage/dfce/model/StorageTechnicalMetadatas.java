package fr.urssaf.image.sae.storage.dfce.model;

/**
 * Énumération contenant la listes des codes courts des métadonnées techniques
 * considéré comme attributs.<br/>
 * Ces métadonnées sont à exclure des la liste des métadonnées du
 * {@link SAEDocument}.
 * 
 * @author akenore
 * 
 */
public enum StorageTechnicalMetadatas {
	// Date creation
	DATECREATION("DateCreation", "_creationDate"),
	// Titre du document
	TITRE("Titre", "_titre"),
	// type
	TYPE("type", "_type"),
	// Le type hash
	TYPEHASH("TypeHash", "version.1.digest.alogorithm"),
	// Le hash
	HASH("Hash", "version.1.digest"),
	// referenceUUID
	REFERENCEUUID("referenceUUID", "version.1.virtual.ref.uuid"),
	// DateArchivage
	DATEARCHIVE("DateArchivage", "_archivageDate"),
	// currentVersionNumber
	CURRENTVERSIONNUMBER("currentVersionNumber", "_versionNb"),
	// version number
	VERSIONNUMBER("versionNumber", "version.1"),
	// startPage
	STARTPAGE("startPage", "version.1.virtual.start.page"),
	// endPage
	ENDPAGE("endPage", "version.1.virtual.end.page"),
	// ObjectType
	OBJECTTYPE("ObjectType", "OTY"),
	// Gel
	GEL("Gel", "GEL"),
	// TracabilitePostArchivage
	TRACABILITEPOSTARCHIVAGE("TracabilitePostArchivage", "TOA"),
	// TailleFichier
	TAILLEFICHIER("TailleFichier", "version.1.size"),
	// DureeConservation
	DUREECONSERVATION("DureeConservation", "DCO"),
	//NomFichier 
	NOMFICHIER("NomFichier","NFI"),
	//DateModification
	DATEMODIFICATION("DateModification" ,"DMO"),
	// pas de valeur
	NOVALUE("", "");

	// Le code court de la métadonnée.
	private String shortCode;

	/**
	 * 
	 * @param shortCode
	 *            . Le code court
	 */
	StorageTechnicalMetadatas(final String longCode, final String shortCode) {
		this.shortCode = shortCode;
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

	// Le code long de la métadonnée.
	private String longCode;

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

}
