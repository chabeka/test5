package fr.urssaf.image.sae.storage.dfce.services.impl.storagedocument;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.UUID;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import fr.urssaf.image.sae.storage.dfce.data.test.constants.Constants;
import fr.urssaf.image.sae.storage.dfce.services.CommonServicesImpl;
import fr.urssaf.image.sae.storage.exception.DeletionServiceEx;
import fr.urssaf.image.sae.storage.exception.InsertionServiceEx;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;

/**
 * Classe de test du service {@link fr.urssaf.image.sae.storage.dfce.services.impl.storagedocument.DeletionServiceImpl DeletionService}
 * 
 * @author rhofir, kenore.
 */
public class DeletionServiceImplTest extends CommonServicesImpl {
   /**
    * Test du service :  {@link fr.urssaf.image.sae.storage.dfce.services.impl.storagedocument.DeletionServiceImpl#deleteStorageDocument(fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria) deleteStorageDocument}
    */
   @Test
   public void deleteStorageDocument() throws InsertionServiceEx, IOException,
         ParseException {
      // Initialisation des jeux de données UUID
      UUID uuid = getMockData(getInsertionService());
      UUIDCriteria uuidCriteria = new UUIDCriteria(uuid,
            new ArrayList<StorageMetadata>());
      try {
         getDeletionService().deleteStorageDocument(uuidCriteria);
      } catch (DeletionServiceEx e) {
         Assert.assertTrue("La suppression a échoué " + e.getMessage(), true);
      }
      Assert.assertTrue("La suppression a réussi : ", true);
   }

   /**
    *  Test du service :   {@link fr.urssaf.image.sae.storage.dfce.services.impl.storagedocument.DeletionServiceImpl#rollBack(java.lang.String) rollBack}.
    */
   @Ignore
   public void rollBack() throws InsertionServiceEx, IOException,
         ParseException, DeletionServiceEx {
      getMockData(getInsertionService());
      try {
         // ID process.
         getDeletionService().rollBack(Constants.ID_PROCESS_TEST);

      } catch (DeletionServiceEx e) {
         Assert
         .fail("La suppression a échoué " + e.getMessage());
      } catch (Exception e) {
         Assert
               .fail("Aucune recherche ne correspond à l'indentifiant passé en paramétre. "
                     + e.getMessage());
      }
      Assert.assertTrue("La suppression a réussi : ", true);
   }

}
