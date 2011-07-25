package fr.urssaf.image.sae.storage.dfce.services.impl.storagedocument;

import java.util.ArrayList;

import net.docubase.toolkit.service.ServiceProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.storage.dfce.annotations.Loggable;
import fr.urssaf.image.sae.storage.dfce.annotations.ServiceChecked;
import fr.urssaf.image.sae.storage.dfce.contants.Constants;
import fr.urssaf.image.sae.storage.dfce.messages.LogLevel;
import fr.urssaf.image.sae.storage.dfce.messages.MessageHandler;
import fr.urssaf.image.sae.storage.dfce.model.AbstractServices;
import fr.urssaf.image.sae.storage.exception.DeletionServiceEx;
import fr.urssaf.image.sae.storage.exception.SearchingServiceEx;
import fr.urssaf.image.sae.storage.model.connection.StorageBase;
import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocuments;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.LuceneCriteria;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;
import fr.urssaf.image.sae.storage.services.storagedocument.DeletionService;
import fr.urssaf.image.sae.storage.services.storagedocument.SearchingService;

/**
 * Implémente les services de l'interface {@link DeletionService}.
 * 
 * @author Akenore, Rhofir
 * 
 */
@Service
@Qualifier("deletionService")
public class DeletionServiceImpl extends AbstractServices implements
      DeletionService {
   @Autowired
   @Qualifier("searchingService")
   private SearchingService searchingService;

   /**
    * @param searchingService
    *           : Le service de recherche.
    */
   public final void setSearchingService(final SearchingService searchingService) {
      this.searchingService = searchingService;
   }

   /**
    * @return Le service de recherche.
    */
   public final SearchingService getSearchingService() {
      return searchingService;
   }

   /**
    * Construit un {@link DeletionServiceImpl}.
    * 
    * @param storageBase
    *           : La base de stockage
    */
   public DeletionServiceImpl(final StorageBase storageBase) {
      super(storageBase);
   }

   /**
    * Construit un {@link DeletionServiceImpl} par défaut.
    **/
   public DeletionServiceImpl() {
      super();
   }

   /**
    * {@inheritDoc}
    */
   @Loggable(LogLevel.TRACE)
   @ServiceChecked
   public final void deleteStorageDocument(final UUIDCriteria uuidCriteria)
         throws DeletionServiceEx {
      try {
         ServiceProvider.getStoreService().deleteDocument(
               uuidCriteria.getUuid());
      } catch (Exception except) {
         throw new DeletionServiceEx(MessageHandler
               .getMessage(Constants.DEL_CODE_ERROR), except.getMessage(),
               except);
      }
   }

   /**
    * {@inheritDoc}
    */
   @SuppressWarnings("PMD.LongVariable")
   public final void setDeletionServiceParameter(
         final StorageConnectionParameter storageConnectionParameter) {
      setStorageBase(storageConnectionParameter.getStorageBase());
      searchingService.setSearchingServiceParameter(storageConnectionParameter);
   }

   /**
    * {@inheritDoc}
    */
   @Loggable(LogLevel.TRACE)
   @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
   @ServiceChecked
   public final void rollBack(String processId) throws DeletionServiceEx {
      final String lucene = String.format("%s:%s", "itm", processId);
      StorageDocuments storageDocuments;
      try {
         storageDocuments = searchingService
               .searchStorageDocumentByLuceneCriteria(new LuceneCriteria(
                     lucene, Integer.parseInt(MessageHandler
                           .getMessage("max.lucene.results")), null));
         for (StorageDocument storageDocument : storageDocuments
               .getAllStorageDocument()) {
            deleteStorageDocument(new UUIDCriteria(storageDocument.getUuid(),
                  new ArrayList<StorageMetadata>()));
         }
      } catch (NumberFormatException except) {
         throw new DeletionServiceEx(MessageHandler
               .getMessage(Constants.DEL_CODE_ERROR), except.getMessage(),
               except);
      } catch (SearchingServiceEx except) {
         throw new DeletionServiceEx(MessageHandler
               .getMessage(Constants.DEL_CODE_ERROR), except.getMessage(),
               except);
      }
   }
}
