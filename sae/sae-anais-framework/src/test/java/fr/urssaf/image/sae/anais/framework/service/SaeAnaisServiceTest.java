package fr.urssaf.image.sae.anais.framework.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import anaisJavaApi.AnaisExceptionAuthFailure;
import fr.urssaf.image.sae.anais.framework.component.ConnectionFactory;
import fr.urssaf.image.sae.anais.framework.component.DataSource;
import fr.urssaf.image.sae.anais.framework.service.exception.SaeAnaisApiException;

@SuppressWarnings("PMD")
public class SaeAnaisServiceTest {

   private static ConnectionFactory factory;

   private static final String CODEIR = "IR69";

   private static final String CODE_ORG = "CER69";

   private static final String LOGIN_SUCCESS = "CER6990430";

   private static final String PASSWORD_SUCCESS = null;// EN ATTENTE;

   private static final String PASSWORD_FAILURE = "inconnu";

   @BeforeClass
   public static void initClass() {

      DataSource dataSource = new DataSource();

      dataSource.setCodeapp("RECHERCHE-DOCUMENTAIRE");
      dataSource.setPasswd("rechercheDoc");
      dataSource.setCodeenv("PROD");
      dataSource
            .setAppdn("cn=USR_READ_NAT_APP_RECHERCHE-DOCUMENTAIRE,OU=RECHERCHE-DOCUMENTAIRE,OU=Applications,OU=Technique,dc=recouv");
      dataSource.setPort(389);

      dataSource.setHostname("cer44anaistest.cer44.recouv");
      dataSource.setTimeout("5000");
      dataSource.setUsetls(false);

      factory = new ConnectionFactory(dataSource);
   }

   @Test
   @Ignore
   public void authentifactionSucess() {

      SaeAnaisService service = new SaeAnaisService(factory);

      String xml = service.authentifierPourSaeParLoginPassword(LOGIN_SUCCESS,
            PASSWORD_SUCCESS, CODEIR, CODE_ORG);

      assertNull(xml);

   }

   @Test
   public void authFailure() {

      SaeAnaisService service = new SaeAnaisService(factory);

      try {
         service.authentifierPourSaeParLoginPassword(LOGIN_SUCCESS,
               PASSWORD_FAILURE, CODEIR, CODE_ORG);
         fail("le test ne doit pas passer");
      } catch (SaeAnaisApiException e) {
         assertEquals("le login est incorrect",
               AnaisExceptionAuthFailure.class, e.getCause().getClass());
      }

   }

   @Test
   public void loginEmpty() {

      SaeAnaisService service = new SaeAnaisService(factory);

      try {
         service.authentifierPourSaeParLoginPassword(null, PASSWORD_SUCCESS,
               CODEIR, CODE_ORG);
         fail("le test ne doit pas passer");
      } catch (IllegalArgumentException e) {
         assertEquals("le login doit être renseigné",
               "L’identifiant de l’utilisateur doit être renseigné", e
                     .getMessage());
      }

   }

   @Test
   public void passwordEmpty() {

      SaeAnaisService service = new SaeAnaisService(factory);

      try {
         service.authentifierPourSaeParLoginPassword(LOGIN_SUCCESS, null,
               CODEIR, CODE_ORG);
         fail("le test ne doit pas passer");
      } catch (IllegalArgumentException e) {
         assertEquals("le password doit être renseigné",
               "Le mot de passe de l’utilisateur doit être renseigné", e
                     .getMessage());
      }

   }

}
