package fr.urssaf.image.sae.webdemo.maquette;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import net.htmlparser.jericho.Source;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import fr.urssaf.image.commons.maquette.MaquetteFilter;
import fr.urssaf.image.commons.maquette.exception.MissingHtmlElementInTemplateParserException;
import fr.urssaf.image.commons.maquette.exception.MissingSourceParserException;
import fr.urssaf.image.commons.maquette.template.parser.internal.LeftColParser;
import fr.urssaf.image.sae.vi.service.VIService;
import fr.urssaf.image.sae.webdemo.TestController;
import fr.urssaf.image.sae.webdemo.security.SecurityAuthentication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-test.xml")
@SuppressWarnings("PMD")
public class BoitesGauchesTest {

   private static final Logger LOG = Logger.getLogger(BoitesGauchesTest.class);

   private MaquetteFilter filtre;

   private FilterChain chain;

   private MockHttpServletResponse response;

   private MockHttpServletRequest request;

   @Autowired
   private ApplicationContext context;

   @Autowired
   private TestController servlet;

   private Source source;

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
      request.setRequestURI("/accueil.html");
      response = new MockHttpServletResponse();
      response.setContentType("text/html");

   }

   @Test
   public void decorateur_noauthenticate() throws IOException, ServletException {

      filter();

      assertLeftCol();
   }

   @Test
   public void decorateur_authenticate() throws Exception {

      authenticate();
      filter();

      try {
         LeftColParser parser = new LeftColParser(source);
         LOG.debug(parser.getLeftColTag().toString());
      } catch (MissingHtmlElementInTemplateParserException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (MissingSourceParserException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      assertLeftCol();

      assertElement(source, "Prenom AGENT-CTD", "user-name");
      assertElement(source, "GESTIONNAIREACCESCOMPLET", "user-rights");

   }

   private void filter() throws IOException, ServletException {

      filtre.doFilter(request, response, chain);

      source = new Source(response.getContentAsString());
   }

   private void authenticate() throws Exception {

      VIService viService = new VIService();

      File file = new File("src/test/resources/saml/ctd_rights.xml");
      String xml = FileUtils.readFileToString(file, "UTF-8");

      Authentication authentication = new SecurityAuthentication(viService
            .readVI(xml));

      SecurityContextHolder.getContext().setAuthentication(authentication);

      AnnotationMethodHandlerAdapter handlerAdapter = context
            .getBean(AnnotationMethodHandlerAdapter.class);
      handlerAdapter.handle(request, response, servlet);

   }

   private void assertLeftCol() {

      assertElement(source, "SAE - Application web de d&eacute;monstration",
            "app-name");
      assertElement(source, "0.1", "app-version");
   }

   private static void assertElement(Source source, String expected, String id) {

      assertEquals(expected, source.getElementById(id).getContent().toString());
   }

}
