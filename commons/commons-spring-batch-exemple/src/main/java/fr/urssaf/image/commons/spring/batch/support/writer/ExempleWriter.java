package fr.urssaf.image.commons.spring.batch.support.writer;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import fr.urssaf.image.commons.spring.batch.model.flat.Livre;

@Component
public class ExempleWriter implements ItemWriter<Livre> {

   private static final Logger LOG = Logger.getLogger(ExempleWriter.class);

   @Override
   public void write(List<? extends Livre> items) {

      for (Livre livre : items) {

         LOG.debug(livre);
      }

   }

}
