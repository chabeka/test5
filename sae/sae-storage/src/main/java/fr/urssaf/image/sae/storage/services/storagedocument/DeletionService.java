package fr.urssaf.image.sae.storage.services.storagedocument;

import fr.urssaf.image.sae.storage.exception.DeletionServiceEx;
import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;

/**
 * Fournit les services de suppression 
 * document
 */
public interface DeletionService {

   /**
    * Permet de supprimer un StorageDocument à partir du  critère «
    * UUIDCriteria ».
    * 
    * @param uuidCriteria :
    *           L'identifiant unique du document
    * 
    * 
    * 
    * @throws DeletionServiceEx
    *            Runtime exception
    */

   void deleteStorageDocument(final UUIDCriteria uuidCriteria)
         throws DeletionServiceEx;
   
   /**
    * Permet d'initialiser les paramètres dont le service aura besoin
    * 
    * @param storageConnectionParameter
	 *            : Les paramètres de connexion à la base de stockage
    */
   @SuppressWarnings("PMD.LongVariable")
   void setDeletionServiceParameter(final StorageConnectionParameter storageConnectionParameter);
   
}
