package fr.urssaf.image.sae.storage.model.storagedocument;

import java.io.File;
import java.util.List;

/**
 * Classe concrète représentant un document contenant un code erreur suite à une
 * insertion qui s’est mal déroulée <li>
 * Attribut codeError : Le message d'erreur retourné par l'archivage du document
 * </li>
 */
public class StorageDocumentOnError extends AbstractStorageDocument {

   private String codeError;

   /**
    * Retourne le code erreur
    * 
    * @return Le code erreur
    */
   public final String getCodeError() {
      return codeError;
   }

   /**
    * Initialise le code erreur
    * 
    * @param codeError
    *           Le code erreur
    */
   public final void setCodeError(final String codeError) {
      this.codeError = codeError;
   }

   /**
    * Constructeur
    * 
    * @param metadatas
    *           Les metadatas du document
    * @param content
    *           Le contenu du document
    * @param filePath
    *           Le chemin du document
    * @param codeError
    *           Le code Erreur
    */
   public StorageDocumentOnError(final List<StorageMetadata> metadatas,
         final Byte[] content, final File filePath, final String codeError) {
      super(metadatas, content, filePath);
      this.codeError = codeError;
   }

}
