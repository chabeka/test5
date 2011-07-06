package fr.urssaf.image.sae.storage.dfce.validation;

import org.apache.commons.lang.Validate;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;

/**
 * 
 * @author akenore
 * 
 */

@Aspect
public class DeletionServiceValidation extends AbstractValidation {
	/**
	 * Valide l'argument de la méthode deleteStorageDocument.
	 * 
	 * 
	 * @param uuidCriteria
	 *            : le critère de recherche
	 */
	@Before(value = "execution(void fr.urssaf.image.sae.storage.dfce..DeletionServiceImpl.deleteStorageDocument(..)) && args(uuidCriteria)")
	public final void deleteStorageDocumentValidation(
			final UUIDCriteria uuidCriteria) {
		
		Validate.notNull(
				uuidCriteria,
				getMessageHandler().getMessage("deletion.from.uuid.criteria.required",
						"delete.impact", "delete.action"));
		Validate.notNull(
				uuidCriteria.getUuid(),
				getMessageHandler().getMessage(
						"deletion.from.uuid.criteria.required",
						"delete.action", "delete.action"));

	}

	
}
