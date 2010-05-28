package fr.urssaf.image.commons.controller.spring.formulaire.support.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.UtilForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.annotation.Rule;
import fr.urssaf.image.commons.controller.spring.formulaire.support.bean.BeanRuleFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.RuleFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.rule.RuleException;
import fr.urssaf.image.commons.util.logger.Log;

public class ValidRuleForm<F extends MyFormulaire> {

	private static final Logger log = Logger.getLogger(ValidRuleForm.class);

	private Map<String, BeanRuleFormulaire> rules;

	public ValidRuleForm(ClassForm<F> classe) {
		this.rules = classe.getRules();
	}

	public boolean valid(F formulaire) {

		boolean valid = true;

		for (String ruleName : this.rules.keySet()) {
			valid = valid & this.isValid(formulaire, ruleName);
		}

		return valid;

	}

	@SuppressWarnings("unchecked")
	public boolean isValid(F formulaire, String ruleName) {

		Object ruleException = this.valid(formulaire, ruleName);

		if (ruleException == null) {
			return false;
		}

		if (Map.class.isAssignableFrom(ruleException.getClass())) {

			Map<String, RuleException> ruleExceptions = (Map<String, RuleException>) ruleException;
			for (RuleException exception : ruleExceptions.values()) {
				if (exception != null) {
					return true;
				}
			}
		}

		return formulaire.getExceptionRuleForm().getExceptions(ruleName) != null;

	}

	@SuppressWarnings("unchecked")
	public Object valid(F formulaire, String ruleName) {

		BeanRuleFormulaire beanRule = rules.get(ruleName);

		Rule rule = beanRule.getRule();
		Object ruleException = null;
		try {
			ruleException = beanRule.getRuleExceptions(formulaire);
			formulaire.getExceptionRuleForm().clearException(ruleName);

		} catch (Exception e) {
			Log.throwException(log, e);
		}

		if (RuleException.class.isAssignableFrom(beanRule.getType())) {
			this.valid(formulaire, ruleName, rule,
					(RuleException) ruleException);
		}

		else if (List.class.isAssignableFrom(beanRule.getType())) {

			this.valid(formulaire, ruleName, rule, (List) ruleException);
		}

		else if (Map.class.isAssignableFrom(beanRule.getType())) {

			this.valid(formulaire, ruleName, rule, (Map) ruleException);
		}

		return ruleException;

	}

	private void valid(F formulaire, String ruleName, Rule rule,
			RuleException ruleException) {
		if (ruleException != null) {
			List<RuleFormulaireException> exceptions = new ArrayList<RuleFormulaireException>();

			List<Object> valeurs = new ArrayList<Object>();
			for (Object value : ruleException.getValues()) {
				valeurs.add(value);
			}

			RuleFormulaireException exception = new RuleFormulaireException(
					valeurs, rule);
			exceptions.add(exception);

			formulaire.getExceptionRuleForm()
					.putException(ruleName, exceptions);
		}
	}

	private void valid(F formulaire, String ruleName, Rule rule,
			List<RuleException> ruleExceptions) {

		List<RuleFormulaireException> exceptions = new ArrayList<RuleFormulaireException>();

		for (RuleException ruleException : ruleExceptions) {
			if (ruleException != null) {
				List<Object> valeurs = new ArrayList<Object>();
				for (Object value : ruleException.getValues()) {
					valeurs.add(value);
				}

				RuleFormulaireException exception = new RuleFormulaireException(
						valeurs, rule);
				exceptions.add(exception);
			}
		}
		if (!exceptions.isEmpty()) {
			formulaire.getExceptionRuleForm()
					.putException(ruleName, exceptions);
		}
	}

	private void valid(F formulaire, String ruleName, Rule rule,
			Map<String, RuleException> ruleExceptions) {

		for (String key : ruleExceptions.keySet()) {

			RuleException ruleException = ruleExceptions.get(key);
			if (ruleException != null) {
				List<Object> valeurs = new ArrayList<Object>();
				for (Object value : ruleException.getValues()) {
					valeurs.add(value);
				}

				RuleFormulaireException exception = new RuleFormulaireException(
						valeurs, rule);

				List<RuleFormulaireException> exceptions = new ArrayList<RuleFormulaireException>();

				exceptions.add(exception);
				formulaire.getExceptionRuleForm().putException(
						UtilForm.getRule(ruleName, key), exceptions);
			} else {
				formulaire.getExceptionRuleForm().clearException(
						UtilForm.getRule(ruleName, key));
			}

		}

	}

}
