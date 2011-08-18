package fr.urssaf.image.sae.storage.dfce.services.provider.impl;

import junit.framework.Assert;

import org.junit.Test;

import fr.urssaf.image.sae.storage.dfce.services.provider.CommonsServicesProvider;
import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;
import fr.urssaf.image.sae.storage.exception.InsertionServiceEx;
import fr.urssaf.image.sae.storage.model.storagedocument.BulkInsertionResults;

/**
 * Classe permettant de test l'insertion en masse des documents en base.
 * 
 * @author akenore, rhofir.
 * 
 */

public class BulkInsertionServiceProviderTest extends CommonsServicesProvider {

   /**
    * Ici on test l'insertion en masse.
    */
   @Test
   @SuppressWarnings("PMD.LongVariable")
   public final void bulkInsertion() throws ConnectionServiceEx,
         InsertionServiceEx {
      // initialise les paramètres de connexion
      getServiceProvider().setStorageServiceProviderParameter(
            getStorageConnectionParameter());
      // On récupère la connexion
      getServiceProvider().getStorageConnectionService().openConnection();
      // on insert en mass les documents.
      BulkInsertionResults bulkInsertionResults = getServiceProvider()
            .getStorageDocumentService().bulkInsertStorageDocument(
                  getStorageDocuments(), false);
      // on test ici si on a bien inséré les 7 documents.
      Assert.assertTrue(bulkInsertionResults.getUuids().size() > 1);
      // on ferme la connection
      getServiceProvider().getStorageConnectionService().closeConnexion();
   }

}
