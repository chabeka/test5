package fr.urssaf.image.commons.controller.spring.exemple.util;

import java.util.Collection;

public final class SelectUtil {
	
	private SelectUtil(){
		
	}

	@SuppressWarnings("unchecked")
	public static boolean contains(Object option, Object value) {

		if (value != null) {

			if (Collection.class.isAssignableFrom(value.getClass())) {

				return ((Collection) value).contains(option);
			}

			return option.equals(value);
		}

		return false;
	}
}
