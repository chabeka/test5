package fr.urssaf.image.sae.anais.portail.controller;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-servlet.xml",
      "/applicationContext.xml" })
@SuppressWarnings("PMD")
public class ConnectionControllerTest extends
      ControllerTestSupport<ConnectionController> {

   @Autowired
   private ConnectionController servlet;

   private ControllerAssert<ConnectionController> controllerAssert;

   private static final String PASSWORD_VALUE = "CER6990010";

   private static final String LOGIN_VALUE = "CER6990010";

   private static final String PASSWORD_FIELD = "userPassword";

   private static final String LOGIN_FIELD = "userLogin";

   @Before
   public void init() {

      controllerAssert = new ControllerAssert<ConnectionController>(this,
            servlet);

   }

   @Test
   public void getDefaultView() {

      this.initGet();

      controllerAssert.assertView("connection/connection");
   }

   @Test
   public void connectSuccess() throws IOException {

      this.initPost();

      this.initParameter(LOGIN_FIELD, LOGIN_VALUE);
      this.initParameter(PASSWORD_FIELD, PASSWORD_VALUE);

      controllerAssert.assertView("connection/connection_success");
      
   }

   @Test
   public void connectFailure_password() {

      this.connectFailure_password(null);
      this.connectFailure_password(" ");
      this.connectFailure_password("");

   }
   
   private void connectFailure_password(String password) {

      this.initPost();

      this.initParameter(LOGIN_FIELD, LOGIN_VALUE);
      this.initParameter(PASSWORD_FIELD, password);
      
      controllerAssert.assertError(PASSWORD_FIELD, null, "connectionForm",
            "NotEmpty", "Le mot de passe doit être renseigné");

      controllerAssert.assertView("connection/connection_failure");

   }

   @Test
   public void connectFailure_login() {

      this.connectFailure_login(null);
      this.connectFailure_login("");
      this.connectFailure_login(" ");

   }
   
   private void connectFailure_login(String login) {

      this.initPost();

      this.initParameter(LOGIN_FIELD, login);
      this.initParameter(PASSWORD_FIELD, PASSWORD_VALUE);
      controllerAssert.assertError(LOGIN_FIELD, null, "connectionForm",
            "NotEmpty", "L''identifiant doit être renseigné");

      controllerAssert.assertView("connection/connection_failure");

   }

   @Test
   public void connectFaillure_authentification() {

      this.initPost();

      this.initParameter(LOGIN_FIELD, LOGIN_VALUE);
      this.initParameter(PASSWORD_FIELD, "inconnu");

      controllerAssert.assertView("connection/connection_failure");

   }
}
