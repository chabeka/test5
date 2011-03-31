package fr.urssaf.image.sae.webservices;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.webservices.security.AuthenticateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-service.xml",
      "/applicationContext-security.xml" })
public class SaeServiceTest {

   @Autowired
   private SaeService service;

   @Test
   public void ping() {

      assertEquals("Test du ping", "Les services SAE sont en ligne", service
            .ping());
   }

   @Test
   public void pingSecure() {

      AuthenticateUtils.authenticate();
      
      assertEquals("Test du ping sécurisé",
            "Les services du SAE sécurisés par authentification sont en ligne",
            service.pingSecure());
   }
}
