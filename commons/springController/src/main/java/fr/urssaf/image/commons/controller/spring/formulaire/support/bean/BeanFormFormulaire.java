package fr.urssaf.image.commons.controller.spring.formulaire.support.bean;

import java.lang.reflect.Method;

import fr.urssaf.image.commons.controller.spring.formulaire.support.annotation.Formulaire;

public class BeanFormFormulaire {

	private Formulaire form;

	private Method method;

	public BeanFormFormulaire(Formulaire form, Method method) {
		this.form = form;
		this.method = method;
	}

	public Formulaire getForm() {
		return form;
	}

	public Method getMethod() {
		return method;
	}

}
