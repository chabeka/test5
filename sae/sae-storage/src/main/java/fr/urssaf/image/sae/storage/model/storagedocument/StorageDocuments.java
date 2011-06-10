package fr.urssaf.image.sae.storage.model.storagedocument;

import java.util.List;

/**
 * Classe concrète représentant la liste des documents
 * 
 *<li>
 * Attribut storageDocuments : La liste des documents bien archivés</li>
 */
public class StorageDocuments {

   private List<StorageDocument> storageDocuments;

   /**
    * Retourne la liste des documents
    * 
    * @return La liste des documents
    */
   public final List<StorageDocument> getStorageDocuments() {
      return storageDocuments;
   }

   /**
    * Initialise la liste des documents
    * 
    * @param storageDocuments
    *           La liste des documents
    */
   public final void setStorageDocuments(List<StorageDocument> storageDocuments) {
      this.storageDocuments = storageDocuments;
   }

}
