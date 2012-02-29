package fr.urssaf.image.sae.ordonnanceur.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.ordonnanceur.exception.JobDejaReserveException;
import fr.urssaf.image.sae.ordonnanceur.exception.JobInexistantException;
import fr.urssaf.image.sae.ordonnanceur.service.JobService;

/**
 * Implémentation du service {@link JobService}
 * 
 * 
 */
@Service
public class JobServiceImpl implements JobService {

   /**
    * {@inheritDoc}
    */
   @Override
   public final List<JobExecution> recupJobEnCours() {
      // TODO Auto-generated method stub
      return null;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final Map<String, List<JobInstance>> recupJobsALancer() {
      // TODO Auto-generated method stub
      return null;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final void reserveJob(long idJob) throws JobInexistantException,
         JobDejaReserveException {
      // TODO Auto-generated method stub

   }

}
