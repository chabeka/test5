package fr.urssaf.image.sae.services.capture.impl;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import fr.urssaf.image.sae.bo.model.untyped.UntypedDocument;
import fr.urssaf.image.sae.building.services.BuildService;
import fr.urssaf.image.sae.ecde.exception.EcdeBadURLException;
import fr.urssaf.image.sae.ecde.exception.EcdeBadURLFormatException;
import fr.urssaf.image.sae.ecde.modele.source.EcdeSource;
import fr.urssaf.image.sae.ecde.service.EcdeFileService;
import fr.urssaf.image.sae.mapping.exception.InvalidSAETypeException;
import fr.urssaf.image.sae.mapping.exception.MappingFromReferentialException;
import fr.urssaf.image.sae.services.capture.SAECaptureService;
import fr.urssaf.image.sae.services.document.commons.SAECommonCaptureService;
import fr.urssaf.image.sae.services.exception.capture.DuplicatedMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.EmptyDocumentEx;
import fr.urssaf.image.sae.services.exception.capture.InvalidValueTypeAndFormatMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.NotArchivableMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.NotSpecifiableMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.RequiredArchivableMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.RequiredStorageMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.SAECaptureServiceEx;
import fr.urssaf.image.sae.services.exception.capture.UnknownHashCodeEx;
import fr.urssaf.image.sae.services.exception.capture.UnknownMetadataEx;
import fr.urssaf.image.sae.services.exception.enrichment.SAEEnrichmentEx;
import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;
import fr.urssaf.image.sae.storage.exception.InsertionServiceEx;
import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.services.StorageServiceProvider;

/**
 * Implémentation du service {@link SAECaptureService}
 * 
 */
@Service
public class SAECaptureServiceImpl implements SAECaptureService {

   private final StorageServiceProvider serviceProvider;

   private final StorageConnectionParameter connectionParam;

   private final EcdeFileService ecdeFileService;

   private final BuildService buildService;

   private final SAECommonCaptureService commonsService;

   /**
    * initialisation des différents services du SAE nécessaire à la capture
    * 
    * @param serviceProvider
    *           façade des services DFCE
    * @param connectionParam
    *           configuration de la connexion à DFCE
    * @param ecdeFileService
    *           service de l'ECDE
    * @param buildService
    *           service d'instanciation des BO
    * @param commonsService
    *           service commun de la capture
    */
   @Autowired
   public SAECaptureServiceImpl(
         @Qualifier("storageServiceProvider") StorageServiceProvider serviceProvider,
         @Qualifier("storageConnectionParameter") StorageConnectionParameter connectionParam,
         EcdeFileService ecdeFileService, BuildService buildService,
         SAECommonCaptureService commonsService) {

      Assert.notNull(serviceProvider);
      Assert.notNull(connectionParam);
      Assert.notNull(ecdeFileService);
      Assert.notNull(buildService);
      Assert.notNull(commonsService);

      this.ecdeFileService = ecdeFileService;
      this.serviceProvider = serviceProvider;
      this.connectionParam = connectionParam;

      this.buildService = buildService;
      this.commonsService = commonsService;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final UUID capture(Map<String, String> metadatas, URI ecdeURL)
         throws SAECaptureServiceEx, RequiredStorageMetadataEx,
         InvalidValueTypeAndFormatMetadataEx, UnknownMetadataEx,
         DuplicatedMetadataEx, NotSpecifiableMetadataEx, EmptyDocumentEx,
         RequiredArchivableMetadataEx, NotArchivableMetadataEx {

      // chargement du document de l'ECDE
      File ecdeFile = loadEcdeFile(ecdeURL);

      // instanciation d'un UntypedDocument
      UntypedDocument untypedDocument = createUntypedDocument(metadatas,
            ecdeFile);

      // appel du service commun d'archivage dans la capture unitaire

      StorageDocument storageDoc;
      try {
         storageDoc = commonsService
               .buildStorageDocumentForCapture(untypedDocument);
      } catch (SAEEnrichmentEx e) {
         throw new SAECaptureServiceEx(e);
      } catch (MappingFromReferentialException e) {
         throw new SAECaptureServiceEx(e);
      } catch (InvalidSAETypeException e) {
         throw new SAECaptureServiceEx(e);
      } catch (UnknownHashCodeEx e) {
         throw new SAECaptureServiceEx(e);
      }

      // le type du document est obligatoire dans l'archivage DFCE
      String typeDoc = FilenameUtils.getExtension(ecdeFile.getName());
      storageDoc.setTypeDoc(typeDoc);

      // archivage du document dans DFCE
      UUID uuid = insererStorageDocument(storageDoc);

      return uuid;

   }

   private UntypedDocument createUntypedDocument(Map<String, String> metadatas,
         File ecdeFile) throws SAECaptureServiceEx {

      // TODO vérification que le fichier extrait de l'url ECDE existe bien!

      // conversion du fichier extrait de l'url ECDE en bytes[]
      byte[] fileContent;
      try {
         fileContent = FileUtils.readFileToByteArray(ecdeFile);
      } catch (IOException e) {
         throw new SAECaptureServiceEx(e);
      }

      // instanciation de la classe UntypedDocument avec la liste des
      // métadonnées et le contenu du document à archiver
      UntypedDocument untypedDocument = buildService.buildUntypedDocument(
            fileContent, metadatas);

      return untypedDocument;
   }

   private File loadEcdeFile(URI ecdeURL) throws SAECaptureServiceEx {

      // CODE TEMPORAIRE
      // vérification que ecdeURL respecte bien le format d'une URL ECDE
      // par défaut un seul point de montage de l'ECDE se fait sur le répertoire
      // temporaire de l'OS dans le sous répertoire 'ecde', le DNS de l'ECDE est
      // ecde.cer69.recouv

      File basePath = new File(FilenameUtils.concat(SystemUtils
            .getJavaIoTmpDir().getAbsolutePath(), "ecde"));
      EcdeSource source = new EcdeSource("ecde.cer69.recouv", basePath);

      try {
         return ecdeFileService.convertURIToFile(ecdeURL, source);
      } catch (EcdeBadURLException e) {
         throw new SAECaptureServiceEx(e);
      } catch (EcdeBadURLFormatException e) {
         throw new SAECaptureServiceEx(e);
      }

   }

   private UUID insererStorageDocument(StorageDocument storageDoc)
         throws SAECaptureServiceEx {

      // ouverture de la connexion DFCE
      serviceProvider.setStorageServiceProviderParameter(connectionParam);
      try {
         serviceProvider.getStorageConnectionService().openConnection();
      } catch (ConnectionServiceEx e) {
         throw new SAECaptureServiceEx(e);
      }

      // insertion du document à archiver dans DFCE puis fermeture de la
      // connexion DFCE
      UUID uuid;
      try {
         uuid = serviceProvider.getStorageDocumentService()
               .insertStorageDocument(storageDoc).getUuid();
      } catch (InsertionServiceEx e) {
         throw new SAECaptureServiceEx(e);
      } finally {
         serviceProvider.getStorageConnectionService().closeConnexion();
      }

      return uuid;
   }
}
