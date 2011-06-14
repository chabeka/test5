package fr.urssaf.image.sae.storage.services.storagedocument;

import java.util.List;
import java.util.UUID;

import fr.urssaf.image.sae.storage.exception.InsertionServiceRtEx;
import fr.urssaf.image.sae.storage.model.storagedocument.BulkInsertionResults;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;

/**
 * Fournit les services d’insertion de document
 * 
 */
public interface InsertionService {
   // CHECKSTYLE:OFF
   /**
    * Permet d'insérer un document unique
    * 
    * @param strorageDocument
    *           : Le document à stocker
    * 
    * @return L'identifiant unique du document
    * 
    * @throws InsertionServiceRtEx
    *            Runtime exception typée
    */
   UUID insertStorageDocument(StorageDocument strorageDocument)
         throws InsertionServiceRtEx;

   /**
    * Permet de réaliser une insertion en masse de documents
    * 
    * @param storageDocuments
    *           : Les documents à stocker
    * @return Le resultat des insertions réussies et échouées
    */
   BulkInsertionResults bulkInsertStorageDocument(
         List<StorageDocument> storageDocuments);
   
}
