package fr.urssaf.image.commons.spring.batch.support.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import fr.urssaf.image.commons.spring.batch.model.xml.LivreType;

@Component
public class ControleProcessor implements ItemProcessor<LivreType, LivreType> {

   @Override
   public LivreType process(LivreType item) {

      return item;
   }

}
