package fr.urssaf.image.sae.webdemo.interceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.webdemo.TestController;
import fr.urssaf.image.sae.webdemo.controller.ApplicationDemoController;
import fr.urssaf.image.sae.webdemo.controller.ConnectionController;
import fr.urssaf.image.sae.webdemo.controller.ConnectionFailureController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-servlet.xml",
      "/applicationContext.xml" })
@SuppressWarnings("PMD")
public class ConnectionInterceptorTest {

   private ConnectionInterceptor interceptor;

   private MockHttpServletResponse response;

   private MockHttpServletRequest request;

   @Before
   public void before() {

      request = new MockHttpServletRequest();
      response = new MockHttpServletResponse();

      interceptor = new ConnectionInterceptor();
   }

   @Autowired
   private ConnectionController connectionController;

   @Autowired
   private TestController controller;

   @Test
   public void connection_success() throws Exception {

      String samlResponse = FileUtils.readFileToString(new File(
            "src/test/resources/saml/ctd_rights.xml"), "UTF-8");

      connectionController.createSession(samlResponse, request);
      assertConnect(controller);
   }

   @Test
   public void connect_failure() throws Exception {

      assertFalse("il ne doit pas être possible d'être connecté", interceptor
            .preHandle(request, response, controller));
      assertEquals("connectionFailure.html", response.getRedirectedUrl());

   }

   @Autowired
   private ConnectionFailureController connectionFailureController;

   @Autowired
   private ApplicationDemoController applicationdemoController;

   @Test
   public void connect_exception() throws Exception {

      assertConnect(connectionController);
      assertConnect(connectionFailureController);
      assertConnect(applicationdemoController);

   }

   public void assertConnect(Object handler) throws Exception {

      assertTrue("on doit être connecté",interceptor.preHandle(request, response, handler));
      assertNull(response.getRedirectedUrl());

   }
}
