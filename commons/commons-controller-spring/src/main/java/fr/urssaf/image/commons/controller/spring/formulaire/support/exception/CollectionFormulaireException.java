package fr.urssaf.image.commons.controller.spring.formulaire.support.exception;

import java.util.ArrayList;
import java.util.List;

public class CollectionFormulaireException extends FieldException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ValidatorFormulaireException> validExceptions = new ArrayList<ValidatorFormulaireException>();

	private List<TypeFormulaireException> typeExceptions = new ArrayList<TypeFormulaireException>();

	public void putTypeFormulaireException(TypeFormulaireException exception) {
		typeExceptions.add(exception);
	}

	public void putValidatorFormulaireException(
			ValidatorFormulaireException exception) {

		validExceptions.add(exception);

	}

	public List<ValidatorFormulaireException> getValidatorFormulaireExceptions() {
		return validExceptions;
	}

	public List<TypeFormulaireException> getTypeFormulaireExceptions() {
		return typeExceptions;
	}

}
