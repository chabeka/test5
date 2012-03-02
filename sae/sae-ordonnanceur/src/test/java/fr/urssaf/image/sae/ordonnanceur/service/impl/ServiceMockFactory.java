package fr.urssaf.image.sae.ordonnanceur.service.impl;

import org.easymock.EasyMock;
import org.springframework.stereotype.Component;

import fr.urssaf.image.sae.ordonnanceur.service.DecisionService;
import fr.urssaf.image.sae.ordonnanceur.service.JobService;
import fr.urssaf.image.sae.ordonnanceur.support.TraitementLauncherSupport;

/**
 * Implémentation des Mocks des services
 * 
 * 
 */
@Component
public class ServiceMockFactory {

   /**
    * 
    * @return instance de {@link JobService}
    */
   public final JobService createJobService() {

      JobService service = EasyMock.createMock(JobService.class);

      return service;
   }

   /**
    * 
    * @return instance de {@link DecisionService}
    */
   public DecisionService createDecisionService() {

      DecisionService service = EasyMock.createMock(DecisionService.class);

      return service;

   }

   /**
    * 
    * @return instance de {@link TraitementLauncherSupport}
    */
   public TraitementLauncherSupport createTraitementLauncherSupport() {

      TraitementLauncherSupport service = EasyMock
            .createMock(TraitementLauncherSupport.class);

      return service;

   }

}
