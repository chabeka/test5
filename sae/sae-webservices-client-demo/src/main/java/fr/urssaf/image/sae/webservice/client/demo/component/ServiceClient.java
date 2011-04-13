package fr.urssaf.image.sae.webservice.client.demo.component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import fr.urssaf.image.sae.webservice.client.demo.util.XMLUtils;

/**
 * Classe pour envoyer des messages SOAP au serveur de web service
 * 
 * 
 */
public class ServiceClient {

   private final String soapAction;

   private final URL server;

   /**
    * initialise les paramètres du message SOAP
    * 
    * @param soapAction
    *           action du message SOAP
    * @param server
    *           URL du serveur
    */
   public ServiceClient(String soapAction, URL server) {

      if (StringUtils.isBlank(soapAction)) {
         throw new IllegalArgumentException("'soapAction' is required");
      }

      if (server == null) {
         throw new IllegalArgumentException("'server' is required");
      }

      this.soapAction = soapAction;
      this.server = server;
   }

   /**
    * envoie un message SOAP
    * 
    * @param soapMsg
    *           contenu de la requête
    * @return réponse de la requête
    */
   public final String sendReceive(InputStream soapMsg) {

      try {

         HttpURLConnection connection = (HttpURLConnection) server
               .openConnection();

         connection.setDoOutput(true);
         connection.setDoInput(true);
         connection.setRequestMethod("POST");
         connection.setRequestProperty("SOAPAction", soapAction);
         connection.setRequestProperty("Content-Type",
               "text/xml; charset=UTF-8");

         OutputStream output = connection.getOutputStream();

         IOUtils.copy(soapMsg, output);

         InputStream input;
         try {
            input = connection.getInputStream();

         } catch (IOException e) {
            input = connection.getErrorStream();
         }

         return XMLUtils.print(input);

      } catch (IOException e) {
         throw new IllegalStateException(e);
      }

   }

   /**
    * envoie un message SOAP
    * 
    * @param soapMsg
    *           contenu de la requête
    * @return réponse de la requête
    */
   public final String sendReceive(String soapMsg) {

      return sendReceive(IOUtils.toInputStream(soapMsg));
   }

}
