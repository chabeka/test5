package fr.urssaf.image.sae.services.enrichment;

import fr.urssaf.image.sae.bo.model.bo.SAEDocument;
import fr.urssaf.image.sae.exception.enrichment.SAEEnrichmentEx;

/**
 * Service d’enrichissement des métadonnées.
 * 
 *@author rhofir.
 */
public interface SAEEnrichmentMetadataService {
   /**
    * Enrichie les métadonnées à partir du référentiel des codes RCND.
    * 
    * @param saeDoc
    *           Classe représentant un document de type {@link SAEDocument}.
    * @throws SAEEnrichmentEx
    *            {@link SAEEnrichmentEx}
    */
   void enrichmentMetadata(SAEDocument saeDoc) throws SAEEnrichmentEx;
}
