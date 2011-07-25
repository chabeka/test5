package fr.urssaf.image.sae.storage.dfce.services.provider.impl;

import java.util.UUID;

import junit.framework.Assert;

import org.junit.Test;

import fr.urssaf.image.sae.storage.dfce.services.provider.CommonsServicesProvider;
import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;
import fr.urssaf.image.sae.storage.exception.InsertionServiceEx;
import fr.urssaf.image.sae.storage.exception.RetrievalServiceEx;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;
/**
 * Classe permettant de test la récupération d'un document en base.
 * @author akenore
 *
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class RetrieveDocumentByUUIDServiceProviderTest extends
		CommonsServicesProvider {

	// Ici on test la récupération du document
	@Test
	public final void retrieveDocument() throws ConnectionServiceEx,
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
		StorageDocument storageDocument= getServiceProvider()
				.getStorageDocumentService().retrieveStorageDocumentByUUID(
						new UUIDCriteria(uuid, null));
		// ici on vérifie qu'on a bien un contenu
		Assert.assertNotNull(storageDocument.getContent());
		// ici on vérifie qu'on a bien des métadonnées
		Assert.assertTrue(storageDocument.getMetadatas().size() > 3);
		// on ferme la connection
		getServiceProvider().getStorageConnectionService().closeConnexion();
	}
}
