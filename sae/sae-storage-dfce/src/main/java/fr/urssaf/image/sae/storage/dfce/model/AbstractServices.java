package fr.urssaf.image.sae.storage.dfce.model;

import net.docubase.toolkit.model.base.Base;
import net.docubase.toolkit.service.ServiceProvider;
import fr.urssaf.image.sae.storage.model.connection.StorageBase;

/**
 * Classe abstraite contenant les attributs communs de toutes les
 * implementations:
 * <ul>
 * <li>{@link InsertionServiceImpl } : Implementation de l'interface
 * InsertionServiceI.</li>
 * <li>{@link SearchingServiceImpl } : Implementation de l'interface
 * SearchingService</li>
 * <li>{@link RetrievalServiceImpl }</li> : Implementation de l'interface
 * RetrievalService
 * </ul>
 * Elle contient également l'attribut :
 * <ul>
 * <li>
 * Attribut storageBase : Classe concrète contenant le nom de la base de
 * stockage</li>
 * </ul>
 * 
 * @author akenore,rhofir.
 *
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class AbstractServices {

   private StorageBase storageBase;

   /**
    * Initialise la base de stockage
    * 
    * @param storageBase
    *           : La base de stockage
    */
   public final void setStorageBase(final StorageBase storageBase) {
      this.storageBase = storageBase;
   }

   /**
    * 
    * @return Retourne la base de stockage.
    */
   public final StorageBase getStorageBase() {
      return storageBase;
   }

   /**
    * Construit un {@link AbstractServices}
    * 
    * @param storageBase
    *           : La base de stockage
    */
   public AbstractServices(final StorageBase storageBase) {
      this.storageBase = storageBase;
   }

   /**
    * Construit par défaut un {@link AbstractServices}
    */
   public AbstractServices() {
      // ici on fait rien
   }

   /**
    * @return Une occurrence de la base DFCE.
    */
   public final Base getBaseDFCE() {
      return ServiceProvider.getBaseAdministrationService().getBase(
            storageBase.getBaseName());
   }

}
