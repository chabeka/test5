package fr.urssaf.image.commons.controller.spring.formulaire.type;

import java.util.ArrayList;

import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.FormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;

public class ByteType extends AbstractObjectType<Byte> {

	@Override
	public Byte getNotEmptyObject(String valeur) throws TypeFormulaireException {

		try {
			return Byte.valueOf(valeur);
		} catch (NumberFormatException e) {
			ArrayList<Object> valeurs = new ArrayList<Object>();
			valeurs.add(valeur);
			throw new TypeFormulaireException(valeur, Byte.class,
					new FormulaireException(valeurs, "exception.byte"));
		}
	}
	
	public String getValue(Byte object) {
		return Byte.toString(object);
	}

}
