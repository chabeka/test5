package fr.urssaf.image.sae.storage.dfce.services.impl.storagedocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.docubase.toolkit.exception.ged.ExceededSearchLimitException;
import net.docubase.toolkit.model.document.Document;
import net.docubase.toolkit.model.search.SearchResult;
import net.docubase.toolkit.service.ServiceProvider;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.storage.dfce.annotations.Loggable;
import fr.urssaf.image.sae.storage.dfce.annotations.ServiceChecked;
import fr.urssaf.image.sae.storage.dfce.contants.Constants;
import fr.urssaf.image.sae.storage.dfce.mapping.BeanMapper;
import fr.urssaf.image.sae.storage.dfce.messages.LogLevel;
import fr.urssaf.image.sae.storage.dfce.messages.MessageHandler;
import fr.urssaf.image.sae.storage.dfce.model.AbstractServices;
import fr.urssaf.image.sae.storage.dfce.utils.Utils;
import fr.urssaf.image.sae.storage.exception.SearchingServiceEx;
import fr.urssaf.image.sae.storage.exception.StorageException;
import fr.urssaf.image.sae.storage.model.connection.StorageBase;
import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocuments;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.LuceneCriteria;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;
import fr.urssaf.image.sae.storage.services.storagedocument.SearchingService;

/**
 * Implémente les services de l'interface {@link SearchingService} .
 * 
 * @author akenore, rhofir.
 * 
 */
@Service
@Qualifier("searchingService")
public class SearchingServiceImpl extends AbstractServices implements
      SearchingService {
   /**
    * Construit un {@link SearchingServiceImpl }.
    * 
    * @param storageBase
    *           : La base de stockage
    */
   public SearchingServiceImpl(final StorageBase storageBase) {
      super(storageBase);
   }

   /**
    * Construit un {@link SearchingServiceImpl } par défaut.
    **/
   public SearchingServiceImpl() {
      super();
   }

   /**
    * {@inheritDoc}
    */
   @Loggable(LogLevel.TRACE)
   @ServiceChecked
   public final StorageDocuments searchStorageDocumentByLuceneCriteria(
         final LuceneCriteria luceneCriteria) throws SearchingServiceEx {
      List<StorageDocument> storageDocuments = new ArrayList<StorageDocument>();
      try {
         final SearchResult searchResult = ServiceProvider.getSearchService()
               .search(luceneCriteria.getLuceneQuery(),
                     luceneCriteria.getLimit(), getBaseDFCE());
         for (Document document : Utils.nullSafeIterable(searchResult
               .getDocuments())) {
            storageDocuments.add(BeanMapper.dfceDocumentToStorageDocument(
                  document, luceneCriteria.getDesiredStorageMetadatas()));
         }
      } catch (StorageException srcSerEx) {
         throw new SearchingServiceEx(MessageHandler
               .getMessage(Constants.SRH_CODE_ERROR), srcSerEx.getMessage(),
               srcSerEx);
      } catch (IOException ioExcept) {
         throw new SearchingServiceEx(MessageHandler
               .getMessage(Constants.SRH_CODE_ERROR), ioExcept.getMessage(),
               ioExcept);
      } catch (ExceededSearchLimitException exceedSearchEx) {
         throw new SearchingServiceEx(MessageHandler
               .getMessage(Constants.SRH_CODE_ERROR), exceedSearchEx
               .getMessage(), exceedSearchEx);
      } catch (Exception except) {
         throw new SearchingServiceEx(MessageHandler
               .getMessage(Constants.SRH_CODE_ERROR), except.getMessage(),
               except);
      }
      return new StorageDocuments(storageDocuments);
   }

   /**
    * {@inheritDoc}
    */
   @Loggable(LogLevel.TRACE)
   @ServiceChecked
   public final StorageDocument searchStorageDocumentByUUIDCriteria(
         final UUIDCriteria uUIDCriteria) throws SearchingServiceEx {
      try {
         Document docDfce = ServiceProvider.getSearchService()
               .getDocumentByUUID(getBaseDFCE(), uUIDCriteria.getUuid());

         StorageDocument storageDoc = null;

         if (docDfce != null) {
            storageDoc = BeanMapper.dfceDocumentToStorageDocument(docDfce,
                  uUIDCriteria.getDesiredStorageMetadatas());
         }

         return storageDoc;

      } catch (StorageException srcSerEx) {
         throw new SearchingServiceEx(MessageHandler
               .getMessage(Constants.SRH_CODE_ERROR), srcSerEx.getMessage(),
               srcSerEx);
      } catch (IOException ioExcept) {
         throw new SearchingServiceEx(MessageHandler
               .getMessage(Constants.SRH_CODE_ERROR), ioExcept.getMessage(),
               ioExcept);
      } catch (Exception except) {
         throw new SearchingServiceEx(MessageHandler
               .getMessage(Constants.SRH_CODE_ERROR), except.getMessage(),
               except);
      }
   }

   /**
    * {@inheritDoc}
    */
   @SuppressWarnings("PMD.LongVariable")
   public final void setSearchingServiceParameter(
         final StorageConnectionParameter storageConnectionParameter) {
      setStorageBase(storageConnectionParameter.getStorageBase());
   }

   /**
    * {@inheritDoc}
    */
   @ServiceChecked
   public final StorageDocument searchMetaDatasByUUIDCriteria(
         UUIDCriteria uuidCriteria) throws SearchingServiceEx {
      try {
         Document docDfce = ServiceProvider.getSearchService()
               .getDocumentByUUID(getBaseDFCE(), uuidCriteria.getUuid());
         return BeanMapper.dfceMetaDataToStorageDocument(docDfce, uuidCriteria
               .getDesiredStorageMetadatas());
      } catch (StorageException srcSerEx) {
         throw new SearchingServiceEx(MessageHandler
               .getMessage(Constants.SRH_CODE_ERROR), srcSerEx.getMessage(),
               srcSerEx);
      } catch (IOException ioExcept) {
         throw new SearchingServiceEx(MessageHandler
               .getMessage(Constants.SRH_CODE_ERROR), ioExcept.getMessage(),
               ioExcept);
      } catch (Exception except) {
         throw new SearchingServiceEx(MessageHandler
               .getMessage(Constants.SRH_CODE_ERROR), except.getMessage(),
               except);
      }
   }

}
