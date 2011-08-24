package fr.urssaf.image.sae.services.document.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.bo.model.untyped.UntypedDocument;
import fr.urssaf.image.sae.metadata.exceptions.ReferentialException;
import fr.urssaf.image.sae.services.document.SAEConsultationService;
import fr.urssaf.image.sae.services.document.component.ServicesConverter;
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

   private final ServicesConverter servicesConverter;

   /**
    * attribution des paramètres de l'implémentation
    * 
    * @param servicesConverter
    *           instance du service de conversion
    */
   @Autowired
   public SAEConsultationServiceImpl(ServicesConverter servicesConverter) {
      super();
      this.servicesConverter = servicesConverter;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final UntypedDocument consultation(UUID idArchive)
         throws SAEConsultationServiceException {

      this.getStorageServiceProvider().setStorageServiceProviderParameter(
            this.getStorageConnectionParameter());

      try {
         this.getStorageServiceProvider().getStorageConnectionService()
               .openConnection();

         UUIDCriteria uuidCriteria = new UUIDCriteria(idArchive, null);

         try {

            StorageDocument storageDocument = this.getStorageServiceProvider()
                  .getStorageDocumentService().retrieveStorageDocumentByUUID(
                        uuidCriteria);

            UntypedDocument untypedDocument = null;

            if (storageDocument != null) {

               untypedDocument = servicesConverter
                     .convertToUntypedDocument(storageDocument);

            }

            return untypedDocument;

         } catch (RetrievalServiceEx e) {

            throw new SAEConsultationServiceException(e);

         } catch (ReferentialException e) {

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
