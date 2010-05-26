package fr.urssaf.image.commons.controller.spring.formulaire.type;

import java.util.ArrayList;

import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.FormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;

public class FType extends AbstractObjectType<Float> {

	@Override
	public Float getNotEmptyObject(String valeur) throws TypeFormulaireException {
		try {
			return Float.parseFloat(valeur);
		} catch (NumberFormatException e) {
			ArrayList<Object> valeurs = new ArrayList<Object>();
			valeurs.add(valeur);
			throw new TypeFormulaireException(valeur, Float.class,
					new FormulaireException(valeurs, "exception.float"));
		}
	}

	public String getValue(Float object) {
		return Float.toString(object);
	}

}
