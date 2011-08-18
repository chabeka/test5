package fr.urssaf.image.sae.webservices.service.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.urssaf.image.sae.services.SAEServiceProvider;
import fr.urssaf.image.sae.services.document.SAEConsultationService;

/**
 * Classe de composant pour récuperer les Services exposé dans l'artefact
 * sae-services<br>
 * 
 * 
 */
@Component
public final class SAEServiceManager {

   @Autowired
   private SAEServiceProvider provider;

   /**
    * 
    * @return service de consultation du SAE
    */
   public SAEConsultationService loadSAEConsultationService() {

      return provider.getSaeDocumentService();

   }

}
