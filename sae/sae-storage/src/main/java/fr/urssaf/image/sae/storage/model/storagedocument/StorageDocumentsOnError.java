package fr.urssaf.image.sae.storage.model.storagedocument;

import java.util.List;

/**
 * Classe concrète représentant la liste des documents en erreur
 * 
 * <li>
 * Attribut storageDocumentsOnError : La liste des documents qui ont causé une
 * erreur d'archivage</li>
 */
public class StorageDocumentsOnError {

   private List<StorageDocumentOnError> storageDocumentsOnError;

   /**
    * Retourne la liste des documents en erreur
    * 
    * @return La liste des documents en erreur
    */
   public final List<StorageDocumentOnError> getStorageDocumentsOnError() {
      return storageDocumentsOnError;
   }

   /**
    * Initialise la liste des documents en erreur
    * 
    * @param storageDocumentsOnError
    *           La liste des documents en erreur
    */
   public final void setStorageDocuments(
         List<StorageDocumentOnError> storageDocumentsOnError) {
      this.storageDocumentsOnError = storageDocumentsOnError;
   }

}
