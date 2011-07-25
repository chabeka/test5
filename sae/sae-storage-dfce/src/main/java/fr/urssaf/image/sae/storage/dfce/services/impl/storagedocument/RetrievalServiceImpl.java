package fr.urssaf.image.sae.storage.dfce.services.impl.storagedocument;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.docubase.toolkit.model.document.Document;
import net.docubase.toolkit.service.ServiceProvider;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.storage.dfce.annotations.Loggable;
import fr.urssaf.image.sae.storage.dfce.annotations.ServiceChecked;
import fr.urssaf.image.sae.storage.dfce.contants.Constants;
import fr.urssaf.image.sae.storage.dfce.messages.LogLevel;
import fr.urssaf.image.sae.storage.dfce.messages.MessageHandler;
import fr.urssaf.image.sae.storage.dfce.model.AbstractServices;
import fr.urssaf.image.sae.storage.exception.RetrievalServiceEx;
import fr.urssaf.image.sae.storage.exception.SearchingServiceEx;
import fr.urssaf.image.sae.storage.model.connection.StorageBase;
import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;
import fr.urssaf.image.sae.storage.services.storagedocument.RetrievalService;
import fr.urssaf.image.sae.storage.services.storagedocument.SearchingService;

/**
 * Implémente les services de l'interface {@link RetrievalService}.
 * 
 * @author akenore
 * 
 */
@Service
@Qualifier("retrievalService")
public class RetrievalServiceImpl extends AbstractServices implements
      RetrievalService {
   @Autowired
   @Qualifier("searchingService")
   private SearchingService searchingService;

   /**
    * Construit un {@link RetrievalServiceImpl }.
    * 
    * @param storageBase
    *           : La base de stockage
    */
   public RetrievalServiceImpl(final StorageBase storageBase) {
      super(storageBase);
   }

   /**
    * Construit un {@link RetrievalServiceImpl } par défaut.
    **/
   public RetrievalServiceImpl() {
      super();
   }

   /**
    * {@inheritDoc}
    */
   @Loggable(LogLevel.TRACE)
   @ServiceChecked
   public final StorageDocument retrieveStorageDocumentByUUID(
         final UUIDCriteria uUIDCriteria) throws RetrievalServiceEx {
      try {
         return searchingService
               .searchStorageDocumentByUUIDCriteria(uUIDCriteria);
      } catch (SearchingServiceEx srcSerEx) {
         throw new RetrievalServiceEx(MessageHandler
               .getMessage(Constants.RTR_CODE_ERROR), srcSerEx.getMessage(),
               srcSerEx);
      } catch (Exception exc) {
         throw new RetrievalServiceEx(MessageHandler
               .getMessage(Constants.RTR_CODE_ERROR), exc.getMessage(), exc);
      }
   }

   /**
    * {@inheritDoc}
    */
   @Loggable(LogLevel.TRACE)
   @ServiceChecked
   public final byte[] retrieveStorageDocumentContentByUUID(
         final UUIDCriteria uUIDCriteria) throws RetrievalServiceEx {
      try {
         Document docDfce = ServiceProvider.getSearchService()
               .getDocumentByUUID(getBaseDFCE(), uUIDCriteria.getUuid());
         InputStream docContent = ServiceProvider.getStoreService()
               .getDocumentFile(docDfce);
         return IOUtils.toByteArray(docContent);
      } catch (IOException except) {
         throw new RetrievalServiceEx(MessageHandler
               .getMessage(Constants.RTR_CODE_ERROR), except.getMessage(),
               except);
      } catch (Exception except) {
         throw new RetrievalServiceEx(MessageHandler
               .getMessage(Constants.SRH_CODE_ERROR), except.getMessage(),
               except);
      }
   }

   /**
    * {@inheritDoc}
    */
   @Loggable(LogLevel.TRACE)
   @ServiceChecked
   public final List<StorageMetadata> retrieveStorageDocumentMetaDatasByUUID(
         final UUIDCriteria uUIDCriteria) throws RetrievalServiceEx {
      try {
         return searchingService.searchMetaDatasByUUIDCriteria(uUIDCriteria)
               .getMetadatas();
      } catch (SearchingServiceEx srcSerEx) {
         throw new RetrievalServiceEx(srcSerEx.getMessage(), srcSerEx);
      } catch (Exception except) {
         throw new RetrievalServiceEx(MessageHandler
               .getMessage(Constants.RTR_CODE_ERROR), except.getMessage(),
               except);
      }
   }

   /**
    * {@inheritDoc}
    */
   @SuppressWarnings("PMD.LongVariable")
   public final void setRetrievalServiceParameter(
         final StorageConnectionParameter storageConnectionParameter) {
      setStorageBase(storageConnectionParameter.getStorageBase());
      searchingService.setSearchingServiceParameter(storageConnectionParameter);
   }

   /**
    * @param searchingService
    *           the searchingService to set
    */
   public final void setSearchingService(final SearchingService searchingService) {
      this.searchingService = searchingService;
   }

   /**
    * @return the searchingService
    */
   public final SearchingService getSearchingService() {
      return searchingService;
   }

}
