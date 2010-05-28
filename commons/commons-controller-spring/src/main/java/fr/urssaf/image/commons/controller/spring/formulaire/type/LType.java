package fr.urssaf.image.commons.controller.spring.formulaire.type;

import java.util.ArrayList;

import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.FormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;

public class LType extends AbstractObjectType<Long> {

	@Override
	public Long getNotEmptyObject(String valeur) throws TypeFormulaireException {
		try {
			return Long.parseLong(valeur);
		} catch (NumberFormatException e) {
			ArrayList<Object> valeurs = new ArrayList<Object>();
			valeurs.add(valeur);
			throw new TypeFormulaireException(valeur, Long.class,
					new FormulaireException(valeurs, "exception.long"));
		}
	}

	public String getValue(Long object) {
		return Long.toString(object);
	}

}
