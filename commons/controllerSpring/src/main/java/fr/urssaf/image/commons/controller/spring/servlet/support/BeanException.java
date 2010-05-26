package fr.urssaf.image.commons.controller.spring.servlet.support;

import java.util.ArrayList;
import java.util.List;

import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.AnnotationFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.CollectionFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.FieldException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.FormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.ValidatorFormulaireException;

public class BeanException {

	private List<FormulaireException> exceptions = new ArrayList<FormulaireException>();;

	private String value;

	public BeanException(FieldException fieldException) {

		if (fieldException.getClass().isAssignableFrom(
				TypeFormulaireException.class)) {

			TypeFormulaireException exception = (TypeFormulaireException) fieldException;
			this.value = exception.getValue();
			this.putFormulaireExceptions(exception.getException());

		}

		else if (fieldException.getClass().isAssignableFrom(
				AnnotationFormulaireException.class)) {

			AnnotationFormulaireException exception = (AnnotationFormulaireException) fieldException;

			for (ValidatorFormulaireException validatorFormulaireException : exception
					.getValidatorFormulaireException()) {
				this.putFormulaireExceptions(validatorFormulaireException
						.getException());
			}

		}

		else if (fieldException.getClass().isAssignableFrom(
				CollectionFormulaireException.class)) {

			CollectionFormulaireException exception = (CollectionFormulaireException) fieldException;

			for (TypeFormulaireException typeFormulaireException : exception
					.getTypeFormulaireExceptions()) {

				this.putFormulaireExceptions(typeFormulaireException
						.getException());
			}

			for (ValidatorFormulaireException validatorFormulaireException : exception
					.getValidatorFormulaireExceptions()) {
				
				this.putFormulaireExceptions(validatorFormulaireException
						.getException());
			}

		}

	}

	private void putFormulaireExceptions(
			FormulaireException exceptions) {
		this.exceptions.add(exceptions);
	}

	public List<FormulaireException> getFormulaireExceptions() {
		return exceptions;
	}

	public String getValue() {
		return value;
	}

}
