package fr.urssaf.image.commons.controller.spring.servlet.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.FieldException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.RuleFormulaireException;

public class ExceptionController {

	public Map<String, BeanException> getBeanException(MyFormulaire formulaire) {

		Map<String, FieldException> exceptions = allExceptions(formulaire);

		Map<String, BeanException> beanExceptions = new HashMap<String, BeanException>();

		for (String field : exceptions.keySet()) {

			BeanException beanException = new BeanException(exceptions
					.get(field));

			beanExceptions.put(field, beanException);

		}

		return beanExceptions;

	}

	private Map<String, FieldException> allExceptions(MyFormulaire formulaire) {

		Map<String, FieldException> exceptions = new HashMap<String, FieldException>();

		for (String field : formulaire.getException().getFieldExceptions()) {
			exceptions
					.put(field, formulaire.getException().getException(field));
		}

		allExceptions(formulaire, exceptions);

		return exceptions;
	}

	private Map<String, FieldException> allExceptions(MyFormulaire formulaire,
			String formName) {

		Map<String, FieldException> exceptions = new HashMap<String, FieldException>();

		for (String field : formulaire.getException().getFieldExceptions()) {
			exceptions.put(formName + "." + field, formulaire.getException()
					.getException(field));
		}

		allExceptions(formulaire, exceptions);

		return exceptions;

	}

	private void allExceptions(MyFormulaire formulaire,
			Map<String, FieldException> exceptions) {

		for (String name : formulaire.getFormulaires()) {
			MyFormulaire form = formulaire.getFormulaire(name);
			exceptions.putAll(allExceptions(form, name));
		}

	}

	public Map<String, List<RuleFormulaireException>> allRuleExceptions(
			MyFormulaire formulaire) {

		Map<String, List<RuleFormulaireException>> exceptions = new HashMap<String, List<RuleFormulaireException>>();

		for (String rule : formulaire.getExceptionRuleForm().getRules()) {
			exceptions.put(rule, formulaire.getExceptionRuleForm()
					.getExceptions(rule));
		}

		allRuleExceptions(formulaire, exceptions);

		return exceptions;
	}

	private Map<String, List<RuleFormulaireException>> allRuleExceptions(
			MyFormulaire formulaire, String formName) {

		Map<String, List<RuleFormulaireException>> exceptions = new HashMap<String, List<RuleFormulaireException>>();

		for (String rule : formulaire.getExceptionRuleForm().getRules()) {
			exceptions.put(formName + "." + rule, formulaire
					.getExceptionRuleForm().getExceptions(rule));
		}

		allRuleExceptions(formulaire, exceptions);

		return exceptions;

	}

	private void allRuleExceptions(MyFormulaire formulaire,
			Map<String, List<RuleFormulaireException>> exceptions) {

		for (String name : formulaire.getFormulaires()) {
			MyFormulaire form = formulaire.getFormulaire(name);
			exceptions.putAll(allRuleExceptions(form, name));
		}
	}

}
