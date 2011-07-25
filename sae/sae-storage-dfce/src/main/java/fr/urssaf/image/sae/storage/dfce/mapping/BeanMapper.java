package fr.urssaf.image.sae.storage.dfce.mapping;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.docubase.toolkit.model.ToolkitFactory;
import net.docubase.toolkit.model.base.Base;
import net.docubase.toolkit.model.base.BaseCategory;
import net.docubase.toolkit.model.document.Criterion;
import net.docubase.toolkit.model.document.Document;
import net.docubase.toolkit.service.ServiceProvider;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import fr.urssaf.image.sae.storage.dfce.utils.Utils;
import fr.urssaf.image.sae.storage.exception.StorageException;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;

/**
 * Fournit des méthodes statiques de conversion des elements DFCE ceux du SAE.
 */

public final class BeanMapper {

   /**
    * Permet de convertir un {@link Document} en {@link StorageDocument}.<br/>
    * 
    * @param document
    *           : Le document DFCE.
    * @param desiredMetaDatas
    *           : Les métadonnées souhaitées.
    * @return une occurrence de StorageDocument
    * @throws StorageException
    *            : Exception levée lorsque qu'un dysfonctionnement se produit.
    * @throws IOException
    *            : Exception levée lorsque qu'un dysfonctionnement se produit
    *            lors des I/O.
    */
   @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
   public static StorageDocument dfceDocumentToStorageDocument(
         final Document document, final List<StorageMetadata> desiredMetaDatas)
         throws StorageException, IOException {
      // on construit la liste des métadonnées à partir de la liste des
      // métadonnées souhaitées.
      final List<StorageMetadata> metaDatas = storageMetaDatasFromCriterions(
            document, desiredMetaDatas);
      return buildStorageDocument(document, metaDatas);
   }

   /**
    * Permet de convertir les métadonnées DFCE vers les métadonnées
    * StorageDocument.<br/>
    * 
    * @param document
    *           : Le document DFCE.
    * @param desiredMetaData
    *           : Les métadonnées souhaitées.
    * @return une occurrence de StorageDocument contenant uniquement les
    *         métadonnées.
    * @throws StorageException
    *            : Exception levée lorsque qu'un dysfonctionnement se produit.
    * @throws IOException
    *            : Exception levée lorsque qu'un dysfonctionnement se produit
    *            lors des I/O.
    */
   @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
   public static StorageDocument dfceMetaDataToStorageDocument(
         final Document document, final List<StorageMetadata> desiredMetaData)
         throws StorageException, IOException {
      List<StorageMetadata> metaDatas = storageMetaDatasFromCriterions(
            document, desiredMetaData);
      return new StorageDocument(metaDatas);
   }

   /**
    * Construit la liste des {@link StorageMetadata} à partir de la liste des
    * {@link Criterion}.
    * 
    * @param document
    *           : Le document DFCE.
    * @param desiredMetaData
    *           : La liste des métadonnées souhaitées.
    * @return La liste des {@link StorageMetadata} à partir de la liste des
    *         {@link Criterion}.
    */
   @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
   private static List<StorageMetadata> storageMetaDatasFromCriterions(
         final Document document, final List<StorageMetadata> desiredMetaData) {
      // Permet de savoir si la liste listMetaData est constitué.
      boolean treatementDone = false;
      List<StorageMetadata> metaDatas = new ArrayList<StorageMetadata>();
      if (document != null) {
         List<Criterion> criterions = document.getAllCriterions();
         // Traitement pour filtrer sur la liste des métadonnées souhaitées.
         for (StorageMetadata metadata : Utils
               .nullSafeIterable(desiredMetaData)) {
            treatementDone = true;
            for (Criterion criterion : Utils.nullSafeIterable(criterions)) {
               if (criterion.getCategoryName()
                     .contains(metadata.getShortCode())) {
                  metaDatas.add(new StorageMetadata(metadata.getLongCode(),
                        metadata.getShortCode(), criterion.getWord()));
               }
            }
         }
         // la liste des métadonnées souhaitées est vide
         if (!treatementDone) {
            for (Criterion criterion : Utils.nullSafeIterable(criterions)) {
               metaDatas.add(new StorageMetadata(null, criterion
                     .getCategoryName(), criterion.getWord()));
            }
         }
      }
      return metaDatas;
   }

   /**
    * Construit une occurrence de storageDocument à partir d'un document DFCE.
    * 
    * @param document
    *           : Le document DFCE.
    * @param listMetaData
    *           : La liste des métadonnées.
    * @throws IOException
    *            Exception levée lorsque qu'un dysfonctionnement se produit lors
    *            des I/O.
    */
   private static StorageDocument buildStorageDocument(final Document document,
         List<StorageMetadata> listMetaData) throws IOException {
      // Instance de StorageDocument
      StorageDocument storageDocument = new StorageDocument();
      InputStream docContent = ServiceProvider.getStoreService()
            .getDocumentFile(document);
      storageDocument.setCreationDate(document.getCreationDate());
      storageDocument.setTitle(document.getTitle());
      storageDocument.setContent(IOUtils.toByteArray(docContent));
      storageDocument.setUuid(document.getUuid());
      storageDocument.setMetadatas(listMetaData);
      return storageDocument;
   }

   /**
    * Permet de convertir {@link StorageDocument} en {@link Document}.
    * 
    * @param baseDFCE
    *           : La base dfce
    * @param storageDocument
    *           : Un StorageDocment.
    * @return Un document DFCE à partir d'un storageDocment.
    * @throws ParseException
    *            Exception si le parsing de la date ne se passe pas bien.
    */
   public static Document storageDocumentToDfceDocument(final Base baseDFCE,
         final StorageDocument storageDocument) throws ParseException {
      BaseCategory baseCategory = null;
      Date dateCreation = new Date();
      Base base = ServiceProvider.getBaseAdministrationService().getBase(
            baseDFCE.getBaseId());
      Document document = ToolkitFactory.getInstance().createDocumentTag(base);
      document.setType(storageDocument.getTypeDoc());
      // Si la date de creation est définie on remplace la date du jour par la
      // dite date
      if (storageDocument.getCreationDate() != null) {
         dateCreation = storageDocument.getCreationDate();
      }
      document.setCreationDate(dateCreation);
      // Si le titre est défini on l'associe au document
      if (storageDocument.getTitle() != null) {
         document.setTitle(storageDocument.getTitle());
      }
      for (StorageMetadata storageMetadata : Utils
            .nullSafeIterable(storageDocument.getMetadatas())) {
         if (!StringUtils.isEmpty(storageMetadata.getShortCode())
               && storageMetadata.getValue() != null) {
            baseCategory = base.getBaseCategory(storageMetadata.getShortCode());
            document.addCriterion(baseCategory, storageMetadata.getValue());
         }
      }
      return document;
   }

   /** Cette classe n'est pas faite pour être instanciée. */
   private BeanMapper() {
      assert false;
   }
}
