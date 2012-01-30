package fr.urssaf.image.commons.spring.batch.support.listener;

import org.apache.log4j.Logger;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.stereotype.Component;

import fr.urssaf.image.commons.spring.batch.model.flat.Livre;

@Component
public class SkipListener {

   private static final Logger LOG = Logger.getLogger(SkipListener.class);

   @OnSkipInRead
   public void onSkipInRead(Throwable cause) {

      LOG.error("skip sur lecture", cause);
   }

   @OnSkipInProcess
   public void onSkipInProcess(Livre item, Throwable cause) {

      LOG.error("skip sur processus " + item.getTitre(), cause);
   }

   @OnSkipInWrite
   public void onSkipInWrite(Livre item, Throwable cause) {

      LOG.error("skip sur écriture " + item.getTitre(), cause);
   }
}
