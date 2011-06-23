package fr.urssaf.image.sae.webservices.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import fr.cirtil.www.saeservice.ArchivageMasse;
import fr.cirtil.www.saeservice.ArchivageMasseResponse;
import fr.cirtil.www.saeservice.ArchivageUnitaire;
import fr.cirtil.www.saeservice.ArchivageUnitaireResponse;
import fr.cirtil.www.saeservice.Consultation;
import fr.cirtil.www.saeservice.ConsultationResponse;
import fr.cirtil.www.saeservice.MetadonneeType;
import fr.cirtil.www.saeservice.Recherche;
import fr.cirtil.www.saeservice.RechercheResponse;
import fr.urssaf.image.sae.storage.exception.InsertionServiceEx;
import fr.urssaf.image.sae.storage.exception.RetrievalServiceEx;
import fr.urssaf.image.sae.storage.exception.SearchingServiceEx;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocuments;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.LuceneCriteria;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;
import fr.urssaf.image.sae.storage.services.storagedocument.StorageDocumentService;
import fr.urssaf.image.sae.webservices.SaeStorageService;
import fr.urssaf.image.sae.webservices.exception.SaeStorageException;
import fr.urssaf.image.sae.webservices.impl.factory.ObjectStorageFactory;
import fr.urssaf.image.sae.webservices.impl.factory.ObjectStorageResponseFactory;
import fr.urssaf.image.sae.webservices.impl.helper.ObjectStorageHelper;

/**
 * Implémentation de {@link SaeStorageService}<br>
 * La classe est un singleton de type {@link Service} esta ccessible avec
 * {@link Autowired}<br>
 * Si aucun bean de type {@link StorageDocumentService} n'est instancié alors
 * une Exception sera levée par Spring
 * 
 * 
 */
@Service
public class SaeStorageServiceImpl implements SaeStorageService {

   private final StorageDocumentService storageService;

   /**
    * 
    * @param storageService
    *           implementation de{@link StorageDocumentService }
    */
   @Autowired
   public SaeStorageServiceImpl(
         @Qualifier("storageDocumentService") StorageDocumentService storageService) {
      Assert.notNull(storageService, "storageService is required");
      this.storageService = storageService;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final ArchivageMasseResponse bulkCapture(ArchivageMasse request) {

      ArchivageMasseResponse response = ObjectStorageResponseFactory
            .createArchivageMasseResponse();

      // TODO implémenter l'archivage de masse

      return response;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final ArchivageUnitaireResponse capture(ArchivageUnitaire request) {

      ArchivageUnitaireResponse response;

      StorageDocument storageDocument = ObjectStorageFactory
            .createStorageDocument(request);
      try {

         UUID uuid = storageService.insertStorageDocument(storageDocument);

         if (uuid == null) {

            // TODO créer une réponse quand aucun StorageDocument n'est trouvé
            throw new SaeStorageException(
                  "il n'existe aucun identifiant unique pour cette capture");

         } else {

            response = ObjectStorageResponseFactory
                  .createArchivageUnitaireResponse(uuid);
         }

      } catch (InsertionServiceEx e) {
         throw new SaeStorageException(e);
      }

      return response;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final ConsultationResponse consultation(Consultation request) {

      ConsultationResponse response;

      UUIDCriteria uuidCriteria = ObjectStorageFactory
            .createUUIDCriteria(request);

      try {

         StorageDocument storageDocument = storageService
               .retrieveStorageDocumentByUUID(uuidCriteria);

         // TODO créer une réponse quand aucun StorageDocument n'est trouvé ou
         // quand celui-ci n'est pas valide
         if (storageDocument == null) {

            throw new SaeStorageException(
                  "Aucun document n'existe pour ces critères");

         } else {

            List<MetadonneeType> metadonnees = ObjectStorageHelper
                  .createListMetadonneeType(storageDocument);

            response = ObjectStorageResponseFactory.createConsultationResponse(
                  storageDocument.getContent(), metadonnees);

         }

      } catch (RetrievalServiceEx e) {
         throw new SaeStorageException(e);
      }

      return response;
   }

   /**
    * {@inheritDoc}
    */

   @Override
   public final RechercheResponse search(Recherche request) {

      RechercheResponse response;

      LuceneCriteria luceneCriteria = ObjectStorageFactory
            .createLuceneCriteria(request);

      try {
         StorageDocuments storageDocuments = storageService
               .searchStorageDocumentByLuceneCriteria(luceneCriteria);

         // TODO créer une réponse quand aucun StorageDocuments n'est trouvé ou
         // quand celui-ci n'est pas trouvé
         if (storageDocuments == null) {

            throw new SaeStorageException(
                  "Aucun document n'existe pour ces critères");

         } else {

            response = ObjectStorageResponseFactory.createRechercheResponse(
                  storageDocuments.getListOfStorageDocument(), false);

         }

      } catch (SearchingServiceEx e) {
         throw new SaeStorageException(e);
      }

      return response;
   }

}
