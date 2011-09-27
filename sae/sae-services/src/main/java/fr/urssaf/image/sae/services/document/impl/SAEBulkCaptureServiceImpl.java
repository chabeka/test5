package fr.urssaf.image.sae.services.document.impl;

import org.springframework.beans.factory.annotation.Qualifier;
/**
 * 
 */
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.services.document.SAEBulkCaptureService;
import fr.urssaf.image.sae.services.exception.capture.SAECaptureServiceEx;
/**
 * Fournit l'implémentation des services pour la capture.<BR />
 * 
 * @author rhofir.
 */
@Service
@Qualifier("saeBulkCaptureService")
public class SAEBulkCaptureServiceImpl implements SAEBulkCaptureService {
   // CHECKSTYLE:OFF
   /**
    * {@inheritDoc}
    */
   @Override
   public void bulkCapture(String urlSommaire) throws SAECaptureServiceEx {
      // TODO Auto-generated method stub

   }
   // CHECKSTYLE:ON
}
