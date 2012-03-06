package fr.urssaf.image.sae.webservices.service;

import fr.cirtil.www.saeservice.ConsultationMTOM;
import fr.cirtil.www.saeservice.ConsultationMTOMResponse;
import fr.urssaf.image.sae.webservices.exception.ConsultationAxisFault;

/**
 * Service web de consultation du SAE
 * 
 * 
 */
public interface WSConsultationMTOMService {

   /**
    * 
    * Service pour l'opération <b>ConsultationMTOM</b>
    * 
    * <pre>
    * &lt;wsdl:operation name="consultationMTOM">
    *    &lt;wsdl:documentation>Service de consultation documentaire avec optimisation MTOM du SAE</wsdl:documentation>
    *    ...      
    * &lt;/wsdl:operation>
    * </pre>
    * 
    * La requête correspond à :
    * 
    * <pre>
    *   &lt;xsd:element name="consultationMTOM"
    *       type="sae:consultationMTOMRequestType"/>
    * </pre>
    * 
    * La réponse correspond à :
    * 
    * <pre>
    * &lt;wsdl:message name="consultationMTOMResponseMessage">
    * </pre>
    * 
    * @param request
    *           Requête de consultationMTOM du service web <b>ConsultationMTOM</b>
    * @return Réponse du service web <b>ConsultationMTOM</b>
    * 
    * @throws ConsultationAxisFault
    *            Une exception est levée lors de la consultationMTOM
    */
   ConsultationMTOMResponse consultationMTOM(ConsultationMTOM request)
         throws ConsultationAxisFault;
}
