package fr.urssaf.image.sae.metadata.rules;

import org.springframework.stereotype.Component;

import fr.urssaf.image.sae.bo.model.bo.SAEMetadata;
import fr.urssaf.image.sae.metadata.referential.model.MetadataReference;

/**
 * Cette règle permet de determine si la métadonnées d'un document de SAE à
 * belle et bien une correspondance dans le référentiel des métadonnées.
 * 
 * @author akenore
 * 
 */
@Component
public class MetadataExistingRule extends
		AbstractLeafRule<SAEMetadata, MetadataReference> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
	public final boolean isSatisfiedBy(final SAEMetadata saeMetadata,
			final MetadataReference metaDataReference) {
		boolean result = true;
		if (metaDataReference == null) {
			result = false;
		}
		return result;
	}
}
