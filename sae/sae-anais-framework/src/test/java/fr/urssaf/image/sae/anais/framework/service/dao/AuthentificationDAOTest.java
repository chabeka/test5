package fr.urssaf.image.sae.anais.framework.service.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import anaisJavaApi.AnaisExceptionAuthFailure;
import fr.urssaf.image.sae.anais.framework.component.ConnectionFactory;
import fr.urssaf.image.sae.anais.framework.component.DataSource;
import fr.urssaf.image.sae.anais.framework.service.Users;
import fr.urssaf.image.sae.anais.framework.service.exception.SaeAnaisApiException;

@SuppressWarnings("PMD")
public class AuthentificationDAOTest {

   private static ConnectionFactory factory;

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

   private AuthentificationDAO dao;

   @Before
   public void initDAO() {
      dao = new AuthentificationDAO(factory);
   }

   @Test
   @Ignore
   public void authentifactionSucess() {

      String xml = dao.createXMLToken(Users.User1.LOGIN, Users.User1.PASSWORD,
            Users.User1.CODEIR, Users.User1.CODE_ORG);

      assertNull(xml);

   }

   @Test
   public void authFailure() {

      try {
         dao.createXMLToken(Users.User1.LOGIN, "inconnu", Users.User1.CODEIR,
               Users.User1.CODE_ORG);
         fail("le test ne doit pas passer");
      } catch (SaeAnaisApiException e) {
         assertEquals("le login est incorrect",
               AnaisExceptionAuthFailure.class, e.getCause().getClass());
      }

   }

}
