package fr.urssaf.image.commons.controller.spring.formulaire.validator.support;

import fr.urssaf.image.commons.controller.spring.formulaire.support.validator.ValidatorAbstract;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.Pattern;

public class PatternValidator extends PatternValidatorAbstract implements
		ValidatorAbstract<String, Pattern> {

	public String getValidatorException() {
		return "exception.pattern";
	}

	public void initialize(Pattern parametres) {
		this.initalize(parametres.regex());
	}

	@Override
	public Class<Pattern> getAnnotation() {
		return Pattern.class;
	}

}
