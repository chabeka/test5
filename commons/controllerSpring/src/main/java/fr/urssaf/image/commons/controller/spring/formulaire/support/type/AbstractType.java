package fr.urssaf.image.commons.controller.spring.formulaire.support.type;

import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;

public interface AbstractType<T> {

	public T getObject(String value) throws TypeFormulaireException;
	
	public String getValue(T object);
	
}
