package fr.urssaf.image.sae.services.document.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.ecde.service.EcdeServices;
import fr.urssaf.image.sae.services.batch.BulkCaptureJobWrapper;
import fr.urssaf.image.sae.services.document.SAEBulkCaptureService;

/**
 * Fournit l'implémentation des services pour la capture.<BR />
 * 
 * @author rhofir.
 */
@Service
@Qualifier("saeBulkCaptureService")
public class SAEBulkCaptureServiceImpl implements SAEBulkCaptureService {
   @Autowired
   private TaskExecutor taskExecutor;
   @Autowired
   @Qualifier("ecdeServices")
   private EcdeServices ecdeServices;

   /**
    * {@inheritDoc}
    * puisqu'on est dans une thread séparée
    */
   public final void bulkCapture(String urlEcde) {
      BulkCaptureJobWrapper bulkCaptureJob = new BulkCaptureJobWrapper(
            urlEcde, ecdeServices);
      taskExecutor.execute(bulkCaptureJob);
   }

}
