package fr.urssaf.image.sae.webservices.skeleton;

import static org.junit.Assert.assertEquals;

import javax.naming.NamingException;

import org.apache.axis2.context.MessageContext;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cirtil.www.saeservice.PingRequest;
import fr.cirtil.www.saeservice.PingResponse;
import fr.urssaf.image.sae.webservices.component.IgcConfigFactory;
import fr.urssaf.image.sae.webservices.component.SecurityFactory;
import fr.urssaf.image.sae.webservices.component.TempDirectoryFactory;
import fr.urssaf.image.sae.webservices.util.Axis2Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
@SuppressWarnings( { "PMD.MethodNamingConventions" })
public class PingTest {

   @Autowired
   private SaeServiceSkeleton skeleton;

   private MessageContext ctx;

   @BeforeClass
   public static void beforeClass() throws NamingException {

      String igcConfig = "igcConfig.xml";

      // création des répertoires pour le dépot des CRL et du
      // fichier de configuration

      TempDirectoryFactory.createDirectory();
      TempDirectoryFactory.createACDirectory();
      TempDirectoryFactory.createCRLDirectory();

      // création du fichier igcConfig.xml
      IgcConfigFactory.createIgcConfig(igcConfig);

      // téléchargement d'un certificat dans le répertoires temporaire
      SecurityFactory.downloadCertificat("pseudo_IGCA.crt");

      // paramétrage de la variable JNDI
      SimpleNamingContextBuilder builder = SimpleNamingContextBuilder
            .emptyActivatedContextBuilder();
      builder.bind("java:comp/env/SAE_Fichier_Configuration_IGC",
            TempDirectoryFactory.DIRECTORY + "/" + igcConfig);
   }

   @AfterClass
   public static void afterClass() {

      TempDirectoryFactory.cleanDirectory();
   }

   @Before
   public void before() {

      ctx = new MessageContext();
      MessageContext.setCurrentMessageContext(ctx);

   }

   @Test
   public void ping() {

      Axis2Utils.initMessageContext(ctx, "src/test/resources/request/ping.xml");

      PingRequest request = new PingRequest();

      PingResponse response = skeleton.ping(request);

      assertEquals("Test du ping", "Les services SAE sont en ligne", response
            .getPingString());
   }

}
