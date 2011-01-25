package fr.urssaf.image.commons.springsecurity.webservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml",
      "/applicationContext-security.xml" })
public class SimpleWebServiceTest {

   @Autowired
   private SimpleWebService service;

   @Test
   public void saveSuccess() {

      save("ROLE_ADMIN");

   }

   @Test(expected = AccessDeniedException.class)
   public void saveFailure() {

      save("ROLE_USER");
   }

   private void save(String role) {

      service.save(role, "text", "title");
   }

   @Test
   public void loadSuccess() {

      service.load("ROLE_USER");

   }

   @Test(expected = AccessDeniedException.class)
   public void loadFailure() {

      service.load("ROLE_ADMIN");
   }

}
