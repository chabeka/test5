package fr.urssaf.image.sae.storage.dfce.services.impl.storagedocument;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.storage.dfce.annotations.Loggable;
import fr.urssaf.image.sae.storage.dfce.messages.LogLevel;
import fr.urssaf.image.sae.storage.dfce.model.AbstractDfceService;
import fr.urssaf.image.sae.storage.exception.DeletionServiceEx;
import fr.urssaf.image.sae.storage.model.connection.StorageBase;
import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;
import fr.urssaf.image.sae.storage.services.storagedocument.DeletionService;

/**
 * Implémente les services de l'interface insertionService.
 * 
 * @author akenore
 * 
 */
@Service
@Qualifier("deletionService")
public class DeletionServiceImpl extends AbstractDfceService implements
		DeletionService {
	/**
	 * Constructeur de la classe
	 * 
	 * @param storageBase
	 *            : La base de stockage
	 */
	public DeletionServiceImpl(final StorageBase storageBase) {
		super(storageBase);
	}

	/**
	 * Construction par défaut
	 **/
	public DeletionServiceImpl() {
		super();
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Loggable(LogLevel.TRACE)
	public void deleteStorageDocument(final UUIDCriteria uuidCriteria)
			throws DeletionServiceEx {
		//TODO
	}
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void setDeletionServiceParameter(
			final StorageConnectionParameter storageConnectionParameter) {
		setStorageBase(storageConnectionParameter.getStorageBase());
	}

}
