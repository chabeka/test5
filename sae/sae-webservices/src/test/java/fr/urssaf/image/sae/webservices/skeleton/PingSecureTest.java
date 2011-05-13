package fr.urssaf.image.sae.webservices.skeleton;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import javax.naming.NamingException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cirtil.www.saeservice.PingSecureRequest;
import fr.cirtil.www.saeservice.PingSecureResponse;
import fr.urssaf.image.sae.webservices.component.IgcConfigFactory;
import fr.urssaf.image.sae.webservices.component.SecurityFactory;
import fr.urssaf.image.sae.webservices.component.TempDirectoryFactory;
import fr.urssaf.image.sae.webservices.security.ActionsUnitaires;
import fr.urssaf.image.sae.webservices.security.igc.IgcService;
import fr.urssaf.image.sae.webservices.security.spring.AuthenticationContext;
import fr.urssaf.image.sae.webservices.security.spring.AuthenticationToken;
import fr.urssaf.image.sae.webservices.util.Axis2Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
@SuppressWarnings( { "PMD.MethodNamingConventions" })
public class PingSecureTest {

   private static final String FAIL_MSG = "le test doit échouer";

   private static final String FAULT_CODE = "FaultCode incorrect";

   @Autowired
   private SaeServiceSkeleton skeleton;

   @Autowired
   private IgcService igcService;

   private MessageContext ctx;

   private static final String IGC_CONFIG = "igcConfig.xml";

   @BeforeClass
   public static void beforeClass() throws NamingException {

      // création des répertoires pour le dépot des CRL et du
      // fichier de configuration

      TempDirectoryFactory.createDirectory();
      TempDirectoryFactory.createACDirectory();
      TempDirectoryFactory.createCRLDirectory();

      // création du fichier igcConfig.xml
      IgcConfigFactory.createIgcConfig(IGC_CONFIG);

      // téléchargement d'un certificat dans le répertoires temporaire
      SecurityFactory.downloadCertificat("pseudo_IGCA.crt");

      // paramétrage de la variable JNDI
      SimpleNamingContextBuilder builder = SimpleNamingContextBuilder
            .emptyActivatedContextBuilder();
      builder.bind("java:comp/env/SAE_Fichier_Configuration_IGC",
            TempDirectoryFactory.DIRECTORY + "/" + IGC_CONFIG);

   }

   @AfterClass
   public static void afterClass() {

      TempDirectoryFactory.cleanDirectory();
   }

   @Before
   public void before() {
      ctx = new MessageContext();
      MessageContext.setCurrentMessageContext(ctx);

      igcService.initCertifsAndCrl();

      // téléchargement des CRL dans le répertoires temporaire
      TempDirectoryFactory.cleanCRLDirectory();
      SecurityFactory.downloadCRLs(IGC_CONFIG);

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
   public void pingSecure_success() throws AxisFault {

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

   @Test
   public void pingSecure_failure_accessDenied() throws AxisFault {

      try {
         this
               .pingSecure("src/test/resources/request/pingsecure_failure_accessDenied.xml");
         fail(FAIL_MSG);
      } catch (AxisFault e) {

         assertEquals(
               FAULT_CODE,
               "Les droits présents dans le vecteur d'identification sont insuffisants pour effectuer l'action demandée",
               e.getMessage());
         assertEquals(FAULT_CODE, "DroitsInsuffisants", e.getFaultCode()
               .getLocalPart());
         assertEquals(FAULT_CODE, "sae", e.getFaultCode().getPrefix());

         assertEquals(FAULT_CODE, "urn:sae:faultcodes", e.getFaultCode()
               .getNamespaceURI());

      }

   }

   @Test
   public void pingSecure_failure_loadCertifsAndCrlException() throws AxisFault {

      TempDirectoryFactory.cleanCRLDirectory();
      SecurityFactory.downloadCRL("pseudo_IGCA.crt");
      
      try {
         this.pingSecure("src/test/resources/request/pingsecure_success.xml");
         fail(FAIL_MSG);
      } catch (AxisFault e) {

         assertEquals(
               FAULT_CODE,
               "Une erreur interne du SAE s'est produite lors du chargement des certificats des AC racine ou des CRL",
               e.getMessage());
         assertEquals(FAULT_CODE, "CertificateInitializationError", e
               .getFaultCode().getLocalPart());
         assertEquals(FAULT_CODE, "sae", e.getFaultCode().getPrefix());

         assertEquals(FAULT_CODE, "urn:sae:faultcodes", e.getFaultCode()
               .getNamespaceURI());

      }

   }

}
