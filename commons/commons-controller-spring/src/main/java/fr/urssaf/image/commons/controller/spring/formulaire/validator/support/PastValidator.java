package fr.urssaf.image.commons.controller.spring.formulaire.validator.support;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import fr.urssaf.image.commons.controller.spring.formulaire.support.validator.ValidatorAbstract;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.Past;
import fr.urssaf.image.commons.util.date.DateCompareUtil;
import fr.urssaf.image.commons.util.date.DateFormatUtil;
import fr.urssaf.image.commons.util.date.DateUtil;

public class PastValidator implements ValidatorAbstract<Date, Past> {

	private Date today;
	
	private Date date;

	public boolean isValid(Date date) {
		this.date = date;
		return date != null ? DateCompareUtil.inf(date, today) : true;
	}

	public String getValidatorException() {
		return "exception.past";
	}

	public void initialize(Past parametres) {
		today = DateUtil.today();
	}

	public List<String> getExceptionParameters() {
		List<String> liste = new ArrayList<String>();

		liste.add(getLibelleValue(date));
		liste.add(getLibelleValue(today));

		return liste;
	}

	public String getLibelleValue(Date date) {
		return DateFormatUtil.dateFr(date);

	}

	@Override
	public Class<Past> getAnnotation() {
		return Past.class;
	}

}
