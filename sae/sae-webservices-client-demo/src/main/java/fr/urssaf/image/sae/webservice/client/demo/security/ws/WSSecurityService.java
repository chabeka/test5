package fr.urssaf.image.sae.webservice.client.demo.security.ws;

import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

import fr.urssaf.image.sae.webservice.client.demo.util.ResourceUtils;
import fr.urssaf.image.sae.webservice.client.demo.util.StreamUtils;

/**
 * Classe de service de WS Security
 * 
 * 
 */
public class WSSecurityService {

   private static final String WS_SECURITY = "security/wsseSecurity.xml";

   /**
    * Instanciation de l'entête WS Security basé sur le format du fichier
    * "security/wsseSecurity.xml"
    * 
    * @param url
    *           valeur de [url]
    * @param action
    *           valeur de [action]
    * @param assertion
    *           valeur de [assertion]
    * @return entête WS Security
    */
   public final String createWSSEHeader(URL url, String action, String assertion) {

      InputStream wsseStream = ResourceUtils.loadResource(this, WS_SECURITY);

      String[] searchList = new String[] { "[url]", "[action]", "[messageID]",
            "[assertion]" };
      String[] replacementList = new String[] { url.toExternalForm(), action,
            UUID.randomUUID().toString(), assertion };

      return StreamUtils.createObject(wsseStream, searchList, replacementList);

   }

}
