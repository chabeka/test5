package fr.urssaf.image.sae.webservices.security.igc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;

import fr.urssaf.image.sae.igc.exception.IgcConfigException;
import fr.urssaf.image.sae.igc.modele.IgcConfig;
import fr.urssaf.image.sae.webservices.component.IgcConfigFactory;
import fr.urssaf.image.sae.webservices.component.TempDirectoryFactory;

@SuppressWarnings( { "PMD.MethodNamingConventions" })
public class IgcFactoryTest {

   private static final String FAIL_MSG = "le test doit échouer";

   private static final Logger LOG = Logger.getLogger(IgcFactoryTest.class);

   @BeforeClass
   public static void beforeClass() {

      // création des répertoires pour le dépot des CRL et du
      // fichier de configuration

      TempDirectoryFactory.createDirectory();
      TempDirectoryFactory.createACDirectory();
      TempDirectoryFactory.createCRLDirectory();

   }

   @Test
   public void createIgcConfig_success() throws IOException {

      IgcConfigFactory.createIgcConfigXML("igcConfig_success.xml");

      assertNotNull("instance attendue",
            createIgcConfig("igcConfig_success.xml"));
   }

   @Test
   public void createIgcConfig_failure() throws IOException {

      try {
         IgcConfigFactory.createIgcConfigXML("igcConfig_failure.xml", new String[0]);
         createIgcConfig("igcConfig_failure.xml");

         fail(FAIL_MSG);
      } catch (IllegalArgumentException e) {
         LOG.debug(e.getCause().getMessage());
         assertEquals("Exception non attendue", IgcConfigException.class, e
               .getCause().getClass());
      }
   }

   private IgcConfig createIgcConfig(String igcConfig) throws IOException {

      FileSystemResource igcConfigResource = new FileSystemResource(
            TempDirectoryFactory.DIRECTORY + "/" + igcConfig);
      return IgcFactory.createIgcConfig(igcConfigResource);
   }

}
