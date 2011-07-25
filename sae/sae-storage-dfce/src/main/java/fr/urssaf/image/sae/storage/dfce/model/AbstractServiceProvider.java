package fr.urssaf.image.sae.storage.dfce.model;

import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;

/**
 * Classe abstraite contient les attributs communs à toutes les implementations
 * des interfaces {@link StorageConnectionServiceImpl } et
 * {@link StorageServiceProviderImpl }.
 * <ul>
 * <li>
 * storageConnectionParameter : Classe concrète contenant les paramètres de
 * connexion à la base de stockage</li>
 * </ul>
 * 
 * @author akenore, rhofir.
 * 
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class AbstractServiceProvider {

   @SuppressWarnings("PMD.LongVariable")
   private StorageConnectionParameter storageConnectionParameter;

   /**
    * 
    * @return  Les paramètres de connexion à la base de stockage
    */
   public final StorageConnectionParameter getStorageConnectionParameter() {
      return storageConnectionParameter;
   }

   /**
    * 
    * @param storageConnectionParameter
    *           : Initialise les paramètres de connexion à la base de stockage
    */
   @SuppressWarnings("PMD.LongVariable")
   public final void setStorageConnectionParameter(
         final StorageConnectionParameter storageConnectionParameter) {
      this.storageConnectionParameter = storageConnectionParameter;
   }

   /**
    * Construit un {@link AbstractServiceProvider}
    * 
    * @param storageConnectionParameter
    *           : Les paramètres de connexion à la base de stockage
    */
   @SuppressWarnings("PMD.LongVariable")
   public AbstractServiceProvider(
         final StorageConnectionParameter storageConnectionParameter) {
      this.storageConnectionParameter = storageConnectionParameter;
   }

   /**
    * Construit par défaut un {@link AbstractServiceProvider}
    */
   public AbstractServiceProvider() {
      // Ici on ne fait rien
   }

}
