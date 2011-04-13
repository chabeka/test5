package fr.urssaf.image.sae.webservice.client.demo.service;

import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import fr.urssaf.image.sae.webservice.client.demo.component.DefaultKeystore;
import fr.urssaf.image.sae.webservice.client.demo.component.DefaultServer;
import fr.urssaf.image.sae.webservice.client.demo.component.ServiceClient;
import fr.urssaf.image.sae.webservice.client.demo.security.signature.exception.XmlSignatureException;
import fr.urssaf.image.sae.webservice.client.demo.security.ws.SAML20Service;
import fr.urssaf.image.sae.webservice.client.demo.security.ws.WSSecurityService;
import fr.urssaf.image.sae.webservice.client.demo.util.ResourceUtils;
import fr.urssaf.image.sae.webservice.client.demo.util.StreamUtils;

/**
 * Client consommateur du Ping sécurisé
 * 
 * 
 */
public class PingSecureService {

   private static final Logger LOG = Logger.getLogger(PingSecureService.class);

   private final URL serverURL;

   private static final String PING_REQUEST = "soap/pingSecure.xml";

   private final WSSecurityService wsService;

   private final SAML20Service assertionService;

   /**
    * instanciation de l'URL par défaut du web service
    */
   public PingSecureService() {

      this.serverURL = DefaultServer.getInstance().getUrl();
      this.wsService = new WSSecurityService();
      this.assertionService = new SAML20Service();

   }

   /**
    * Appel du web service 'PingSecure'<br>
    * <br>
    * Le message soap est configuré dans soap/pingSecure.xml
    * 
    * @param role
    *           role du pagm
    * @return message du serveur de web services
    */
   public final String pingSecure(String role) {

      KeyStore keystore = DefaultKeystore.getInstance().getKeystore();
      String alias = DefaultKeystore.getInstance().getAlias();
      String password = DefaultKeystore.getInstance().getPassword();

      DateTime systemDate = new DateTime();

      DateTime notAfter = systemDate.plusHours(1);
      DateTime notBefore = systemDate.minus(1);

      String assertion;
      try {

         assertion = this.assertionService.createAssertion20(role, notAfter,
               notBefore, keystore, alias, password);
      } catch (XmlSignatureException e) {
         throw new IllegalStateException(e);
      }

      String wsseSecurity = this.wsService.createWSSEHeader(this.serverURL,
            "PingSecure", assertion);

      InputStream soapMessage = ResourceUtils.loadResource(this, PING_REQUEST);

      String[] searchList = new String[] { "[wsseSecurity]" };
      String[] replacementList = new String[] { wsseSecurity };

      String soapMessageWS = StreamUtils.createObject(soapMessage, searchList,
            replacementList);

      ServiceClient client = new ServiceClient("PingSecure", serverURL);
      return client.sendReceive(soapMessageWS);

   }

   /**
    * Méthode executable pour le ping secure <br>
    * arguments:
    * <ul>
    * <li>arg[0]: role</li>
    * </ul>
    * 
    * @param args
    *           arguments prise en compte
    */
   public static void main(String[] args) {

      if (ArrayUtils.isEmpty(args)) {
         throw new IllegalArgumentException("role is required");
      }

      String role = args[0];
      PingSecureService service = new PingSecureService();

      LOG.debug("\n" + service.pingSecure(role));

   }
}
