package fr.urssaf.image.sae.webservice.client.demo;

import java.net.URL;

import fr.urssaf.image.sae.webservice.client.demo.component.DefaultServer;
import fr.urssaf.image.sae.webservice.client.demo.component.ServiceClient;
import fr.urssaf.image.sae.webservice.client.demo.util.ResourceUtils;

/**
 * Client consommateur du Ping
 * 
 * 
 */
public class PingService {

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

}
