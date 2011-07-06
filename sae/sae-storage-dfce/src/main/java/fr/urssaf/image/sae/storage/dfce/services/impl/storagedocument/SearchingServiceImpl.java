package fr.urssaf.image.sae.storage.dfce.services.impl.storagedocument;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.storage.dfce.annotations.Loggable;
import fr.urssaf.image.sae.storage.dfce.messages.LogLevel;
import fr.urssaf.image.sae.storage.dfce.model.AbstractDfceService;
import fr.urssaf.image.sae.storage.exception.SearchingServiceEx;
import fr.urssaf.image.sae.storage.model.connection.StorageBase;
import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocuments;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.LuceneCriteria;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;
import fr.urssaf.image.sae.storage.services.storagedocument.SearchingService;

/**
 * Implémente les services de l'interface SearchingService.
 * 
 * @author akenore
 * 
 */
@Service
@Qualifier("searchingService")
public class SearchingServiceImpl extends AbstractDfceService implements
		SearchingService {

	/**
	 * Constructeur de la classe
	 * 
	 * @param storageBase
	 *            : La base de stockage
	 */
	public SearchingServiceImpl(final StorageBase storageBase) {
		super(storageBase);
	}

	/**
	 * Construction par défaut
	 **/
	public SearchingServiceImpl() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Loggable(LogLevel.TRACE)
	public final StorageDocuments searchStorageDocumentByLuceneCriteria(
			final LuceneCriteria luceneCriteria) throws SearchingServiceEx {
		try {
			//TODO
			return null;
		} catch (RuntimeException runtimeException) {
			throw new SearchingServiceEx(getMessageHandler().getMessage(""), runtimeException);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Loggable(LogLevel.TRACE)
	public final StorageDocument searchStorageDocumentByUUIDCriteria(
			final UUIDCriteria uUIDCriteria) throws SearchingServiceEx {

		try {
			//TODO
			return null;
		} catch (RuntimeException runtimeException) {
			throw new SearchingServiceEx("", runtimeException);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void setSearchingServiceParameter(
			final StorageConnectionParameter storageConnectionParameter) {
		setStorageBase(storageConnectionParameter.getStorageBase());
	}

}
