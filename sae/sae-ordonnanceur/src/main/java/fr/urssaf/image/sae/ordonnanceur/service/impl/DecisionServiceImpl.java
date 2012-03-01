package fr.urssaf.image.sae.ordonnanceur.service.impl;

import java.net.URI;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.ecde.modele.source.EcdeSource;
import fr.urssaf.image.sae.ecde.modele.source.EcdeSources;
import fr.urssaf.image.sae.ordonnanceur.exception.AucunJobALancerException;
import fr.urssaf.image.sae.ordonnanceur.service.DecisionService;

/**
 * Implémentation du sevice {@link DecisionService}
 * 
 * 
 */
@Service
public class DecisionServiceImpl implements DecisionService {

   private static final String CAPTURE_MASSE_JN = "capture_masse";

   private static final String CAPTURE_MASSE_ECDE = "capture.masse.sommaire";

   @Autowired
   private EcdeSources ecdeSources;

   /**
    * {@inheritDoc}
    */
   @Override
   public final long trouverJobALancer(Map<String, List<JobInstance>> mapJobs,
         List<JobExecution> jobsEnCours) throws AucunJobALancerException {

      // vérification que des traitements sont à lancer
      if (mapJobs == null) {
         throw new AucunJobALancerException();
      }

      // pour l'instant la partie décisionnelle ne prend actuellement en compte
      // que les traitements d'archivage de masse.
      // si un nouveau type de traitement est ajouté, l'algo sera modifié.

      // on récupère les traitement de la capture en masse
      List<JobInstance> jobInstances = mapJobs.get(CAPTURE_MASSE_JN);

      // vérification que des traitements de capture en masse sont à lancer
      if (CollectionUtils.isEmpty(jobInstances)) {
         throw new AucunJobALancerException();
      }

      // filtrage des capture en masse sur l'ECDE local
      jobInstances = filtrerCaptureMasseByECDE(jobInstances);

      // vérification que des traitements de capture en masse sur l'ECDE local
      // sont à lancer
      if (CollectionUtils.isEmpty(jobInstances)) {
         throw new AucunJobALancerException();
      }

      // filtrage des traitements en cours sur les capture en masse sur le
      // serveur courant
      if (jobsEnCours != null) {
         List<JobExecution> jobEnCours = filtrerCaptureMasseEnCours(jobsEnCours);

         // si un traitement de capture en masse est en cours alors aucun
         // traitement n'est à lancer sur le serveur
         if (!jobEnCours.isEmpty()) {
            throw new AucunJobALancerException();
         }

      }

      // on renvoie la capture en masse enn attente avec le plus petit
      // identifiant
      // on trie les traitements en attente en fonction de leur identifiant
      sortCaptureMasseEnCoursByECDE(jobInstances);

      long idJobAlancer = jobInstances.get(0).getId();

      return idJobAlancer;
   }

   private List<JobInstance> filtrerCaptureMasseByECDE(
         List<JobInstance> jobInstances) {

      @SuppressWarnings("unchecked")
      List<JobInstance> jobCaptureMasse = (List<JobInstance>) CollectionUtils
            .select(jobInstances, new Predicate() {

               @Override
               public boolean evaluate(Object object) {

                  boolean isLocal = evaluateLocal((JobInstance) object);

                  return isLocal;
               }

            });

      return jobCaptureMasse;
   }

   private EcdeSource loadEcdeSource(URI ecdeURL) {

      EcdeSource source = null;

      for (EcdeSource ecdeSource : ecdeSources.getSources()) {

         if (StringUtils.equals(ecdeURL.getAuthority(), ecdeSource.getHost())) {

            source = ecdeSource;
            break;

         }
      }

      return source;
   }

   private List<JobExecution> filtrerCaptureMasseEnCours(
         List<JobExecution> jobInstances) {

      @SuppressWarnings("unchecked")
      List<JobExecution> jobCaptureMasse = (List<JobExecution>) CollectionUtils
            .select(jobInstances, new Predicate() {

               @Override
               public boolean evaluate(Object object) {

                  JobInstance jobInstance = ((JobExecution) object)
                        .getJobInstance();

                  boolean isLocal = evaluateLocal(jobInstance);

                  boolean isCaptureMasse = evaluateCaptureMasse(jobInstance);

                  return isLocal && isCaptureMasse;
               }

            });

      return jobCaptureMasse;
   }

   private boolean evaluateLocal(JobInstance jobInstance) {

      URI ecdeURL = URI.create(jobInstance.getJobParameters().getString(
            CAPTURE_MASSE_ECDE));

      EcdeSource ecdeSource = loadEcdeSource(ecdeURL);

      boolean isLocal = false;

      if (ecdeSource != null) {
         isLocal = ecdeSource.isLocal();
      }

      return isLocal;
   }

   private boolean evaluateCaptureMasse(JobInstance jobInstance) {

      boolean isCaptureMasse = CAPTURE_MASSE_JN
            .equals(jobInstance.getJobName());

      return isCaptureMasse;
   }

   private void sortCaptureMasseEnCoursByECDE(List<JobInstance> jobInstances) {

      Comparator<JobInstance> jobComparator = new Comparator<JobInstance>() {

         @Override
         public int compare(JobInstance job1, JobInstance job2) {

            return job1.getId().compareTo(job2.getId());
         }

      };
      Collections.sort(jobInstances, jobComparator);
   }

}
