package fr.urssaf.image.commons.controller.spring.formulaire.type;

import java.util.ArrayList;

import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.FormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;

public class BooleanType extends AbstractObjectType<Boolean> {

	@Override
	public Boolean getNotEmptyObject(String value)
			throws TypeFormulaireException {

		try {
			
			return Boolean.valueOf(value);
			
		} catch (NumberFormatException e) {
			ArrayList<Object> valeurs = new ArrayList<Object>();
			valeurs.add(value);
			throw new TypeFormulaireException(value, Boolean.class,
					new FormulaireException(valeurs, "exception.bool"));
		}
	}

	public String getValue(Boolean object) {
		return Boolean.toString(object);
	}

}
