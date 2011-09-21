package fr.urssaf.image.sae.services.document.impl;

import fr.urssaf.image.sae.services.document.SAECaptureService;
import fr.urssaf.image.sae.services.exception.capture.SAECaptureServiceEx;
import fr.urssaf.image.sae.services.messages.ServiceMessageHandler;
import fr.urssaf.image.sae.storage.dfce.contants.Constants;
import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;
import fr.urssaf.image.sae.storage.exception.InsertionServiceEx;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocuments;

/**
 * Fournit l'implémentation des services :<br>
 * <lu> <li>Capture unitaire.</li> <br>
 * <li>Capture en masse.</li> </lu>
 * 
 * @author akenore,rhofir.
 */
//@Service
//@Qualifier("saeCaptureService")
public class SAECaptureServiceImpl extends AbstractSAEServices implements
      SAECaptureService {

   /**
    * {@inheritDoc}
    */
   public final void bulkCapture(String urlEcde) throws SAECaptureServiceEx {
      try {
         getStorageServiceProvider().setStorageServiceProviderParameter(
               getStorageConnectionParameter());
         getStorageServiceProvider().getStorageConnectionService()
               .openConnection();
         getStorageServiceProvider().getStorageDocumentService()
               .bulkInsertStorageDocument(new StorageDocuments(), true);
      } catch (ConnectionServiceEx except) {
         throw new SAECaptureServiceEx(ServiceMessageHandler
               .getMessage(Constants.CNT_CODE_ERROR), except.getMessage(),
               except);
      } catch (InsertionServiceEx except) {
         throw new SAECaptureServiceEx(ServiceMessageHandler
               .getMessage(Constants.CNT_CODE_ERROR), except.getMessage(),
               except);
      } finally {
         getStorageServiceProvider().getStorageConnectionService()
               .closeConnexion();
      }
   }

}
