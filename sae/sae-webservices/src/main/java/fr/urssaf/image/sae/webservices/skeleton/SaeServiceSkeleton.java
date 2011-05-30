/**
 * SaeServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:18:42 CET)
 * 
 * Le fichier est ensuite mis à jour manuellement lors de l'évolution du WSDL
 * 
 */
package fr.urssaf.image.sae.webservices.skeleton;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.cirtil.www.saeservice.ArchivageMasse;
import fr.cirtil.www.saeservice.ArchivageMasseResponse;
import fr.cirtil.www.saeservice.ArchivageUnitaire;
import fr.cirtil.www.saeservice.ArchivageUnitaireResponse;
import fr.cirtil.www.saeservice.Consultation;
import fr.cirtil.www.saeservice.ConsultationResponse;
import fr.cirtil.www.saeservice.PingRequest;
import fr.cirtil.www.saeservice.PingResponse;
import fr.cirtil.www.saeservice.PingSecureRequest;
import fr.cirtil.www.saeservice.PingSecureResponse;
import fr.cirtil.www.saeservice.Recherche;
import fr.cirtil.www.saeservice.RechercheResponse;
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
 *          &lt;actionMapping>Ping&lt;/actionMapping>
 *          &lt;outputActionMapping>Ping&lt;/outputActionMapping>
 *       &lt;/operation>
 *       &lt;operation name="PingSecure" mep="http://www.w3.org/ns/wsdl/in-out" namespace="http://www.cirtil.fr/saeService">
 *             &lt;actionMapping>PingSecure&lt;/actionMapping>
 *             &lt;outputActionMapping>PingSecure&lt;/outputActionMapping>
 *         &lt;/operation>
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
   
   /**
    * endpoint du ping sécurisé
    * 
    * @param pingRequest
    *           vide
    * @return reponse du web service
    */
   public final PingSecureResponse pingSecure(PingSecureRequest pingRequest) {

      PingSecureResponse response = new PingSecureResponse();

      response.setPingString(service.pingSecure());

      return response;
   }
   
   

   /**
    * endpoint de la capture unitaire
    * 
    * @param request 
    * @return reponse du web service
    * @throws AxisFault 
    */
   public final ArchivageUnitaireResponse archivageUnitaireSecure(
         ArchivageUnitaire request) throws AxisFault {
      throw new AxisFault(
         buildServiceNonImplementeSoapFaultCode(),
         "Le service d'archivage unitaire n'est pas encore disponible",
         null,
         null,
         null);
   }
   
   
   /**
    * endpoint de la capture de masse
    * 
    * @param request request du web service
    * @return reponse du web service
    * @throws AxisFault 
    */
   public final ArchivageMasseResponse archivageMasseSecure(
         ArchivageMasse request) throws AxisFault {
      throw new AxisFault(
         buildServiceNonImplementeSoapFaultCode(),
         "Le service d'archivage de masse n'est pas encore disponible",
         null,
         null,
         null);
   }
   
   
   /**
    * endpoint de recherche
    * 
    * @param request request du web service
    * @return reponse du web service
    * @throws AxisFault 
    */
   public final RechercheResponse rechercheSecure(
         Recherche request) throws AxisFault {
      throw new AxisFault(
         buildServiceNonImplementeSoapFaultCode(),
         "Le service de recherche n'est pas encore disponible",
         null,
         null,
         null);
   }
   
   
   /**
    * endpoint de consultation
    * 
    * @param request request du web service
    * @return reponse du web service
    * @throws AxisFault 
    */
   public final ConsultationResponse consultationSecure(
         Consultation request) throws AxisFault {
      throw new AxisFault(
         buildServiceNonImplementeSoapFaultCode(),
         "Le service de consultation n'est pas encore disponible",
         null,
         null,
         null);
   }
   
   
   private QName buildServiceNonImplementeSoapFaultCode() {
      return new QName("urn:sae:faultcodes","ServiceNonImplemente","sae");
   }
   

}
