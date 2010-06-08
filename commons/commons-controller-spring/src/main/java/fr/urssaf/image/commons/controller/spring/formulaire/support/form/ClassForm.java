package fr.urssaf.image.commons.controller.spring.formulaire.support.form;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;


import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.UtilForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.annotation.Formulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.bean.BeanFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.bean.BeanRuleFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.type.TypeForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.validator.ValidatorAbstract;
import fr.urssaf.image.commons.util.logger.Log;

public final class ClassForm<F extends MyFormulaire> {

	private static final Logger log = Logger.getLogger(ClassForm.class);

	@SuppressWarnings("unchecked")
	private Map<Method, ClassForm> formulaires = new HashMap<Method, ClassForm>();

	@SuppressWarnings("unchecked")
	private Map<String, ClassForm> classForms = new HashMap<String, ClassForm>();

	private TypeForm typeFactory;

	private ClassRuleForm<F> classRuleForm;
	
	private ClassFieldForm<F> classFieldForm;

	public ClassForm(Class<? extends F> classe) {

		this.classFieldForm = new ClassFieldForm<F>(classe);
		this.classRuleForm = new ClassRuleForm<F>(classe);
		this.typeFactory = new TypeForm();
		for (int i = 0; i < classe.getMethods().length; i++) {
			Method methode = classe.getMethods()[i];
			for (int j = 0; j < methode.getAnnotations().length; j++) {
				Annotation annotation = methode.getAnnotations()[j];
				if (annotation.annotationType().isAssignableFrom(
						Formulaire.class)) {

					this.init(methode);

					classForms.put(UtilForm.getNameForm(methode), formulaires
							.get(methode));

				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void init(Method methode) {

		formulaires.put(methode, new ClassForm(
				(Class<? extends MyFormulaire>) methode.getReturnType()));
	}

	@SuppressWarnings("unchecked")
	public Map<String, MyFormulaire> init(F formulaire) {

		Map<String, MyFormulaire> forms = new HashMap<String, MyFormulaire>();
		for (Object formObject : this.formulaires.keySet()) {

			Method formMethod = (Method) formObject;

			ClassForm classF = (ClassForm) this.formulaires.get(formMethod);

			try {
				MyFormulaire myFormulaire = (MyFormulaire) formMethod.invoke(
						formulaire, new Object[0]);

				myFormulaire.initClassForm(classF);
				forms.put(UtilForm.getNameForm(formMethod), myFormulaire);

			} catch (IllegalArgumentException e) {
				Log.throwException(log, e);
			} catch (IllegalAccessException e) {
				Log.throwException(log, e);
			} catch (InvocationTargetException e) {
				Log.throwException(log, e);
			}
		}

		return forms;
	}

	public TypeForm getTypeFactory() {
		return this.typeFactory;
	}

	private char point = ".".charAt(0);

	public TypeForm getTypeFactory(String field) {

		if (!field.contains(String.valueOf(point))) {
			return getTypeFactory();
		}

		String form = field.substring(0, field.indexOf(point));
		return this.classForms.get(form).getTypeFactory(
				field.substring(field.indexOf(point) + 1));

	}

	public BeanFormulaire getMethode(String field) {
		return classFieldForm.getMethode(field);
	}
	
	public Set<String> getMethodeNames() {
		return classFieldForm.getMethodes().keySet();
	}

	protected Map<String, BeanFormulaire> getMethodes() {
		return classFieldForm.getMethodes();
	}

	@SuppressWarnings("unchecked")
	protected Map<String, List<ValidatorAbstract>> getValidators() {
		return classFieldForm.getValidators();
	}

	protected Map<String, BeanRuleFormulaire> getRules() {
		return this.classRuleForm.getRules();
	}
	
	@SuppressWarnings("unchecked")
	protected Map<Method, ClassForm> getFormulaires() {
		return this.formulaires;
	}

}
