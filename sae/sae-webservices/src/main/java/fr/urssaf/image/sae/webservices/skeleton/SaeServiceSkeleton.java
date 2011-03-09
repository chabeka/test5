/**
 * SaeServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:18:42 CET)
 */
package fr.urssaf.image.sae.webservices.skeleton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.cirtil.www.saeservice.PingRequest;
import fr.cirtil.www.saeservice.PingResponse;
import fr.urssaf.image.sae.webservices.SaeService;

/**
 * Skeleton du web service coté serveur<br>
 * La configuration dans <code>services.xml</code>
 * 
 * <pre>
 * 
 * &lt;service name="SaeService">
 *       &lt;messageReceivers>
 *          &lt;messageReceiver mep="http://www.w3.org/ns/wsdl/in-out"
 *             class="fr.urssaf.image.sae.webservices.skeleton.SaeServiceMessageReceiverInOut" />
 *       &lt;/messageReceivers>
 *       &lt;parameter name="ServiceObjectSupplier">org.apache.axis2.extensions.spring.receivers.SpringAppContextAwareObjectSupplier
 *       &lt;/parameter>
 *       &lt;parameter name="SpringBeanName" locked="false">saeServiceSkeleton
 *       &lt;/parameter>
 *       &lt;parameter name="useOriginalwsdl">true&lt;/parameter>
 *       &lt;parameter name="modifyUserWSDLPortAddress">false&lt;/parameter>
 *       &lt;operation name="Ping" mep="http://www.w3.org/ns/wsdl/in-out"
 *          namespace="http://www.cirtil.fr/saeService">
 *          &lt;actionMapping>Ping</actionMapping>
 *          &lt;outputActionMapping>Ping</outputActionMapping>
 *       &lt;/operation>
 *    &lt;/service>
 * 
 * </pre>
 * 
 * Code généré à partir du plugin maven
 * <code>axis2-wsdl2code-maven-plugin</code><br>
 * <br>
 * Il est nécessaire d'injecter dans les endpoints générés l'implémentation des
 * services
 * 
 */
@Component
public class SaeServiceSkeleton {

   private final SaeService service;

   /**
    * Instanciation du service {@link SaeService}
    * 
    * @param service
    *           implémentation des services web
    */
   @Autowired
   public SaeServiceSkeleton(SaeService service) {
      this.service = service;
   }

   /**
    * endpoint du ping
    * 
    * @param pingRequest
    *           vide
    * @return reponse du web service
    */
   public final PingResponse ping(PingRequest pingRequest) {

      PingResponse response = new PingResponse();

      response.setPingString(service.ping());

      return response;
   }

}
