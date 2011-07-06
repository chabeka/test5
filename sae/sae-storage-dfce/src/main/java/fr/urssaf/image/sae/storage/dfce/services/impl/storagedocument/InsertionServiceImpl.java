package fr.urssaf.image.sae.storage.dfce.services.impl.storagedocument;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.storage.dfce.annotations.Loggable;
import fr.urssaf.image.sae.storage.dfce.messages.LogLevel;
import fr.urssaf.image.sae.storage.dfce.model.AbstractDfceService;
import fr.urssaf.image.sae.storage.exception.InsertionServiceEx;
import fr.urssaf.image.sae.storage.model.connection.StorageBase;
import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;
import fr.urssaf.image.sae.storage.model.storagedocument.BulkInsertionResults;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocuments;
import fr.urssaf.image.sae.storage.services.storagedocument.InsertionService;

/**
 * Implémente les services de l'interface insertionService.
 * 
 * @author akenore
 * 
 */
@Service
@Qualifier("insertionService")
public class InsertionServiceImpl extends AbstractDfceService implements
		InsertionService {
	/**
	 * Constructeur de la classe
	 * 
	 * @param storageBase
	 *            : La base de stockage
	 */
	public InsertionServiceImpl(final StorageBase storageBase) {
		super(storageBase);
	}

	/**
	 * Construction par défaut
	 **/
	public InsertionServiceImpl() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Loggable(LogLevel.TRACE)
	public final BulkInsertionResults bulkInsertStorageDocument(
			final StorageDocuments storageDocuments, final boolean allOrNothing) {
		// TODO
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Loggable(LogLevel.TRACE)
	public final UUID insertStorageDocument(
			final StorageDocument storageDocument) throws InsertionServiceEx {
		try {
			// TODO
			return null;
		} catch (RuntimeException runtimeException) {
			throw new InsertionServiceEx("", runtimeException);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void setInsertionServiceParameter(
			final StorageConnectionParameter storageConnectionParameter) {
		setStorageBase(storageConnectionParameter.getStorageBase());

	}

}
