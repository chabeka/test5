package fr.urssaf.image.commons.controller.spring.formulaire.validator.support;

import java.util.ArrayList;
import java.util.List;

import fr.urssaf.image.commons.util.string.StringUtil;

public class PatternValidatorAbstract {

	private String regex;

	public boolean isValid(String value) {

		if (StringUtil.notEmpty(value)) {
			java.util.regex.Pattern modele = java.util.regex.Pattern.compile(
					regex, java.util.regex.Pattern.MULTILINE
							+ java.util.regex.Pattern.DOTALL);
			return modele.matcher(value).matches();

		} else {
			return true;
		}

	}

	public void initalize(String regex) {
		this.regex = regex;
	}

	public List<String> getExceptionParameters() {
		List<String> liste = new ArrayList<String>();

		liste.add(getLibelleValue(regex));

		return liste;
	}

	public String getLibelleValue(String value) {
		return value;
	}

}
