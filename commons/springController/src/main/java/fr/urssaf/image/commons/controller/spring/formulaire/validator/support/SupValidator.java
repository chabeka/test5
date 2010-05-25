package fr.urssaf.image.commons.controller.spring.formulaire.validator.support;

import java.util.ArrayList;
import java.util.List;


import fr.urssaf.image.commons.controller.spring.formulaire.support.validator.ValidatorAbstract;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.Sup;
import fr.urssaf.image.commons.util.number.DoubleUtil;
import fr.urssaf.image.commons.util.number.NumberUtil;

public class SupValidator implements ValidatorAbstract<Number, Sup> {

	private double sup;

	private Number number;

	public boolean isValid(Number value) {
		this.number = value;
		return value != null ? DoubleUtil.sup(value.doubleValue(), sup) : true;
	}

	public String getValidatorException() {
		return "exception.supNumber";
	}

	public void initialize(Sup parametres) {
		sup = parametres.borneSup();
	}

	public List<String> getExceptionParameters() {
		List<String> liste = new ArrayList<String>();

		liste.add(getLibelleValue(number));

		return liste;
	}

	public String getLibelleValue(Number value) {
		return NumberUtil.toString(value);
	}

	@Override
	public Class<Sup> getAnnotation() {
		return Sup.class;
	}

	
}
