package fr.urssaf.image.commons.controller.spring.formulaire.validator.support;

import java.util.ArrayList;
import java.util.List;


import fr.urssaf.image.commons.controller.spring.formulaire.support.validator.ValidatorAbstract;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.Positif;
import fr.urssaf.image.commons.util.number.NumberUtil;

public class PositifValidator implements ValidatorAbstract<Number, Positif> {

	private Number number;
	
	public boolean isValid(Number number) {
		this.number = number;
		return NumberUtil.positif(number);
	}

	public String getValidatorException() {
		return "exception.positifNumber";
	}

	public void initialize(Positif parametres) {
	
	}

	public List<String> getExceptionParameters() {
		List<String> liste = new ArrayList<String>();

		liste.add(getLibelleValue(number));
	
		return liste;
	}

	public String getLibelleValue(Number number) {
		return NumberUtil.toString(number);
	}

	@Override
	public Class<Positif> getAnnotation() {
		return Positif.class;
	}

}
