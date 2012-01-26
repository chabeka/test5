package fr.urssaf.image.commons.spring.batch.support.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import fr.urssaf.image.commons.spring.batch.model.flat.Livre;

@Component
public class ExempleReader implements ItemReader<Livre>{

   @Override
   public Livre read()  {
    
      //renvoie un item de type Livre
      
      return null;
   }

}
