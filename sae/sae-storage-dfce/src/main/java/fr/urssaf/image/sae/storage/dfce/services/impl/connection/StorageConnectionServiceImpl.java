package fr.urssaf.image.sae.storage.dfce.services.impl.connection;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import fr.urssaf.image.sae.storage.dfce.model.AbstractConnectionServiceProvider;
import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;
import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;
import fr.urssaf.image.sae.storage.services.connection.StorageConnectionService;

/**
 * Fournit l’implémentation des services de connexion à la base de stockage
 * 
 * @author akenore
 * 
 */
@Service
@Qualifier("storageConnectionService")
public class StorageConnectionServiceImpl extends
      AbstractConnectionServiceProvider implements StorageConnectionService {

   /**
    * {@inheritDoc}
    */

   public final void openConnection() throws ConnectionServiceEx {
      buildUrlForConnection(getStorageConnectionParameter());
      // TODO
   }

   /**
    * {@inheritDoc}
    */

   public final void closeConnexion() {
      // TODO
   }

   /**
    * Constructeur par défaut
    */
   public StorageConnectionServiceImpl() {
      super();
   }

   /**
    * 
    * @param storageConnectionParameter
    *           : Les paramètres de connexion à la base de stockage
    */
   @SuppressWarnings("PMD.LongVariable")
   public StorageConnectionServiceImpl(
         final StorageConnectionParameter storageConnectionParameter) {
      super(storageConnectionParameter);
   }

   /**
    * {@inheritDoc}
    */
   @SuppressWarnings("PMD.LongVariable")
   public final void setStorageConnectionServiceParameter(
         final StorageConnectionParameter storageConnectionParameter) {
      setStorageConnectionParameter(storageConnectionParameter);

   }

   /**
    * 
    * @param storageConnectionParameter
    *           : Les paramètres de connexion à la base de stockage
    * @return l'url de connexion à la base de stockage
    */
   @VisibleForTesting
   @SuppressWarnings("PMD.LongVariable")
   private String buildUrlForConnection(
         final StorageConnectionParameter storageConnectionParameter) {
      // TODO

      return null;
   }

}
