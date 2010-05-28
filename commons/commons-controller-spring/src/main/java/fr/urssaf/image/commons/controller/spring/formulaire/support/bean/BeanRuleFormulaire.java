package fr.urssaf.image.commons.controller.spring.formulaire.support.bean;

import java.lang.reflect.Method;

import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.annotation.Rule;

public class BeanRuleFormulaire {

	private Rule rule;

	private Method method;

	public BeanRuleFormulaire(Rule rule, Method method) {
		this.rule = rule;
		this.method = method;
	}

	public Rule getRule() {
		return rule;
	}

	public Object getRuleExceptions(MyFormulaire formulaire) throws Exception {

		return method.invoke(formulaire, new Object[] {});
	}
	
	@SuppressWarnings("unchecked")
	public Class getType() {
		return this.method.getReturnType();
	}

}
