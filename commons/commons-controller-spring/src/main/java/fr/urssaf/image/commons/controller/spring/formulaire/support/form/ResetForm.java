package fr.urssaf.image.commons.controller.spring.formulaire.support.form;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.UtilForm.FieldForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.bean.BeanFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.type.TypeForm;
import fr.urssaf.image.commons.util.logger.Log;

public final class ResetForm<F extends MyFormulaire> {

	private static final Logger log = Logger.getLogger(ResetForm.class);

	private TypeForm factory;

	private Map<String, BeanFormulaire> methodes;

	public ResetForm(ClassForm<F> classe) {
		this.factory = classe.getTypeFactory();
		this.methodes = classe.getMethodes();

	}

	@SuppressWarnings("unchecked")
	public void reset(String field, F formulaire) {

		FieldForm fieldForm = new FieldForm((String) field);
		String key = fieldForm.field;
		List<String> keys = fieldForm.keys;

		if (methodes.containsKey(key)) {
			// on supprime les exceptions de ce champ
			formulaire.getException().clearException(field);
			BeanFormulaire bean = methodes.get(key);

			if (Map.class.isAssignableFrom(bean.getType())) {

				try {

					Object obj = bean.read(formulaire);
					if (obj != null) {
						if (!keys.isEmpty() && Map.class.isInstance(obj)) {

							reset(bean.getGenericType(), keys.get(0), keys,
									(Map) obj);

						} else {
							formulaire.getException().clearException(key);

							bean.write(formulaire, null);

						}
					}
				} catch (Exception e) {
					Log.throwException(log, e);
				}

			}

			else {

				try {

					bean.write(formulaire, null);

				} catch (Exception e) {
					Log.throwException(log, e);
				}
			}

		} else {
			log.warn("le champ " + key
					+ " n'est pas un champ correct du formulaire");
		}

	}

	@SuppressWarnings("unchecked")
	private void reset(Type type, String key, List<String> keys,
			Map<Object, Object> map) {

		if (!keys.isEmpty()) {
			String keyMap = keys.get(0);
			Object obj = null;
			Class<?> classe = null;
			Type generic1 = null;
			if (ParameterizedType.class.isAssignableFrom(type.getClass())) {

				ParameterizedType generic = (ParameterizedType) type;

				classe = (Class) generic.getActualTypeArguments()[0];
				generic1 = (Type) generic.getActualTypeArguments()[1];
				try {

					obj = map.get(factory.getObject(classe, keyMap));

				} catch (TypeFormulaireException e) {
					log.error(e.getMessage());
				}

			} else if (Class.class.isAssignableFrom(type.getClass())) {

				obj = map.get(keyMap);
				classe = String.class;
				generic1 = String.class;

			} else {
				log.error("la classe du parametre:" + type.getClass()
						+ " n'est pas prise en compte");
			}

			if (obj != null) {

				if (keys.size() > 1 && Map.class.isInstance(obj)) {
					keys.remove(0);
					this
							.reset(generic1, keys.get(0), keys, Map.class
									.cast(obj));
				} else {
					try {
						map.put(factory.getObject(classe, key), null);
					} catch (TypeFormulaireException e) {
						log.error(e.getMessage());
					}
				}
			}

		} else {
			log.error("le paramètre '" + key + "' doit comporter une clé");
		}
	}

}
