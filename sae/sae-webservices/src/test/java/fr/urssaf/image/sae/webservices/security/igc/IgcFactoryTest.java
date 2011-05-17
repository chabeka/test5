package fr.urssaf.image.sae.webservices.security.igc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;

import fr.urssaf.image.sae.igc.exception.IgcConfigException;

@SuppressWarnings( { "PMD.MethodNamingConventions" })
public class IgcFactoryTest {

   private static final String FAIL_MSG = "le test doit échouer";

   private static final Logger LOG = Logger.getLogger(IgcFactoryTest.class);

   @Test
   @Ignore
   public void createIgcConfig_success() throws IOException {

      fail("non implementé");
   }

   @Test
   public void createIgcConfig_failure() throws IOException {

      try {

         FileSystemResource igcConfigResource = new FileSystemResource(
               "test.xml");

         IgcFactory.createIgcConfig(igcConfigResource);

         fail(FAIL_MSG);
      } catch (IllegalArgumentException e) {
         LOG.debug(e.getCause().getMessage());
         assertEquals("Exception non attendue", IgcConfigException.class, e
               .getCause().getClass());
      }
   }

}
