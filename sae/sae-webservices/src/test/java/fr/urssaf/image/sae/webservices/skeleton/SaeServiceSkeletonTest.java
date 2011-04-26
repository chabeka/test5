package fr.urssaf.image.sae.webservices.skeleton;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.naming.NamingException;
import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMException;
import org.apache.axiom.om.util.StAXUtils;
import org.apache.axiom.soap.impl.builder.StAXSOAPModelBuilder;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cirtil.www.saeservice.PingRequest;
import fr.cirtil.www.saeservice.PingResponse;
import fr.cirtil.www.saeservice.PingSecureRequest;
import fr.cirtil.www.saeservice.PingSecureResponse;
import fr.urssaf.image.sae.webservices.security.ActionsUnitaires;
import fr.urssaf.image.sae.webservices.security.spring.AuthenticationContext;
import fr.urssaf.image.sae.webservices.security.spring.AuthenticationToken;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
@SuppressWarnings( { "PMD.TooManyMethods", "PMD.MethodNamingConventions" })
public class SaeServiceSkeletonTest {

   private static final String FAIL_MSG = "le test doit échouer";

   @Autowired
   private SaeServiceSkeleton skeleton;

   private MessageContext ctx;

   @BeforeClass
   public static void beforeClass() throws NamingException {

      SimpleNamingContextBuilder builder = SimpleNamingContextBuilder
            .emptyActivatedContextBuilder();

      builder.bind("java:comp/env/SAE_Repertoire_Certificats_ACRacine",
            "src/test/resources/security/AC/AC-01_IGC_A.crt");
      builder.bind("java:comp/env/SAE_Repertoire_Certificats_CRL",
            "src/test/resources/security/CRL");

      builder.activate();
   }

   @Before
   public void before() {

      ctx = new MessageContext();
      MessageContext.setCurrentMessageContext(ctx);

      // Vector<Object> values = new Vector<Object>();
      // Vector<Object> wsResults = new Vector<Object>();
      // WSUsernameTokenPrincipal principal = new WSUsernameTokenPrincipal(
      // "name_test", false);
      // principal.setPassword("password_test");
      // WSSecurityEngineResult engineResult = new WSSecurityEngineResult(0,
      // principal, null, null, null);
      // wsResults.add(engineResult);
      // WSHandlerResult handlerResult = new WSHandlerResult(null, wsResults);
      // values.add(handlerResult);
      // ctx.setProperty(WSHandlerConstants.RECV_RESULTS, values);

   }

   @After
   public void after() {
      SecurityContextHolder.getContext().setAuthentication(null);
   }

   @Test
   public void ping() {

      this.init("src/test/resources/request/ping.xml");

      PingRequest request = new PingRequest();

      PingResponse response = skeleton.ping(request);

      assertEquals("Test du ping", "Les services SAE sont en ligne", response
            .getPingString());
   }

   @Test
   @Ignore("Désactivation du test dans l'attente d'un processus de mise à jour des CRL")
   public void pingSecure_success() {

      this.init("src/test/resources/request/pingsecure_success.xml");

      PingSecureRequest request = new PingSecureRequest();

      PingSecureResponse response = skeleton.pingSecure(request);

      assertEquals("Test du ping",
            "Les services du SAE sécurisés par authentification sont en ligne",
            response.getPingString());

      AuthenticationToken authentification = AuthenticationContext
            .getAuthenticationToken();

      ActionsUnitaires actionUnitaires = authentification.getDetails();
      List<String> actions = actionUnitaires.getListeActionsUniquement();

      assertEquals("le nombre d'actions unitaires est incorrect", 1, actions
            .size());
      assertEquals("ROLE_TOUS", actions.get(0));

      List<String> perimetres = actionUnitaires
            .getPerimetresDonnees("ROLE_TOUS");
      assertEquals("le nombre de perimetre de données est incorrect", 1,
            perimetres.size());
      assertEquals("FULL", perimetres.get(0));

   }

   @Test
   @Ignore("Désactivation du test dans l'attente d'un processus de mise à jour des CRL")
   public void pingSecure_failure_accessDenied() throws AxisFault {

      try {
         this
               .pingSecure_failure("src/test/resources/request/pingsecure_failure_accessDenied.xml");
         fail(FAIL_MSG);
      } catch (AxisFault e) {

         assertEquals(
               "Les droits présents dans le vecteur d'identification sont insuffisants pour effectuer l'action demandée",
               e.getMessage());
         assertEquals("DroitsInsuffisants", e.getFaultCode().getLocalPart());
         assertEquals("sae", e.getFaultCode().getPrefix());
         assertEquals("urn:sae:faultcodes", e.getFaultCode().getNamespaceURI());
      }

   }

   @Test
   @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
   public void pingSecure_failure_noHeader() {

      pingSecure_failure_noVI("src/test/resources/request/pingsecure_failure_noHeader.xml");

   }

   @Test
   @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
   public void pingSecure_failure_noWSsecurity() {

      pingSecure_failure_noVI("src/test/resources/request/pingsecure_failure_noWSsecurity.xml");

   }

   @Test
   @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
   public void pingSecure_failure_noVI() {

      pingSecure_failure_noVI("src/test/resources/request/pingsecure_failure_noVI.xml");

   }

   @SuppressWarnings("PMD.JUnitAssertionsShouldIncludeMessage")
   private void pingSecure_failure_noVI(String soap) {

      try {
         this.pingSecure_failure(soap);

         fail(FAIL_MSG);
      } catch (AxisFault e) {

         assertEquals("La référence au jeton de sécurité est introuvable", e
               .getMessage());
         assertEquals("SecurityTokenUnavailable", e.getFaultCode()
               .getLocalPart());
         assertEquals("wsse", e.getFaultCode().getPrefix());

      }

   }

   @Test
   @SuppressWarnings("PMD.JUnitAssertionsShouldIncludeMessage")
   public void pingSecure_failure_sign() {

      try {
         this
               .pingSecure_failure("src/test/resources/request/pingsecure_failure_sign.xml");

         fail(FAIL_MSG);
      } catch (AxisFault e) {
         assertEquals("La signature ou le chiffrement n'est pas valide", e
               .getMessage());
         assertEquals("FailedCheck", e.getFaultCode().getLocalPart());
         assertEquals("wsse", e.getFaultCode().getPrefix());

      }

   }

   private void pingSecure_failure(String soap) throws AxisFault {

      this.init(soap);

      PingSecureRequest request = new PingSecureRequest();

      skeleton.pingSecure(request);
   }

   private void init(String xml) {

      try {
         InputStream input = new FileInputStream(xml);

         StAXSOAPModelBuilder stax = new StAXSOAPModelBuilder(StAXUtils
               .createXMLStreamReader(input));

         ctx.setEnvelope(stax.getSOAPEnvelope());
      } catch (FileNotFoundException e) {
         throw new IllegalStateException(e);
      } catch (XMLStreamException e) {
         throw new IllegalStateException(e);
      } catch (AxisFault e) {
         throw new IllegalStateException(e);
      } catch (OMException e) {
         throw new IllegalStateException(e);
      }

   }
}
