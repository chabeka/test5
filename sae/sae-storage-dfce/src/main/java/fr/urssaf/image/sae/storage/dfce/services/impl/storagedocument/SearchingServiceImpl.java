package fr.urssaf.image.sae.storage.dfce.services.impl.storagedocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.docubase.toolkit.exception.ged.ExceededSearchLimitException;
import net.docubase.toolkit.exception.ged.SearchQueryParseException;
import net.docubase.toolkit.model.document.Document;
import net.docubase.toolkit.model.search.SearchResult;
import net.docubase.toolkit.service.ServiceProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.storage.dfce.annotations.Loggable;
import fr.urssaf.image.sae.storage.dfce.annotations.ServiceChecked;
import fr.urssaf.image.sae.storage.dfce.constants.Constants;
import fr.urssaf.image.sae.storage.dfce.mapping.BeanMapper;
import fr.urssaf.image.sae.storage.dfce.messages.LogLevel;
import fr.urssaf.image.sae.storage.dfce.messages.StorageMessageHandler;
import fr.urssaf.image.sae.storage.dfce.model.AbstractServices;
import fr.urssaf.image.sae.storage.dfce.utils.Utils;
import fr.urssaf.image.sae.storage.exception.QueryParseServiceEx;
import fr.urssaf.image.sae.storage.exception.SearchingServiceEx;
import fr.urssaf.image.sae.storage.exception.StorageException;
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
   private static final Logger LOG = LoggerFactory
         .getLogger(SearchingServiceImpl.class);

   /**
    * {@inheritDoc}
    */
   @Loggable(LogLevel.TRACE)
   @ServiceChecked
   public final StorageDocuments searchStorageDocumentByLuceneCriteria(
         final LuceneCriteria luceneCriteria) throws SearchingServiceEx,
         QueryParseServiceEx {
      final List<StorageDocument> storageDocuments = new ArrayList<StorageDocument>();
      try {
         // Traces debug - entrée méthode
         String prefixeTrc = "searchStorageDocumentByLuceneCriteria()";
         LOG.debug("{} - Début", prefixeTrc);
         LOG.debug("{} - Requête de recherche : {} ", prefixeTrc,
               luceneCriteria.getLuceneQuery());
         LOG.debug("{} - Le maximum de documents à retourner : {} ",
               prefixeTrc, luceneCriteria.getLimit());
         LOG.debug("{} - Début de la recherche dans DFCE", prefixeTrc);
         // Fin des traces debug - entrée méthode
         final SearchResult searchResult = getDfceService().getSearchService()
               .search(luceneCriteria.getLuceneQuery(),
                     luceneCriteria.getLimit(), getBaseDFCE());
         LOG.debug("{} - Fin de la recherche dans DFCE", prefixeTrc);
         LOG
               .debug(
                     "{} - Le nombre de résultats de recherche renvoyé par DFCE est {}",
                     prefixeTrc, searchResult.getDocuments() == null ? 0
                           : searchResult.getDocuments().size());
         for (Document document : Utils.nullSafeIterable(searchResult
               .getDocuments())) {
            storageDocuments.add(BeanMapper.dfceDocumentToStorageDocument(
                  document, luceneCriteria.getDesiredStorageMetadatas(),
                  getDfceService(), false));
         }
      } catch (SearchQueryParseException except) {
         throw new QueryParseServiceEx(StorageMessageHandler
               .getMessage(Constants.SRH_CODE_ERROR), except.getMessage(),
               except);
      } catch (StorageException srcSerEx) {
         throw new SearchingServiceEx(StorageMessageHandler
               .getMessage(Constants.SRH_CODE_ERROR), srcSerEx.getMessage(),
               srcSerEx);
      } catch (IOException ioExcept) {
         throw new SearchingServiceEx(StorageMessageHandler
               .getMessage(Constants.SRH_CODE_ERROR), ioExcept.getMessage(),
               ioExcept);
      } catch (ExceededSearchLimitException exceedSearchEx) {
         throw new SearchingServiceEx(StorageMessageHandler
               .getMessage(Constants.SRH_CODE_ERROR), exceedSearchEx
               .getMessage(), exceedSearchEx);
      } catch (Exception except) {
         throw new SearchingServiceEx(StorageMessageHandler
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
         final Document docDfce = getDfceService().getSearchService()
               .getDocumentByUUID(getBaseDFCE(), uUIDCriteria.getUuid());

         StorageDocument storageDoc = null;

         if (docDfce != null) {
            storageDoc = BeanMapper.dfceDocumentToStorageDocument(docDfce,
                  uUIDCriteria.getDesiredStorageMetadatas(), getDfceService(),
                  true);
         }

         return storageDoc;

      } catch (StorageException srcSerEx) {
         throw new SearchingServiceEx(StorageMessageHandler
               .getMessage(Constants.SRH_CODE_ERROR), srcSerEx.getMessage(),
               srcSerEx);
      } catch (IOException ioExcept) {
         throw new SearchingServiceEx(StorageMessageHandler
               .getMessage(Constants.SRH_CODE_ERROR), ioExcept.getMessage(),
               ioExcept);
      } catch (Exception except) {
         throw new SearchingServiceEx(StorageMessageHandler
               .getMessage(Constants.SRH_CODE_ERROR), except.getMessage(),
               except);
      }
   }

   /**
    * {@inheritDoc}
    */
   @ServiceChecked
   public final StorageDocument searchMetaDatasByUUIDCriteria(
         final UUIDCriteria uuidCriteria) throws SearchingServiceEx {
      try {
         final Document docDfce = getDfceService().getSearchService()
               .getDocumentByUUID(getBaseDFCE(), uuidCriteria.getUuid());
         return BeanMapper.dfceMetaDataToStorageDocument(docDfce, uuidCriteria
               .getDesiredStorageMetadatas(), getDfceService());
      } catch (StorageException srcSerEx) {
         throw new SearchingServiceEx(StorageMessageHandler
               .getMessage(Constants.SRH_CODE_ERROR), srcSerEx.getMessage(),
               srcSerEx);
      } catch (IOException ioExcept) {
         throw new SearchingServiceEx(StorageMessageHandler
               .getMessage(Constants.SRH_CODE_ERROR), ioExcept.getMessage(),
               ioExcept);
      } catch (Exception except) {
         throw new SearchingServiceEx(StorageMessageHandler
               .getMessage(Constants.SRH_CODE_ERROR), except.getMessage(),
               except);
      }
   }

   /**
    * {@inheritDoc}
    */
   public final <T> void setSearchingServiceParameter(final T parameter) {
      setDfceService((ServiceProvider) parameter);

   }

}
