package fr.urssaf.image.sae.webservices.skeleton;

import static org.junit.Assert.assertEquals;

import javax.xml.stream.XMLStreamReader;

import org.apache.commons.lang.exception.NestableRuntimeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cirtil.www.saeservice.PingRequest;
import fr.cirtil.www.saeservice.PingResponse;
import fr.urssaf.image.sae.webservices.util.Axis2Utils;
import fr.urssaf.image.sae.webservices.util.XMLStreamUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-service-test.xml",
      "/applicationContext-security-test.xml" })
@SuppressWarnings( { "PMD.MethodNamingConventions" })
public class PingTest {

   @Autowired
   private SaeServiceSkeleton skeleton;

   @Before
   public void before() {

      Axis2Utils.initMessageContextSecurity();

   }

   private PingRequest createPing(String filePath) {

      try {

         XMLStreamReader reader = XMLStreamUtils
               .createXMLStreamReader(filePath);
         return PingRequest.Factory.parse(reader);

      } catch (Exception e) {
         throw new NestableRuntimeException(e);
      }

   }

   @Test
   public void ping() {

      PingRequest request = createPing("src/test/resources/request/ping.xml");

      PingResponse response = skeleton.ping(request);

      assertEquals("Test du ping", "Les services SAE sont en ligne", response
            .getPingString());
   }

}
