package fr.urssaf.image.sae.ordonnanceur.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.ordonnanceur.exception.AucunJobALancerException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-sae-ordonnanceur-test.xml" })
@SuppressWarnings("PMD.MethodNamingConventions")
public class DecisionServiceTest {

   private static final String CAPTURE_MASSE_JN = "capture_masse";

   private static final String CER69_SOMMAIRE = "ecde://ecde.cer69.recouv/sommaire.xml";

   private static final String CER44_SOMMAIRE = "ecde://ecde.cer44.recouv/sommaire.xml";

   @Autowired
   private DecisionService decisionService;

   @Test
   public void decisionService_success() throws AucunJobALancerException {

      List<JobExecution> jobsEnCours = new ArrayList<JobExecution>();

      jobsEnCours.add(createJobExecutionCaptureMasse(528, CAPTURE_MASSE_JN,
            CER44_SOMMAIRE));
      jobsEnCours.add(createJobExecutionCaptureMasse(435, "autre_traitement",
            CER69_SOMMAIRE));

      Map<String, List<JobInstance>> mapJobs = new HashMap<String, List<JobInstance>>();

      List<JobInstance> jobCaptureMasse = new ArrayList<JobInstance>();

      jobCaptureMasse.add(createJobCaptureMasse(458, CAPTURE_MASSE_JN,
            CER69_SOMMAIRE));
      jobCaptureMasse.add(createJobCaptureMasse(202, CAPTURE_MASSE_JN,
            CER44_SOMMAIRE));
      jobCaptureMasse.add(createJobCaptureMasse(256, CAPTURE_MASSE_JN,
            CER69_SOMMAIRE));
      jobCaptureMasse.add(createJobCaptureMasse(607, CAPTURE_MASSE_JN,
            CER44_SOMMAIRE));

      mapJobs.put(CAPTURE_MASSE_JN, jobCaptureMasse);

      long idJob = decisionService.trouverJobALancer(mapJobs, jobsEnCours);

      Assert.assertEquals("l'identifiant du job à lancer est inattendu", 256,
            idJob);

   }

   /**
    * aucun traitement en attente renseigné
    * 
    */
   @Test(expected = AucunJobALancerException.class)
   public void decisionService_failure_noJobEnAttente_1()
         throws AucunJobALancerException {

      List<JobExecution> jobsEnCours = new ArrayList<JobExecution>();

      decisionService.trouverJobALancer(null, jobsEnCours);

   }

   /**
    * aucun traitement de capture en masse en attente renseigné
    * 
    */
   @Test(expected = AucunJobALancerException.class)
   public void decisionService_failure_noJobEnAttente_2()
         throws AucunJobALancerException {

      List<JobExecution> jobsEnCours = new ArrayList<JobExecution>();
      Map<String, List<JobInstance>> mapJobs = new HashMap<String, List<JobInstance>>();

      mapJobs.put(CAPTURE_MASSE_JN, new ArrayList<JobInstance>());

      decisionService.trouverJobALancer(mapJobs, jobsEnCours);

   }

   /**
    * aucun traitement de capture en masse en local en attente renseigné
    * 
    */
   @Test(expected = AucunJobALancerException.class)
   public void decisionService_failure_noJobEnAttente_3()
         throws AucunJobALancerException {

      List<JobExecution> jobsEnCours = new ArrayList<JobExecution>();
      Map<String, List<JobInstance>> mapJobs = new HashMap<String, List<JobInstance>>();

      List<JobInstance> jobCaptureMasse = new ArrayList<JobInstance>();

      jobCaptureMasse.add(createJobCaptureMasse(202, CAPTURE_MASSE_JN,
            CER44_SOMMAIRE));
      jobCaptureMasse.add(createJobCaptureMasse(607, CAPTURE_MASSE_JN,
            "ecde://ecde.cer34.recouv/sommaire.xml"));

      mapJobs.put(CAPTURE_MASSE_JN, jobCaptureMasse);

      decisionService.trouverJobALancer(mapJobs, jobsEnCours);

   }

   /**
    * traitement de capture en masse en local en cours
    */
   @Test(expected = AucunJobALancerException.class)
   public void decisionService_failure_noJobEnAttente_4()
         throws AucunJobALancerException {

      List<JobExecution> jobsEnCours = new ArrayList<JobExecution>();
      jobsEnCours.add(createJobExecutionCaptureMasse(202, CAPTURE_MASSE_JN,
            CER69_SOMMAIRE));

      Map<String, List<JobInstance>> mapJobs = new HashMap<String, List<JobInstance>>();

      List<JobInstance> jobCaptureMasse = new ArrayList<JobInstance>();

      jobCaptureMasse.add(createJobCaptureMasse(458, CAPTURE_MASSE_JN,
            CER69_SOMMAIRE));

      mapJobs.put(CAPTURE_MASSE_JN, jobCaptureMasse);

      decisionService.trouverJobALancer(mapJobs, jobsEnCours);

   }

   private JobInstance createJobCaptureMasse(long instanceId, String jobName,
         String urlECDE) {

      Map<String, JobParameter> parameters = new HashMap<String, JobParameter>();
      parameters.put("capture.masse.sommaire", new JobParameter(urlECDE));
      parameters.put("id", new JobParameter(UUID.randomUUID().toString()));

      JobParameters jobParameters = new JobParameters(parameters);

      JobInstance job = new JobInstance(instanceId, jobParameters, jobName);

      return job;
   }

   private JobExecution createJobExecutionCaptureMasse(long instanceId,
         String jobName, String urlECDE) {

      JobInstance job = this
            .createJobCaptureMasse(instanceId, jobName, urlECDE);
      JobExecution execution = new JobExecution(job);

      return execution;
   }
}
