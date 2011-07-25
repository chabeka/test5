package fr.urssaf.image.sae.storage.dfce.services.provider.impl;

import java.util.UUID;

import junit.framework.Assert;

import org.junit.Test;

import fr.urssaf.image.sae.storage.dfce.services.provider.CommonsServicesProvider;
import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;
import fr.urssaf.image.sae.storage.exception.InsertionServiceEx;
import fr.urssaf.image.sae.storage.exception.RetrievalServiceEx;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;

/**
 * Classe permettant de test la récupération du contenue d'un document en base.
 * @author akenore
 *
 */
public class RetrieveContentByUUIDServiceProviderTest extends
		CommonsServicesProvider {

	// Ici on test la recupération du contenu du document
	@Test
	public final void retrieveContent() throws ConnectionServiceEx,
			RetrievalServiceEx, InsertionServiceEx {
		// initialise les paramètres de connexion
		getServiceProvider().setStorageServiceProviderParameter(
				getStorageConnectionParameter());
		// On récupère la connexion
		getServiceProvider().getStorageConnectionService().openConnection();
		// on insert le document.
		UUID uuid = getServiceProvider().getStorageDocumentService()
				.insertStorageDocument(getStorageDocument());
		// on test ici si on a un UUID
		Assert.assertNotNull(uuid);
		byte[] content = getServiceProvider().getStorageDocumentService()
				.retrieveStorageDocumentContentByUUID(
						new UUIDCriteria(uuid, null));
		// ici on vérifie qu'on a bien un contenu
		Assert.assertNotNull(content);
		// on ferme la connection
		getServiceProvider().getStorageConnectionService().closeConnexion();
	}
}
