package fr.urssaf.image.sae.services.factory;

import me.prettyprint.cassandra.utils.Assert;

import org.apache.commons.lang.ObjectUtils;

import fr.urssaf.image.sae.bo.model.untyped.UntypedMetadata;
import fr.urssaf.image.sae.metadata.referential.model.MetadataReference;
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
    * @param metadataReference
    *           instance de la référence de la métadonnée, doit être non null
    * @return instance de {@link UntypedMetadata}
    */
   public static UntypedMetadata createUntypedMetadata(
         StorageMetadata storageMetadata, MetadataReference metadataReference) {

      Assert.notNull(storageMetadata, "'storageMetadata' is required");
      Assert.notNull(metadataReference, "'metadataReference' is required");

      UntypedMetadata untypedMetadata = new UntypedMetadata();
     
      untypedMetadata.setLongCode(metadataReference.getLongCode());
      untypedMetadata
            .setValue(ObjectUtils.toString(storageMetadata.getValue()));

      return untypedMetadata;
   }

}
