package fr.urssaf.image.sae.webservices.skeleton;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cirtil.www.saeservice.PingSecureRequest;
import fr.cirtil.www.saeservice.PingSecureResponse;
import fr.urssaf.image.sae.webservices.security.ActionsUnitaires;
import fr.urssaf.image.sae.webservices.security.spring.AuthenticationContext;
import fr.urssaf.image.sae.webservices.security.spring.AuthenticationToken;
import fr.urssaf.image.sae.webservices.util.Axis2Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-service-test.xml",
      "/applicationContext-security-test.xml" })
@SuppressWarnings( { "PMD.MethodNamingConventions" })
public class PingSecureTest {

   @Autowired
   private SaeServiceSkeleton skeleton;

   private MessageContext ctx;

   @Before
   public void before() {
      ctx = new MessageContext();
      MessageContext.setCurrentMessageContext(ctx);

   }

   @After
   public void after() {
      SecurityContextHolder.getContext().setAuthentication(null);
   }

   private PingSecureResponse pingSecure(String soap) throws AxisFault {

      Axis2Utils.initMessageContext(ctx, soap);

      PingSecureRequest request = new PingSecureRequest();

      return skeleton.pingSecure(request);
   }

   @Test
   public void pingSecure() throws AxisFault {

      PingSecureResponse response = this
            .pingSecure("src/test/resources/request/pingsecure_success.xml");

      assertEquals("Test du ping",
            "Les services du SAE sécurisés par authentification sont en ligne",
            response.getPingString());

      AuthenticationToken authentification = AuthenticationContext
            .getAuthenticationToken();

      ActionsUnitaires actionUnitaires = authentification.getDetails();
      List<String> actions = actionUnitaires.getListeActionsUniquement();

      assertEquals("le nombre d'actions unitaires est incorrect", 1, actions
            .size());
      assertEquals("role inattendue", "ROLE_TOUS", actions.get(0));

      List<String> perimetres = actionUnitaires
            .getPerimetresDonnees("ROLE_TOUS");
      assertEquals("le nombre de perimetre de données est incorrect", 1,
            perimetres.size());
      assertEquals("role inattendue", "FULL", perimetres.get(0));

   }

  
}
