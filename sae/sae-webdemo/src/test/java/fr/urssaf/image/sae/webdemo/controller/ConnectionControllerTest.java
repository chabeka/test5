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
@ContextConfiguration(locations = { "/spring-servlet.xml",
      "/applicationContext.xml" })
@SuppressWarnings("PMD")
public class ConnectionControllerTest extends ControllerTestSupport<ConnectionController> {

   @Autowired
   private ConnectionController servlet;

   private ControllerAssert<ConnectionController> controllerAssert;
   
   private static final String SAML_FIELD = "SAMLResponse";
   
   private static final String SAML_VALUE = "saml_value";
   
   private static final String RELAY_FIELD = "RelayState";
   
   private static final String RELAY_VALUE = "accueil.html";
   
   private static final String FORWARD_403 = "forward:erreur403_viko.html";

   @Before
   public void init() {

      controllerAssert = new ControllerAssert<ConnectionController>(this,
            servlet);

   }

   @Test
   public void connectSuccess(){
      
      this.initPost();

      this.initParameter(SAML_FIELD, SAML_VALUE);
      this.initParameter(RELAY_FIELD, RELAY_VALUE);

      controllerAssert.assertView("forward:accueil.html");
      
   }
   
   @Test
   public void connectFailure_SAML(){
      
      this.assertFailure_SAML("");
      this.assertFailure_SAML(" ");
      this.assertFailure_SAML(null);
      
   }
   
   private void assertFailure_SAML(String saml){
      
      this.initPost();
      
      this.initParameter(RELAY_FIELD, RELAY_VALUE);
      controllerAssert.assertError(SAML_FIELD, saml, "connectionForm",
            "NotEmpty");

      controllerAssert.assertView(FORWARD_403);
      
   }
   
   @Test
   public void connectFailure_Relay(){
      
      this.assertFailure_Relay("");
      this.assertFailure_Relay(" ");
      this.assertFailure_Relay(null);
   }
   
   private void assertFailure_Relay(String relay){
      
      this.initPost();
      
      this.initParameter(SAML_FIELD, SAML_VALUE);
      controllerAssert.assertError("relayState", relay, "connectionForm",
      "NotEmpty");

      controllerAssert.assertView(FORWARD_403);
      
   }
}
