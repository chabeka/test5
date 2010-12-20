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
@ContextConfiguration(locations = { "/spring-servlet.xml",
      "/applicationContext.xml" })
@SuppressWarnings("PMD")
public class LoggerControllerTest extends
      ControllerTestSupport<LoggerController> {

   @Autowired
   private LoggerController servlet;

   private ControllerAssert<LoggerController> controllerAssert;

   @Before
   public void init() throws IOException {

      this.initGet();

      controllerAssert = new ControllerAssert<LoggerController>(this, servlet);

   }

   @Test
   public void exception() {

      controllerAssert.assertView("registre_exploitation");

   }

}
