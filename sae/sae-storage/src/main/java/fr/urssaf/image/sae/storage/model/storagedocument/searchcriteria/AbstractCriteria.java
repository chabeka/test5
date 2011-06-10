package fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria;

import java.util.List;

import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;

/**
 * Classe abstraite contenant les attributs communs des différents critères de
 * recherche et de récupération
 * 
 * <li>
 * Attribut desiredStorageMetadatas : Représente la liste des métadonnées
 * souhaitées dans le cadre d’une recherche ou d’une récupération d’un document.
 * </li>
 */
public abstract class AbstractCriteria {

   private List<StorageMetadata> desiredStorageMetadatas;

   /**
    * Retourne la liste des métadonnées
    * 
    * @return La liste des métadonnées
    */
   public final List<StorageMetadata> getDesiredStorageMetadatas() {
      return desiredStorageMetadatas;
   }

   /**
    * Initialise la liste des métadonnées
    * 
    * @param desiredStorageMetadatas
    *           La liste des métadonnées
    */
   public final void setDesiredStorageMetadatas(
         List<StorageMetadata> desiredStorageMetadatas) {
      this.desiredStorageMetadatas = desiredStorageMetadatas;
   }

   /**
    * Constructeur
    * 
    * @param desiredStorageMetadatas
    *           La liste des métadonnées souhaitée pour la recherche ou la
    *           récupération d'un document
    */
   public AbstractCriteria(List<StorageMetadata> desiredStorageMetadatas) {
      this.desiredStorageMetadatas = desiredStorageMetadatas;
   }
}
