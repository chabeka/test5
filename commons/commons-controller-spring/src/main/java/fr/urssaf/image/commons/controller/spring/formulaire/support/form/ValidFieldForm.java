package fr.urssaf.image.commons.controller.spring.formulaire.support.form;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.UtilForm.FieldForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.bean.BeanFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.AnnotationFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.CollectionFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.ValidatorFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.type.TypeForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.validator.ValidatorAbstract;
import fr.urssaf.image.commons.util.logger.Log;

/**
 * @author Bertrand BARAULT
 * 
 * @param <F>
 */
public final class ValidFieldForm<F extends MyFormulaire> {

	private static final Logger log = Logger.getLogger(ValidFieldForm.class);

	private TypeForm factory;

	@SuppressWarnings("unchecked")
	private Map<String, List<ValidatorAbstract>> validators;

	private Map<String, BeanFormulaire> methodes;

	public ValidFieldForm(ClassForm<F> classe) {
		this.factory = classe.getTypeFactory();
		this.validators = classe.getValidators();
		this.methodes = classe.getMethodes();
	}

	public void valid(F formulaire) {

		for (String field : this.methodes.keySet()) {
			this.valid(formulaire, field);
		}
	}

	@SuppressWarnings("unchecked")
	public void valid(F formulaire, String field) {

		if (formulaire.getException().isTypeFormulaireException(field)) {
			return;
		}

		FieldForm fieldForm = new FieldForm(field);
		String key = fieldForm.field;
		List<String> keys = fieldForm.keys;

		BeanFormulaire bean = this.methodes.get(key);

		List<ValidatorAbstract> validators = this.validators.get(key);

		Object data = null;

		if (validators != null && !validators.isEmpty()) {
			try {

				data = bean.read(formulaire);

			} catch (Exception e) {
				Log.throwException(log, e);
			}

			resetValidator(formulaire, field, data);

			valid(formulaire, data, validators, key, keys);

		}

	}

	private void resetValidator(F formulaire, String field, Object data) {

		if (data != null && Map.class.isAssignableFrom(data.getClass())) {

			for (String keyMap : formulaire.getException().getFieldExceptions()) {

				FieldForm fMap = new FieldForm(keyMap);
				if (field.equals(fMap.field)
						&& !formulaire.getException()
								.isTypeFormulaireException(keyMap)) {
					resetValidator(formulaire, keyMap);
				}

			}

		} else {
			resetValidator(formulaire, field);
		}
	}

	private void resetValidator(F formulaire, String field) {

		if (!formulaire.getException().isEmpty(field)) {

			if (formulaire.getException()
					.isCollectionFormulaireException(field)) {

				CollectionFormulaireException exception = (CollectionFormulaireException) formulaire
						.getException().getException(field);
				exception.getValidatorFormulaireExceptions().clear();
			} else {
				((AnnotationFormulaireException) formulaire.getException()
						.getException(field)).getValidatorFormulaireException()
						.clear();
			}

		}
	}

	@SuppressWarnings("unchecked")
	private void valid(F formulaire, Object data,
			List<ValidatorAbstract> validators, String field, List<String> keys) {

		if (data != null && data.getClass().isArray()) {

			validArray(formulaire, data, validators, field);

		} else if (data != null
				&& Collection.class.isAssignableFrom(data.getClass())) {

			validCollection(formulaire, data, validators, field);

		} else if (data != null && Map.class.isAssignableFrom(data.getClass())) {

			validMap(formulaire, data, validators, field, keys);

		} else {

			validData(formulaire, data, validators, field);

		}

	}

	@SuppressWarnings("unchecked")
	private void validCollection(F formulaire, Object data,
			List<ValidatorAbstract> validators, String field) {

		Collection collection = (Collection) data;
		for (Object dataCollection : collection) {

			for (ValidatorAbstract validator : validators) {
				try {
					this.valid(dataCollection, validator);
				} catch (ValidatorFormulaireException exception) {
					formulaire.getException().getPopulateExceptionForm()
							.putCollectionException(field, exception);
				}
			}

		}
	}

	@SuppressWarnings("unchecked")
	private void validData(F formulaire, Object data,
			List<ValidatorAbstract> validators, String field) {

		for (ValidatorAbstract validator : validators) {

			try {
				this.valid(data, validator);
			} catch (ValidatorFormulaireException exception) {
				formulaire.getException().getPopulateExceptionForm()
						.putException(field, exception);
			}

		}
	}

	@SuppressWarnings("unchecked")
	private void validArray(F formulaire, Object data,
			List<ValidatorAbstract> validators, String field) {

		for (int index = 0; index < Array.getLength(data); index++) {

			Object dataArray = Array.get(data, index);

			for (ValidatorAbstract validator : validators) {
				try {
					this.valid(dataArray, validator);
				} catch (ValidatorFormulaireException exception) {
					formulaire.getException().getPopulateExceptionForm()
							.putCollectionException(field, exception);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void validMap(F formulaire, Object data,
			List<ValidatorAbstract> validators, String field, List<String> keys) {

		String keySelected = null;
		if (!keys.isEmpty()) {
			keySelected = keys.get(0);
			keys.remove(0);
		}

		Map dataMap = (Map) data;
		for (Object key : dataMap.keySet()) {

			String index = factory.getValue(key.getClass(), key);

			if (keySelected == null || keySelected.equals(index)) {

				Object value = dataMap.get(key);

				String newField = field + "[" + index + "]";
				if (!formulaire.getException().isTypeFormulaireException(
						newField)) {
					valid(formulaire, value, validators, newField, keys);
				}

			}

		}
	}

	/**
	 * @param data
	 * @param validator
	 * @throws ValidatorFormulaireException
	 */
	@SuppressWarnings("unchecked")
	private void valid(Object data, ValidatorAbstract validator)
			throws ValidatorFormulaireException {

		boolean valide = validator.isValid(data);
		List<String> parameters = validator.getExceptionParameters();

		if (!valide) {

			ArrayList<Object> valeurs = new ArrayList<Object>();
			if (parameters != null) {
				for (String exceptionParameter : parameters) {
					valeurs.add(exceptionParameter);
				}
			}
			ValidatorFormulaireException exception = new ValidatorFormulaireException(
					valeurs, validator.getValidatorException());

			exception.setValidator(validator);
			throw exception;

		}
	}

}
