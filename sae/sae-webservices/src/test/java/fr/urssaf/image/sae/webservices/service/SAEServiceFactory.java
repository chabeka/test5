package fr.urssaf.image.sae.webservices.service;

import org.easymock.EasyMock;
import org.springframework.stereotype.Component;

import fr.urssaf.image.sae.services.capture.SAECaptureService;

/**
 * Implémentation des Mocks des services SAE
 * 
 * 
 */
@Component
public class SAEServiceFactory {

   /**
    * 
    * @return instance de {@link SAECaptureService}
    */
   public final SAECaptureService createSAECaptureService() {

      SAECaptureService service = EasyMock.createMock(SAECaptureService.class);

      return service;
   }
}
