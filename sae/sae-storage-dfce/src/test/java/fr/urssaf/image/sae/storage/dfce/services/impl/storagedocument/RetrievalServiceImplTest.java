package fr.urssaf.image.sae.storage.dfce.services.impl.storagedocument;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.docubase.toolkit.service.ServiceProvider;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;

import fr.urssaf.image.sae.storage.dfce.data.model.SaeDocument;
import fr.urssaf.image.sae.storage.dfce.data.test.constants.Constants;
import fr.urssaf.image.sae.storage.dfce.data.utils.CheckDataUtils;
import fr.urssaf.image.sae.storage.dfce.mapping.BeanTestDocumentMapper;
import fr.urssaf.image.sae.storage.dfce.services.CommonServicesImpl;
import fr.urssaf.image.sae.storage.exception.DeletionServiceEx;
import fr.urssaf.image.sae.storage.exception.InsertionServiceEx;
import fr.urssaf.image.sae.storage.exception.RetrievalServiceEx;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;

/**
 * Classe de test pour les services de consultation.
 * 
 * @author Rhofir, Kenore.
 * 
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class RetrievalServiceImplTest extends CommonServicesImpl {

   /**
    * Test de consultation par UUID
    * 
    * <br>{@inheritDoc}
    */
   @Test
   public void retrieveStorageDocumentByUUID() throws RetrievalServiceEx,
         InsertionServiceEx, IOException, ParseException, DeletionServiceEx {
      // Initialisation des jeux de données UUID
      UUID uuid = getMockData(getInsertionService());
      UUIDCriteria uuidCriteria = new UUIDCriteria(uuid,
            new ArrayList<StorageMetadata>());
      Assert.assertNotNull("Récupération d'un StorageDocument par uuid :",
            getRetrievalService().retrieveStorageDocumentByUUID(uuidCriteria)
                  .getUuid());
      // Suppression du document insert
      destroyMockTest(uuid,getDeletionService());
   }

   /**
    * Test de récupération du contenue par UUID.
    * 
    * <br>{@inheritDoc}
    */
   @Test
   public void retrieveStorageDocumentContentByUUID()
         throws RetrievalServiceEx, IOException, InsertionServiceEx,
         ParseException, DeletionServiceEx, NoSuchAlgorithmException {
      // Injection de jeu de donnée.
      final SaeDocument saeDocument = getXmlDataService().saeDocumentReader(
            new File(Constants.XML_PATH_DOC_WITHOUT_ERROR[1]));
      StorageDocument storageDocument = BeanTestDocumentMapper
            .saeDocumentXmlToStorageDocument(saeDocument);
      UUID uuid = getInsertionService().insertStorageDocument(storageDocument);

      UUIDCriteria uuidCriteria = new UUIDCriteria(uuid,
            new ArrayList<StorageMetadata>());
      byte[] content = getRetrievalService()
            .retrieveStorageDocumentContentByUUID(uuidCriteria);
      Assert.assertNotNull(
            "Le contenue du document récupérer doit être non null", content);
      // Vérification que les deux Hash code document initial et document
      // récupérer sont identique
      Assert.assertTrue("Les deux SHA1 doivent être identique", DigestUtils
            .shaHex(storageDocument.getContent()).equals(
                  ServiceProvider.getSearchService().getDocumentByUUID(
                        getBase(), uuid).getDigest()));
      // Suppression du document insert
      destroyMockTest(uuid, getDeletionService());
   }

   /**
    * Test de récupération des Métadonnées par UUID.
    * 
    * <br>{@inheritDoc}
    */
   @Test
   public void retrieveStorageDocumentMetaDatasByUUID()
         throws RetrievalServiceEx, DeletionServiceEx, InsertionServiceEx,
         IOException, ParseException {
      // Injection de jeu de donnée.
      final SaeDocument saeDocument = getXmlDataService().saeDocumentReader(
            new File(Constants.XML_PATH_DOC_WITHOUT_ERROR[1]));
      StorageDocument storageDocument = BeanTestDocumentMapper
            .saeDocumentXmlToStorageDocument(saeDocument);
      UUID uuid = getInsertionService().insertStorageDocument(storageDocument);
      UUIDCriteria uuidCriteria = new UUIDCriteria(uuid,
            new ArrayList<StorageMetadata>());
      List<StorageMetadata> storageMetadatas = getRetrievalService()
            .retrieveStorageDocumentMetaDatasByUUID(uuidCriteria);

      Assert.assertNotNull(
            "La liste des Métadonnées récupérer doit être non null : ",
            storageMetadatas);
      // Vérification que les deux liste des métadonnées sont identique du
      // document initial et document récupérer
      Assert.assertTrue(
            "Les deux listes des métaData doivent être identique : ",
            CheckDataUtils.checkMetaDatas(storageDocument.getMetadatas(),
                  storageMetadatas));
      // Suppression du document insert
      destroyMockTest(uuid, getDeletionService());
   }
}
