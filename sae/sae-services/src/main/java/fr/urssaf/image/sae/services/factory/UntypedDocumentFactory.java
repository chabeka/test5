package fr.urssaf.image.sae.services.factory;

import java.util.List;

import me.prettyprint.cassandra.utils.Assert;
import fr.urssaf.image.sae.bo.model.untyped.UntypedDocument;
import fr.urssaf.image.sae.bo.model.untyped.UntypedMetadata;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;

/**
 * Classe d'instanciation d'objet de type {@link UntypedDocument}
 * 
 * 
 */
public final class UntypedDocumentFactory {

   private UntypedDocumentFactory() {

   }

   /**
    * instanciation d'un objet {@link UntypedDocument} à partir d'un objet
    * {@link StorageDocument}.
    * 
    * 
    * @param storageDocument
    *           instance modèle, doit être non null
    * @return instance de {@link UntypedDocument}
    */
   public static UntypedDocument createUntypedDocument(
         StorageDocument storageDocument) {

      Assert.notNull(storageDocument, "'storageDocument' is required");

      UntypedDocument untypedDocument = new UntypedDocument();
      untypedDocument.setUuid(storageDocument.getUuid());
      untypedDocument.setContent(storageDocument.getContent());

      List<UntypedMetadata> metadatas = UntypedMetadataFactory
            .createUntypedMetadata(storageDocument.getMetadatas());
      untypedDocument.setUMetadatas(metadatas);

      return untypedDocument;

   }

}
