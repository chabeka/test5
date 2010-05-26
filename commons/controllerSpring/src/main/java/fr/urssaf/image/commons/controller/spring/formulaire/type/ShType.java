package fr.urssaf.image.commons.controller.spring.formulaire.type;

import java.util.ArrayList;

import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.FormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;

public class ShType extends AbstractObjectType<Short> {

	@Override
	public Short getNotEmptyObject(String valeur) throws TypeFormulaireException {
		try {
			return Short.parseShort(valeur);
		} catch (NumberFormatException e) {
			ArrayList<Object> valeurs = new ArrayList<Object>();
			valeurs.add(valeur);
			throw new TypeFormulaireException(valeur, Short.class,
					new FormulaireException(valeurs, "exception.short"));
		}
	}

	public String getValue(Short object) {
		return Long.toString(object);
	}

}
