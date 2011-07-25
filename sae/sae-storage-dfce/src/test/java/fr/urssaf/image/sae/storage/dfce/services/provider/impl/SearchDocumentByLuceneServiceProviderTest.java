package fr.urssaf.image.sae.storage.dfce.services.provider.impl;

import java.util.UUID;

import junit.framework.Assert;

import org.junit.Test;

import fr.urssaf.image.sae.storage.dfce.services.provider.CommonsServicesProvider;
import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;
import fr.urssaf.image.sae.storage.exception.InsertionServiceEx;
import fr.urssaf.image.sae.storage.exception.SearchingServiceEx;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocuments;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.LuceneCriteria;

/**
 * Classe permettant de tester la recherche d'un document dans la base.
 * 
 * @author akenore, rhofir.
 * 
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class SearchDocumentByLuceneServiceProviderTest extends
      CommonsServicesProvider {

   // Ici on test la recherche d'un document
   @Test
   public final void searchDocument() throws ConnectionServiceEx,
         SearchingServiceEx, InsertionServiceEx {
      // initialise les paramètres de connexion
      getServiceProvider().setStorageServiceProviderParameter(
            getStorageConnectionParameter());
      // On récupère la connexion
      getServiceProvider().getStorageConnectionService().openConnection();
      // On insert le document.
      UUID uuid = getServiceProvider().getStorageDocumentService()
            .insertStorageDocument(getStorageDocument());
      // On test ici si on a un UUID
      Assert.assertNotNull(uuid);
      final String lucene = String.format("%s:%s", "_uuid", uuid.toString());
      StorageDocuments strDocuments = getServiceProvider()
            .getStorageDocumentService().searchStorageDocumentByLuceneCriteria(
                  new LuceneCriteria(lucene, 1, null));
      // ici on vérifie qu'on a bien des documents
      Assert.assertNotNull(strDocuments.getAllStorageDocument());
      // ici on vérifie que le nombre de document est bien supérieur à 1
      Assert.assertTrue(strDocuments.getAllStorageDocument().size() >= 1);
      // on ferme la connection
      getServiceProvider().getStorageConnectionService().closeConnexion();
   }
}
