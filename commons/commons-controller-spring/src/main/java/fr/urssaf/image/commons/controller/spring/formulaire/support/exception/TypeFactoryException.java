package fr.urssaf.image.commons.controller.spring.formulaire.support.exception;

import org.apache.log4j.Logger;

public class TypeFactoryException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static final Logger log = Logger
			.getLogger(TypeFactoryException.class);

	private String field;

	@SuppressWarnings("unchecked")
	private Class classe;

	@SuppressWarnings("unchecked")
	public TypeFactoryException(String field, Class classe) {
		super("le champ '" + field + "' de type '"
				+ classe.getCanonicalName() + "' n'est pas prise en compte");
		this.field = field;
		this.classe = classe;
	}

	public String getField() {
		return field;
	}

	@SuppressWarnings("unchecked")
	public Class getClasse() {
		return classe;
	}

}
