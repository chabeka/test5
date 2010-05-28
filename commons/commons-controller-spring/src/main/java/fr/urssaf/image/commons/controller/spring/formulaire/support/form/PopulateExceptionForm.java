package fr.urssaf.image.commons.controller.spring.formulaire.support.form;

import java.util.Map;

import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.AnnotationFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.CollectionFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.FieldException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.ValidatorFormulaireException;

public final class PopulateExceptionForm {

	private Map<String, FieldException> exceptions;

	public PopulateExceptionForm(Map<String, FieldException> exceptions) {
		this.exceptions = exceptions;
	}

	private boolean isException(String field) {
		return this.exceptions.containsKey(field);
	}

	private void putException(String field, FieldException exception) {
		this.exceptions.put(field, exception);
	}

	protected void putException(String field,
			ValidatorFormulaireException exception) {

		if (!this.isException(field)) {

			this.putException(field,
					(FieldException) new AnnotationFormulaireException());
		}

		((AnnotationFormulaireException) this.exceptions.get(field))
				.putValidatorFormulaireException(exception);

	}

	protected void putCollectionException(String field,
			ValidatorFormulaireException exception) {

		initCollectionFormulaireException(field);

		((CollectionFormulaireException) this.exceptions.get(field))
				.putValidatorFormulaireException(exception);

	}

	protected void putException(String field, TypeFormulaireException exception) {

		this.putException(field, (FieldException) exception);

	}

	protected void putCollectionException(String field,
			TypeFormulaireException exception) {

		initCollectionFormulaireException(field);

		((CollectionFormulaireException) this.exceptions.get(field))
				.putTypeFormulaireException(exception);

	}

	private void initCollectionFormulaireException(String field) {

		if (!this.isException(field)) {

			this.putException(field,
					(FieldException) new CollectionFormulaireException());
		}
	}

}
