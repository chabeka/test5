package fr.urssaf.image.sae.webservices.skeleton;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Vector;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMException;
import org.apache.axiom.om.util.StAXUtils;
import org.apache.axiom.soap.impl.builder.StAXSOAPModelBuilder;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.ws.security.WSSecurityEngineResult;
import org.apache.ws.security.WSUsernameTokenPrincipal;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.apache.ws.security.handler.WSHandlerResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cirtil.www.saeservice.PingRequest;
import fr.cirtil.www.saeservice.PingResponse;
import fr.cirtil.www.saeservice.PingSecureRequest;
import fr.cirtil.www.saeservice.PingSecureResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-service.xml",
      "/applicationContext-security.xml" })
public class SaeServiceSkeletonTest {

   @Autowired
   private SaeServiceSkeleton skeleton;

   private MessageContext ctx;

   @Before
   public void before() {

      ctx = new MessageContext();
      MessageContext.setCurrentMessageContext(ctx);

      Vector<Object> values = new Vector<Object>();
      Vector<Object> wsResults = new Vector<Object>();
      WSUsernameTokenPrincipal principal = new WSUsernameTokenPrincipal(
            "name_test", false);
      principal.setPassword("password_test");
      WSSecurityEngineResult engineResult = new WSSecurityEngineResult(0,
            principal, null, null, null);
      wsResults.add(engineResult);
      WSHandlerResult handlerResult = new WSHandlerResult(null, wsResults);
      values.add(handlerResult);
      ctx.setProperty(WSHandlerConstants.RECV_RESULTS, values);

   }

   @After
   public void after() {
      SecurityContextHolder.getContext().setAuthentication(null);
   }

   @Test
   public void ping() throws AxisFault, FileNotFoundException, OMException,
         XMLStreamException {

      this.init("src/test/resources/request/ping.xml");

      PingRequest request = new PingRequest();

      PingResponse response = skeleton.ping(request);

      assertEquals("Test du ping", "Les services SAE sont en ligne", response
            .getPingString());
   }

   @Test
   public void pingSecure_success() throws AxisFault, FileNotFoundException,
         OMException, XMLStreamException {

      this.init("src/test/resources/request/pingsecure_success.xml");

      PingSecureRequest request = new PingSecureRequest();

      PingSecureResponse response = skeleton.pingSecure(request);

      assertEquals("Test du ping",
            "Les services du SAE sécurisés par authentification sont en ligne",
            response.getPingString());
   }

   @Test(expected = AuthenticationCredentialsNotFoundException.class)
   public void pingSecure_failure() throws AxisFault, FileNotFoundException,
         OMException, XMLStreamException {

      this.init("src/test/resources/request/pingsecure_failure.xml");

      PingSecureRequest request = new PingSecureRequest();

      skeleton.pingSecure(request);

   }

   private void init(String xml) throws FileNotFoundException,
         XMLStreamException, AxisFault, OMException {

      InputStream input = new FileInputStream(xml);

      StAXSOAPModelBuilder stax = new StAXSOAPModelBuilder(StAXUtils
            .createXMLStreamReader(input));

      ctx.setEnvelope(stax.getSOAPEnvelope());

   }
}
