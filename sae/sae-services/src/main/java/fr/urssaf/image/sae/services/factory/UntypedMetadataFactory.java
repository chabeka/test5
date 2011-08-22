package fr.urssaf.image.sae.services.factory;

import java.util.ArrayList;
import java.util.List;

import me.prettyprint.cassandra.utils.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;

import fr.urssaf.image.sae.bo.model.untyped.UntypedMetadata;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;

/**
 * Classe d'instanciation d'objet de type {@link UntypedMetadata}
 * 
 * 
 */
public final class UntypedMetadataFactory {

   private UntypedMetadataFactory() {

   }

   /**
    * instanciation d'un objet {@link UntypedMetadata} à partir d'un objet
    * {@link StorageMetadata}.
    * 
    * 
    * @param storageMetadata
    *           instance modèle, doit être non null
    * @return instance de {@link UntypedMetadata}
    */
   public static UntypedMetadata createUntypedMetadata(
         StorageMetadata storageMetadata) {

      Assert.notNull(storageMetadata, "'storageMetadata' is required");

      UntypedMetadata untypedMetadata = new UntypedMetadata();
      // TODO référentiel métadonnée : charger le code long
      untypedMetadata.setLongCode(storageMetadata.getShortCode());
      untypedMetadata
            .setValue(ObjectUtils.toString(storageMetadata.getValue()));

      return untypedMetadata;
   }

   /**
    * instanciation d'une liste de {@link UntypedMetadata} à partir d'une liste
    * de {@link StorageMetadata}.
    * 
    * 
    * @param storageMetadatas
    *           liste des modèles
    * @return liste de {@link UntypedMetadata}
    */
   public static List<UntypedMetadata> createUntypedMetadata(
         List<StorageMetadata> storageMetadatas) {

      List<UntypedMetadata> untypedMetadatas = new ArrayList<UntypedMetadata>();

      if (CollectionUtils.isNotEmpty(storageMetadatas)) {

         for (StorageMetadata storageMetadata : storageMetadatas) {

            if (storageMetadata != null) {

               untypedMetadatas.add(createUntypedMetadata(storageMetadata));
            }
         }

      }

      return untypedMetadatas;
   }

}
