package fr.urssaf.image.sae.ordonnanceur.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-sae-ordonnanceur-test.xml" })
@SuppressWarnings("PMD.MethodNamingConventions")
public class CoordinationServiceTest {

   @Autowired
   private CoordinationService coordinationService;

   @Test
   public void lancerTraitement_success() {

      coordinationService.lancerTraitement();

   }
}
