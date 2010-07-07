package fr.urssaf.image.commons.controller.spring.formulaire.validator.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;

import fr.urssaf.image.commons.controller.spring.formulaire.support.validator.ValidatorAbstract;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.Positif;
import fr.urssaf.image.commons.util.number.DoubleUtil;

public class PositifValidator implements ValidatorAbstract<Double, Positif> {

	private Double number;
	
	public boolean isValid(Double number) {
		this.number = number;
		return DoubleUtil.sup(number, 0.0);
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

	public String getLibelleValue(Double number) {
		return ObjectUtils.toString(number);
	}

	@Override
	public Class<Positif> getAnnotation() {
		return Positif.class;
	}

}
