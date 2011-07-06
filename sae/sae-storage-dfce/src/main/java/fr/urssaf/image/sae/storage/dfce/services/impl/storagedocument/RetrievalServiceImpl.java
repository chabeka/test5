package fr.urssaf.image.sae.storage.dfce.services.impl.storagedocument;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.storage.dfce.annotations.Loggable;
import fr.urssaf.image.sae.storage.dfce.messages.LogLevel;
import fr.urssaf.image.sae.storage.dfce.model.AbstractDfceService;
import fr.urssaf.image.sae.storage.exception.RetrievalServiceEx;
import fr.urssaf.image.sae.storage.model.connection.StorageBase;
import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;
import fr.urssaf.image.sae.storage.services.storagedocument.RetrievalService;

/**
 * Implémente les services de l'interface RetrievalService.
 * 
 * @author akenore
 * 
 */
@Service
@Qualifier("retrievalService")
public class RetrievalServiceImpl extends AbstractDfceService implements
		RetrievalService {
	/**
	 * Constructeur de la classe
	 * 
	 * @param storageBase
	 *            : La base de stockage
	 */
	public RetrievalServiceImpl(final StorageBase storageBase) {
		super(storageBase);
	}

	/**
	 * Construction par défaut
	 **/
	public RetrievalServiceImpl() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Loggable(LogLevel.TRACE)
	public final StorageDocument retrieveStorageDocumentByUUID(
			final UUIDCriteria uUIDCriteria) throws RetrievalServiceEx {
		try {
			// TODO
			return null;
		} catch (RuntimeException runtimeException) {
			throw new RetrievalServiceEx("", runtimeException);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Loggable(LogLevel.TRACE)
	public final byte[] retrieveStorageDocumentContentByUUID(
			final UUIDCriteria uUIDCriteria) throws RetrievalServiceEx {

		try {
			// TODO
			return null;
		} catch (RuntimeException runtimeException) {
			throw new RetrievalServiceEx("", runtimeException);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Loggable(LogLevel.TRACE)
	public final List<StorageMetadata> retrieveStorageDocumentMetaDatasByUUID(
			final UUIDCriteria uUIDCriteria) throws RetrievalServiceEx {
		try {
			// TODO
			return null;
		} catch (RuntimeException runtimeException) {
			throw new RetrievalServiceEx("", runtimeException);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void setRetrievalServiceParameter(
			final StorageConnectionParameter storageConnectionParameter) {
		setStorageBase(storageConnectionParameter.getStorageBase());

	}

}
