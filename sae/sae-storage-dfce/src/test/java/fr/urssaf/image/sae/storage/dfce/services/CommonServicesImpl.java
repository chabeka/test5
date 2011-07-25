package fr.urssaf.image.sae.storage.dfce.services;

import net.docubase.toolkit.model.base.Base;
import net.docubase.toolkit.service.ServiceProvider;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;
import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;
import fr.urssaf.image.sae.storage.services.connection.StorageConnectionService;
import fr.urssaf.image.sae.storage.services.storagedocument.DeletionService;
import fr.urssaf.image.sae.storage.services.storagedocument.InsertionService;
import fr.urssaf.image.sae.storage.services.storagedocument.RetrievalService;
import fr.urssaf.image.sae.storage.services.storagedocument.SearchingService;

/**
 * Classe de base pour les tests unitaires.
 * 
 */
@SuppressWarnings( { "PMD.ExcessiveImports", "PMD.LongVariable" })
public class CommonServicesImpl extends CommonsServices {
   @Autowired
   @Qualifier("storageConnectionParameter")
   private StorageConnectionParameter storageConnectionParameter;
   @Autowired
   @Qualifier("storageConnectionService")
   private StorageConnectionService storageConnectionService;
   @Autowired
   @Qualifier("insertionService")
   private InsertionService insertionService;
   @Autowired
   @Qualifier("retrievalService")
   private RetrievalService retrievalService;
   @Autowired
   @Qualifier("searchingService")
   private SearchingService searchingService;
   @Autowired
   @Qualifier("deletionService")
   private DeletionService deletionService;
   // Base DFCE
   private Base base = null;

   /**
    * @return : Le service d'insertion.
    */
   public final InsertionService getInsertionService() {
      return insertionService;
   }

   /**
    * @param insertionService
    *           : Le service d'insertion.
    */
   public final void setInsertionService(InsertionService insertionService) {
      this.insertionService = insertionService;
   }

   /**
    * Initialise les paramètres de connexion.<br> {@inheritDoc}
    */
   @Before
   public final void initParameter() throws ConnectionServiceEx {
      storageConnectionService
            .setStorageConnectionServiceParameter(storageConnectionParameter);
      storageConnectionService.openConnection();
      insertionService.setInsertionServiceParameter(storageConnectionParameter);
      retrievalService.setRetrievalServiceParameter(storageConnectionParameter);
      searchingService.setSearchingServiceParameter(storageConnectionParameter);
      setBase(ServiceProvider.getBaseAdministrationService().getBase(
            storageConnectionParameter.getStorageBase().getBaseName()));
      deletionService.setDeletionServiceParameter(storageConnectionParameter);

   }

   /**
    * @return Le service de suppression.
    */
   public final DeletionService getDeletionService() {
      return deletionService;
   }

   /**
    * @param deletionService
    *           :Le service de suppression.
    */
   public final void setDeletionService(DeletionService deletionService) {
      this.deletionService = deletionService;
   }

   /**
    * Ferme la connexion. {@inheritDoc}
    */
   @After
   public final void closeConnection() throws ConnectionServiceEx {
      storageConnectionService.closeConnexion();
   }

   /**
    * @return Le service de connexion.
    */
   public final StorageConnectionService getStorageConnectionService() {
      return storageConnectionService;
   }

   /**
    * @param storageConnectionService
    *           . Le service de connexion.
    */
   public final void setStorageConnectionService(
         StorageConnectionService storageConnectionService) {
      this.storageConnectionService = storageConnectionService;
   }

   /**
    * @return Le service de récupération de document DFCE.
    */
   public final RetrievalService getRetrievalService() {
      return retrievalService;
   }

   /**
    * @param retrievalService
    *           : Le service de récupération de document DFCE.
    */
   public final void setRetrievalService(RetrievalService retrievalService) {
      this.retrievalService = retrievalService;
   }

   /**
    * @return Le service de recherche.
    */
   public final SearchingService getSearchingService() {
      return searchingService;
   }

   /**
    * @param searchingService
    *           : Le service de recherche.
    */
   public final void setSearchingService(SearchingService searchingService) {
      this.searchingService = searchingService;
   }

   /**
    * @return Instance de la base DFCE.
    */
   public final Base getBase() {
      return base;
   }

   /**
    * @param base
    *           :Instance de la base DFCE.
    */
   public final void setBase(Base base) {
      this.base = base;
   }
}
