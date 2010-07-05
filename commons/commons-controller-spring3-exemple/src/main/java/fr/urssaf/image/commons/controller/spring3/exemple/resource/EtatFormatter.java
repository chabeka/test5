package fr.urssaf.image.commons.controller.spring3.exemple.resource;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import fr.urssaf.image.commons.controller.spring3.exemple.modele.Etat;
import fr.urssaf.image.commons.util.string.StringUtil;

public class EtatFormatter implements Formatter<Etat> {

	@Override
	public String print(Etat etat, Locale locale) {

		if (etat != null) {
			switch (etat) {
			case init:
				return "I";
			case close:
				return "C";
			case open:
				return "O";
			default:
				return "";
			}
		}

		return "";
	}

	@Override
	public Etat parse(String text, Locale locale) throws ParseException {
		if (StringUtil.notEmpty(text)) {

			if ("I".equals(text)) {
				return Etat.init;
			}

			else if ("C".equals(text)) {
				return Etat.close;
			}

			else if ("O".equals(text)) {
				return Etat.open;
			}

			else {

				throw new IllegalArgumentException("Could not parse etat: "
						+ text);

			}

		}

		return null;
	}
}
