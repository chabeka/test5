package fr.urssaf.image.sae.webservices.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.cirtil.www.saeservice.Consultation;
import fr.cirtil.www.saeservice.ConsultationResponse;
import fr.cirtil.www.saeservice.MetadonneeType;
import fr.urssaf.image.sae.services.document.SAEDocumentService;
import fr.urssaf.image.sae.services.document.exception.SAEConsultationServiceException;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;
import fr.urssaf.image.sae.webservices.exception.ConsultationAxisFault;
import fr.urssaf.image.sae.webservices.factory.ObjectTypeFactory;
import fr.urssaf.image.sae.webservices.service.WSConsultationService;
import fr.urssaf.image.sae.webservices.service.factory.ObjectConsultationFactory;
import fr.urssaf.image.sae.webservices.util.CollectionUtils;

/**
 * Implémentation de {@link WSConsultationService}<br>
 * L'implémentation est annotée par {@link Service}
 * 
 */
@Service
public class WSConsultationServiceImpl implements WSConsultationService {

   @Autowired
   @Qualifier("documentService")
   private SAEDocumentService saeService;

   /**
    * {@inheritDoc}
    */
   @Override
   public final ConsultationResponse consultation(Consultation request)
         throws ConsultationAxisFault {

      ConsultationResponse response;

      UUID uuid = UUID.fromString(request.getConsultation().getIdArchive()
            .getUuidType());

      if (BooleanUtils.isTrue(request.getConsultation()
            .getUrlConsultationDirecte())) {

         throw new ConsultationAxisFault(
               "la fonctionnalité URL de consultation directe n'est pas implémentée",
               "FonctionNonImplementee");
      }

      try {
         StorageDocument storageDocument = saeService.consultation(uuid);

         if (storageDocument == null) {

            throw new ConsultationAxisFault(
                  "il n'existe aucun document pour l'identifiant d'archivage '"
                        + uuid + "'", "ArchiveNonTrouvee");

         } else {

            List<MetadonneeType> metadatas = new ArrayList<MetadonneeType>();

            for (StorageMetadata storageMetadata : CollectionUtils
                  .loadListNotNull(storageDocument.getMetadatas())) {

               String code = storageMetadata.getShortCode();
               String valeur = storageMetadata.getValue().toString();
               MetadonneeType metadonnee = ObjectTypeFactory
                     .createMetadonneeType(code, valeur);

               metadatas.add(metadonnee);
            }

            byte[] content = storageDocument.getContent();

            response = ObjectConsultationFactory.createConsultationResponse(
                  content, metadatas);

         }

      } catch (SAEConsultationServiceException e) {
         throw new ConsultationAxisFault(e);
      }

      return response;
   }

}
