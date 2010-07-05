package fr.urssaf.image.commons.controller.spring.exemple.resource;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;


import fr.urssaf.image.commons.controller.spring.exemple.modele.Etat;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.FormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.type.AbstractType;

public class EtatType implements AbstractType<Etat> {

	@Override
	public Etat getObject(String value) throws TypeFormulaireException {
		try {
			if (StringUtils.isNotBlank(value)) {
				return Etat.valueOf(value);
			}
		} catch (IllegalArgumentException e) {

			List<Object> values = new ArrayList<Object>();
			values.add(value);

			throw new TypeFormulaireException(value, Etat.class,
					new FormulaireException(values, "exception.etat"));

		}
		
		return null;
	}

	@Override
	public String getValue(Etat etat) {
		return etat.name();
	}

}
