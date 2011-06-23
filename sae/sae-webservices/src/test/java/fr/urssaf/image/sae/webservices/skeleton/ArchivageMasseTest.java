package fr.urssaf.image.sae.webservices.skeleton;

import static org.junit.Assert.assertNotNull;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cirtil.www.saeservice.ArchivageMasse;
import fr.cirtil.www.saeservice.ArchivageMasseResponse;
import fr.urssaf.image.sae.webservices.util.Axis2Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-service.xml",
      "/applicationContext-security-test.xml" })
@SuppressWarnings( { "PMD.MethodNamingConventions" })
public class ArchivageMasseTest {

   @Autowired
   private SaeServiceSkeleton skeleton;

   private MessageContext ctx;

   @Before
   public void before() {

      ctx = new MessageContext();
      MessageContext.setCurrentMessageContext(ctx);

   }

   @Test
   public void archivageMasse_success() throws AxisFault {

      Axis2Utils.initMessageContext(ctx,
            "src/test/resources/request/archivageMasse_success.xml");

      ArchivageMasse request = new ArchivageMasse();

      ArchivageMasseResponse response = skeleton
            .archivageMasseSecure(request);

      assertNotNull("Test de l'archivage masse",response
                  .getArchivageMasseResponse());
   }

}
