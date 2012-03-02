package fr.urssaf.image.sae.ordonnanceur.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.ordonnanceur.exception.AucunJobALancerException;
import fr.urssaf.image.sae.ordonnanceur.service.DecisionService;
import fr.urssaf.image.sae.ordonnanceur.support.CaptureMasseSupport;

/**
 * Implémentation du sevice {@link DecisionService}
 * 
 * 
 */
@Service
public class DecisionServiceImpl implements DecisionService {

   @Autowired
   private CaptureMasseSupport captureMasseSupport;

   /**
    * {@inheritDoc}
    */
   @Override
   public final JobInstance trouverJobALancer(
         Map<String, List<JobInstance>> mapJobs,
         Collection<JobExecution> jobsEnCours) throws AucunJobALancerException {

      // vérification que des traitements sont à lancer
      if (mapJobs == null) {
         throw new AucunJobALancerException();
      }

      // pour l'instant la partie décisionnelle ne prend actuellement en compte
      // que les traitements d'archivage de masse.
      // si un nouveau type de traitement est ajouté, l'algo sera modifié.

      // on récupère les traitement de la capture en masse
      List<JobInstance> jobInstances = mapJobs
            .get(CaptureMasseSupport.CAPTURE_MASSE_JN);

      // vérification que des traitements de capture en masse sont à lancer
      if (CollectionUtils.isEmpty(jobInstances)) {
         throw new AucunJobALancerException();
      }

      // filtrage des capture en masse sur l'ECDE local
      jobInstances = captureMasseSupport
            .filtrerJobInstanceByECDELocal(jobInstances);

      // vérification que des traitements de capture en masse sur l'ECDE local
      // sont à lancer
      if (CollectionUtils.isEmpty(jobInstances)) {
         throw new AucunJobALancerException();
      }

      // filtrage des traitements en cours sur les capture en masse sur le
      // serveur courant
      if (jobsEnCours != null) {
         List<JobExecution> jobEnCours = captureMasseSupport
               .filtrerJobExecutionByECDELocal(jobsEnCours);

         // si un traitement de capture en masse est en cours alors aucun
         // traitement n'est à lancer sur le serveur
         if (!jobEnCours.isEmpty()) {
            throw new AucunJobALancerException();
         }

      }

      // on renvoie la capture en masse enn attente avec le plus petit
      // identifiant
      // on trie les traitements en attente en fonction de leur identifiant
      Collections.sort(jobInstances, new JobInstanceComparator());

      return jobInstances.get(0);
   }

   private static class JobInstanceComparator implements
         Comparator<JobInstance> {

      @Override
      public int compare(JobInstance job1, JobInstance job2) {

         return job1.getId().compareTo(job2.getId());
      }
   }

}
