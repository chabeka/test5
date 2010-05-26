package fr.urssaf.image.commons.controller.spring.formulaire.support.type;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class TypeFactory {

	protected static final Logger log = Logger.getLogger(TypeFactory.class);

	@SuppressWarnings("unchecked")
	private Map<Class, AbstractType> map = new HashMap<Class, AbstractType>();

	@SuppressWarnings("unchecked")
	private Map<Class, AbstractType> mapPrimitif = new HashMap<Class, AbstractType>();

	@SuppressWarnings("unchecked")
	private Map<String, AbstractType> mapField = new HashMap<String, AbstractType>();

	@SuppressWarnings("unchecked")
	public AbstractType getTypeFormulaires(String field, Class classe) {

		if (field != null && mapField.containsKey(field)) {
			return this.mapField.get(field);
		}

		if (classe.isPrimitive()) {
			return this.getTypeFormulaires(classe);

		} else if (map.containsKey(classe)) {
			return map.get(classe);
		}

		return null;
	}
	
	@SuppressWarnings("unchecked")
	private AbstractType getTypeFormulaires(Class classe){
		
		if ("int".equals(classe.getCanonicalName())) {
			return mapPrimitif.get(Integer.class);
		}
		if ("boolean".equals(classe.getCanonicalName())) {
			return mapPrimitif.get(Boolean.class);
		}
		if ("float".equals(classe.getCanonicalName())) {
			return mapPrimitif.get(Float.class);
		}
		if ("double".equals(classe.getCanonicalName())) {
			return mapPrimitif.get(Double.class);
		}
		if ("short".equals(classe.getCanonicalName())) {
			return mapPrimitif.get(Short.class);
		}
		if ("long".equals(classe.getCanonicalName())) {
			return mapPrimitif.get(Long.class);
		}
		if ("byte".equals(classe.getCanonicalName())) {
			return mapPrimitif.get(Byte.class);
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	public void addTypeFormulaire(Type type) {
		if (type.getClasse() != null) {
			map.put(type.getClasse(), type.getType());
		} else {
			mapField.put(type.getField(), type.getType());
		}
	}

	@SuppressWarnings("unchecked")
	protected void addTypeFormulaire(Class classe, AbstractType type) {
		mapPrimitif.put(classe, type);
	}

}
