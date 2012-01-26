package fr.urssaf.image.commons.spring.batch.support.processor;

import javax.xml.bind.JAXBElement;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import fr.urssaf.image.commons.spring.batch.model.flat.Livre;
import fr.urssaf.image.commons.spring.batch.model.xml.LivreType;

@Component
public class ConversionToFileProcessor implements
      ItemProcessor<JAXBElement<LivreType>, Livre> {

   @Override
   public Livre process(JAXBElement<LivreType> item) {

      LivreType livreType = item.getValue();

      Livre livre = new Livre();
      livre.setAuteur(livreType.getAuteur());
      livre.setIdentifiant(livreType.getId());
      livre.setTitre(livreType.getTitre());

      return livre;
   }

}
