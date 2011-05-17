package fr.urssaf.image.sae.webservices.skeleton;

import static org.junit.Assert.assertEquals;

import org.apache.axis2.context.MessageContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cirtil.www.saeservice.PingRequest;
import fr.cirtil.www.saeservice.PingResponse;
import fr.urssaf.image.sae.webservices.util.Axis2Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-service.xml",
      "/applicationContext-security-test.xml" })
@SuppressWarnings( { "PMD.MethodNamingConventions" })
public class PingTest {

   @Autowired
   private SaeServiceSkeleton skeleton;

   private MessageContext ctx;

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
