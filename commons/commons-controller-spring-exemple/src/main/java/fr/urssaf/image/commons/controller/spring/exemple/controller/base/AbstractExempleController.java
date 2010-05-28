package fr.urssaf.image.commons.controller.spring.exemple.controller.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;


import fr.urssaf.image.commons.controller.spring.exemple.annotation.ValidRule;
import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.UtilForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.bean.BeanFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.FormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.RuleFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.rule.RuleException;
import fr.urssaf.image.commons.controller.spring.servlet.AbstractMyController;
import fr.urssaf.image.commons.controller.spring.servlet.support.BeanException;

public abstract class AbstractExempleController<F extends MyFormulaire> extends
		AbstractMyController<F> {

	@Autowired
	private MessageSource messageSource;

	public AbstractExempleController(Class<? extends F> classe) {
		super(classe);
	}

	@SuppressWarnings("unchecked")
	public ModelAndView populateField(HttpServletRequest request,
			HttpServletResponse reponse) {
		String field = request.getParameter("field");
		MyFormulaire formulaire = this.getFormulaire(request, reponse);

		Map<String, List<String>> beans = new HashMap<String, List<String>>();
		beans.put(field, new ArrayList<String>());

		if (!formulaire.isValid(field)) {

			BeanException beanException = new BeanException(formulaire
					.getException(field));

			for (FormulaireException exception : beanException
					.getFormulaireExceptions()) {

				String message = messageSource.getMessage(exception.getCode(),
						exception.getParameters().toArray(), request
								.getLocale());
				beans.get(field).add(message);

			}

		}

		BeanFormulaire beanFormulaire = formulaire.getBeanFormulaire(field);

		ValidRule validRule = (ValidRule) beanFormulaire
				.getAnnotation(ValidRule.class);
		if (validRule != null) {

			for (String rule : validRule.rules()) {
				Object ruleException = formulaire.validRule(rule);

				if (ruleException == null
						|| !Map.class
								.isAssignableFrom(ruleException.getClass())) {
					populateRule(request, formulaire, rule, beans);
				}

				else if (Map.class.isAssignableFrom(ruleException.getClass())) {

					Map<String, RuleException> ruleExceptions = (Map<String, RuleException>) ruleException;
					for (String ruleIndex : ruleExceptions.keySet()) {
						populateRule(request, formulaire, UtilForm.getRule(
								rule, ruleIndex), beans);
					}
				}

			}

		}

		return new ModelAndView("ajax", beans);
	}

	private void populateRule(HttpServletRequest request,
			MyFormulaire formulaire, String ruleName,
			Map<String, List<String>> beans) {

		beans.put(ruleName, new ArrayList<String>());
		if (formulaire.getRuleExceptions(ruleName) != null) {
			for (RuleFormulaireException exception : formulaire
					.getRuleExceptions(ruleName)) {
				String message = messageSource.getMessage(exception
						.getException().getCode(), exception.getException()
						.getParameters().toArray(), request.getLocale());

				beans.get(ruleName).add(message);
			}
		}
	}
}
