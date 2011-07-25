package fr.urssaf.image.sae.storage.dfce.services.impl.connection;

import net.docubase.toolkit.service.Authentication;

import org.junit.Assert;
import org.junit.Test;

import fr.urssaf.image.sae.storage.dfce.services.CommonServicesImpl;
import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;
import fr.urssaf.image.sae.storage.exception.InsertionServiceEx;

/**
 * Classe de test du service de connection.
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class StorageServiceConnectionTest extends CommonServicesImpl {

   /**
    * on test ici la validation par aspect
    */
   @Test
   public final void openConnectionTest() throws ConnectionServiceEx,
         InsertionServiceEx {
      getStorageConnectionService().openConnection();
      Assert.assertTrue(Authentication.isServerUp());
   }

   /**
    * on test ici la validation par aspect
    */
   @Test
   public final void closeConnectionTest() throws ConnectionServiceEx,
         InsertionServiceEx {
      getStorageConnectionService().openConnection();
      getStorageConnectionService().closeConnexion();
      Assert.assertFalse(Authentication.isSessionActive());
   }
}
