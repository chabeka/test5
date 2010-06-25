package fr.urssaf.image.commons.controller.spring3.exemple.resource;

import java.beans.PropertyEditorSupport;

import fr.urssaf.image.commons.controller.spring3.exemple.modele.Etat;
import fr.urssaf.image.commons.util.string.StringUtil;

public class EtatEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		if (StringUtil.notEmpty(text)) {

			try {

				setValue(Etat.valueOf(text));
			} catch (IllegalArgumentException e) {

				throw new IllegalArgumentException("Could not parse etat: "
						+ e.getMessage(), e);

			}
		}

		setValue(null);
	}

	@Override
	public String getAsText() {
		Etat etat = (Etat) getValue();
		return (etat != null ? etat.name() : "");
	}

}
