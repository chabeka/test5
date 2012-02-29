package fr.urssaf.image.sae.ordonnanceur.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.ordonnanceur.exception.AucunJobALancerException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-sae-ordonnanceur-test.xml" })
@SuppressWarnings("PMD.MethodNamingConventions")
public class DecisionServiceTest {

   @Autowired
   private DecisionService decisionService;

   @Test
   public void decisionService_success() throws AucunJobALancerException {

      List<JobExecution> jobsEnCours = new ArrayList<JobExecution>();
      Map<String, List<JobInstance>> mapJobs = new HashMap<String, List<JobInstance>>();

      decisionService.trouverJobALancer(mapJobs, jobsEnCours);

   }
}
