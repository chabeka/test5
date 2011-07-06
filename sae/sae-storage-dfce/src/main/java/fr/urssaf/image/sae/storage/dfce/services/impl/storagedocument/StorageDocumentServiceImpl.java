package fr.urssaf.image.sae.storage.dfce.services.impl.storagedocument;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.storage.dfce.annotations.FacadePattern;
import fr.urssaf.image.sae.storage.exception.DeletionServiceEx;
import fr.urssaf.image.sae.storage.exception.InsertionServiceEx;
import fr.urssaf.image.sae.storage.exception.RetrievalServiceEx;
import fr.urssaf.image.sae.storage.exception.SearchingServiceEx;
import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;
import fr.urssaf.image.sae.storage.model.storagedocument.BulkInsertionResults;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocuments;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.LuceneCriteria;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;
import fr.urssaf.image.sae.storage.services.storagedocument.DeletionService;
import fr.urssaf.image.sae.storage.services.storagedocument.InsertionService;
import fr.urssaf.image.sae.storage.services.storagedocument.RetrievalService;
import fr.urssaf.image.sae.storage.services.storagedocument.SearchingService;
import fr.urssaf.image.sae.storage.services.storagedocument.StorageDocumentService;

/**
 * 
 * @author akenore
 * 
 */
@Service
@Qualifier("storageDocumentService")
@FacadePattern(participants = { InsertionServiceImpl.class,
		RetrievalServiceImpl.class, SearchingServiceImpl.class,
		DeletionServiceImpl.class }, comment = "Fournit les services des classes participantes")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class StorageDocumentServiceImpl implements StorageDocumentService {
	@Autowired
	@Qualifier("insertionService")
	private InsertionService insertionService;
	@Autowired
	@Qualifier("searchingService")
	private SearchingService searchingService;
	@Autowired
	@Qualifier("retrievalService")
	private RetrievalService retrievalService;

	@Autowired
	@Qualifier("deletionService")
	private DeletionService deletionService;

	/**
	 * @return les services de suppression
	 */
	public final DeletionService getDeletionService() {
		return deletionService;
	}

	/**
	 * @param deletionService
	 *            : les services de suppression
	 */
	public final void setDeletionService(final DeletionService deletionService) {
		this.deletionService = deletionService;
	}

	/**
	 * {@inheritDoc}
	 */

	public final UUID insertStorageDocument(
			final StorageDocument storageDocument) throws InsertionServiceEx {
		return insertionService.insertStorageDocument(storageDocument);
	}

	/**
	 * 
	 * @return Les services d'insertions
	 */
	public final InsertionService getInsertionService() {
		return insertionService;
	}

	/**
	 * 
	 * @return les services de recherche
	 */
	public final SearchingService getSearchingService() {
		return searchingService;
	}

	/**
	 * 
	 * @return les services de récupération
	 */
	public final RetrievalService getRetrievalService() {
		return retrievalService;
	}

	/**
	 * {@inheritDoc}
	 */

	public final StorageDocuments searchStorageDocumentByLuceneCriteria(
			final LuceneCriteria luceneCriteria) throws SearchingServiceEx {

		return searchingService
				.searchStorageDocumentByLuceneCriteria(luceneCriteria);
	}

	/**
	 * {@inheritDoc}
	 */

	public final StorageDocument searchStorageDocumentByUUIDCriteria(
			final UUIDCriteria uUIDCriteria) throws SearchingServiceEx {

		return searchingService
				.searchStorageDocumentByUUIDCriteria(uUIDCriteria);
	}

	/**
	 * {@inheritDoc}
	 */
	public final StorageDocument retrieveStorageDocumentByUUID(
			final UUIDCriteria uUIDCriteria) throws RetrievalServiceEx {

		return retrievalService.retrieveStorageDocumentByUUID(uUIDCriteria);
	}

	/**
	 * {@inheritDoc}
	 */

	public final byte[] retrieveStorageDocumentContentByUUID(
			final UUIDCriteria uUIDCriteria) throws RetrievalServiceEx {

		return retrievalService
				.retrieveStorageDocumentContentByUUID(uUIDCriteria);
	}

	/**
	 * {@inheritDoc}
	 */

	public final List<StorageMetadata> retrieveStorageDocumentMetaDatasByUUID(
			final UUIDCriteria uUIDCriteria) throws RetrievalServiceEx {

		return retrievalService
				.retrieveStorageDocumentMetaDatasByUUID(uUIDCriteria);
	}

	/**
	 * {@inheritDoc}
	 */
	public final BulkInsertionResults bulkInsertStorageDocument(
			final StorageDocuments storageDocuments, final boolean allOrNothing) {

		return insertionService.bulkInsertStorageDocument(storageDocuments,
				allOrNothing);
	}

	/**
	 * Initialise les services d'insertion
	 * 
	 * @param insertionService
	 *            : les services d'insertions
	 */
	public final void setInsertionService(
			final InsertionService insertionService) {
		this.insertionService = insertionService;
	}

	/**
	 * Initialise les services de recherche
	 * 
	 * @param searchingService
	 *            : Le service de recherche
	 */
	public final void setSearchingService(
			final SearchingService searchingService) {
		this.searchingService = searchingService;
	}

	/**
	 * Initialise les services de récupération
	 * 
	 * @param retrievalService
	 *            : les services de récupération
	 */
	public final void setRetrievalService(
			final RetrievalService retrievalService) {
		this.retrievalService = retrievalService;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void setInsertionServiceParameter(
			final StorageConnectionParameter storageConnectionParameter) {
		this.insertionService
				.setInsertionServiceParameter(storageConnectionParameter);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void setSearchingServiceParameter(
			final StorageConnectionParameter storageConnectionParameter) {
		this.searchingService
				.setSearchingServiceParameter(storageConnectionParameter);

	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void setRetrievalServiceParameter(
			final StorageConnectionParameter storageConnectionParameter) {
		this.retrievalService
				.setRetrievalServiceParameter(storageConnectionParameter);
	}

	/**
	 * {@inheritDoc}
	 */
	public final void deleteStorageDocument(final UUIDCriteria uuidCriteria)
			throws DeletionServiceEx {
		this.deleteStorageDocument(uuidCriteria);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void setDeletionServiceParameter(
			final StorageConnectionParameter storageConnectionParameter) {
		this.deletionService
				.setDeletionServiceParameter(storageConnectionParameter);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void setStorageDocumentServiceParameter(
			final StorageConnectionParameter storageConnectionParameter) {
		insertionService
				.setInsertionServiceParameter(storageConnectionParameter);
		retrievalService
				.setRetrievalServiceParameter(storageConnectionParameter);
		searchingService
				.setSearchingServiceParameter(storageConnectionParameter);
		deletionService.setDeletionServiceParameter(storageConnectionParameter);
	}

}
