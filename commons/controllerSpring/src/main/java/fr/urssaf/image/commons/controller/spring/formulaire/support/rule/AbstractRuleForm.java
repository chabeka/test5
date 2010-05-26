package fr.urssaf.image.commons.controller.spring.formulaire.support.rule;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRuleForm {

	protected abstract boolean isValid();

	protected List<Object> getValues() {
		return new ArrayList<Object>();
	}

	public RuleException getRuleException() {

		if (!isValid()) {

			return new RuleException(getValues());
		}

		return null;
	}
}
