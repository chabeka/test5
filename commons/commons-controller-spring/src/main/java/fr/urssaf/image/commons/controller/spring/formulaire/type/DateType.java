package fr.urssaf.image.commons.controller.spring.formulaire.type;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;


import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.FormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.type.AbstractType;

public class DateType implements AbstractType<Date> {

	private boolean obligatoire;
	
	private String dateFormat;
	
	public DateType(){
		this("dd/MM/yyyy");
	}
	
	public DateType(String dateFormat){
		this.dateFormat = dateFormat;
	}

	public Date getObject(String value) throws TypeFormulaireException {

		if (!obligatoire && StringUtils.isBlank(value)){
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(dateFormat,Locale.getDefault());

		try {
			format.parse(value);
		} catch (ParseException e) {
			ArrayList<Object> valeurs = new ArrayList<Object>();
			valeurs.add(value);
			valeurs.add(dateFormat);
			throw new TypeFormulaireException(value, Date.class,
					new FormulaireException(valeurs, "exception.date.format"));
		}

		try {
			format.setLenient(false);
			return format.parse(value);
		} catch (ParseException e) {
			ArrayList<Object> valeurs = new ArrayList<Object>();
			valeurs.add(value);
			throw new TypeFormulaireException(value, Date.class,
					new FormulaireException(valeurs, "exception.date"));
		}

	}
	
	public boolean getObligatoire() {
		return this.obligatoire;
	}

	public void setObligatoire(boolean obligatoire) {
		this.obligatoire = obligatoire;
	}

	public String getValue(Date object) {
		if (object != null) {
			SimpleDateFormat format = new SimpleDateFormat(dateFormat,Locale.getDefault());
			return format.format(object);
		} 
			
		return null;
	}

}
