package fr.urssaf.image.sae.services.document;

import java.util.UUID;

import fr.urssaf.image.sae.services.document.exception.SAEConsultationServiceException;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;

/**
 * Ensemble des services de consultation du SAE
 * 
 */
public interface SAEConsultationService {

   /**
    * 
    * Service de consultation du SAE
    * 
    * @param idArchive
    *           identifiant unique de l'archive
    * @return Document correspondant à l'identifiant, peut-être null si
    *         l'identifiant n'existe pas dans le SAE
    * @throws SAEConsultationServiceException
    *            une exception est levée lors de la consultation
    */
   StorageDocument consultation(UUID idArchive)
         throws SAEConsultationServiceException;
}
