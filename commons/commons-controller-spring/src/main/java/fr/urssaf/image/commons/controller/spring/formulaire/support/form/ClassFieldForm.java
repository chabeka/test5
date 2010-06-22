package fr.urssaf.image.commons.controller.spring.formulaire.support.form;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;


import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.UtilForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.bean.BeanFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.bean.BeanRuleFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.validator.ValidatorAbstract;
import fr.urssaf.image.commons.controller.spring.formulaire.support.validator.ValidatorAnnotation;
import fr.urssaf.image.commons.util.logger.Log;

public final class ClassFieldForm<F extends MyFormulaire> {

	private static final Logger log = Logger.getLogger(ClassFieldForm.class);

	private Map<String, BeanFormulaire> methodes = new HashMap<String, BeanFormulaire>();

	@SuppressWarnings("unchecked")
	private Map<String, List<ValidatorAbstract>> validators = new HashMap<String, List<ValidatorAbstract>>();

	private ClassRuleForm<F> classRuleForm;

	public ClassFieldForm(Class<? extends F> classe) {
		this.init(classe);

	}

	@SuppressWarnings("unchecked")
	private void init(Class<? extends F> classe) {

		for (PropertyDescriptor property : BeanUtils
				.getPropertyDescriptors(classe)) {

			BeanFormulaire bean = new BeanFormulaire();

			// traitement des bean
			if (property.getReadMethod() != null
					&& property.getWriteMethod() != null) {

				bean.setMethodRead(property.getReadMethod());
				bean.setMethodWrite(property.getWriteMethod());

				this.methodes.put(property.getName(), bean);
				this.validators.put(property.getName(),
						new ArrayList<ValidatorAbstract>());

				for (int j = 0; j < property.getReadMethod().getAnnotations().length; j++) {

					Annotation annotation = property.getReadMethod()
							.getAnnotations()[j];
					if (annotation.annotationType().isAnnotationPresent(
							ValidatorAnnotation.class)) {

						ValidatorAnnotation validatorClass = annotation
								.annotationType().getAnnotation(
										ValidatorAnnotation.class);

						initValidator(annotation, validatorClass, property
								.getName(), bean);

					}
				}
			}

		}

	}

	protected BeanFormulaire getMethode(String field) {
		UtilForm.FieldForm fieldForm = new UtilForm.FieldForm(field);
		return this.methodes.get(fieldForm.field);
	}

	protected Map<String, BeanFormulaire> getMethodes() {
		return this.methodes;
	}

	@SuppressWarnings("unchecked")
	protected Map<String, List<ValidatorAbstract>> getValidators() {
		return this.validators;
	}

	protected Map<String, BeanRuleFormulaire> getRules() {
		return this.classRuleForm.getRules();
	}

	@SuppressWarnings("unchecked")
	private void initValidator(Annotation annotation,
			ValidatorAnnotation validatorClass, String name, BeanFormulaire bean) {

		Class typeValidator = (Class) ((ParameterizedType) validatorClass
				.value().getGenericInterfaces()[0]).getActualTypeArguments()[0];

		Class classe = getClass(bean.getGenericType());
		if (!typeValidator.isAssignableFrom(classe)) {
			log.warn("l'annotation "
					+ annotation.annotationType().getSimpleName()
					+ " ne peut pas être appliqué sur le bean "+name+" de type "
					+ classe.getSimpleName() + " mais "
					+ typeValidator.getSimpleName());
			return;
		}

		try {
			Constructor<? extends ValidatorAbstract> constructor = validatorClass
					.value().getConstructor(new Class[0]);
			ValidatorAbstract validator = constructor.newInstance();
			validator.initialize(annotation);

			validators.get(name).add(validator);

		} catch (SecurityException e) {
			Log.throwException(log, e);
		} catch (NoSuchMethodException e) {
			Log.throwException(log, e);
		} catch (IllegalArgumentException e) {
			Log.throwException(log, e);
		} catch (InstantiationException e) {
			Log.throwException(log, e);
		} catch (IllegalAccessException e) {
			Log.throwException(log, e);
		} catch (InvocationTargetException e) {
			Log.throwException(log, e);
		}
	}

	@SuppressWarnings("unchecked")
	private Class getClass(Type type) {

		Class classe = type.getClass();

		if (ParameterizedType.class.isAssignableFrom(classe)) {

			ParameterizedType generic = (ParameterizedType) type;

			classe = (Class) generic.getRawType();

		} else if (Class.class.isAssignableFrom(classe)) {
			classe = (Class) type;
		}

		if (classe.isArray()) {

			return classe.getComponentType();

		} else if (Collection.class.isAssignableFrom(classe)) {

			if (ParameterizedType.class.isAssignableFrom(type.getClass())) {

				ParameterizedType generic0 = (ParameterizedType) type;

				return (Class) generic0.getActualTypeArguments()[0];

			}

			return String.class;

		} else if (Map.class.isAssignableFrom(classe)) {

			if (ParameterizedType.class.isAssignableFrom(type.getClass())) {

				ParameterizedType generic = (ParameterizedType) type;

				return getClass((Type) generic.getActualTypeArguments()[1]);

			}

			return String.class;

		} else {
			return classe;
		}

	}

}
