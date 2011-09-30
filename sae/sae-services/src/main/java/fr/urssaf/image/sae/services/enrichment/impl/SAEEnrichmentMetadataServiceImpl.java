package fr.urssaf.image.sae.services.enrichment.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.cassandra.thrift.Cassandra.system_add_column_family_args;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.bo.model.bo.SAEDocument;
import fr.urssaf.image.sae.bo.model.bo.SAEMetadata;
import fr.urssaf.image.sae.mapping.utils.Utils;
import fr.urssaf.image.sae.metadata.exceptions.ReferentialException;
import fr.urssaf.image.sae.metadata.referential.services.MetadataReferenceDAO;
import fr.urssaf.image.sae.services.enrichment.SAEEnrichmentMetadataService;
import fr.urssaf.image.sae.services.enrichment.dao.RNDReferenceDAO;
import fr.urssaf.image.sae.services.enrichment.dao.impl.SAEMetatadaFinderUtils;
import fr.urssaf.image.sae.services.enrichment.xml.model.SAEArchivalMetadatas;
import fr.urssaf.image.sae.services.exception.enrichment.ReferentialRndException;
import fr.urssaf.image.sae.services.exception.enrichment.SAEEnrichmentEx;
import fr.urssaf.image.sae.services.exception.enrichment.UnknownCodeRndEx;

/**
 * Classe concrète pour l’enrichissement des métadonnées.
 * 
 * @author rhofir.
 */
@Service
@Qualifier("saeEnrichmentMetadataService")
@SuppressWarnings( { "PMD.CyclomaticComplexity", "PMD.LongVariable" })
public class SAEEnrichmentMetadataServiceImpl implements
      SAEEnrichmentMetadataService {
   @Autowired
   @Qualifier("rndReferenceDAO")
   private RNDReferenceDAO rndReferenceDAO;
   @Autowired
   @Qualifier("metadataReferenceDAO")
   private MetadataReferenceDAO metadataReferenceDAO;

   /**
    * @return Le service RND reference.
    */
   public final RNDReferenceDAO getRndReferenceDAO() {
      return rndReferenceDAO;
   }

   /**
    * @param rndReferenceDAO
    *           : Le service RND reference.
    */
   public final void setRndReferenceDAO(RNDReferenceDAO rndReferenceDAO) {
      this.rndReferenceDAO = rndReferenceDAO;
   }

   /**
    * @return Le service metadataReferenceDAO.
    */
   public final MetadataReferenceDAO getMetadataReferenceDAO() {
      return metadataReferenceDAO;
   }

   /**
    * @param metadataReferenceDAO
    *           : Le service metadataReferenceDAO.
    */
   public final void setMetadataReferenceDAO(
         MetadataReferenceDAO metadataReferenceDAO) {
      this.metadataReferenceDAO = metadataReferenceDAO;
   }

   @Override
   public final void enrichmentMetadata(SAEDocument saeDoc)
         throws SAEEnrichmentEx, ReferentialRndException, UnknownCodeRndEx {
      List<SAEMetadata> saeMetadatas = saeDoc.getMetadatas();

      String rndValue = SAEMetatadaFinderUtils.codeMetadataFinder(saeMetadatas,
            SAEArchivalMetadatas.CODERND.getLongCode());
      try {
         if (!StringUtils.isEmpty(rndValue)) {
            completedMetadatas(saeDoc, rndValue);
         }
      } catch (ReferentialRndException e) {
         throw new ReferentialRndException(e.getMessage(), e);
      } catch (UnknownCodeRndEx e) {
         throw new UnknownCodeRndEx(e.getMessage(), e);
      } catch (ParseException e) {
         throw new SAEEnrichmentEx(e.getMessage(), e);
      } catch (ReferentialException e) {
         throw new SAEEnrichmentEx(e.getMessage(), e);
      }
   }

   /**
    * Permet de compléter les métadonnées non specifiable
    * 
    * @param document
    *           : le document retourné par DFCE.
    * @param metadata
    *           : La métadonnée désirés.
    * @throws UnknownCodeRndEx
    * @throws ReferentialRndException
    * @throws ParseException
    * @throws ReferentialException
    */
   // CHECKSTYLE:OFF
   @SuppressWarnings( { "PMD.AvoidInstantiatingObjectsInLoops",
         "PMD.ExcessiveMethodLength" })
   private void completedMetadatas(SAEDocument saeDocument, String rndCode)
         throws ReferentialRndException, UnknownCodeRndEx, ParseException,
         ReferentialException {
      SAEMetadata saeMetadata = new SAEMetadata();
      for (SAEArchivalMetadatas metadata : SAEArchivalMetadatas.values()) {
         saeMetadata = new SAEMetadata();
         saeMetadata.setLongCode(metadata.getLongCode());
         metadata = SAEMetatadaFinderUtils
               .metadtaFinder(metadata.getLongCode());
         System.out.println(metadata);
         switch (metadata) {
         
         case CODEACTIVITE:
            saeMetadata.setShortCode(metadataReferenceDAO.getByLongCode(
                  SAEArchivalMetadatas.CODEACTIVITE.getLongCode())
                  .getShortCode());
            saeMetadata.setValue(rndReferenceDAO.getActivityCodeByRnd(rndCode));
            saeDocument.getMetadatas().add(saeMetadata);
            break;
         case CODEFONCTION:
            saeMetadata.setShortCode(metadataReferenceDAO.getByLongCode(
                  SAEArchivalMetadatas.CODEFONCTION.getLongCode())
                  .getShortCode());
            saeMetadata.setValue(rndReferenceDAO.getFonctionCodeByRnd(rndCode));
            saeDocument.getMetadatas().add(saeMetadata);
            break;
         case DUREECONSERVATION:
            saeMetadata.setShortCode(metadataReferenceDAO.getByLongCode(
                  SAEArchivalMetadatas.DUREECONSERVATION.getLongCode())
                  .getShortCode());
            saeMetadata.setValue(rndReferenceDAO
                  .getStorageDurationByRnd(rndCode));
            saeDocument.getMetadatas().add(saeMetadata);
            break;
         case DATEARCHIVAGE:
            saeMetadata.setShortCode(metadataReferenceDAO.getByLongCode(
                  SAEArchivalMetadatas.DATEARCHIVAGE.getLongCode())
                  .getShortCode());
            saeMetadata.setValue(Utils.dateToString(new Date()));
            saeDocument.getMetadatas().add(saeMetadata);
            break;
         case DATEDEBUTCONSERVATION:
            if (SAEMetatadaFinderUtils.dateMetadataFinder(saeDocument
                  .getMetadatas(), SAEArchivalMetadatas.DATEDEBUTCONSERVATION
                  .getLongCode()) == null) {
               saeMetadata.setShortCode(metadataReferenceDAO.getByLongCode(
                     SAEArchivalMetadatas.DATEDEBUTCONSERVATION.getLongCode())
                     .getShortCode());
               saeMetadata.setValue(Utils.dateToString(new Date()));
               saeDocument.getMetadatas().add(saeMetadata);
            }
            break;
         case DATEFINCONSERVATION:
            saeMetadata.setShortCode(metadataReferenceDAO.getByLongCode(
                  SAEArchivalMetadatas.DATEFINCONSERVATION.getLongCode())
                  .getShortCode());
            saeMetadata.setValue(Utils
                  .dateToString(DateUtils.addDays(new Date(), rndReferenceDAO
                        .getStorageDurationByRnd(rndCode))));
            saeDocument.getMetadatas().add(saeMetadata);
            break;
         case GEL:
            saeMetadata.setShortCode(metadataReferenceDAO.getByLongCode(
                  SAEArchivalMetadatas.GEL.getLongCode()).getShortCode());
            saeMetadata.setValue(false);
            saeDocument.getMetadatas().add(saeMetadata);
            break;
         case OBJECTTYPE:
            saeMetadata
                  .setShortCode(metadataReferenceDAO.getByLongCode(
                        SAEArchivalMetadatas.OBJECTTYPE.getLongCode())
                        .getShortCode());
            saeMetadata.setValue("autonomous");
            saeDocument.getMetadatas().add(saeMetadata);
            break;
//         case TYPE:
//            saeMetadata.setShortCode(metadataReferenceDAO.getByLongCode(
//                  SAEArchivalMetadatas.TYPE.getLongCode()).getShortCode());
//            // FIXME attente de spécification.
//            saeMetadata.setValue("PDF");
//            saeDocument.getMetadatas().add(saeMetadata);
//            break;
         case CONTRATDESERVICE:
            saeMetadata.setShortCode(metadataReferenceDAO.getByLongCode(
                  SAEArchivalMetadatas.CONTRATDESERVICE.getLongCode())
                  .getShortCode());
            // FIXME attente de spécification.
            saeMetadata.setValue("ATT_PROD_001");
            saeDocument.getMetadatas().add(saeMetadata);
            break;
         case VERSIONRND:
            if (SAEMetatadaFinderUtils.codeMetadataFinder(saeDocument
                  .getMetadatas(), SAEArchivalMetadatas.VERSIONRND
                  .getLongCode()) == null) {
               saeMetadata.setShortCode(metadataReferenceDAO.getByLongCode(
                     SAEArchivalMetadatas.VERSIONRND.getLongCode())
                     .getShortCode());
               saeMetadata.setValue(rndReferenceDAO.getTypeDocument(rndCode)
                     .getVersionRnd());
               saeDocument.getMetadatas().add(saeMetadata);
            }
            break;
         default:
            break;
         }
      }
   }
   // CHECKSTYLE:ON
}
