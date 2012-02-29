package fr.urssaf.image.sae.ordonnanceur.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.ordonnanceur.exception.JobDejaReserveException;
import fr.urssaf.image.sae.ordonnanceur.exception.JobInexistantException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-sae-ordonnanceur-test.xml" })
@SuppressWarnings("PMD.MethodNamingConventions")
public class JobServiceTest {

   @Autowired
   private JobService jobService;

   @Test
   public void recupJobEnCours_success() {

      jobService.recupJobEnCours();
   }

   @Test
   public void recupJobsALancer_success() {

      jobService.recupJobsALancer();
   }

   @Test
   public void reserveJob_success() throws JobInexistantException,
         JobDejaReserveException {

      long idJob = 1;
      jobService.reserveJob(idJob);
   }
}
