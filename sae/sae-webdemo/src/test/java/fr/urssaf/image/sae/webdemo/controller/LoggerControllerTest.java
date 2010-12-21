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

   private static final String START_FIELD = "start";

   private static final String LIMIT_FIELD = "limit";

   private static final String SORT_FIELD = "sort";

   private static final String DIR_FIELD = "dir";

   @Before
   public void init() throws IOException {

      controllerAssert = new ControllerAssert<LoggerController>(this, servlet);

   }

   @Test(expected = IllegalStateException.class)
   public void loggerException() {

      new LoggerController(null);

   }

   @Test
   public void main() {

      this.initGet();
      controllerAssert.assertView("registre_exploitation");

   }

   @Test
   public void search() {

      this.initPost();
      this.setURI("/registre_exploitation/search.html");

      this.initParameter(START_FIELD, "0");
      this.initParameter(LIMIT_FIELD, "25");
      this.initParameter(SORT_FIELD, "idseq");
      this.initParameter(DIR_FIELD, "DESC");

      controllerAssert.assertResponseBody("registre_exploitation");

   }

}
