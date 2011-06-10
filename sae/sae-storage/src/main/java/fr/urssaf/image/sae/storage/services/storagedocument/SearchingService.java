package fr.urssaf.image.sae.storage.services.storagedocument;

import fr.urssaf.image.sae.storage.exception.SearchingServiceRtEx;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocuments;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.LuceneCriteria;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;

/**
 * Fournit les services de recherche de document
 */
public interface SearchingService {

   /**
    * Permet de faire une recherche par une requête lucene
    * 
    * @param luceneCriteria
    *           La requête Lucene
    * 
    * @return Les résultats de la recherche
    * 
    * @throws SearchingServiceRtEx
    *            Une exception runtime
    */
   StorageDocuments searchStorageDocumentByLuceneCriteria(
         LuceneCriteria luceneCriteria) throws SearchingServiceRtEx;

   /**
    * Permet de faire une recherche par UUID de document
    * 
    * @param uuidCriteria
    *           L'UUID du document à rechercher
    * 
    * @return Le resultat de la recherche
    * 
    * @throws SearchingServiceRtEx
    *            Une esception runtime
    */
   StorageDocument searchStorageDocumentByUUIDCriteria(UUIDCriteria uuidCriteria)
         throws SearchingServiceRtEx;
}
