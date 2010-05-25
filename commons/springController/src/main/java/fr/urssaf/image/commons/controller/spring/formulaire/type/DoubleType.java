package fr.urssaf.image.commons.controller.spring.formulaire.type;

import java.util.ArrayList;

import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.FormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;

public class DoubleType extends AbstractObjectType<Double> {

	@Override
	public Double getNotEmptyObject(String valeur) throws TypeFormulaireException {
		try {
			return Double.valueOf(valeur);
		} catch (NumberFormatException e) {
			ArrayList<Object> valeurs = new ArrayList<Object>();
			valeurs.add(valeur);
			throw new TypeFormulaireException(valeur, Double.class,
					new FormulaireException(valeurs, "exception.double"));
		}
	}

	public String getValue(Double object) {
		return Double.toString(object);
	}

}
