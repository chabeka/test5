package fr.urssaf.image.sae.services.batch;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import fr.urssaf.image.sae.services.CommonsServices;
import fr.urssaf.image.sae.services.document.SAEBulkCaptureService;

@SuppressWarnings("all")
public class SAEBulkCaptureServiceImplTest extends CommonsServices {
   @Autowired
   @Qualifier("saeBulkCaptureService")
   SAEBulkCaptureService saeBulkCaptureService;

   private static final String SOMMAIRE = getSommaire() + "sommaire.xml";

   private static String getSommaire() {
      return "ecde://ecde.tu.recouv/TU/20110929/0000/";
   }

   @Test
   @Ignore
   public final void bulkCapture() {
      saeBulkCaptureService.bulkCapture(SOMMAIRE);
   }
}
