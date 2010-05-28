package fr.urssaf.image.commons.controller.spring.formulaire.support.form;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.annotation.Rule;
import fr.urssaf.image.commons.controller.spring.formulaire.support.bean.BeanRuleFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.rule.RuleException;

public final class ClassRuleForm<F extends MyFormulaire> {

	private static final Logger log = Logger.getLogger(ClassRuleForm.class);

	private Map<String, BeanRuleFormulaire> rules = new HashMap<String, BeanRuleFormulaire>();

	public ClassRuleForm(Class<? extends F> classe) {

		this.init(classe);
	}

	private void init(Class<? extends F> classe) {

		for (int i = 0; i < classe.getMethods().length; i++) {
			Method methode = classe.getMethods()[i];

			for (int j = 0; j < methode.getAnnotations().length; j++) {

				Annotation annotation = methode.getAnnotations()[j];
				if (annotation.annotationType().isAssignableFrom(Rule.class)) {

					initRule(methode, (Rule) annotation);

				}

			}

		}
	}

	protected Map<String, BeanRuleFormulaire> getRules() {
		return this.rules;
	}

	private boolean checkRule(Method methode) {

		StringBuffer warn = new StringBuffer();
		warn.append("la règle " + methode.getName() + " ");

		if (methode.getParameterTypes().length > 0) {

			warn.append("ne doit pas comporter de paramètre ");
			warn.append("pour être prise en compte");
			log.warn(warn.toString());

			return false;

		}

		if (List.class.isAssignableFrom(methode.getReturnType())
				&& ParameterizedType.class.isAssignableFrom(methode
						.getGenericReturnType().getClass())) {

			return checkListRule(methode);

		}

		if (Map.class.isAssignableFrom(methode.getReturnType())
				&& ParameterizedType.class.isAssignableFrom(methode
						.getGenericReturnType().getClass())) {

			return checkMapRule(methode);

		}

		if (RuleException.class.isAssignableFrom(methode.getReturnType())) {

			return true;
		}

		warn
				.append("ne renvoie pas une liste ou une Map de type RuleException ou simplement une RuleException");
		log.warn(warn.toString());

		return false;
	}

	@SuppressWarnings("unchecked")
	private boolean checkListRule(Method methode) {

		ParameterizedType type = (ParameterizedType) methode
				.getGenericReturnType();

		if (Class.class.isAssignableFrom(type.getActualTypeArguments()[0]
				.getClass())
				&& RuleException.class.isAssignableFrom((Class) type
						.getActualTypeArguments()[0])) {

			return true;
		}

		return false;

	}

	@SuppressWarnings("unchecked")
	private boolean checkMapRule(Method methode) {

		ParameterizedType generic = (ParameterizedType) methode
				.getGenericReturnType();

		Object generic0 = generic.getActualTypeArguments()[0];
		if (Class.class.isAssignableFrom(generic0.getClass())
				&& String.class.isAssignableFrom((Class) generic0)) {

			Object generic1 = generic.getActualTypeArguments()[1];

			if (Class.class.isAssignableFrom(generic1.getClass())
					&& RuleException.class.isAssignableFrom((Class) generic1)) {

				return true;
			}

		}

		return false;

	}

	private void initRule(Method methode, Rule rule) {

		if (this.checkRule(methode)) {

			BeanRuleFormulaire beanRule = new BeanRuleFormulaire(rule, methode);

			this.rules.put(methode.getName(), beanRule);
		}

	}

}
