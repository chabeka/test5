package fr.urssaf.image.commons.spring.batch.support.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import fr.urssaf.image.commons.spring.batch.model.flat.Livre;
import fr.urssaf.image.commons.spring.batch.model.xml.LivreType;

@Component
public class ConversionToXMLProcessor implements
      ItemProcessor<Livre, LivreType> {

   @Override
   public LivreType process(Livre item) {

      LivreType livreType = new LivreType();

      livreType.setAuteur(item.getAuteur());
      livreType.setId(item.getIdentifiant());
      livreType.setTitre(item.getTitre());

      return livreType;
   }

}
