package fr.urssaf.image.commons.springsecurity.webservice.custom.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.xml.ws.soap.SOAPFaultException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class SimpleServiceTest {

   @Autowired
   private SimpleService service;

   @Test
   public void saveSuccess() {

      service.save("ROLE_ADMIN");

   }

   @Test
   public void saveFailure() {

      try {
         service.save("ROLE_USER");
         fail("doit lever une exception " + SOAPFaultException.class);
      } catch (SOAPFaultException e) {
         assertEquals("Access is denied", e.getMessage());

      }
   }

   @Test
   public void loadSuccess() {

      service.load("ROLE_USER");

   }

   @Test
   public void loadFailure() {

      try {
         service.load("ROLE_ADMIN");
         fail("doit lever une exception " + SOAPFaultException.class);
      } catch (SOAPFaultException e) {
         assertEquals("Access is denied", e.getMessage());

      }
   }

}
