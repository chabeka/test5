package fr.urssaf.image.tests.dfcetest;

import static org.junit.Assert.fail;
import net.docubase.toolkit.Authentication;
import net.docubase.toolkit.model.ToolkitFactory;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.urssaf.image.tests.dfcetest.helpers.NcotiHelper;

public abstract class AbstractNcotiTest {

   /** 
    * Chaque classe fille a son logger.
    */
   protected Logger log = LoggerFactory.getLogger(getClass());

   /** 
    * Nom de la base GED pour ce test. 
    * Tronqué à 8 caractères dans Docubase. 
    * On laisse une valeur en dur pour éviter de créer trop de base car
    * il semblerait que la version 0.9.1 n'apprécie pas. 
    */
   protected static final String BASE_ID = "NCOTI_99";// + System.nanoTime();
   protected static ToolkitFactory toolkit;
   protected static final String ADM_LOGIN = "_ADMIN";
   protected static final String ADM_PASSWORD = "DOCUBASE";
   protected static final Integer DOMAIN_ID = Integer.valueOf(1);
   protected static final String AMF_HOST = "cer69-ds4int";
   protected static final Integer AMF_PORT = Integer.valueOf(4020);

   @BeforeClass
   public static void before() throws Exception {
     boolean openSession = Authentication.openSession(ADM_LOGIN, ADM_PASSWORD, AMF_HOST, AMF_PORT,
            DOMAIN_ID);
      if (!openSession) {
         fail("Impossible d'ouvrir une session");
      }
      NcotiHelper.createOrReplaceBase(BASE_ID);
      toolkit = ToolkitFactory.getInstance();
   }

   @AfterClass
   public static void after() {
      DocubaseHelper.dropBase(BASE_ID);

      if (Authentication.isSessionActive()) {
         Authentication.closeSession();
      }
   }
}
