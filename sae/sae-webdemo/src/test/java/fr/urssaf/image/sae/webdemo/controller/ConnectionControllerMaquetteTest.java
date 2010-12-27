package fr.urssaf.image.sae.webdemo.controller;

import java.io.File;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import fr.urssaf.image.commons.maquette.MaquetteFilter;
import fr.urssaf.image.sae.webdemo.maquette.BoitesGauches;
import fr.urssaf.image.sae.webdemo.maquette.Menus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-test.xml")
@SuppressWarnings("PMD")
public class ConnectionControllerMaquetteTest {

   private MaquetteFilter filtre;

   private FilterChain chain;

   private MockHttpServletResponse response;

   private MockHttpServletRequest request;

   @Autowired
   private ApplicationContext context;

   @Autowired
   private ConnectionController servlet;

   @Before
   public void init() throws ServletException {

      filtre = new MaquetteFilter();
      chain = new MockFilterChain();

      MockFilterConfig filterConfig = new MockFilterConfig();
      filterConfig.addInitParameter("implementationILeftCol",
            BoitesGauches.class.getName());
      filterConfig.addInitParameter("implementationIMenu", Menus.class
            .getName());

      // Paramétrage
      filtre.init(filterConfig);

      request = new MockHttpServletRequest();
      request.setMethod("POST");
      response = new MockHttpServletResponse();
      response.setContentType("text/html");

   }

   @Test
   public void connectSuccess() throws Exception {

      this.request.setParameter(ConnectionControllerTest.SAML_FIELD, FileUtils
            .readFileToString(
                  new File("src/test/resources/saml/ctd_rights.txt"), "UTF-8"));

      assertResponseContent();

   }

   @Test
   public void connectFailure() throws Exception {

      this.request.setParameter(ConnectionControllerTest.SAML_FIELD,
            "saml-reponse");

      assertResponseContent();

   }

   public void assertResponseContent() throws Exception {

      request.setRequestURI("/connection.html");
      this.request.setParameter(ConnectionControllerTest.RELAY_FIELD, "/");

      AnnotationMethodHandlerAdapter handlerAdapter = context
            .getBean(AnnotationMethodHandlerAdapter.class);
      handlerAdapter.handle(request, response, servlet);

      filtre.doFilter(request, response, chain);

      response.getContentAsString();

   }

}
