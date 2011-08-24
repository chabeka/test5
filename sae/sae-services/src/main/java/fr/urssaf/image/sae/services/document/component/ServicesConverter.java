package fr.urssaf.image.sae.services.document.component;

import java.util.ArrayList;
import java.util.List;

//import me.prettyprint.cassandra.utils.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import fr.urssaf.image.sae.bo.model.untyped.UntypedDocument;
import fr.urssaf.image.sae.bo.model.untyped.UntypedMetadata;
import fr.urssaf.image.sae.metadata.exceptions.ReferentialException;
import fr.urssaf.image.sae.metadata.referential.model.MetadataReference;
import fr.urssaf.image.sae.metadata.referential.services.MetadataReferenceDAO;
import fr.urssaf.image.sae.services.factory.UntypedMetadataFactory;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;

/**
 * Service de conversion du modèle objet de l'artefact <code>sae-services</code>
 * 
 * 
 * 
 */
@Component
public class ServicesConverter {

   private final MetadataReferenceDAO metadataRefDAO;

   /**
    * attribution des paramètres de l'implémentation
    * 
    * @param metadataRefDAO
    *           service du référentiel des métadonnées
    */
   @Autowired
   public ServicesConverter(MetadataReferenceDAO metadataRefDAO) {
      this.metadataRefDAO = metadataRefDAO;
   }

   /**
    * service de conversion d'une instance de {@link StorageDocument} en une
    * instance {@link UntypedDocument}<br>
    * <br>
    * Mécanisme de conversion en {@link UntypedDocument} :
    * <ul>
    * <li><code>uuid</code> de {@link UntypedDocument} copié à partir de la
    * valeur de <code>uuid</code> de {@link storageDocument}</li>
    * <li><code>content</code> de {@link UntypedDocument} copié à partir de la
    * valeur de <code>content</code> de {@link storageDocument}</li>
    * <li>liste des {@link StorageMetadata} de {@link UntypedDocument}
    * instanciée à partir de la liste des {@link UntypedMetadata} de
    * {@link storageDocument}
    * <ul>
    * <li><code>value</code> de {@link UntypedMetadata} copié à partir de la
    * valeur de <code>value</code> de {@link storageMetadata}</li>
    * <li><code>longCode</code> de {@link UntypedMetadata} copié à partir de la
    * valeur de <code>longCode</code> de {@link MetadataReference} récupéré du
    * référentiel des métadonnées avec la valeur <code>shortCode</code> de
    * {@link storageMetadata}</li>
    * </ul>
    * </li>
    * </ul>
    * 
    * @param storageDocument
    *           objet à convertir
    * @return instance de {@link UntypedDocument}
    * @throws ReferentialException
    *            exception levée par l'appel du service de référentiel des
    *            métadonnées
    */
   public final UntypedDocument convertToUntypedDocument(
         StorageDocument storageDocument) throws ReferentialException {

      Assert.notNull(storageDocument, "'storageDocument' is required");

      UntypedDocument untypedDocument = new UntypedDocument();
      untypedDocument.setUuid(storageDocument.getUuid());
      untypedDocument.setContent(storageDocument.getContent());

      List<UntypedMetadata> untypedMetadatas = new ArrayList<UntypedMetadata>();

      List<StorageMetadata> storageMetadatas = storageDocument.getMetadatas();

      if (CollectionUtils.isNotEmpty(storageMetadatas)) {

         untypedMetadatas = createUntypedMetadata(storageDocument
               .getMetadatas());

      }

      untypedDocument.setUMetadatas(untypedMetadatas);

      return untypedDocument;

   }

   private List<UntypedMetadata> createUntypedMetadata(
         List<StorageMetadata> storageMetadatas) throws ReferentialException {

      Assert.notEmpty(storageMetadatas, "'storageMetadatas' is required");

      List<UntypedMetadata> untypedMetadatas = new ArrayList<UntypedMetadata>();

      for (StorageMetadata storageMetadata : storageMetadatas) {

         if (storageMetadata != null) {

            MetadataReference metadataReference = metadataRefDAO
                  .getByShortCode(storageMetadata.getShortCode());

            if (metadataReference != null && metadataReference.isConsultable()) {
               untypedMetadatas
                     .add(UntypedMetadataFactory.createUntypedMetadata(
                           storageMetadata, metadataReference));
            }
         }
      }

      return untypedMetadatas;
   }

}
