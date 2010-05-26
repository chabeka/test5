package fr.urssaf.image.commons.controller.spring.exemple.resource;

import org.springframework.stereotype.Component;


import fr.urssaf.image.commons.controller.spring.exemple.modele.Etat;
import fr.urssaf.image.commons.controller.spring.formulaire.TypeResource;
import fr.urssaf.image.commons.controller.spring.formulaire.support.type.Type;

@Component
public class ExempleRessource {

	public ExempleRessource(){
		Type<Etat> type = new Type<Etat>(Etat.class, new EtatType());
		TypeResource.getInstance().addTypeFormulaire(type);
	}
}
