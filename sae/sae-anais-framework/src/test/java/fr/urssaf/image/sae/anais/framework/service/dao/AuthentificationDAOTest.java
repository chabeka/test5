package fr.urssaf.image.sae.anais.framework.service.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import anaisJavaApi.AnaisExceptionAuthFailure;
import fr.urssaf.image.sae.anais.framework.component.ConnectionFactory;
import fr.urssaf.image.sae.anais.framework.component.DataSource;
import fr.urssaf.image.sae.anais.framework.service.exception.SaeAnaisApiException;
import fr.urssaf.image.sae.anais.framework.util.CTD;
import fr.urssaf.image.sae.anais.framework.util.InitFactory;
import fr.urssaf.image.sae.anais.framework.util.TokenCheck;

@SuppressWarnings({"PMD.JUnitAssertionsShouldIncludeMessage"})
public class AuthentificationDAOTest {

   private static final Logger LOG = Logger
         .getLogger(AuthentificationDAOTest.class);

   private static ConnectionFactory factory;

   private static CTD ctd;

   @BeforeClass
   public static void initClass() {

      DataSource dataSource = InitFactory.initDataSource();
      factory = new ConnectionFactory(dataSource);

      ctd = InitFactory.initCTD("ctd1");
   }

   private AuthentificationDAO dao;

   @Before
   public void initDAO() {
      dao = new AuthentificationDAO(factory);
   }

   @Test
   public void authSucess() throws IOException {

      String xml = dao.createXMLToken(ctd.getUserLogin(),
            ctd.getUserPassword(), ctd.getCodeir(), ctd.getCodeorg());

      LOG.debug(xml);
      assertTrue(TokenCheck.checkCTD0(xml));

   }

   @Test
   public void authFailure() {

      try {
         dao.createXMLToken(ctd.getUserLogin(), "inconnu", ctd.getCodeir(), ctd
               .getCodeorg());
         fail("le test ne doit pas passer");
      } catch (SaeAnaisApiException e) {
         assertEquals("le login est incorrect",
               AnaisExceptionAuthFailure.class, e.getCause().getClass());
      }

   }

}
