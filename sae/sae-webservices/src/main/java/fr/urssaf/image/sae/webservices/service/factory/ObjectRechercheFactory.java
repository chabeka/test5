package fr.urssaf.image.sae.webservices.service.factory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import fr.cirtil.www.saeservice.ListeMetadonneeType;
import fr.cirtil.www.saeservice.ListeResultatRechercheType;
import fr.cirtil.www.saeservice.MetadonneeType;
import fr.cirtil.www.saeservice.RechercheResponse;
import fr.cirtil.www.saeservice.RechercheResponseType;
import fr.cirtil.www.saeservice.ResultatRechercheType;
import fr.urssaf.image.sae.bo.model.untyped.UntypedDocument;
import fr.urssaf.image.sae.bo.model.untyped.UntypedMetadata;
import fr.urssaf.image.sae.webservices.factory.ObjectTypeFactory;

/**
 * Classe d'instanciation de :
 * <ul>
 * <li>{@link RechercheResponse}</li>
 * </ul>
 * 
 * 
 */
public final class ObjectRechercheFactory {

   private ObjectRechercheFactory() {
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
    * @param untypedDocuments
    *           valeur de <code>listeResultatRechercheType</code>
    * @param resultatTronque
    *           valeur de <code>resultatTronque</code>
    * @return instance de {@link RechercheResponse}
    */
   public static RechercheResponse createRechercheResponse(
         List<UntypedDocument> untypedDocuments, boolean resultatTronque) {

      RechercheResponse response = new RechercheResponse();
      RechercheResponseType responseType = new RechercheResponseType();
      response.setRechercheResponse(responseType);

      ListeResultatRechercheType resultatsType = new ListeResultatRechercheType();

      if (CollectionUtils.isNotEmpty(untypedDocuments)) {

         for (UntypedDocument storageDocument : untypedDocuments) {

            ResultatRechercheType resultatRecherche = ObjectTypeFactory.createResultatRechercheType();

            resultatRecherche.setIdArchive(ObjectTypeFactory.createUuidType(storageDocument.getUuid()));
            ListeMetadonneeType listeMetadonnee = ObjectTypeFactory.createListeMetadonneeType();

            List<MetadonneeType> metadonnees = createListMetadonneeType(storageDocument);

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
    * instanciation d'une liste de {@link MetadonneeType} à partir des
    * {@link StorageMetadata} contenu dans une instance de
    * {@link StorageDocument}
    * <ul>
    * <li>{@link MetadonneeType#setCode} : {@link StorageMetadata#getCode()}</li>
    * <li>{@link MetadonneeType#setValeur} : {@link StorageMetadata#getValue()}</li>
    * </ul>
    * 
    * @param untypedDocument
    *           instance de {@link StorageDocument} doit être non null
    * @return collection d'instance de {@link MetadonneeType}
    */
   public static List<MetadonneeType> createListMetadonneeType(UntypedDocument untypedDocument) {

      List<MetadonneeType> metadonnees = new ArrayList<MetadonneeType>();

      for (UntypedMetadata untypedMetadata :untypedDocument.getUMetadatas()) {

         String code = untypedMetadata.getLongCode();
         String valeur = untypedMetadata.getValue().toString();
         MetadonneeType metadonnee = ObjectTypeFactory.createMetadonneeType(code, valeur);

         metadonnees.add(metadonnee);
      }
      return metadonnees;
   }   
}
