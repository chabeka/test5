package fr.urssaf.image.sae.ordonnanceur.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.ordonnanceur.exception.AucunJobALancerException;
import fr.urssaf.image.sae.ordonnanceur.exception.JobDejaReserveException;
import fr.urssaf.image.sae.ordonnanceur.exception.JobInexistantException;
import fr.urssaf.image.sae.ordonnanceur.service.CoordinationService;
import fr.urssaf.image.sae.ordonnanceur.service.DecisionService;
import fr.urssaf.image.sae.ordonnanceur.service.JobService;
import fr.urssaf.image.sae.ordonnanceur.support.TraitementLauncherSupport;

/**
 * implémentation du service {@link CoordinationService}
 * 
 * 
 */
@Service
public class CoordinationServiceImpl implements CoordinationService {

   private final DecisionService decisionService;

   private final JobService jobService;

   private final TraitementLauncherSupport captureMasseLauncher;

   /**
    * 
    * @param decisionService
    *           service de décision pour les traitements à lancer
    * @param jobService
    *           service de la pile des travaux
    * @param captureMasseLauncher
    *           service de lancement du traitement de la capture en masse
    */
   @Autowired
   public CoordinationServiceImpl(
         DecisionService decisionService,
         JobService jobService,
         @Qualifier("captureMasseLauncher") TraitementLauncherSupport captureMasseLauncher) {

      this.decisionService = decisionService;
      this.jobService = jobService;
      this.captureMasseLauncher = captureMasseLauncher;

   }

   /**
    * {@inheritDoc}
    * 
    */
   @Override
   public final long lancerTraitement() throws AucunJobALancerException {

      // Récupération d'un traitement à lancer
      JobInstance traitement = trouverJobALancer();

      // Récupération de l'identifiant du traitement de capture en masse
      this.captureMasseLauncher.lancerTraitement(traitement);

      // renvoie l'identifiant du traitement lancé
      return traitement.getId();

   }

   private JobInstance trouverJobALancer() throws AucunJobALancerException {

      // etape 1 : Récupération de la liste des traitements

      Collection<JobExecution> jobsEnCours = this.jobService.recupJobEnCours();

      Map<String, List<JobInstance>> mapJobs = this.jobService
            .recupJobsALancer();

      // Etape 2: Décision du traitement à lancer

      JobInstance traitement = this.decisionService.trouverJobALancer(mapJobs,
            jobsEnCours);

      // Etape3 : Réservation du traitement

      try {

         // TODO locker la table JobInstance avec Zookeeper

         this.jobService.reserveJob(traitement.getId());

      } catch (JobInexistantException e) {

         // le traitement n'existe plus, on chercher un nouveau traitement à
         // lancer
         traitement = trouverJobALancer();

      } catch (JobDejaReserveException e) {

         // le traitement est déjà réservé, on chercher un nouveau traitement à
         // lancer
         traitement = trouverJobALancer();

      } finally {

         // TODO libérer le lock la table JobInstance avec Zookeeper
      }

      // renvoie le traitement

      return traitement;
   }

}
