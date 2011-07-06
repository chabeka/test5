package fr.urssaf.image.sae.storage.dfce.validation;

import org.apache.commons.lang.Validate;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;

/**
 * Fournit des méthodes pour valider les services de récupération
 * 
 * @author akenore
 * 
 */
@Aspect
public class RetrievalServiceValidation extends AbstractValidation {

	/**
	 * Valide l'argument de la méthode retrieveStorageDocumentByUUID.
	 * 
	 * 
	 * @param uUIDCriteria
	 *            : Le critère UUID
	 */
	@Before(value = "execution( fr.urssaf.image.sae.storage.model..StorageDocument  fr.urssaf.image.sae.storage..RetrievalServiceImpl.retrieveStorageDocumentByUUID(..)) && args(uUIDCriteria)")
	public final void retrieveStorageDocumentByUUIDValidation(
			final UUIDCriteria uUIDCriteria) {
		checkNotNull(uUIDCriteria, "retrieve.document.impact",
				"retrieve.document.action");
	}

	/**
	 * Valide l'argument de la méthode retrieveStorageDocumentContentByUUID.
	 * 
	 * @param uUIDCriteria
	 *            : Le critère UUID
	 */
	@Before(value = "execution( byte[] fr.urssaf.image.sae.storage..RetrievalServiceImpl.retrieveStorageDocumentContentByUUID(..)) && args(uUIDCriteria)")
	public final void retrieveStorageDocumentContentByUUIDValidation(
			final UUIDCriteria uUIDCriteria) {
		checkNotNull(uUIDCriteria, "retrieve.document.content.impact",
				"retrieve.document.action");
	}

	/**
	 * Valide l'argument de la méthode retrieveStorageDocumentContentByUUID.
	 * 
	 * @param uUIDCriteria
	 *            : Le critère UUID
	 */
	@Before(value = "execution(java.util.List<fr.urssaf.image.sae.storage.model..StorageMetadata> fr.urssaf.image.sae.storage..RetrievalServiceImpl.retrieveStorageDocumentMetaDatasByUUID(..)) && args(uUIDCriteria)")
	public final void retrieveStorageDocumentMetaDatasByUUIDValidation(
			final UUIDCriteria uUIDCriteria) {
		checkNotNull(uUIDCriteria, "retrieve.document.metadonnee.impact",
				"retrieve.document.action");
	}

	/**
	 * @param message
	 *            : Le message
	 * @param uUIDCriteria
	 *            : Le critère UUID
	 */
	private void checkNotNull(final UUIDCriteria uUIDCriteria,
			final String... message) {
		Validate.notNull(
				uUIDCriteria,
				getMessageHandler().getMessage(
						"retrieve.from.uuid.criteria.required", message[0],
						message[1]));
		Validate.notNull(
				uUIDCriteria.getUuid(),
				getMessageHandler().getMessage(
						"retrieve.from.uuid.criteria.required", message[0],
						message[1]));

	}
}
