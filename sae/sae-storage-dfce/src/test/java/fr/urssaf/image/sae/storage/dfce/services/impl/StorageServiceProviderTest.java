package fr.urssaf.image.sae.storage.dfce.services.impl;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;
import fr.urssaf.image.sae.storage.exception.InsertionServiceEx;
import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;
import fr.urssaf.image.sae.storage.services.StorageServiceProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-sae-storage-dfce.xml" })
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class StorageServiceProviderTest {
	@Autowired
	@Qualifier("storageConnectionParameter")
	@SuppressWarnings("PMD.LongVariable")
	private StorageConnectionParameter storageConnectionParameter;
	@SuppressWarnings("PMD.LongVariable")
	@Autowired
	@Qualifier("storageServiceProvider")
	private StorageServiceProvider serviceProvider;
	
	/**
	 * @param storageServiceProvider
	 *            : La façade de services
	 * 
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void setServiceProvider(
			final StorageServiceProvider storageServiceProvider) {
		this.serviceProvider = storageServiceProvider;
	}

	/**
	 * @return La façade de services
	 */
	public final StorageServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	/**
	 * Initialise les paramètres de connexion
	 * 
	 * @param storageConnectionParameter
	 *            : les paramètres de connexion
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void setStorageConnectionParameter(
			final StorageConnectionParameter storageConnectionParameter) {
		this.storageConnectionParameter = storageConnectionParameter;
	}

	/**
	 * 
	 * @return les paramètres de connexion
	 */
	public final StorageConnectionParameter getStorageConnectionParameter() {
		return storageConnectionParameter;
	}

	/**
	 * on test ici la validation par aspect
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void storageServiceProvider() throws ConnectionServiceEx,
			InsertionServiceEx {
		serviceProvider
				.setStorageServiceProviderParameter(storageConnectionParameter);
		serviceProvider.getStorageDocumentService()
				.insertStorageDocument(null);
	}

}
