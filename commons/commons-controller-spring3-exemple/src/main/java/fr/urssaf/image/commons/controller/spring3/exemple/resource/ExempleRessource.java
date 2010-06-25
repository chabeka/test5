package fr.urssaf.image.commons.controller.spring3.exemple.resource;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import fr.urssaf.image.commons.controller.spring3.exemple.modele.Etat;

@Component
public class ExempleRessource {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Etat.class, new EtatEditor());
	}
}
