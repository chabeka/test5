package fr.urssaf.image.commons.controller.spring.formulaire.validator.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;


import fr.urssaf.image.commons.controller.spring.formulaire.support.validator.ValidatorAbstract;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.NotEmpty;

public class NotEmptyValidator implements ValidatorAbstract<String, NotEmpty> {

	public boolean isValid(String value) {
		return StringUtils.isNotBlank(value);

	}

	public String getValidatorException() {
		return "exception.empty";
	}

	public void initialize(NotEmpty parametres) {
		
	}

	public List<String> getExceptionParameters() {
		List<String> liste = new ArrayList<String>();

		liste.add("test");

		return liste;
	}

	public String getLibelleValue(String value) {
		return value;
	}

	@Override
	public Class<NotEmpty> getAnnotation() {
		return NotEmpty.class;
	}

}
