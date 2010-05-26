package fr.urssaf.image.commons.controller.spring.formulaire.support.exception;

import java.util.ArrayList;
import java.util.List;


public class AnnotationFormulaireException extends FieldException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ValidatorFormulaireException> validExceptions = new ArrayList<ValidatorFormulaireException>();

	public void putValidatorFormulaireException(
			ValidatorFormulaireException exception) {
		validExceptions.add(exception);
	}

	public List<ValidatorFormulaireException> getValidatorFormulaireException() {
		return validExceptions;
	}
	
}
