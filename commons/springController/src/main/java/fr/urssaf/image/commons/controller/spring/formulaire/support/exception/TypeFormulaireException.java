package fr.urssaf.image.commons.controller.spring.formulaire.support.exception;

public class TypeFormulaireException extends FieldException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	private Class classe;

	private String value;

	private FormulaireException exception;

	@SuppressWarnings("unchecked")
	public TypeFormulaireException(String value, Class classe,
			FormulaireException exception) {
		this.classe = classe;
		this.value = value;
		this.exception = exception;
	}

	@SuppressWarnings("unchecked")
	public Class getClasse() {
		return this.classe;
	}

	public String getValue() {
		return this.value;
	}

	public FormulaireException getException() {
		return this.exception;
	}

}
