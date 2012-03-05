package fr.urssaf.image.sae.ordonnanceur.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
      "/applicationContext-sae-ordonnanceur-service-test.xml",
      "/applicationContext-sae-ordonnanceur-cassandra-test.xml" })
@SuppressWarnings("PMD.MethodNamingConventions")
public class CaptureMasseSupportTest {

   @Autowired
   private CaptureMasseSupport captureMasseSupport;

   private static final String CAPTURE_MASSE_JN = "capture_masse";

   private static final String PARAMETER_ECDE = "capture.masse.sommaire";

   @Test
   public void filtrerJobInstanceByECDELocal() {

      List<JobInstance> jobInstances = new ArrayList<JobInstance>();

      // traitement de capture en masse local
      jobInstances.add(createJobInstance(1, CAPTURE_MASSE_JN, PARAMETER_ECDE,
            "ecde://ecde.cer69.recouv/sommaire.xml"));
      jobInstances.add(createJobInstance(2, CAPTURE_MASSE_JN, PARAMETER_ECDE,
            "ecde://ecde.cer34.recouv/sommaire.xml"));
      // traitement de capture en masse non local
      jobInstances.add(createJobInstance(3, CAPTURE_MASSE_JN, PARAMETER_ECDE,
            "ecde://ecde.cer44.recouv/sommaire.xml"));
      // traitement de capture en masse sans configuration
      jobInstances.add(createJobInstance(4, CAPTURE_MASSE_JN, PARAMETER_ECDE,
            "ecde://ecde.cer78.recouv/sommaire.xml"));
      // autre traitement de masse
      jobInstances.add(createJobInstance(5, "OTHER_JN", "OTHER_PARAMETER1",
            "OTHER_VALUE1"));
      // traitement de capture en masse sans paramètre pour URL ECDE
      jobInstances.add(createJobInstance(6, CAPTURE_MASSE_JN,
            "OTHER_PARAMETER2", "OTHER_VALUE2"));
      // traitement de capture en masse avec paramètre pour URL ECDE erroné
      jobInstances.add(createJobInstance(7, CAPTURE_MASSE_JN, PARAMETER_ECDE,
            "ecde://azaz^^/sommaire.xml"));

      List<JobInstance> traitements = captureMasseSupport
            .filtrerJobInstanceByECDELocal(jobInstances);

      Assert.assertEquals("le nombre de traitements filtrés est inattendu", 2,
            traitements.size());

      Assert.assertEquals("le job instance est inattendu", jobInstances.get(0),
            traitements.get(0));
      Assert.assertEquals("le job instance de capture est inattendu",
            jobInstances.get(1), traitements.get(1));
   }

   @Test
   public void filtrerJobExecutionByECDELocal() {

      List<JobExecution> jobExecutions = new ArrayList<JobExecution>();

      // traitement de capture en masse local
      jobExecutions.add(createJobExecution(1, CAPTURE_MASSE_JN, PARAMETER_ECDE,
            "ecde://ecde.cer69.recouv/sommaire.xml"));
      jobExecutions.add(createJobExecution(2, CAPTURE_MASSE_JN, PARAMETER_ECDE,
            "ecde://ecde.cer34.recouv/sommaire.xml"));
      // traitement de capture en masse non local
      jobExecutions.add(createJobExecution(3, CAPTURE_MASSE_JN, PARAMETER_ECDE,
            "ecde://ecde.cer44.recouv/sommaire.xml"));
      // traitement de capture en masse sans configuration
      jobExecutions.add(createJobExecution(4, CAPTURE_MASSE_JN, PARAMETER_ECDE,
            "ecde://ecde.cer78.recouv/sommaire.xml"));
      // autre traitement de masse
      jobExecutions.add(createJobExecution(5, "OTHER_JN", "OTHER_PARAMETER1",
            "OTHER_VALUE1"));
      // traitement de capture en masse sans paramètre pour URL ECDE
      jobExecutions.add(createJobExecution(6, CAPTURE_MASSE_JN,
            "OTHER_PARAMETER2", "OTHER_VALUE2"));
      // traitement de capture en masse avec paramètre pour URL ECDE erroné
      jobExecutions.add(createJobExecution(7, CAPTURE_MASSE_JN, PARAMETER_ECDE,
            "ecde://azaz^^/sommaire.xml"));

      List<JobExecution> traitements = captureMasseSupport
            .filtrerJobExecutionByECDELocal(jobExecutions);

      Assert.assertEquals("le nombre de traitements filtrés est inattendu", 2,
            traitements.size());

      Assert.assertEquals("le job execution est inattendu", jobExecutions
            .get(0), traitements.get(0));
      Assert.assertEquals("le job execution est inattendu", jobExecutions
            .get(1), traitements.get(1));
   }

   private JobInstance createJobInstance(long instanceId, String jobName,
         String parameterName, String parameterValue) {

      Map<String, JobParameter> parameters = new HashMap<String, JobParameter>();
      parameters.put(parameterName, new JobParameter(parameterValue));

      JobParameters jobParameters = new JobParameters(parameters);

      JobInstance job = new JobInstance(instanceId, jobParameters, jobName);

      return job;
   }

   private JobExecution createJobExecution(long instanceId, String jobName,
         String parameterName, String parameterValue) {

      JobInstance job = this.createJobInstance(instanceId, jobName,
            parameterName, parameterValue);
      JobExecution execution = new JobExecution(job);

      return execution;
   }
}
