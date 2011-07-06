package fr.urssaf.image.sae.storage.dfce.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import fr.urssaf.image.sae.storage.dfce.annotations.FacadePattern;
import fr.urssaf.image.sae.storage.dfce.services.impl.connection.StorageConnectionServiceImpl;
import fr.urssaf.image.sae.storage.dfce.services.impl.storagedocument.StorageDocumentServiceImpl;
import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;
import fr.urssaf.image.sae.storage.services.StorageServiceProvider;
import fr.urssaf.image.sae.storage.services.connection.StorageConnectionService;
import fr.urssaf.image.sae.storage.services.storagedocument.StorageDocumentService;

/**
 * Fournit la façade des implementations des services
 * {@link StorageDocumentService } , {@link StorageConnectionService}. <li>
 * Attribut StorageDocumentService : l’ensemble des services d’insertion, de
 * recherche, de récupération.</li> <li>Attribut storageConnectionService :le
 * service de connexion à la base de stockage</li>
 * 
 * @author akenore
 * 
 */
@Service
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@Qualifier("storageServiceProvider")
@FacadePattern(participants = { StorageDocumentServiceImpl.class,
		StorageConnectionServiceImpl.class }, comment = "Fournit les services des classes participantes")
public class StorageServiceProviderImpl implements StorageServiceProvider {
	@SuppressWarnings("PMD.LongVariable")
	@Autowired
	@Qualifier("storageDocumentService")
	private StorageDocumentService storageDocumentService;

	@SuppressWarnings("PMD.LongVariable")
	@Autowired
	@Qualifier("storageConnectionService")
	private StorageConnectionService storageConnectionService;

	/**
	 * 
	 * @return les services d'insertion ,de recherche, de suppression,de récupération
	 */
	public final StorageDocumentService getStorageDocumentService() {
		return storageDocumentService;
	}

	/**
	 * 
	 * @return l'interface des services de connexion
	 */
	public final StorageConnectionService getStorageConnectionService() {
		return storageConnectionService;
	}

	/**
	 * Initialise les services d'insertion ,de recherche,récupération
	 * 
	 * @param storageDocumentService
	 *            : la façade des services d'insertion ,de
	 *            recherche,récupération
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void setStorageDocumentService(
			final StorageDocumentService storageDocumentService) {
		this.storageDocumentService = storageDocumentService;
	}

	/**
	 * Initialise les services de connexion
	 * 
	 * @param storageConnectionService
	 *            : L'interface des services de connexion
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void setStorageConnectionService(
			final StorageConnectionService storageConnectionService) {
		this.storageConnectionService = storageConnectionService;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void setStorageServiceProviderParameter(
			final StorageConnectionParameter storageConnectionParameter) {
		storageConnectionService
				.setStorageConnectionServiceParameter(storageConnectionParameter);
		storageDocumentService
				.setStorageDocumentServiceParameter(storageConnectionParameter);

	}

}
