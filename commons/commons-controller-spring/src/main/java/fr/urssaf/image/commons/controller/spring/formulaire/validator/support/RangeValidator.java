package fr.urssaf.image.commons.controller.spring.formulaire.validator.support;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;


import fr.urssaf.image.commons.controller.spring.formulaire.support.validator.ValidatorAbstract;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.Range;
import fr.urssaf.image.commons.util.number.DoubleUtil;
import fr.urssaf.image.commons.util.number.NumberUtil;

public class RangeValidator implements ValidatorAbstract<Number, Range> {

	private double max;

	private double min;

	private boolean maxClose;

	private boolean minClose;

	private Number number;

	private String format;

	public boolean isValid(Number number) {
		this.number = number;
		if (number != null) {

			return DoubleUtil.range(number.doubleValue(), min, max, minClose,
					maxClose);
		}

		return true;

	}

	public String getValidatorException() {
		return "exception.range";
	}

	public void initialize(Range parametres) {
		max = parametres.max();
		min = parametres.min();
		maxClose = parametres.maxClose();
		minClose = parametres.minClose();
		format = parametres.format();

	}

	public List<String> getExceptionParameters() {
		List<String> liste = new ArrayList<String>();

		liste.add(NumberUtil.toString(number));

		Format format = new DecimalFormat(this.format);
		liste.add(format.format(min));
		liste.add(format.format(max));

		return liste;
	}

	public String getLibelleValue(Number value) {
		return NumberUtil.toString(value);
	}

	@Override
	public Class<Range> getAnnotation() {
		return Range.class;
	}

}
