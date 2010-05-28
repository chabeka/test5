package fr.urssaf.image.commons.controller.spring.formulaire.validator.support;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import fr.urssaf.image.commons.controller.spring.formulaire.support.validator.ValidatorAbstract;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.Futur;
import fr.urssaf.image.commons.util.date.DateCompareUtil;
import fr.urssaf.image.commons.util.date.DateUtil;

public class FuturValidator implements ValidatorAbstract<Date, Futur> {

	private Date today;

	private Date date;

	public boolean isValid(Date date) {
		this.date = date;
		return date != null ? DateCompareUtil.sup(date, today) : true;
	}

	public String getValidatorException() {
		return "exception.futur";
	}

	public void initialize(Futur parametres) {
		today = DateUtil.today();
	}

	public List<String> getExceptionParameters() {
		List<String> liste = new ArrayList<String>();

		liste.add(getLibelleValue(date));
		liste.add(getLibelleValue(today));

		return liste;
	}

	public String getLibelleValue(Date date) {
		return DateUtil.dateFr(date);

	}

	@Override
	public Class<Futur> getAnnotation() {
		return Futur.class;
	}
	
}
