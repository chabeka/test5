package fr.urssaf.image.sae.webservices.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.cirtil.www.saeservice.Consultation;
import fr.cirtil.www.saeservice.ConsultationMTOM;
import fr.cirtil.www.saeservice.ConsultationMTOMResponse;
import fr.cirtil.www.saeservice.ConsultationResponse;
import fr.cirtil.www.saeservice.ListeMetadonneeCodeType;
import fr.cirtil.www.saeservice.MetadonneeType;
import fr.urssaf.image.sae.bo.model.untyped.UntypedDocument;
import fr.urssaf.image.sae.bo.model.untyped.UntypedMetadata;
import fr.urssaf.image.sae.services.consultation.model.ConsultParams;
import fr.urssaf.image.sae.services.document.SAEDocumentService;
import fr.urssaf.image.sae.services.exception.UnknownDesiredMetadataEx;
import fr.urssaf.image.sae.services.exception.consultation.MetaDataUnauthorizedToConsultEx;
import fr.urssaf.image.sae.services.exception.consultation.SAEConsultationServiceException;
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
   
   
   private static final Logger LOG = LoggerFactory
         .getLogger(WSConsultationServiceImpl.class);
   
   
   @Autowired
   @Qualifier("documentService")
   private SAEDocumentService saeService;

   
   /**
    * {@inheritDoc}
    */
   @Override
   public final ConsultationResponse consultation(Consultation request)
         throws ConsultationAxisFault {

      // Traces debug - entrée méthode
      String prefixeTrc = "consultation()";
      LOG.debug("{} - Début", prefixeTrc);
      // Fin des traces debug - entrée méthode
      
      // Lecture de l'UUID depuis l'objet de requête de la couche ws
      UUID uuid = UUID.fromString(request.getConsultation().getIdArchive()
            .getUuidType());
      LOG.debug("{} - UUID envoyé par l'application cliente : {}", prefixeTrc,
            uuid);
      
      // Lecture des métadonnées depuis l'objet de requête de la couche ws
      ListeMetadonneeCodeType listeMetas = request.getConsultation().getMetadonnees();
      
      // Appel de la méthode commune entre avec MTOM et sans MTOM
      // Cette méthode se charge des vérifications et de la levée des AxisFault
      UntypedDocument untypedDocument = consultationCommune(uuid, listeMetas);
      
      // Conversion de l'objet UntypedDocument en un objet de la couche web service
      List<MetadonneeType> metadatas = convertListeMetasServiceToWebService(untypedDocument.getUMetadatas());
      ConsultationResponse response = ObjectConsultationFactory.createConsultationResponse(
            untypedDocument.getContent(), 
            metadatas);
      
      // Traces debug - sortie méthode
      LOG.debug("{} - Sortie", prefixeTrc);
      
      // Renvoie l'objet de réponse de la couche web service
      return response;
      
   }
   
   
   /**
    * {@inheritDoc}
    */
   @Override
   public final ConsultationMTOMResponse consultationMTOM(ConsultationMTOM request)
         throws ConsultationAxisFault {

      // Traces debug - entrée méthode
      String prefixeTrc = "consultationMTOM()";
      LOG.debug("{} - Début", prefixeTrc);
      // Fin des traces debug - entrée méthode
      
      // Lecture de l'UUID depuis l'objet de requête de la couche ws
      UUID uuid = UUID.fromString(request.getConsultationMTOM().getIdArchive()
            .getUuidType());
      LOG.debug("{} - UUID envoyé par l'application cliente : {}", prefixeTrc,
            uuid);
      
      // Lecture des métadonnées depuis l'objet de requête de la couche ws
      ListeMetadonneeCodeType listeMetas = request.getConsultationMTOM().getMetadonnees();
      
      // Appel de la méthode commune entre avec MTOM et sans MTOM
      // Cette méthode se charge des vérifications et de la levée des AxisFault
      UntypedDocument untypedDocument = consultationCommune(uuid, listeMetas);
      
      // Conversion de l'objet UntypedDocument en un objet de la couche web service
      List<MetadonneeType> metadatas = convertListeMetasServiceToWebService(untypedDocument.getUMetadatas());
      ConsultationMTOMResponse response = ObjectConsultationFactory.createConsultationMTOMResponse(
            untypedDocument.getContent(), metadatas);
      
      // Traces debug - sortie méthode
      LOG.debug("{} - Sortie", prefixeTrc);
      
      // Renvoie l'objet de réponse de la couche web service
      return response;
      
   }
   
   
      
   private UntypedDocument consultationCommune(UUID uuid, ListeMetadonneeCodeType listeMeta)
         throws ConsultationAxisFault {

      // Traces debug - entrée méthode
      String prefixeTrc = "consultationCommune()";
      LOG.debug("{} - Début", prefixeTrc);
      // Fin des traces debug - entrée méthode
      
      try {

         // Convertit la liste des métadonnées de l'objet de la couche ws vers un
         // objet plus exploitable
         List<String> listMetas = convertListeMetasWebServiceToService(listeMeta);
         
         // Appel de la couche service
         ConsultParams consultParams = new ConsultParams(uuid, listMetas);
         UntypedDocument untypedDocument = saeService
               .consultation(consultParams);

         // Regarde si l'archive a été retrouvée dans le SAE. Si ce n'est pas le
         // cas, on lève la SoapFault correspondante
         if (untypedDocument == null) {
            LOG
                  .debug(
                        "{} - L'archive demandée n'a pas été retrouvée dans le SAE ({})",
                        prefixeTrc, uuid);
            throw new ConsultationAxisFault(
                  "Il n'existe aucun document pour l'identifiant d'archivage '"
                        + uuid + "'", "ArchiveNonTrouvee");

         } else {

            // Traces debug - sortie méthode
            LOG.debug("{} - Sortie", prefixeTrc);
            
            // Renvoie le UntypedDocument
            return untypedDocument;

         }
         
      } catch (SAEConsultationServiceException e) {
         throw new ConsultationAxisFault(e);
      
      } catch (UnknownDesiredMetadataEx e) {
         throw new ConsultationAxisFault(e.getMessage(),
               "ConsultationMetadonneesInexistante", e);
      } catch (MetaDataUnauthorizedToConsultEx e) {
         throw new ConsultationAxisFault(e.getMessage(),
               "ConsultationMetadonneesNonAutorisees", e);
      }

   }
   
   
   private List<String> convertListeMetasWebServiceToService(ListeMetadonneeCodeType listeMetaWs) {

      if (listeMetaWs==null) {
         return null ;
      } else {
         return ObjectTypeFactory.buildMetaCodeFromWS(listeMetaWs);
      }
      
   }
   
   
   private List<MetadonneeType> convertListeMetasServiceToWebService(List<UntypedMetadata> listeMetasService) {
      
      List<MetadonneeType> metadatas = new ArrayList<MetadonneeType>();

      for (UntypedMetadata untypedMetadata : CollectionUtils
            .loadListNotNull(listeMetasService)) {

         String code = untypedMetadata.getLongCode();
         String valeur = untypedMetadata.getValue();
         if (untypedMetadata.getValue() == null) {
            valeur = StringUtils.EMPTY;
         }
         MetadonneeType metadonnee = ObjectTypeFactory
               .createMetadonneeType(code, valeur);

         metadatas.add(metadonnee);
      }
      
      return metadatas;
      
   }

}
