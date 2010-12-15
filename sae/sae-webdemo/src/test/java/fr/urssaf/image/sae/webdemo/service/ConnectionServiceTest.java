package fr.urssaf.image.sae.webdemo.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-servlet.xml",
      "/applicationContext.xml" })
@SuppressWarnings("PMD")
public class ConnectionServiceTest {

   @Autowired
   private ConnectionService service;

   @Test
   public void isValidateService_success() {

      this.assertTrueValidateService("/accueil.html");
      this.assertTrueValidateService("/connection");
      
   }
   
   @Test
   public void isValidateService_failure() {

      this.assertFalseValidateService("/service");
      
   }

   private void assertTrueValidateService(String servlet) {

      assertTrue("la servlet est invalide " + servlet, service
            .isValidateService(servlet));
   }
   
   private void assertFalseValidateService(String servlet) {

      assertFalse("la servlet est valide " + servlet, service
            .isValidateService(servlet));
   }
}
