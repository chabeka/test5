package fr.urssaf.image.sae.services.controles.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.bo.model.MetadataError;
import fr.urssaf.image.sae.bo.model.bo.SAEDocument;
import fr.urssaf.image.sae.bo.model.untyped.UntypedDocument;
import fr.urssaf.image.sae.metadata.control.services.MetadataControlServices;
import fr.urssaf.image.sae.services.controles.SAEControlesCaptureService;
import fr.urssaf.image.sae.services.enrichment.dao.impl.SAEMetatadaFinderUtils;
import fr.urssaf.image.sae.services.enrichment.xml.model.SAEArchivalMetadatas;
import fr.urssaf.image.sae.services.exception.capture.DuplicatedMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.EmptyDocumentEx;
import fr.urssaf.image.sae.services.exception.capture.InvalidValueTypeAndFormatMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.NotSpecifiableMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.RequiredArchivableMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.RequiredStorageMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.SAECaptureServiceRuntimeException;
import fr.urssaf.image.sae.services.exception.capture.UnknownHashCodeEx;
import fr.urssaf.image.sae.services.exception.capture.UnknownMetadataEx;
import fr.urssaf.image.sae.services.util.FormatUtils;
import fr.urssaf.image.sae.services.util.ResourceMessagesUtils;
import fr.urssaf.image.sae.storage.dfce.utils.Utils;

/**
 * Classe de contrôle des métadonnées.
 * 
 * @author rhofir.
 */
@Service
@Qualifier("saeControlesCaptureService")
public class SAEControlesCaptureServiceImpl implements
      SAEControlesCaptureService {
   private static final Logger LOGGER = LoggerFactory
         .getLogger(SAEControlesCaptureServiceImpl.class);
   @Autowired
   @Qualifier("metadataControlServices")
   private MetadataControlServices metadataCS;

   /**
    * {@inheritDoc}
    */
   public final void checkSaeMetadataForCapture(SAEDocument saeDocument)
         throws NotSpecifiableMetadataEx, RequiredArchivableMetadataEx {
      // Traces debug - entrée méthode
      String prefixeTrc = "checkSaeMetadataForCapture()";
      LOGGER.debug("{} - Début", prefixeTrc);
      // Fin des traces debug - entrée méthode
      List<MetadataError> errorsList = metadataCS
            .checkArchivableMetadata(saeDocument);
      String listeCodeLong = null;
      LOGGER
            .debug(
                  "{} - Début de la vérification : "
                        + "Les métadonnées fournies par l'application cliente sont spécifiables à l'archivage",
                  prefixeTrc);
      if (CollectionUtils.isNotEmpty(errorsList)) {
         listeCodeLong = buildLongCodeError(errorsList);
         LOGGER.debug("{} - {}", prefixeTrc, ResourceMessagesUtils
               .loadMessage("capture.metadonnees.interdites",
                     buildLongCodeError(errorsList)));
         throw new NotSpecifiableMetadataEx(ResourceMessagesUtils
               .loadMessage("capture.metadonnees.interdites",
                     buildLongCodeError(errorsList)));
      }
      LOGGER
            .debug(
                  "{} - Fin de la vérification : "
                        + "Les métadonnées fournies par l'application cliente sont spécifiables à l'archivage",
                  prefixeTrc);

      errorsList = metadataCS.checkRequiredForArchivalMetadata(saeDocument);
      LOGGER
            .debug(
                  "{} - Début de la vérification : Les métadonnées obligatoires à l'archivage sont renseignées",
                  prefixeTrc);
      if (CollectionUtils.isNotEmpty(errorsList)) {
         listeCodeLong = buildLongCodeError(errorsList);
         LOGGER.debug("{} - {}", prefixeTrc, ResourceMessagesUtils.loadMessage(
               "capture.metadonnees.archivage.obligatoire", listeCodeLong));
         throw new RequiredArchivableMetadataEx(ResourceMessagesUtils
               .loadMessage("capture.metadonnees.archivage.obligatoire",
                     listeCodeLong));
      }
      LOGGER
            .debug(
                  "{} - Fin de la vérification : Les métadonnées obligatoires à l'archivage sont renseignées",
                  prefixeTrc);
      // Traces debug - sortie méthode
      LOGGER.debug("{} - Sortie", prefixeTrc);
      // Fin des traces debug - sortie méthode
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final void checkSaeMetadataForStorage(SAEDocument sAEDocument)
         throws RequiredStorageMetadataEx {
      // Traces debug - entrée méthode
      String prefixeTrc = "checkSaeMetadataForCapture()";
      LOGGER.debug("{} - Début", prefixeTrc);
      // Fin des traces debug - entrée méthode
      String listeCodeLong = null;
      LOGGER.debug("{} - Début de la vérification : "
            + "Les métadonnées obligatoires lors du stockage sont présentes",
            prefixeTrc);
      List<MetadataError> errorsList = metadataCS
            .checkRequiredForStorageMetadata(sAEDocument);
      if (CollectionUtils.isNotEmpty(errorsList)) {
         listeCodeLong = buildLongCodeError(errorsList);
         LOGGER.debug("{} - {}", prefixeTrc, ResourceMessagesUtils.loadMessage(
               "capture.metadonnees.stockage.obligatoire", listeCodeLong));
         throw new RequiredStorageMetadataEx(ResourceMessagesUtils
               .loadMessage("erreur.technique.capture.unitaire"));
      }
      LOGGER.debug("{} - Fin de la vérification : "
            + "Les métadonnées obligatoire lors du stockage sont présentes",
            prefixeTrc);
      // Traces debug - sortie méthode
      LOGGER.debug("{} - Sortie", prefixeTrc);
      // Fin des traces debug - sortie méthode
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final void checkHashCodeMetadataForStorage(SAEDocument saeDocument)
         throws UnknownHashCodeEx {
      // Traces debug - entrée méthode
      String prefixeTrc = "checkHashCodeMetadataForStorage()";
      LOGGER.debug("{} - Début", prefixeTrc);
      // Fin des traces debug - entrée méthode
      String hashCodeValue = SAEMetatadaFinderUtils.codeMetadataFinder(
            saeDocument.getMetadatas(), SAEArchivalMetadatas.HASH_CODE
                  .getLongCode());
      LOGGER.debug("{} - Hash du document à archiver: {}", prefixeTrc,
            hashCodeValue);
      String algoHashCode = SAEMetatadaFinderUtils.codeMetadataFinder(
            saeDocument.getMetadatas(), SAEArchivalMetadatas.TYPE_HASH
                  .getLongCode());
      LOGGER.debug("{} - Algorithme du document à archiver: {}", prefixeTrc,
            algoHashCode);
      // FIXME vérifier que l'algorithme passer fait partie d'une liste
      // pré-définit.
      File docFile = new File(saeDocument.getFilePath());
      LOGGER.debug("{} - Début de la vérification : Le type de hash est SHA-1",
            prefixeTrc);
      if (!"SHA-1".equals(algoHashCode)) {
         LOGGER
               .debug(
                     "{} - L'algorithme du document à archiver est différent de SHA-1",
                     prefixeTrc);
         throw new UnknownHashCodeEx(ResourceMessagesUtils.loadMessage(
               "capture.hash.erreur", docFile.getName()));
      }
      LOGGER.debug("{} - Fin de la vérification : "
            + "Le type de hash est SHA-1", prefixeTrc);
      // FIXME à partie de l'algorithme calculer le hashCode.
      LOGGER
            .debug(
                  "{} - Début de la vérification : "
                        + "Equivalence entre le hash fourni en métadonnée et le hash recalculé à partir du fichier",
                  prefixeTrc);
      try {
         if (!DigestUtils.shaHex(FileUtils.readFileToByteArray(docFile))
               .equals(hashCodeValue.trim())) {
            LOGGER
                  .debug(
                        "{} - Hash du document {} est différent que celui recalculé {}",
                        new Object[] {
                              prefixeTrc,
                              hashCodeValue,
                              DigestUtils.shaHex(FileUtils
                                    .readFileToByteArray(docFile)) });
            throw new UnknownHashCodeEx(ResourceMessagesUtils.loadMessage(
                  "capture.hash.erreur", docFile.getName()));
         }
         LOGGER
               .debug(
                     "{} - Fin de la vérification : "
                           + "Equivalence entre le hash fourni en métadonnée et le hash recalculé à partir du fichier",
                     prefixeTrc);
      } catch (IOException e) {
         throw new SAECaptureServiceRuntimeException(e);
      }
      // Traces debug - sortie méthode
      LOGGER.debug("{} - Sortie", prefixeTrc);
      // Fin des traces debug - sortie méthode
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final void checkUntypedDocument(UntypedDocument untypedDocument)
         throws EmptyDocumentEx {
      // Traces debug - entrée méthode
      String prefixeTrc = "checkUntypedDocument()";
      LOGGER.debug("{} - Début", prefixeTrc);
      // Fin des traces debug - entrée méthode
      File docFile = new File(untypedDocument.getFilePath());
      LOGGER
            .debug(
                  "{} - Début de la vérification : "
                        + "La taille du document fournie par l'application cliente est supérieure à 0 octet",
                  prefixeTrc);
      if (docFile.exists()) {
         if (docFile.length() == 0) {
            LOGGER.debug("{} - {}", prefixeTrc, ResourceMessagesUtils
                  .loadMessage("capture.fichier.vide", docFile.getName()));
            throw new EmptyDocumentEx(ResourceMessagesUtils.loadMessage(
                  "capture.fichier.vide", docFile.getName()));
         }
      }
      LOGGER
            .debug(
                  "{} - Fin de la vérification : "
                        + "La taille du document fournie par l'application cliente est supérieure à 0 octet",
                  prefixeTrc);
      // Traces debug - sortie méthode
      LOGGER.debug("{} - Sortie", prefixeTrc);
      // Fin des traces debug - sortie méthode
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final void checkUntypedMetadata(UntypedDocument untypedDocument)
         throws UnknownMetadataEx, DuplicatedMetadataEx,
         InvalidValueTypeAndFormatMetadataEx, RequiredArchivableMetadataEx {
      // Traces debug - entrée méthode
      String prefixeTrc = "checkUntypedDocument()";
      LOGGER.debug("{} - Début", prefixeTrc);
      // Fin des traces debug - entrée méthode
      String listeCodeLong = null;
      LOGGER
            .debug(
                  "{} - Début de la vérification :"
                        + " Les métadonnées fournies par l'application cliente existent dans le référentiel des métadonnées",
                  prefixeTrc);
      List<MetadataError> errorsList = metadataCS
            .checkExistingMetadata(untypedDocument);
      if (CollectionUtils.isNotEmpty(errorsList)) {
         listeCodeLong = buildLongCodeError(errorsList);
         LOGGER.debug("{} - {}", prefixeTrc, ResourceMessagesUtils.loadMessage(
               "capture.metadonnees.inconnu", listeCodeLong));
         throw new UnknownMetadataEx(ResourceMessagesUtils.loadMessage(
               "capture.metadonnees.inconnu", listeCodeLong));
      }
      LOGGER
            .debug(
                  "{} - Fin de la vérification : "
                        + "Les métadonnées fournies par l'application cliente existent dans le référentiel des métadonnées",
                  prefixeTrc);
      LOGGER
            .debug(
                  "{} - Début de la vérification : Les métadonnées ne sont pas multi-valuées",
                  prefixeTrc);
      errorsList = metadataCS.checkDuplicateMetadata(untypedDocument
            .getUMetadatas());
      if (CollectionUtils.isNotEmpty(errorsList)) {
         listeCodeLong = buildLongCodeError(errorsList);
         LOGGER.debug("{} - {}", prefixeTrc, ResourceMessagesUtils.loadMessage(
               "capture.metadonnees.doublon", listeCodeLong));
         throw new DuplicatedMetadataEx(ResourceMessagesUtils.loadMessage(
               "capture.metadonnees.doublon", listeCodeLong));
      }
      LOGGER
            .debug(
                  "{} - Fin de la vérification : Les métadonnées ne sont pas multi-valuées",
                  prefixeTrc);
      LOGGER
            .debug(
                  "{} - Début de la vérification : Les métadonnées obligatoires à l'archivage sont renseignées",
                  prefixeTrc);
      errorsList = metadataCS.checkMetadataRequiredValue(untypedDocument);
      if (CollectionUtils.isNotEmpty(errorsList)) {
         listeCodeLong = buildLongCodeError(errorsList);
         LOGGER.debug("{} - {}", prefixeTrc, ResourceMessagesUtils.loadMessage(
               "capture.metadonnees.archivage.obligatoire", listeCodeLong));
         throw new RequiredArchivableMetadataEx(ResourceMessagesUtils
               .loadMessage("capture.metadonnees.archivage.obligatoire",
                     listeCodeLong));
      }
      LOGGER
            .debug(
                  "{} - Fin de la vérification : Les métadonnées obligatoires à l'archivage sont renseignées",
                  prefixeTrc);
      LOGGER
            .debug(
                  "{} - Début de la vérification : Les métadonnées respectent leurs contraintes de format",
                  prefixeTrc);
      errorsList = metadataCS.checkMetadataValueTypeAndFormat(untypedDocument);
      if (CollectionUtils.isNotEmpty(errorsList)) {
         listeCodeLong = buildLongCodeError(errorsList);
         LOGGER.debug("{} - {}", prefixeTrc, ResourceMessagesUtils.loadMessage(
               "capture.metadonnees.format.type.non.valide", listeCodeLong));
         throw new InvalidValueTypeAndFormatMetadataEx(ResourceMessagesUtils
               .loadMessage("capture.metadonnees.format.type.non.valide",
                     listeCodeLong));
      }
      LOGGER
            .debug(
                  "{} - Fin de la vérification : Les métadonnées respectent leurs contraintes de format",
                  prefixeTrc);
      // Traces debug - sortie méthode
      LOGGER.debug("{} - Sortie", prefixeTrc);
      // Fin des traces debug - sortie méthode
   }

   /**
    * Construire une list de code long.
    * 
    * @param errorsList
    *           : Liste de de type {@link MetadataError}
    * @return Liste de code long à partir d'une liste de de type
    *         {@link MetadataError}
    */
   private String buildLongCodeError(List<MetadataError> errorsList) {
      List<String> codeLongErrors = new ArrayList<String>();
      for (MetadataError metadataError : Utils.nullSafeIterable(errorsList)) {
         codeLongErrors.add(metadataError.getLongCode());
      }

      return FormatUtils.formattingDisplayList(codeLongErrors);
   }
}
