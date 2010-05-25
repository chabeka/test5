package fr.urssaf.image.commons.controller.spring.formulaire.type;

import java.util.ArrayList;

import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.FormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;

public class BoolType extends AbstractObjectType<Boolean> {

	@Override
	public Boolean getNotEmptyObject(String value) throws TypeFormulaireException {

		try {
			
			return Boolean.parseBoolean(value);
			
		} catch (NumberFormatException e) {
			ArrayList<Object> valeurs = new ArrayList<Object>();
			valeurs.add(value);
			throw new TypeFormulaireException(value, Boolean.class,
					new FormulaireException(valeurs, "exception.bool"));
		}
	}

	public Object getNull() {
		return false;
	}

	public String getValue(Boolean object) {
		return Boolean.toString(object);
	}

}
