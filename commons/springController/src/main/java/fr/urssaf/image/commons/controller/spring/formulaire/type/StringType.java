package fr.urssaf.image.commons.controller.spring.formulaire.type;

import java.util.Collection;

import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.type.AbstractType;

public class StringType implements AbstractType<String> {

	public String getObject(String valeur) throws TypeFormulaireException {
		return valeur;
	}

	public Collection<String> getCollection(Collection<String> liste,
			String[] parametres) throws TypeFormulaireException {

		if (liste != null) {
			for (int i = 0; i < parametres.length; i++) {

				liste.add(getObject(parametres[i]));

			}
		}

		return liste;
	}

	public String getValue(String object) {
		return object;
	}

}
