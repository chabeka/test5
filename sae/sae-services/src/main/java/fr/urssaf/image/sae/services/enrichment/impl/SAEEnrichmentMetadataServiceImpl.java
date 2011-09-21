package fr.urssaf.image.sae.services.enrichment.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.bo.model.bo.SAEDocument;
import fr.urssaf.image.sae.bo.model.bo.SAEMetadata;
import fr.urssaf.image.sae.mapping.utils.Utils;
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
@SuppressWarnings("PMD.CyclomaticComplexity")
public class SAEEnrichmentMetadataServiceImpl implements
      SAEEnrichmentMetadataService {
   @Autowired
   @Qualifier("rndReferenceDAO")
   private RNDReferenceDAO rndReferenceDAO;

   // @Autowired
   // @Qualifier("saeMetatadaFinder")
   // private static SAEMetatadaFinderUtils saeMetatadaFinder;

   @Override
   public final void enrichmentMetadata(SAEDocument saeDoc)
         throws SAEEnrichmentEx {
      List<SAEMetadata> saeMetadatas = saeDoc.getMetadatas();

      String rndValue = SAEMetatadaFinderUtils.codeMetadataFinder(saeMetadatas,
            SAEArchivalMetadatas.CODERND.getLongCode());
      try {
         if (!StringUtils.isEmpty(rndValue)) {
            completedMetadatas(saeDoc, rndValue);
         }
      } catch (ReferentialRndException e) {
         throw new SAEEnrichmentEx(e.getMessage(), e);
      } catch (UnknownCodeRndEx e) {
         throw new SAEEnrichmentEx(e.getMessage(), e);
      } catch (ParseException e) {
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
    */
   // CHECKSTYLE:OFF
   @SuppressWarnings( { "PMD.CyclomaticComplexity",
         "PMD.AvoidInstantiatingObjectsInLoops" })

   private void completedMetadatas(SAEDocument saeDocument, String rndCode)
         throws ReferentialRndException, UnknownCodeRndEx, ParseException {
      SAEMetadata saeMetadata = new SAEMetadata();
      for (SAEArchivalMetadatas metadata : SAEArchivalMetadatas.values()) {
         saeMetadata = new SAEMetadata();
         saeMetadata.setLongCode(metadata.getLongCode());
         metadata = SAEMetatadaFinderUtils
               .metadtaFinder(metadata.getLongCode());
         switch (metadata) {
         case CODEACTIVITE:
            saeMetadata.setValue(rndReferenceDAO.getActivityCodeByRnd(rndCode));
            saeDocument.getMetadatas().add(saeMetadata);
            break;
         case CODEFONCTION:
            saeMetadata.setValue(rndReferenceDAO.getFonctionCodeByRnd(rndCode));
            saeDocument.getMetadatas().add(saeMetadata);
            break;
         case DUREECONSERVATION:
            saeMetadata.setValue(rndReferenceDAO
                  .getStorageDurationByRnd(rndCode));
            saeDocument.getMetadatas().add(saeMetadata);
            break;
         case DATEARCHIVAGE:
            saeMetadata.setValue(Utils.dateToString(new Date()));
            saeDocument.getMetadatas().add(saeMetadata);
            break;
         case DATEDEBUTCONSERVATION:
            saeMetadata.setValue(Utils.dateToString(new Date()));
            saeDocument.getMetadatas().add(saeMetadata);
            break;
         case DATEFINCONSERVATION:
            saeMetadata.setValue(Utils
                  .dateToString(DateUtils.addDays(new Date(), rndReferenceDAO
                        .getStorageDurationByRnd(rndCode))));
            saeDocument.getMetadatas().add(saeMetadata);
            break;
         case GEL:
            saeMetadata.setValue(false);
            saeDocument.getMetadatas().add(saeMetadata);
         case OBJECTTYPE:
            saeMetadata.setValue("autonomous");
            saeDocument.getMetadatas().add(saeMetadata);
            break;
         case TYPE:
            saeMetadata.setValue("PDF");
            saeDocument.getMetadatas().add(saeMetadata);
            break;
         case CONTRATDESERVICE:
            // FIXME attente de spécification.
            saeMetadata.setValue("ATT_PROD_001");
            saeDocument.getMetadatas().add(saeMetadata);
            break;
         default:
            break;
         }

      }
   }
   // CHECKSTYLE:ON
}
