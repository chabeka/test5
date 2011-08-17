package fr.urssaf.image.sae.services.document.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.services.document.SAEConsultationService;
import fr.urssaf.image.sae.services.document.exception.SAEConsultationServiceException;
import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;
import fr.urssaf.image.sae.storage.exception.RetrievalServiceEx;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;

/**
 * Implémentation du service {@link SAEConsultationService}
 * 
 */
@Service
@Qualifier("saeConsultationService")
public class SAEConsultationServiceImpl extends AbstractSAEServices implements
      SAEConsultationService {

   /**
    * {@inheritDoc}
    */
   @Override
   public final StorageDocument consultation(UUID idArchive)
         throws SAEConsultationServiceException {

      this.getStorageServiceProvider().setStorageServiceProviderParameter(
            this.getStorageConnectionParameter());

      try {
         this.getStorageServiceProvider().getStorageConnectionService()
               .openConnection();

         UUIDCriteria uuidCriteria = new UUIDCriteria(idArchive, null);

         try {

            return this.getStorageServiceProvider().getStorageDocumentService()
                  .retrieveStorageDocumentByUUID(uuidCriteria);

         } catch (RetrievalServiceEx e) {

            throw new SAEConsultationServiceException(e);

         } finally {

            this.getStorageServiceProvider().getStorageConnectionService()
                  .closeConnexion();
         }

      } catch (ConnectionServiceEx e) {

         throw new SAEConsultationServiceException(e);
      }

   }

}
