package fr.urssaf.image.sae.storage.model.connection;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Classe concrète contenant le nom de la base de stockage <br />
 * 
 * <li>
 * Attribut baseName : Représente le nom de la base de stockage</li>
 */
public class StorageBase {

	private String baseName;

	/**
	 * Retourne le nom de la base de stockage
	 * 
	 * @return Le nom de la base de stockage
	 */
	public final String getBaseName() {
		return baseName;
	}

	/**
	 * Initialise nom de la base de stockage
	 * 
	 * @param baseName
	 *            : Le nom de la base de stockage
	 */
	public final void setBaseName(final String baseName) {
		this.baseName = baseName;
	}

	/**
	 * Constructeur
	 * 
	 * @param baseName
	 *            : Le nom de la base de stockage
	 */
	public StorageBase(final String baseName) {
		this.baseName = baseName;
	}

	/**
	 * Constructeur par défaut
	 * 
	 * 
	 */
	public StorageBase() {
//ici on ne fait rien
	}

	@Override
	public final String toString() {
		return new ToStringBuilder(this).append("baseName", baseName)
				.toString();
	}
}
