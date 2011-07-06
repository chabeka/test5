package fr.urssaf.image.sae.storage.model.storagedocument;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * Classe concrète représentant un document contenant un identifiant unique
 * suite à une insertion qui s’est bien déroulée.
 * 
 * <li>
 * Attribut uuid : L'uuid du document</li>
 */
public class StorageDocument extends AbstractStorageDocument {

   private UUID uuid;

   /**
    * Retourne l’identifiant unique universel
    * 
    * @return UUID du document
    */
   public final UUID getUuid() {
      return uuid;
   }

   /**
    * Initialise l’identifiant unique universel.
    * 
    * @param uuid :
    *           L'identifiant universel unique
    */
   public final void setUuid(final UUID uuid) {
      this.uuid = uuid;
   }

   /**
    * Constructeur
    * 
    * @param metadatas :
    *           Les metadatas du document
    * @param content :
    *           Le contenu du document
    * @param filePath :
    *           Le chemin du document
    * 
    */
   public StorageDocument(final List<StorageMetadata> metadatas,
         final byte[] content, final File filePath) {
      super(metadatas, content, filePath);
     
   }
   /**
    * Constructeur
    * 
    * @param metadatas :
    *           Les metadatas du document
    * @param content :
    *           Le contenu du document
    * @param filePath :
    *           Le chemin du document
    * @param uuid : l'uuid
    * 
    */
   public StorageDocument(final List<StorageMetadata> metadatas,
	         final byte[] content, final File filePath ,final UUID uuid) {
	      super(metadatas, content, filePath);
	      this.uuid = uuid;
	   }
   
}
