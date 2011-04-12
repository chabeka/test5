package fr.urssaf.image.sae.webservice.client.demo.service;

import java.net.URL;

import org.apache.log4j.Logger;

import fr.urssaf.image.sae.webservice.client.demo.component.DefaultServer;
import fr.urssaf.image.sae.webservice.client.demo.component.ServiceClient;
import fr.urssaf.image.sae.webservice.client.demo.util.ResourceUtils;

/**
 * Client consommateur du Ping
 * 
 * 
 */
public class PingService {

   private static final Logger LOG = Logger.getLogger(PingService.class);

   private final URL serverURL;

   private static final String PING_REQUEST = "soap/ping.xml";

   /**
    * instanciation de l'URL par défaut du web service
    */
   public PingService() {

      this.serverURL = DefaultServer.getInstance().getUrl();
   }

   /**
    * Appel du web service 'Ping'<br>
    * <br>
    * Le message soap est configuré dans soap/ping.xml
    * 
    * @return message du serveur de web services
    */
   public final String ping() {

      ServiceClient client = new ServiceClient("Ping", serverURL);
      return client.sendReceive(ResourceUtils.loadResource(this, PING_REQUEST));

   }

   /**
    * Méthode executable pour le ping
    * 
    * @param args
    *           aucun argument n'est prise en compte
    */
   public static void main(String[] args) {

      PingService service = new PingService();
      LOG.debug("\n"+service.ping());

   }

}
