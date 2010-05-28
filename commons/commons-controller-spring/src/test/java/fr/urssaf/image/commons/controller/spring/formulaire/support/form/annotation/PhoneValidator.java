package fr.urssaf.image.commons.controller.spring.formulaire.support.form.annotation;

import fr.urssaf.image.commons.controller.spring.formulaire.support.validator.ValidatorAbstract;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.support.PatternValidatorAbstract;

public class PhoneValidator extends PatternValidatorAbstract implements
		ValidatorAbstract<String, Phone> {

	public String getValidatorException() {
		return "exception.phone";
	}

	public void initialize(Phone parametres) {
		this.initalize(parametres.regex());
	}

	@Override
	public Class<Phone> getAnnotation() {
		return Phone.class;
	}

}
