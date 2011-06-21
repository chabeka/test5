package fr.urssaf.image.sae.storage.model.storagedocument;

import java.util.List;

/**
 * Classe concrète représentant la liste des documents
 * 
 * <li>
 * Attribut storageDocuments : La liste des documents bien archivés</li>
 */
public class StorageDocuments {

	private List<StorageDocument> listOfStorageDocument; // NOPMD

	/**
	 * Retourne la liste des documents
	 * 
	 * @return La liste des documents
	 */
	public final List<StorageDocument> getListOfStorageDocument() {
		return listOfStorageDocument;
	}

	/**
	 * Initialise la liste des documents
	 * 
	 * @param listOfStorageDocument
	 *            : La liste des documents
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void setListOfStorageDocument(
			final List<StorageDocument> listOfStorageDocument) {
		this.listOfStorageDocument = listOfStorageDocument;
	}

	/**
	 * Constructeur
	 * 
	 * @param listOfStorageDocument
	 *            La liste des documents
	 */
	@SuppressWarnings("PMD.LongVariable")
	public StorageDocuments(final List<StorageDocument> listOfStorageDocument) {
		this.listOfStorageDocument = listOfStorageDocument;
	}

	/**
	 * Constructeur par défaut
	 * 
	 */
	public StorageDocuments() {
		// ici on ne fait rien
	}
}
