package fr.urssaf.image.sae.storage.model.storagedocument;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Classe concrète représentant le résultat de l’insertion en masse<BR>
 * 
 * <li>
 * Attribut storageDocuments : la liste des documents archivés</li> <li>
 * Attribut storageDocumentsOnError : la liste des documents en erreur</li> <li>
 * Attribut uuids : La liste des uuids des documents archivés</li>
 */

public class BulkInsertionResults {

   private StorageDocuments storageDocuments;
   @SuppressWarnings("PMD.LongVariable")
   private StorageDocumentsOnError storageDocumentsOnError;

   private List<UUID> uuids;

   /**
    * Retourne la liste des documents archivés
    * 
    * @return Liste des documents archivés
    */
   public final StorageDocuments getStorageDocuments() {
      return storageDocuments;
   }

   /**
    * Retourne la liste des documents en erreur
    * 
    * @param storageDocuments
    *           : Liste des documents en erreur
    */
   public final void setStorageDocuments(final StorageDocuments storageDocuments) {
      this.storageDocuments = storageDocuments;
   }

   /**
    * Retourne la liste des documents en erreur
    * 
    * @return la liste des documents en erreur
    */
   public final StorageDocumentsOnError getStorageDocumentsOnError() {
      return storageDocumentsOnError;
   }

   /**
    * Initialise la liste des documents en erreur
    * 
    * @param storageDocumentsOnError
    *           : Liste des documents en erreur
    */
   @SuppressWarnings("PMD.LongVariable")
   public final void setStorageDocumentsOnError(
         final StorageDocumentsOnError storageDocumentsOnError) {
      this.storageDocumentsOnError = storageDocumentsOnError;
   }

   /**
    * Retourne la liste des UUID des documents dont l’insertion en masse s’est
    * bien déroulée
    * 
    * @return liste des UUIDs
    */
   public final List<UUID> getUuids() {
      List<UUID> listUuids = new ArrayList<UUID>();
      if (storageDocuments != null
            && (storageDocuments.getStorageDocuments() != null)) {
         // Ici on parcour tous les documents pour récuperer les uuids
         for (StorageDocument document : storageDocuments.getStorageDocuments()) {
            listUuids.add(document.getUuid());
         }
      }
      return uuids;
   }

   /**
    * Initialise la liste des UUID des documents dont l’insertion en masse s’est
    * bien déroulée
    * 
    * @param uuids
    *           : La liste des UUIDS
    */
   public final void setUuids(final List<UUID> uuids) {
      this.uuids = uuids;
   }

   /**
    * Constructeur
    * 
    * @param storageDocuments
    *           : Les documents archivés
    * @param storageDocumentsOnError
    *           : Les documents en erreur
    * @param uuids
    *           : Les UUIDs des documents archivés
    */
   @SuppressWarnings("PMD.LongVariable")
   public BulkInsertionResults(final StorageDocuments storageDocuments,
         final StorageDocumentsOnError storageDocumentsOnError,
         final List<UUID> uuids) {
      this.storageDocuments = storageDocuments;
      this.storageDocumentsOnError = storageDocumentsOnError;
      this.uuids = uuids;
   }

}
