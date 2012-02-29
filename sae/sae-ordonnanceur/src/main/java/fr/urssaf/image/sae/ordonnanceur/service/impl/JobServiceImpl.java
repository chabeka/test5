package fr.urssaf.image.sae.ordonnanceur.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.urssaf.image.commons.cassandra.spring.batch.dao.CassandraJobExecutionDao;
import fr.urssaf.image.commons.cassandra.spring.batch.dao.CassandraJobInstanceDao;
import fr.urssaf.image.sae.ordonnanceur.exception.JobDejaReserveException;
import fr.urssaf.image.sae.ordonnanceur.exception.JobInexistantException;
import fr.urssaf.image.sae.ordonnanceur.service.JobService;

/**
 * Implémentation du service {@link JobService}.<br>
 * <br>
 * la persistance des traitements s'appuie sur Spring Batch.<br>
 * L'implémentation de la persistance est fournié par Cassandra.
 * 
 * 
 * 
 */
@Service
public class JobServiceImpl implements JobService {

   private final CassandraJobExecutionDao jobExecutionDao;

   private final CassandraJobInstanceDao jobInstanceDao;

   /**
    * 
    * @param jobExecutionDao
    *           dao d'exécution des job
    * @param jobInstanceDao
    *           dao d'instanciation des job
    */
   @Autowired
   public JobServiceImpl(CassandraJobExecutionDao jobExecutionDao,
         CassandraJobInstanceDao jobInstanceDao) {

      this.jobExecutionDao = jobExecutionDao;
      this.jobInstanceDao = jobInstanceDao;

   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final Collection<JobExecution> recupJobEnCours() {

      Collection<JobExecution> jobExecutions = jobExecutionDao
            .getRunningJobExecutions();

      return jobExecutions;

   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final Map<String, List<JobInstance>> recupJobsALancer() {

      Map<String, List<JobInstance>> jobInstancesMap = new HashMap<String, List<JobInstance>>();

      List<JobInstance> jobInstances = jobInstanceDao
            .getUnreservedJobInstances();

      for (JobInstance jobInstance : jobInstances) {

         String jobName = jobInstance.getJobName();
         if (!jobInstancesMap.containsKey(jobName)) {
            jobInstancesMap.put(jobName, new ArrayList<JobInstance>());
         }

         jobInstancesMap.get(jobName).add(jobInstance);

      }

      return jobInstancesMap;
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
