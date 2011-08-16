package fr.urssaf.image.sae.webservices.impl.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fr.cirtil.www.saeservice.ArchivageUnitaire;
import fr.cirtil.www.saeservice.Consultation;
import fr.cirtil.www.saeservice.Recherche;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.LuceneCriteria;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;

/**
 * Classe d'instanciation du modèle du package
 * {@link fr.urssaf.image.sae.storage.model}
 * 
 * 
 */
public final class ObjectStorageFactory {

   private ObjectStorageFactory() {

   }

   /**
    * instanciation d'une la classe {@link StorageDocument} à partir d'une
    * instance de {@link ArchivageUnitaire}
    * <ul>
    * <li><code>metadatas</code>:</li>
    * <li><code>filePath</code>:</li>
    * <li><code>content</code>:</li>
    * <li><code>uuid</code>:</li>
    * </ul>
    * 
    * @param request
    *           instance de {@link ArchivageUnitaire}
    * @return instance de {@link StorageDocument}
    */
   public static StorageDocument createStorageDocument(ArchivageUnitaire request) {

      // TODO convertir ArchivageUnitaire en StorageDocument
      List<StorageMetadata> metadatas = new ArrayList<StorageMetadata>();
      // File filePath = new File("");
      byte[] content = "content".getBytes();
      UUID uuid = UUID.fromString("110E8400-E29B-11D4-A716-446655440000");

      StorageDocument storageDocument = new StorageDocument(metadatas, content,
            uuid);

      return storageDocument;
   }

   /**
    * instanciation de la classe {@link UUIDCriteria} à partir d'une instance de
    * {@link Consultation}
    * 
    * @param request
    *           instance de {@link Consultation}
    * @return instance de {@link UUIDCriteria}
    */
   public static UUIDCriteria createUUIDCriteria(Consultation request) {

      // TODO convertir UUIDCriteria en Consultation
      UUID uuid = UUID.fromString("110E8400-E29B-11D4-A716-446655440000");
      List<StorageMetadata> metadatas = new ArrayList<StorageMetadata>();

      UUIDCriteria criteria = new UUIDCriteria(uuid, metadatas);

      return criteria;
   }

   /**
    * instanciation de la classe {@link LuceneCriteria} à partir d'une instance
    * de {@link Recherche}
    * 
    * @param request
    *           instance de {@link Recherche}
    * @return instance de {@link LuceneCriteria}
    */
   public static LuceneCriteria createLuceneCriteria(Recherche request) {

      // TODO convertir UUIDCriteria en Recherche
      String luceneQuery = "lucene";
      int limit = 0;
      List<StorageMetadata> metadatas = new ArrayList<StorageMetadata>();

      LuceneCriteria criteria = new LuceneCriteria(luceneQuery, limit,
            metadatas);

      return criteria;
   }

}
