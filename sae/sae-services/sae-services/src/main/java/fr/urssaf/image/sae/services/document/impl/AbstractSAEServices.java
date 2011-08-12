package fr.urssaf.image.sae.services.document.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import fr.urssaf.image.sae.storage.model.connection.StorageConnectionParameter;
import fr.urssaf.image.sae.storage.services.StorageServiceProvider;

/**
 * Classe abstraite contenant les attributs communs de toutes les
 * implementations:
 * <ul>
 * <li>{@link fr.urssaf.image.sae.services.document.SAECaptureService Capture} :
 * Implementation de capture unitaire et en masse,</li>
 * <li>{@link fr.urssaf.image.sae.services.document.SAESearchService Recherche}
 * : Implementation de recherche,</li>
 * <li>{@link fr.urssaf.image.sae.services.document.SAEConsultationService
 * Consultation} : Implementation de la consultation.</li>
 * </ul>
 * 
 * @author akenore,rhofir.
 * 
 */
@SuppressWarnings( { "PMD.AbstractClassWithoutAbstractMethod",
      "PMD.LongVariable" })
public abstract class AbstractSAEServices {
   @Autowired
   @Qualifier("storageServiceProvider")
   private StorageServiceProvider storageServiceProvider;
   @Autowired
   @Qualifier("storageConnectionParameter")
   private StorageConnectionParameter storageConnectionParameter;

   /**
    * @return La façade de services Storage DFCE.
    */
   public final StorageServiceProvider getStorageServiceProvider() {
      return storageServiceProvider;
   }

   /**
    * @param storageServiceProvider
    *           : La façade de services Storage DFCE.
    */
   public final void setStorageServiceProvider(
         StorageServiceProvider storageServiceProvider) {
      this.storageServiceProvider = storageServiceProvider;
   }

   /**
    * @return Les paramètres de connexion
    */
   public final StorageConnectionParameter getStorageConnectionParameter() {
      return storageConnectionParameter;
   }

   /**
    * @param storageConnectionParameter
    *           : Les paramètres de connexion
    */
   public final void setStorageConnectionParameter(
         StorageConnectionParameter storageConnectionParameter) {
      this.storageConnectionParameter = storageConnectionParameter;
   }

}
