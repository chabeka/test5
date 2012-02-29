package fr.urssaf.image.sae.ordonnanceur.service;

import java.util.List;
import java.util.Map;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;

import fr.urssaf.image.sae.ordonnanceur.exception.AucunJobALancerException;

/**
 * Service de décision pour sélectionner les traitements en masse à exécuter
 * 
 * 
 */
public interface DecisionService {

   /**
    * Choisit un des jobs à lancer à partir de la liste fournie, et renvoie son
    * identifiant
    * 
    * @param mapJobs
    *           liste des travaux en attente, en clé le nom du job et en valeur
    *           la liste des travaux associés
    * @param jobsEnCours
    *           liste des travaux en cours
    * @return identifiant du job à lancer
    * @throws AucunJobALancerException
    *            Exception levée si aucun job n'est à lancer
    */
   long trouverJobALancer(Map<String, List<JobInstance>> mapJobs,
         List<JobExecution> jobsEnCours) throws AucunJobALancerException;
}
