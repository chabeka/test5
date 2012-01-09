package fr.urssaf.image.sae.integration.ihmweb.injection;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;
import fr.urssaf.image.sae.storage.exception.InsertionServiceEx;
import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;
import fr.urssaf.image.sae.storage.services.StorageServiceProvider;
import fr.urssaf.image.sae.storage.services.storagedocument.StorageDocumentService;


/**
 * Service pour injecter des documents dans DFCE via la surcouche CIRTIL à l'API DFCE
 */
@Service
public final class InjectionService {

   
   private final StorageDocumentService storageDocumentService;
   
   
   /**
    * Constructeur
    * 
    * @param provider provider des services de stockage
    * @param connectionParameter paramètres de connexion à DFCE
    * @throws ConnectionServiceEx en cas d'erreur de connexion à DFCE 
    */
   @Autowired
   public InjectionService(
         StorageServiceProvider provider,
         StorageConnectionParameter connectionParameter) throws ConnectionServiceEx {
      
//      provider.setStorageServiceProviderParameter(connectionParameter);
//
//      provider.getStorageConnectionService().openConnection();
//
//      storageDocumentService = provider.getStorageDocumentService();
      
      
      provider.openConnexion();
      
      storageDocumentService = provider.getStorageDocumentService() ;

      
      
   }
   
   
   /**
    * Injecte un document dans DFCE (sans aucun contrôle)
    * 
    * @param contenu le contenu du document
    * @param metadonnees les métadonnées
    * @return l'UUID du document injecté
    * @throws InsertionServiceEx en cas de problème lors du stockage
    */
//   public UUID injecteDocument(
//         byte[] contenu,
//         List<StorageMetadata> metadonnees) throws InsertionServiceEx {
//      
//      StorageDocument document = new StorageDocument();
//      
//      document.setContent(contenu);
//      
//      document.setMetadatas(metadonnees);
//      
//      StorageDocument storageDocument = storageDocumentService.insertStorageDocument(document);
//      
//      UUID uuid = storageDocument.getUuid(); 
//      
//      return uuid;
//      
//   }
   
   
   
   /**
    * Injecte un document dans DFCE (sans aucun contrôle)
    * 
    * @param contenu le contenu du document
    * @param metadonnees les métadonnées
    * @return l'UUID du document injecté
    * @throws InsertionServiceEx en cas de problème lors du stockage
    */
   public UUID injecteDocument(
         String cheminCompletFichier,
         List<StorageMetadata> metadonnees) throws InsertionServiceEx {
      
      StorageDocument document = new StorageDocument();
      
      // document.setContent(contenu);
      document.setFilePath(cheminCompletFichier);
      
      document.setMetadatas(metadonnees);
      
      StorageDocument storageDocument = storageDocumentService.insertStorageDocument(document);
      
      UUID uuid = storageDocument.getUuid(); 
      
      return uuid;
      
   }
   
}
