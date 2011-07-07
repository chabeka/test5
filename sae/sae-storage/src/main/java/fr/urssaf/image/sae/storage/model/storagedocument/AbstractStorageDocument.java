package fr.urssaf.image.sae.storage.model.storagedocument;

import java.io.File;
import java.util.List;

/**
 * Classe abstraite contenant les attributs communs des différents types de
 * documents destinés au stockage</BR>
 * 
 * <li>
 * Attribut metadatas : Liste des métadatas</li> <li>
 * Attribut content : Le contenu du document</li> <li>
 * Attribut filePath : Le chemin du document</li>
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class AbstractStorageDocument {

   private List<StorageMetadata> metadatas;

   private byte[] content;

   private File filePath;

   /**
    * Retourne la liste des métadonnées.
    * 
    * @return Liste des métadonnées
    */
   public final List<StorageMetadata> getMetadatas() {
      return metadatas;
   }

   /**
    * Initialise la liste des métadonnées.
    * 
    * @param metadatas :
    *           La liste des métadonnées
    */
   public final void setMetadatas(final List<StorageMetadata> metadatas) {
      this.metadatas = metadatas;
   }

   /**
    * Retourne le contenu du document
    * 
    * @return Le contenu du document
    */
   @SuppressWarnings("PMD.MethodReturnsInternalArray")
   public final byte[] getContent() {
      return content;
   }

   /**
    * Initialise le contenu du document
    * 
    * @param content :
    *           Le contenu du document
    */
   @SuppressWarnings("PMD.ArrayIsStoredDirectly")
   public final void setContent(final byte[] content) {
      this.content = content;
   }

   /**
    * Retourne le chemin du fichier
    * 
    * @return Le chemin du fichier
    */
   public final File getFilePath() {
      return filePath;
   }

   /**
    * Initialise le chemin du fichier
    * 
    * @param filePath :
    *           Le chemin du document
    */
   public final void setFilePath(final File filePath) {
      this.filePath = filePath;
   }

   /**
    * Constructeur
    * 
    * @param metadatas : 
    *           Les metadatas du document
    * @param content : 
    *           Le contenu du document
    * @param filePath :
    *           Le chemin du fichier
    */
   @SuppressWarnings("PMD.ArrayIsStoredDirectly")
   public AbstractStorageDocument(final List<StorageMetadata> metadatas,
         final byte[] content, final File filePath) {
      this.metadatas = metadatas;
      this.content = content;
      this.filePath = filePath;
   }

}