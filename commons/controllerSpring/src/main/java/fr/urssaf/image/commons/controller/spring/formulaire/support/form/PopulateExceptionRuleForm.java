package fr.urssaf.image.commons.controller.spring.formulaire.support.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.RuleFormulaireException;

public final class PopulateExceptionRuleForm {

	private Map<String, List<RuleFormulaireException>> ruleExceptions = new HashMap<String, List<RuleFormulaireException>>();

	public List<RuleFormulaireException> getExceptions(String rule) {
		return this.ruleExceptions.get(rule);
	}
	
	public void clearException() {
		this.ruleExceptions.clear();
	}

	public void clearException(String rule) {
		this.ruleExceptions.remove(rule);
	}

	public boolean isEmpty() {
		return this.ruleExceptions.isEmpty();
	}
	
	public Set<String> getRules() {
		return this.ruleExceptions.keySet();
	}

	protected void putException(String rule, List<RuleFormulaireException> exception) {

		this.ruleExceptions.put(rule, exception);
	}

}
