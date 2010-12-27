package fr.urssaf.image.sae.webdemo.controller;

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
public class ApplicationDemoControllerTest extends
      ControllerTestSupport<ApplicationDemoController> {

   @Autowired
   private ApplicationDemoController servlet;

   private ControllerAssert<ApplicationDemoController> controllerAssert;

   @Before
   public void init() {

      controllerAssert = new ControllerAssert<ApplicationDemoController>(this,
            servlet);

   }

   @Test
   public void doPost() {

      this.initPost();

      controllerAssert.assertView("forward:connection.html");

   }

   @Test
   public void doGet() {

      this.initGet();

      controllerAssert.assertView("redirect:");
   }
}
