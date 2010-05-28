package fr.urssaf.image.commons.controller.spring.formulaire.support.exception;

import java.util.List;

import fr.urssaf.image.commons.controller.spring.formulaire.support.validator.ValidatorAbstract;

public class ValidatorFormulaireException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private FormulaireException exception;
	
	@SuppressWarnings("unchecked")
	private ValidatorAbstract validator;

	public ValidatorFormulaireException(List<Object> parameters, String code) {
		this.exception = new FormulaireException(parameters,code);
	}

	@SuppressWarnings("unchecked")
	public void setValidator(ValidatorAbstract validator) {
		this.validator = validator;
	}

	@SuppressWarnings("unchecked")
	public ValidatorAbstract getValidator() {
		return this.validator;
	}
	
	public FormulaireException getException(){
		return this.exception;
	}

}
