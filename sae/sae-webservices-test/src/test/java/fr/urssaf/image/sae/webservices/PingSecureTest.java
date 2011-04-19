package fr.urssaf.image.sae.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.context.SecurityContextHolder;

import fr.urssaf.image.sae.webservices.modele.SaeServiceStub;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.PingSecureRequest;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.PingSecureResponse;
import fr.urssaf.image.sae.webservices.util.AuthenticateUtils;

@SuppressWarnings({
   "PMD.MethodNamingConventions",
   "PMD.LongVariable"})
public class PingSecureTest {

   private SaeServiceStub service;

   private static final Logger LOG = Logger.getLogger(PingSecureTest.class);

   private static final String SECURITY_PATH = "src/main/resources/META-INF";

   @Before
   public void before() throws AxisFault, ConfigurationException {

      Configuration config = new PropertiesConfiguration(
            "sae-webservices-test.properties");

      ConfigurationContext ctx = ConfigurationContextFactory
            .createConfigurationContextFromFileSystem(SECURITY_PATH,
                  SECURITY_PATH + "/axis2.xml");

      service = new SaeServiceStub(ctx, config.getString("urlServiceWeb"));
   }

   @After
   public void after() {

      SecurityContextHolder.getContext().setAuthentication(null);
   }

   private String getSoapFaultInfos(AxisFault fault) {
      StringBuilder messageFailure = new StringBuilder();
      messageFailure.append("SoapFault :");
      messageFailure.append("\r\n" + "FaultCode = "
            + fault.getFaultCode().getPrefix() + ":"
            + fault.getFaultCode().getLocalPart());
      messageFailure.append("\r\n" + "FaultString = " + fault.getMessage());
      return messageFailure.toString();
   }

   @Test
   public void pingSecureAvecViOk_success() throws RemoteException {

      try {

         AuthenticateUtils.authenticate("ROLE_TOUS");

         PingSecureRequest request = new PingSecureRequest();

         PingSecureResponse response = service.pingSecure(request);

         LOG.debug(response.getPingString());

         assertEquals(
               "Test du ping securisé",
               "Les services du SAE sécurisés par authentification sont en ligne",
               response.getPingString());

      } catch (AxisFault fault) {
         fail(getSoapFaultInfos(fault));
      }

   }

   
   
   private void verificationSoapFault(
         AxisFault fault,
         String faultCodeAttendu,
         String faultStringAttendu) {
      
      // Résultats obtenus
      String faultCodeObtenu = 
         fault.getFaultCode().getPrefix() + 
         ":" + 
         fault.getFaultCode().getLocalPart();
      String faultStringObtenu = fault.getMessage();
      
      // Assertions
      assertEquals("Le FaultCode n'est pas bon",faultCodeAttendu,faultCodeObtenu);
      assertEquals("La FaultString n'est pas bonne",faultStringAttendu,faultStringObtenu);
      
   }
   
   
   
   /**
    * Test unitaire de la SoapFault sae:DroitsInsuffisants<br>
    * <br>
    * Cas de test : On consomme le service pingSecure avec un droit applicatif
    * qui n'est pas suffisant<br>
    * <br>
    * Résultat attendu : levée d'une SoapFault avec les données suivantes :<br>
    * <ul>
    *    <li>FaultCode   : sae:DroitsInsuffisants</li>
    *    <li>FaultString : Les droits présents dans le vecteur d'identification sont insuffisants pour effectuer l'action demandée</li>
    * </ul>
    * 
    * @throws RemoteException
    */
   @Test
   public void pingSecureAvecViOk_failure() throws RemoteException {

      AuthenticateUtils.authenticate("OTHER_ROLE");

      PingSecureRequest request = new PingSecureRequest();

      try {
         service.pingSecure(request);
         fail("le test doit échouer");
      } catch (AxisFault fault) {
         
         // On trace
         LOG.debug(getSoapFaultInfos(fault));
         
         // Résultats attendus
         String faultCodeAttendu = "sae:DroitsInsuffisants";
         String faultStringAttendu = "Les droits présents dans le vecteur d'identification sont insuffisants pour effectuer l'action demandée";
         
         // La vérification de la SoapFault est dans une autre méthode partagée
         verificationSoapFault(fault,faultCodeAttendu,faultStringAttendu);
         
      }

   }

   
   /**
    * Test unitaire de la SoapFault wsse:SecurityTokenUnavailable<br>
    * <br>
    * Cas de test : On consomme le service pingSecure sans mettre de VI dans le message SOAP<br>
    * <br>
    * Résultat attendu : levée d'une SoapFault avec les données suivantes :<br>
    * <ul>
    *    <li>FaultCode   : wsse:SecurityTokenUnavailable</li>
    *    <li>FaultString : La référence au jeton de sécurité est introuvable</li>
    * </ul>
    * 
    * @throws RemoteException
    */
   @Test
   public void pingSecureSansVI() throws RemoteException {

      PingSecureRequest request = new PingSecureRequest();

      try {
         service.pingSecure(request);
         fail("le test doit échouer");
      } catch (AxisFault fault) {
         
         // On trace
         LOG.debug(getSoapFaultInfos(fault));
         
         // Résultats attendus
         String faultCodeAttendu = "wsse:SecurityTokenUnavailable";
         String faultStringAttendu = "La référence au jeton de sécurité est introuvable";
         
         // La vérification de la SoapFault est dans une autre méthode partagée
         verificationSoapFault(fault,faultCodeAttendu,faultStringAttendu);

      }
   }
}
