package fr.urssaf.image.sae.webservices.impl.factory;

import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.Assert;

import fr.cirtil.www.saeservice.ArchivageMasseResponse;
import fr.cirtil.www.saeservice.ArchivageMasseResponseType;
import fr.cirtil.www.saeservice.ArchivageUnitaireResponse;
import fr.cirtil.www.saeservice.ArchivageUnitaireResponseType;
import fr.cirtil.www.saeservice.ConsultationResponse;
import fr.cirtil.www.saeservice.ConsultationResponseType;
import fr.cirtil.www.saeservice.ListeMetadonneeType;
import fr.cirtil.www.saeservice.ListeResultatRechercheType;
import fr.cirtil.www.saeservice.MetadonneeType;
import fr.cirtil.www.saeservice.ObjetNumeriqueType;
import fr.cirtil.www.saeservice.RechercheResponse;
import fr.cirtil.www.saeservice.RechercheResponseType;
import fr.cirtil.www.saeservice.ResultatRechercheType;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.webservices.factory.ObjectTypeFactory;
import fr.urssaf.image.sae.webservices.impl.helper.ObjectStorageHelper;

/**
 * Classe d'instanciation des réponses pour l'implémentation
 * {@link fr.urssaf.image.sae.webservices.impl.SaeStorageServiceImpl}
 * 
 * 
 */
public final class ObjectStorageResponseFactory {

   private ObjectStorageResponseFactory() {

   }

   /**
    * instanciation de {@link ConsultationResponse}.<br>
    * Implementation de {@link ConsultationResponseType}
    * 
    * <pre>
    * &lt;xsd:complexType name="consultationResponseType">
    *    &lt;xsd:sequence>
    *       &lt;xsd:element name="objetNumerique" type="sae:objetNumeriqueType">
    *        ...
    *       &lt;/xsd:element>
    *       &lt;xsd:element name="metadonnees" type="sae:listeMetadonneeType">
    *       ...
    *       &lt;/xsd:element>
    *    &lt;/xsd:sequence>
    * &lt;/xsd:complexType>
    * </pre>
    * 
    * 
    * @param content
    *           valeur de <code>objetNumeriqueType</code> non vide
    * @param metadonnees
    *           valeur de <code>listeMetadonneeType</code>
    * @return instance de {@link ConsultationResponse}
    */
   public static ConsultationResponse createConsultationResponse(
         byte[] content, List<MetadonneeType> metadonnees) {

      Assert.notNull(content, "content is required");

      ConsultationResponse response = createConsultationResponse();
      ConsultationResponseType responseType = response
            .getConsultationResponse();

      ObjetNumeriqueType objetNumerique = ObjectTypeFactory
            .createObjetNumeriqueType(content);

      responseType.setObjetNumerique(objetNumerique);

      ListeMetadonneeType listeMetadonnee = new ListeMetadonneeType();

      if (CollectionUtils.isNotEmpty(metadonnees)) {

         for (MetadonneeType metadonnee : metadonnees) {

            listeMetadonnee.addMetadonnee(metadonnee);
         }

      }

      responseType.setMetadonnees(listeMetadonnee);

      return response;

   }

   /**
    * instanciation de {@link ConsultationResponse} vide<br>
    * 
    * @return instance de {@link ConsultationResponse}
    */
   public static ConsultationResponse createConsultationResponse() {

      ConsultationResponse response = new ConsultationResponse();
      ConsultationResponseType responseType = new ConsultationResponseType();
      response.setConsultationResponse(responseType);

      return response;
   }

   /**
    * instanciation de {@link RechercheResponse}.<br>
    * Implementation de {@link RechercheResponseType}
    * 
    * <pre>
    * &lt;xsd:complexType name="rechercheResponseType">
    *    &lt;xsd:sequence>
    *       &lt;xsd:element name="resultats" type="sae:listeResultatRechercheType">
    *       ...       
    *       &lt;/xsd:element>
    *       &lt;xsd:element name="resultatTronque" type="xsd:boolean">
    *       ...
    *       &lt;/xsd:element>
    *    &lt;/xsd:sequence>
    * &lt;/xsd:complexType>
    * </pre>
    * 
    * @param storageDocuments
    *           valeur de <code>listeResultatRechercheType</code>
    * @param resultatTronque
    *           valeur de <code>resultatTronque</code>
    * @return instance de {@link RechercheResponse}
    */
   public static RechercheResponse createRechercheResponse(
         List<StorageDocument> storageDocuments, boolean resultatTronque) {

      RechercheResponse response = new RechercheResponse();
      RechercheResponseType responseType = new RechercheResponseType();
      response.setRechercheResponse(responseType);

      ListeResultatRechercheType resultatsType = new ListeResultatRechercheType();

      if (CollectionUtils.isNotEmpty(storageDocuments)) {

         for (StorageDocument storageDocument : storageDocuments) {

            ResultatRechercheType resultatRecherche = ObjectTypeFactory
                  .createResultatRechercheType();

            resultatRecherche.setIdArchive(ObjectTypeFactory
                  .createUuidType(storageDocument.getUuid()));
            ListeMetadonneeType listeMetadonnee = ObjectTypeFactory
                  .createListeMetadonneeType();

            List<MetadonneeType> metadonnees = ObjectStorageHelper
                  .createListMetadonneeType(storageDocument);

            if (CollectionUtils.isNotEmpty(metadonnees)) {

               for (MetadonneeType metaDonnee : metadonnees) {

                  listeMetadonnee.addMetadonnee(metaDonnee);
               }

            }

            resultatRecherche.setMetadonnees(listeMetadonnee);
            resultatsType.addResultat(resultatRecherche);
         }
      }

      responseType.setResultats(resultatsType);
      responseType.setResultatTronque(resultatTronque);

      return response;

   }

   /**
    * instanciation de {@link ArchivageUnitaireResponse}.<br>
    * Implementation de {@link ArchivageUnitaireResponseType}
    * 
    * <pre>
    * &lt;xsd:complexType name="archivageUnitaireResponseType">
    *    ...     
    *    &lt;xsd:sequence>
    *       &lt;xsd:element name="idArchive" type="sae:uuidType">
    *       ...      
    *       &lt;/xsd:element>
    *    &lt;/xsd:sequence>
    * &lt;/xsd:complexType>
    * </pre>
    * 
    * @param idArchive
    *           valeur de <code>uuidType</code>
    * @return instance de {@link ArchivageUnitaireResponse}
    */
   public static ArchivageUnitaireResponse createArchivageUnitaireResponse(
         UUID idArchive) {

      ArchivageUnitaireResponse response = createArchivageUnitaireResponse();
      ArchivageUnitaireResponseType responseType = response
            .getArchivageUnitaireResponse();

      responseType.setIdArchive(ObjectTypeFactory.createUuidType(idArchive));

      return response;
   }

   /**
    * instanciation de {@link ArchivageUnitaireResponse} vide.<br>
    * 
    * @return instance de {@link ArchivageUnitaireResponse}
    */
   public static ArchivageUnitaireResponse createArchivageUnitaireResponse() {

      ArchivageUnitaireResponse response = new ArchivageUnitaireResponse();
      ArchivageUnitaireResponseType responseType = new ArchivageUnitaireResponseType();
      response.setArchivageUnitaireResponse(responseType);

      return response;
   }

   /**
    * instanciation de {@link ArchivageMasseResponse}.<br>
    * implémentation de {@link ArchivageMasseResponseType}
    * 
    * <pre>
    * &lt;xsd:complexType name="archivageMasseResponseType">
    * ...
    * &lt;/xsd:complexType>
    * </pre>
    * 
    * @return instance de {@link ArchivageMasseResponse}
    */
   public static ArchivageMasseResponse createArchivageMasseResponse() {

      ArchivageMasseResponse response = new ArchivageMasseResponse();
      ArchivageMasseResponseType responseType = new ArchivageMasseResponseType();
      response.setArchivageMasseResponse(responseType);

      return response;
   }

}
