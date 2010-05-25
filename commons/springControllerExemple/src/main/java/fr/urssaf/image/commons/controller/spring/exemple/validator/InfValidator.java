package fr.urssaf.image.commons.controller.spring.exemple.validator;

import java.util.ArrayList;
import java.util.List;


import fr.urssaf.image.commons.controller.spring.formulaire.support.validator.ValidatorAbstract;
import fr.urssaf.image.commons.util.number.DoubleUtil;
import fr.urssaf.image.commons.util.number.NumberUtil;

public class InfValidator implements ValidatorAbstract<Number, Inf> {

	private double inf;

	private Number number;

	@Override
	public boolean isValid(Number value) {
		this.number = value;
		return value != null ? DoubleUtil.inf(value.doubleValue(), inf) : true;
	}

	@Override
	public String getValidatorException() {
		return "exception.infNumber";
	}

	@Override
	public void initialize(Inf parametres) {
		inf = parametres.borneInf();
	}

	@Override
	public List<String> getExceptionParameters() {
		List<String> liste = new ArrayList<String>();

		liste.add(getLibelleValue(number));

		return liste;
	}
	@Override
	public String getLibelleValue(Number value) {
		return NumberUtil.toString(value);
	}

	@Override
	public Class<Inf> getAnnotation() {
		return Inf.class;
	}

	
}
