package fr.urssaf.image.commons.controller.spring.formulaire;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.CollectionFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.FieldException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.PopulateExceptionForm;

public class MyExceptionFormulaire {

	private Map<String, FieldException> exceptions = new HashMap<String, FieldException>();

	private PopulateExceptionForm popExceptionForm;
	
	public MyExceptionFormulaire(){
		this.popExceptionForm = new PopulateExceptionForm(exceptions);
	}
	
	public PopulateExceptionForm getPopulateExceptionForm(){
		return this.popExceptionForm;
	}
	
	
	public void clearException(String field) {
		this.exceptions.remove(field);
	}

	public void clearException() {
		this.exceptions.clear();
	}

	public Set<String> getFieldExceptions() {
		return this.exceptions.keySet();
	}

	public FieldException getException(String field) {
		return this.exceptions.get(field);
	}

	public boolean isEmpty(String field) {
		return this.exceptions.get(field) == null;
	}

	public boolean isEmpty() {
		return this.exceptions.isEmpty();
	}

	public boolean isCollectionFormulaireException(String field) {

		if (!this.isEmpty(field)
				&& CollectionFormulaireException.class
						.isAssignableFrom(this.exceptions.get(field).getClass())) {

			return true;

		}

		return false;
	}

	public boolean isTypeFormulaireException(String field) {

		if (!this.isEmpty(field)
				&& TypeFormulaireException.class
						.isAssignableFrom(this.exceptions.get(field).getClass())) {
			return true;

		}

		return false;
	}


}
