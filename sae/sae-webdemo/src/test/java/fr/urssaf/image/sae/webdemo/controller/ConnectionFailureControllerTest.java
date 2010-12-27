package fr.urssaf.image.sae.webdemo.controller;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.webdemo.ControllerAssert;
import fr.urssaf.image.sae.webdemo.ControllerTestSupport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-test.xml")
@SuppressWarnings("PMD")
public class ConnectionFailureControllerTest extends
      ControllerTestSupport<ConnectionFailureController> {

   @Autowired
   private ConnectionFailureController servlet;

   private ControllerAssert<ConnectionFailureController> controllerAssert;

   @Before
   public void init() throws IOException {

      this.initGet();

      controllerAssert = new ControllerAssert<ConnectionFailureController>(
            this, servlet);

   }

   @Test
   public void exception() {

      controllerAssert.assertView("error/erreur403_pasauthentifie");

   }

}
