package fr.urssaf.image.commons.controller.spring.formulaire.type;


import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.type.AbstractType;
import fr.urssaf.image.commons.util.string.StringUtil;

public abstract class AbstractObjectType<T> implements AbstractType<T> {

	@Override
	public final T getObject(String value) throws TypeFormulaireException {

		if (!StringUtil.notEmpty(value)) {
			return null;
		}
		
		return getNotEmptyObject(value);
	}
	
	public abstract T getNotEmptyObject(String value) throws TypeFormulaireException;
	
	

}
