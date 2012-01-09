package fr.urssaf.image.sae.integration.ihmweb.injection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.integration.ihmweb.service.referentiels.ReferentielMetadonneesService;
import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;
import fr.urssaf.image.sae.storage.exception.InsertionServiceEx;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;
import fr.urssaf.image.sae.storage.services.StorageServiceProvider;
import fr.urssaf.image.sae.storage.services.storagedocument.StorageDocumentService;


/**
 * Test de la moulinette d'injection
 */
@SuppressWarnings("PMD")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-sae-integration-ihmweb-injection.xml")
// @Ignore("Ce ne sont pas des TU, mais des mécanismes d'injection de données dans DFCE")
public class CoucheStorageTest {

   
   @Autowired
   private StorageServiceProvider provider;
   
   
   @Autowired
   private ReferentielMetadonneesService refMetaService;
   
   
   @Autowired
   private InjectionTools injectionTools;
   
  
   @Test
   public void injecteUnDocument_UniquementMetadonneesObligatoireArchivageEtStockage() 
      throws IOException, ConnectionServiceEx, InsertionServiceEx {
      
      // Contenu
      byte[] contenu = FileUtils.readFileToByteArray(
            new File("src/test/resources/doc/attestation_consultation.pdf"));
      
      // Métadonnées
      List<StorageMetadata> metadonnees = new ArrayList<StorageMetadata>();
      
      // Obligatoire à l'archivage
      
      metadonnees.add(new StorageMetadata(
            refMetaService.getCodeCourt("Titre"),
            "Attestation de vigilance"));
      
      metadonnees.add(new StorageMetadata(
            refMetaService.getCodeCourt("DateCreation"),
            InjectionTools.buildDate(2011,10,01))); 
      
      metadonnees.add(new StorageMetadata(
            refMetaService.getCodeCourt("ApplicationProductrice"),
            "ADELAIDE")); 
      
      metadonnees.add(new StorageMetadata(
            refMetaService.getCodeCourt("CodeOrganismeProprietaire"),
            "CER69")); 
      
      metadonnees.add(new StorageMetadata(
            refMetaService.getCodeCourt("CodeOrganismeGestionnaire"),
            "UR750")); 
      
      metadonnees.add(new StorageMetadata(
            refMetaService.getCodeCourt("CodeRND"),
            "2.3.1.1.12"));
      
      // Hash => gestion auto DFCE à la capture 
//      metadonnees.add(new StorageMetadata(
//            refMetaService.getCodeCourt("Hash"),
//            )); 
      
      // TypeHash => gestion auto DFCE à la capture
//      metadonnees.add(new StorageMetadata(
//            refMetaService.getCodeCourt("TypeHash"),
//            )); 
      
      metadonnees.add(new StorageMetadata(
            refMetaService.getCodeCourt("NbPages"),
            2)); 
      
      metadonnees.add(new StorageMetadata(
            refMetaService.getCodeCourt("FormatFichier"),
            "fmt/354"));
      
      
      
      // Ajout des métadonnées obligatoire au stockage
      injectionTools.ajouteMetadonneesObligatoireAuStockage(metadonnees,null);
      
      // Un log
      System.out.println(InjectionTools.metadonneesPourAffichage(metadonnees));
      
      // Préparation du StorageDocument
      StorageDocument document = new StorageDocument();
      document.setContent(contenu);
      document.setMetadatas(metadonnees);
      
      // Ouverture de la connexion à DFCE
      provider.openConnexion();
      
      // Appel au service de stockage
      StorageDocumentService storageDocumentService = provider.getStorageDocumentService() ;
      StorageDocument storageDocument = storageDocumentService.insertStorageDocument(document);
      UUID uuid = storageDocument.getUuid(); 
      
      // Pour les tests
      System.out.println(uuid);
      
   }
   
   
   
   
}
