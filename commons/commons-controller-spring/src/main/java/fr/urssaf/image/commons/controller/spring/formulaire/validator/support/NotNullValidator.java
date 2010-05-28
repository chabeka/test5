package fr.urssaf.image.commons.controller.spring.formulaire.validator.support;

import java.util.List;

import fr.urssaf.image.commons.controller.spring.formulaire.support.validator.ValidatorAbstract;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.NotNull;

public class NotNullValidator implements ValidatorAbstract<Object, NotNull> {

	public boolean isValid(Object obj) {
		return obj != null;

	}

	public String getValidatorException() {
		return "exception.notnull";
	}

	public void initialize(NotNull parametres) {
	}

	public List<String> getExceptionParameters() {
		return null;
	}

	public String getLibelleValue(Object value) {
		return null;
	}

	@Override
	public Class<NotNull> getAnnotation() {
		return NotNull.class;
	}

}
