package fr.urssaf.image.sae.ordonnanceur.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.commons.cassandra.spring.batch.dao.CassandraJobExecutionDao;
import fr.urssaf.image.commons.cassandra.spring.batch.dao.CassandraJobInstanceDao;
import fr.urssaf.image.commons.cassandra.spring.batch.dao.CassandraStepExecutionDao;
import fr.urssaf.image.sae.ordonnanceur.exception.JobDejaReserveException;
import fr.urssaf.image.sae.ordonnanceur.exception.JobInexistantException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
      "/applicationContext-sae-ordonnanceur-test.xml",
      "/applicationContext-sae-ordonnanceur-datasource-test.xml" })
@SuppressWarnings("PMD.MethodNamingConventions")
public class JobServiceTest {

   @Autowired
   private JobService jobService;

   @Autowired
   private CassandraJobInstanceDao jobInstanceDao;

   @Autowired
   private CassandraJobExecutionDao jobExecutionDao;

   @Autowired
   private CassandraStepExecutionDao stepExecutionDao;

   private JobInstance jobInstance;

   @Before
   public void before() {

      // création d'un traitement

      Map<String, JobParameter> parameters = new HashMap<String, JobParameter>();
      parameters.put("id", new JobParameter(UUID.randomUUID().toString()));
      JobParameters jobParameters = new JobParameters(parameters);
      jobInstance = jobInstanceDao.createJobInstance("jobTest", jobParameters);
   }

   @After
   public void after() {

      // suppression du traitement
      if (jobInstance != null) {

         jobInstanceDao.deleteJobInstance(jobInstance.getId(), jobExecutionDao,
               stepExecutionDao);
      }
   }

   @Test
   public void recupJobEnCours_success() {

      JobExecution jobExecution = new JobExecution(jobInstance);
      jobExecutionDao.saveJobExecution(jobExecution);

      // récupération des traitements en cours
      Collection<JobExecution> jobEnCours = jobService.recupJobEnCours();

      Assert.assertTrue("la liste des job en cours doit être non vide",
            !jobEnCours.isEmpty());

   }

   @Test
   public void recupJobsALancer_success() {

      String jobName = jobInstance.getJobName();

      // récupération des traitements à lancer
      Map<String, List<JobInstance>> jobsAlancer = jobService
            .recupJobsALancer();

      Assert.assertTrue("la liste des job à lancer doit être non vide",
            !jobsAlancer.isEmpty());

      Assert.assertTrue("la liste des job à lancer doit contenir " + jobName,
            jobsAlancer.containsKey(jobName));

      Assert.assertTrue("la liste des job à lancer doit être non vide pour "
            + jobName, !jobsAlancer.get(jobName).isEmpty());
   }

   @Test
   public void reserveJob_success() throws JobInexistantException,
         JobDejaReserveException {

      long idJob = 1;
      jobService.reserveJob(idJob);
   }
}
