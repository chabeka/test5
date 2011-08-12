package fr.urssaf.image.sae.services.document.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.exception.SAESearchServiceEx;
import fr.urssaf.image.sae.model.SAELuceneCriteria;
import fr.urssaf.image.sae.model.UntypedDocument;
import fr.urssaf.image.sae.services.document.SAESearchService;
import fr.urssaf.image.sae.storage.dfce.contants.Constants;
import fr.urssaf.image.sae.storage.dfce.messages.MessageHandler;
import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;
import fr.urssaf.image.sae.storage.exception.SearchingServiceEx;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocuments;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.LuceneCriteria;

/**
 * Fournit l'implémentation des services pour la recherche.<BR />
 * 
 * @author akenore,rhofir.
 */
@Service
@Qualifier("saeSearchService")
@SuppressWarnings("PMD.LongVariable")
public class SAESearchServiceImpl extends AbstractSAEServices implements
      SAESearchService {
   /**
    * {@inheritDoc}
    */
   public final List<UntypedDocument> search(SAELuceneCriteria sAELuceneCriteria)
         throws SAESearchServiceEx {
      try {
         LuceneCriteria luceneCriteria = new LuceneCriteria(sAELuceneCriteria
               .getLuceneQuery(), sAELuceneCriteria.getLimit(),
               new ArrayList<StorageMetadata>());
         getStorageServiceProvider().setStorageServiceProviderParameter(
               getStorageConnectionParameter());
         getStorageServiceProvider().getStorageConnectionService()
               .openConnection();
         StorageDocuments storageDocuments = getStorageServiceProvider()
               .getStorageDocumentService()
               .searchStorageDocumentByLuceneCriteria(luceneCriteria);
         // TODO passer l'objet storageDocuments à sea-bo pour le convertir.
         storageDocuments.getAllStorageDocument();
         // TODO à supprimer
         return new ArrayList<UntypedDocument>();
      } catch (ConnectionServiceEx except) {
         throw new SAESearchServiceEx(MessageHandler
               .getMessage(Constants.CNT_CODE_ERROR), except.getMessage(),
               except);
      } catch (SearchingServiceEx except) {
         throw new SAESearchServiceEx(MessageHandler
               .getMessage(Constants.RTR_CODE_ERROR), except.getMessage(),
               except);
      } finally {
         getStorageServiceProvider().getStorageConnectionService()
               .closeConnexion();
      }
   }
}
