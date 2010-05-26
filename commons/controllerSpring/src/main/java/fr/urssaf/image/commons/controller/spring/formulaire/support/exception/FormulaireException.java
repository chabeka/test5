package fr.urssaf.image.commons.controller.spring.formulaire.support.exception;

import java.util.List;

public class FormulaireException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final List<Object> parameters;

	private final String code;
	
	public FormulaireException(List<Object> parameters, String code) {

		this.parameters = parameters;
		this.code = code;
	}
	
	public List<Object> getParameters() {
		return this.parameters;
	}

	public String getCode() {
		return this.code;
	}
	

}
