package fr.urssaf.image.commons.controller.spring.formulaire.type;


import org.apache.commons.lang.StringUtils;

import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.type.AbstractType;

public abstract class AbstractObjectType<T> implements AbstractType<T> {

	@Override
	public final T getObject(String value) throws TypeFormulaireException {

		if (!StringUtils.isNotBlank(value)) {
			return null;
		}
		
		return getNotEmptyObject(value);
	}
	
	public abstract T getNotEmptyObject(String value) throws TypeFormulaireException;
	
	

}
