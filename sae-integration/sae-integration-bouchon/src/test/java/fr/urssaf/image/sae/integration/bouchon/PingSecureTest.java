package fr.urssaf.image.sae.integration.bouchon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.PingSecureRequest;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.PingSecureResponse;
import fr.urssaf.image.sae.integration.bouchon.security.VIHandler;
import fr.urssaf.image.sae.integration.bouchon.utils.SoapFaultUtils;


/**
 * Tests de l'opération "pingSecure" et de toutes les SoapFault liées à l'authentification<br>
 * <br>
 * Tests réalisés :<br>
 * <br>
 * <ul>
 *    <li>consommation de l'opération pingSecure avec un bloc d'authentification correct<br><br></li>
 *    <li>consommation de l'opération pingSecure de sorte à obtenir chacune des SoapFault suivantes :
 *       <br><br>
 *       <ul>
 *          <li>wsse:SecurityTokenUnavailable</li>
 *          <li>wsse:InvalidSecurityToken</li>
 *          <li>wsse:FailedCheck</li>
 *          <li>vi:InvalidPagm  <b><i>=> Ce test n'est pas actif pour l'instant, car il
 *          nécessite l'implémentation des droits côté SAE.</i></b>
 *          </li>
 *          <li>vi:InvalidVI</li>
 *          <li>vi:InvalidService</li>
 *          <li>vi:InvalidAuthLevel</li>
 *          <li>sae:DroitsInsuffisants</li>
 *       </ul>
 *    </li>
 * </ul>
 * 
 */
public class PingSecureTest {

   private static Configuration config;
   
   private static final Logger LOG = Logger.getLogger(PingSecureTest.class);
   
   private static final String SECURITY_PATH = "src/main/resources/META-INF";

   
   @BeforeClass
   public static void beforeClass() throws ConfigurationException, AxisFault {
      
      config = new PropertiesConfiguration(
            "sae-webservices-test.properties");
    
   }
   

   private SaeServiceStub getServiceStub(String fichierRessourceVi) throws AxisFault {
      
      ConfigurationContext configContext = 
         ConfigurationContextFactory.createConfigurationContextFromFileSystem(
            SECURITY_PATH,
            SECURITY_PATH + "/axis2.xml");
      
      configContext.setProperty(
            VIHandler.PROP_FICHIER_VI, 
            fichierRessourceVi);

      SaeServiceStub service = new SaeServiceStub(
            configContext,
            config.getString("urlServiceWeb"));
      
      return service;
      
   }

   
   /**
    * Cas de test : 002_PingSecure_ok
    */
   @Test
   public void cas_002_PingSecure_ok() throws RemoteException {

      SaeServiceStub service = getServiceStub("vi/vi_ok.xml");
      
      PingSecureRequest request = new PingSecureRequest();

      PingSecureResponse response = service.pingSecure(request);

      LOG.debug(response.getPingString());

      assertEquals(
            "pingSecure - VI complet",
            "Les services du SAE sécurisés par authentification sont en ligne",
            response.getPingString());
      
   }
   
   
   private void pingSecure_soapfault(
         String fichierRessourceVi,
         String faultMessageAttendu,
         QName faultCodeAttendu) throws RemoteException {
    
      SaeServiceStub service = getServiceStub(fichierRessourceVi);
      
      PingSecureRequest request = new PingSecureRequest();

      try {
         
         // Appel de l'opération pingSecure
         service.pingSecure(request);
         
         // Echec du test, car on aurait du avoir une SoapFault
         fail(
               "On aurait du obtenir une SoapFault " + 
               faultCodeAttendu.getPrefix() + 
               ":" + 
               faultCodeAttendu.getLocalPart());
         
      } catch (AxisFault fault) {
         
         SoapFaultUtils.checkSoapFault(
               fault,
               faultMessageAttendu,
               faultCodeAttendu);

      }
      
   }
   
   
   /**
    * Cas de test : 003_PingSecure-SoapFault_wsse_SecurityTokenUnavailable
    */
   @Test
   public void cas_003_PingSecure_SoapFault_wsse_SecurityTokenUnavailable() throws RemoteException {
      
      pingSecure_soapfault(
            "vi/vi_SoapFault_wsse_SecurityTokenUnavailable.xml",
            "La référence au jeton de sécurité est introuvable",
            new QName(SoapFaultUtils.NAMESPACE_WSSE,"SecurityTokenUnavailable","wsse"));
      
   }
   
   
   /**
    * Cas de test : 004_PingSecure-SoapFault_wsse_InvalidSecurityToken
    */
   @Test
   public void cas_004_PingSecure_SoapFault_wsse_InvalidSecurityToken() throws RemoteException {
      
      pingSecure_soapfault(
            "vi/vi_SoapFault_wsse_InvalidSecurityToken.xml",
            "Le jeton de sécurité fourni est invalide",
            new QName(SoapFaultUtils.NAMESPACE_WSSE,"InvalidSecurityToken","wsse"));
      
   }
   
   
   /**
    * Cas de test : 005_PingSecure-SoapFault_wsse_FailedCheck
    */
   @Test
   public void cas_005_PingSecure_SoapFault_wsse_FailedCheck() throws RemoteException {
      
      pingSecure_soapfault(
            "vi/vi_SoapFault_wsse_FailedCheck.xml",
            "La signature ou le chiffrement n'est pas valide",
            new QName(SoapFaultUtils.NAMESPACE_WSSE,"FailedCheck","wsse"));
      
   }
   
   
   /**
    * Cas de test : 006_PingSecure-SoapFault_vi_InvalidVI
    */
   @Test
   public void cas_006_PingSecure_SoapFault_vi_InvalidVI() throws RemoteException {
      
      pingSecure_soapfault(
            "vi/vi_SoapFault_vi_InvalidVI.xml",
            "Le VI est invalide",
            new QName(SoapFaultUtils.NAMESPACE_VI,"InvalidVI","vi"));
      
   }
   
   
   /**
    * Cas de test : 007_PingSecure-SoapFault_vi_InvalidService
    */
   @Test
   public void cas_007_PingSecure_SoapFault_vi_InvalidService() throws RemoteException {
      
      pingSecure_soapfault(
            "vi/vi_SoapFault_vi_InvalidService.xml",
            "Le service visé par le VI n'existe pas ou est invalide",
            new QName(SoapFaultUtils.NAMESPACE_VI,"InvalidService","vi"));
      
   }
   
   
   /**
    * Cas de test : 009_PingSecure-SoapFault_vi_InvalidPagm
    */
   @Test
   @Ignore("Le test ne peut pas encore fonctionner : il manque la gestion des droits côté SAE")
   public void cas_009_PingSecure_SoapFault_vi_InvalidPagm() throws RemoteException {
      
      pingSecure_soapfault(
            "vi/vi_SoapFault_vi_InvalidPagm.xml",
            "Le ou les PAGM présents dans le VI sont invalides",
            new QName(SoapFaultUtils.NAMESPACE_VI,"InvalidPagm","vi"));
      
   }
   
   
   /**
    * Cas de test : 008_PingSecure-SoapFault_vi_InvalidAuthLevel
    */
   @Test
   public void cas_008_PingSecure_SoapFault_vi_InvalidAuthLevel() throws RemoteException {
      
      pingSecure_soapfault(
            "vi/vi_SoapFault_vi_InvalidAuthLevel.xml",
            "Le niveau d'authentification initial n'est pas conforme au contrat d'interopérabilité",
            new QName(SoapFaultUtils.NAMESPACE_VI,"InvalidAuthLevel","vi"));
      
   }
   
   
   /**
    * Cas de test : 010_PingSecure-SoapFault_sae_DroitsInsuffisants
    */
   @Test
   public void cas_010_PingSecure_SoapFault_sae_DroitsInsuffisants() throws RemoteException {
      
      pingSecure_soapfault(
            "vi/vi_SoapFault_sae_DroitsInsuffisants.xml",
            "Les droits présents dans le vecteur d'identification sont insuffisants pour effectuer l'action demandée",
            new QName(SoapFaultUtils.NAMESPACE_SAE,"DroitsInsuffisants","sae"));
      
   }
   
}
