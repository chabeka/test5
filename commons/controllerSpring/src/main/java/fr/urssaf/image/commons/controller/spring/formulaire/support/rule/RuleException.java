package fr.urssaf.image.commons.controller.spring.formulaire.support.rule;

import java.util.List;

public class RuleException {
	
	private List<Object> values;

	public RuleException(List<Object> values) {
		this.values = values;
	}

	public List<Object> getValues() {
		return this.values;
	}

}
