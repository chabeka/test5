package fr.urssaf.image.sae.storage.dfce.validation;

import org.apache.commons.lang.Validate;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.LuceneCriteria;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;

/**
 * 
 * @author akenore
 * 
 */
@Aspect
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class SearchingServiceValidation extends AbstractValidation {
	/**
	 * Valide l'argument de la méthode searchStorageDocumentByLuceneCriteria.
	 * 
	 * 
	 * @param luceneCriteria
	 *            : Le critère lucene de recherche
	 */
	@Before(value = "execution( fr.urssaf.image.sae.storage.model..StorageDocuments  fr.urssaf.image.sae.storage..SearchingServiceImpl.searchStorageDocumentByLuceneCriteria(..)) && args(luceneCriteria)")
	public final void searchStorageDocumentByLuceneCriteriaValidation(
			final LuceneCriteria luceneCriteria) {
		Validate.notNull(
				luceneCriteria,
				getMessageHandler().getMessage(
						"search.by.lucene.criteria.required", "search.impact",
						"search.lucene.action"));
		Validate.notNull(
				luceneCriteria.getLuceneQuery(),
				getMessageHandler().getMessage(
						"search.by.lucene.query.required", "search.impact",
						"search.lucene.action"));
	}

	/**
	 * Valide l'argument de la méthode searchStorageDocumentByUUIDCriteria
	 * 
	 * @param uUIDCriteria
	 *            : Le critère uuid
	 * 
	 */
	@Before(value = "execution( fr.urssaf.image.sae.storage.model..StorageDocument  fr.urssaf.image.sae.storage..SearchingServiceImpl.searchStorageDocumentByUUIDCriteria(..)) && args(uUIDCriteria)")
	public final void searchStorageDocumentByUUIDCriteriaValidation(
			final UUIDCriteria uUIDCriteria) {
		Validate.notNull(
				uUIDCriteria,
				getMessageHandler().getMessage("search.uuid.required",
						"search.impact", "search.uuid.action"));
		
			Validate.notNull(
					uUIDCriteria.getUuid(),
					getMessageHandler().getMessage("search.uuid.action",
							"search.impact", "search.uuid.action"));
		
	}
}
