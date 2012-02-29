package fr.urssaf.image.sae.ordonnanceur.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.ordonnanceur.exception.AucunJobALancerException;
import fr.urssaf.image.sae.ordonnanceur.service.DecisionService;

/**
 * Implémentation du sevice {@link DecisionService}
 * 
 * 
 */
@Service
public class DecisionServiceImpl implements DecisionService {

   /**
    * {@inheritDoc}
    */
   @Override
   public final long trouverJobALancer(Map<String, List<JobInstance>> mapJobs,
         List<JobExecution> jobsEnCours) throws AucunJobALancerException {
      // TODO Auto-generated method stub
      return 0;
   }

}
