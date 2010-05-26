package fr.urssaf.image.commons.controller.spring.formulaire.support.form;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;


import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.UtilForm.FieldForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.bean.BeanFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.type.TypeForm;
import fr.urssaf.image.commons.util.logger.Log;

public final class PopulateForm<F extends MyFormulaire> {

	private static final Logger log = Logger.getLogger(PopulateForm.class);

	private TypeForm factory;

	private Map<String, BeanFormulaire> methodes;

	public PopulateForm(ClassForm<F> classe) {
		this.factory = classe.getTypeFactory();
		this.methodes = classe.getMethodes();

	}
	
	@SuppressWarnings("unchecked")
	private Object getData(Type type, String[] parametres, String key,
			String field, List<String> keys, Object map, F formulaire) {

		Object data = null;
		Class classe = type.getClass();

		if (ParameterizedType.class.isAssignableFrom(classe)) {

			ParameterizedType generic = (ParameterizedType) type;

			classe = (Class) generic.getRawType();

		} else if (Class.class.isAssignableFrom(classe)) {
			classe = (Class) type;
		}

		if (GenericArrayType.class.isAssignableFrom(classe)) {

			GenericArrayType generic = (GenericArrayType) type;

			data = this.getArray((Class) generic.getGenericComponentType(),
					parametres, key, field, formulaire);

		} else if (Collection.class.isAssignableFrom(classe)) {

			data = this.getCollection(type, parametres, key, field, formulaire);

		} else if (Map.class.isAssignableFrom(classe)) {

			data = this.getMap(type, parametres, key, field, formulaire, keys,
					map);

		} else {
			data = getData(classe, parametres, key, field, formulaire);
		}

		return data;
	}

	public void populate(String field, String[] values, F formulaire) {

		List<String> keyMethode = new ArrayList<String>();

		FieldForm fieldForm = new FieldForm((String) field);

		String key = fieldForm.field;
		List<String> keys = fieldForm.keys;

		Object data = null;
		if (methodes.containsKey(key)) {
			// on supprime les exceptions de ce champ
			formulaire.getException().clearException(field);
			keyMethode.add(key);
			BeanFormulaire bean = methodes.get(key);

			if (bean.getType().isArray()) {

				data = this.getArray(bean.getType().getComponentType(), values,
						key, key, formulaire);
			} else if (Collection.class.isAssignableFrom(bean.getType())) {

				data = this.getCollection(bean.getGenericType(), values, key,
						key, formulaire);

			} else if (Map.class.isAssignableFrom(bean.getType())) {

				try {

					Object objectRead = bean.read(formulaire);

					data = this.getMap(bean.getGenericType(), values, key,
							(String) field, formulaire, keys, objectRead);

				} catch (Exception e) {
					Log.throwException(log, e);
				}

			} else {

				data = getData(bean.getType(), values, key, key, formulaire);

			}

			try {

				bean.write(formulaire, data);

			} catch (Exception e) {
				Log.throwException(log, e);
			}
		}

	}

	@SuppressWarnings("unchecked")
	private Object getData(Class type, String[] parametres, String key,
			String field, F formulaire) {

		try {
			if (parametres.length > 1) {
				log
						.warn("le champ "
								+ key
								+ " devrait être une collection ou un tableau car il comporte plusieurs valeurs:"
								+ Log.getLogger(parametres, "|".charAt(0)));
			}
			return factory.getObject(key, type, parametres[0]);
		} catch (TypeFormulaireException e) {

			formulaire.getException().getPopulateExceptionForm()
					.putException(field, e);
			return null;

		}
	}

	@SuppressWarnings("unchecked")
	private Map<Object, Object> getMap(Class<? extends Map> type) {

		if (type.isInterface()) {
			return new HashMap<Object, Object>();
		}

		try {
			return type.newInstance();
		} catch (InstantiationException e) {
			Log.throwException(log, e);
		} catch (IllegalAccessException e) {
			Log.throwException(log, e);
		}

		return null;

	}

	@SuppressWarnings("unchecked")
	private Map getMap(Type type, String[] parametres, String key,
			String field, F formulaire, List<String> keys, Object map) {

		if (!keys.isEmpty()) {
			String keyMap = keys.get(0);
			keys.remove(0);
			Map<Object, Object> mapElement = (Map<Object, Object>) map;

			if (ParameterizedType.class.isAssignableFrom(type.getClass())) {

				ParameterizedType generic = (ParameterizedType) type;

				Class generic0 = (Class) generic.getActualTypeArguments()[0];
				Type generic1 = (Type) generic.getActualTypeArguments()[1];

				if (mapElement == null) {
					mapElement = this.getMap((Class<? extends Map>) generic
							.getRawType());
				}

				try {

					mapElement.put(factory.getObject(generic0, keyMap), this
							.getData(generic1, parametres, key, field, keys,
									mapElement.get(factory.getObject(generic0,
											keyMap)), formulaire));

				} catch (TypeFormulaireException e) {
					formulaire.getException().getPopulateExceptionForm()
							.putException(field, e);
				}

			} else if (Class.class.isAssignableFrom(type.getClass())) {

				if (mapElement == null) {
					mapElement = this.getMap(Map.class);
				}

				try {
					mapElement.put(keyMap, this.getData(String.class,
							parametres, key, field, keys, mapElement
									.get(factory
											.getObject(String.class, keyMap)),
							formulaire));
				} catch (TypeFormulaireException e) {
					formulaire.getException().getPopulateExceptionForm()
							.putException(field, e);
				}

			} else {
				log.error("la classe du parametre:" + type.getClass()
						+ " n'est pas prise en compte");
			}

			return mapElement;

		} else {
			log.error("le paramètre '" + field + "' doit comporter une clé");
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private Collection getCollection(Type type, String[] parametres,
			String key, String field, F formulaire) {

		Collection data = null;

		if (ParameterizedType.class.isAssignableFrom(type.getClass())) {

			ParameterizedType generic0 = (ParameterizedType) type;

			Class generic1 = (Class) generic0.getActualTypeArguments()[0];
			data = this
					.getCollection(
							(Class<? extends Collection<Object>>) generic0
									.getRawType(), generic1, parametres, key,
							field, formulaire);

		} else if (Class.class.isAssignableFrom(type.getClass())) {

			Class<? extends Collection<Object>> generic0 = (Class<? extends Collection<Object>>) type;
			data = this.getCollection(generic0, String.class, parametres, key,
					field, formulaire);

		} else {
			log.error("la classe du parametre:" + type.getClass()
					+ " n'est pas prise en compte");
		}

		return data;
	}

	@SuppressWarnings("unchecked")
	private Collection getCollection(Class<? extends Collection<Object>> type,
			Class classe, String[] parametres, String key, String field,
			F formulaire) {

		Collection<Object> liste = null;

		if (type.isInterface()) {

			if (List.class.isAssignableFrom(type)) {
				liste = new ArrayList<Object>();
			} else if (Set.class.isAssignableFrom(type)) {
				liste = new HashSet<Object>();
			} else {
				log.warn("catégorie:collection type:" + type
						+ " n'est pas implémentée ");
			}
		} else {
			try {
				liste = type.newInstance();
			} catch (InstantiationException e) {
				Log.throwException(log, e);
			} catch (IllegalAccessException e) {
				Log.throwException(log, e);
			}
		}

		if (liste != null) {

			for (int i = 0; i < parametres.length; i++) {
				try {

					Object data = factory.getObject(key, classe, parametres[i]);

					liste.add(data);

				} catch (TypeFormulaireException e) {
					formulaire.getException().getPopulateExceptionForm()
							.putCollectionException(field, e);

				}

			}

		}
		return liste;

	}

	@SuppressWarnings("unchecked")
	private Object getArray(Class classe, String[] parametres, String key,
			String field, F formulaire) {

		Object data = null;

		try {

			data = Array.newInstance(classe, parametres.length);

			for (int i = 0; i < parametres.length; i++) {
				try {
					Object dataElement = factory.getObject(key, classe,
							parametres[i]);

					Array.set(data, i, dataElement);
				} catch (ArrayIndexOutOfBoundsException e) {
					Log.throwException(log, e);
				} catch (IllegalArgumentException e) {
					Log.throwException(log, e);
				} catch (TypeFormulaireException e) {
					formulaire.getException().getPopulateExceptionForm()
							.putCollectionException(field, e);
				}
			}

		} catch (NegativeArraySizeException e) {
			Log.throwException(log, e);

		}

		return data;
	}

}
