package fr.urssaf.image.sae.services.document;

import fr.urssaf.image.sae.services.exception.capture.SAECaptureServiceEx;

/**
 * Fournit l’ensemble des services :<BR/>
 * <lu> <li>Capture unitaire,</li> <br>
 * <li>Capture en masse.</li> </lu>
 * 
 * @author rhofir.
 */
public interface SAEBulkCaptureService {

   /**
    * Service pour l'opération <b>capture en Masse</b>
    * 
    * @param urlSommaire
    *           : L’URL ECDE du fichier sommaire.xml décrivant le traitement de
    *           masse.
    * @throws SAECaptureServiceEx
    *            Exception levée lorsque la prise en charge de la capture en
    *            masse ne se déroule pas bien.
    */
   void bulkCapture(String urlSommaire) throws SAECaptureServiceEx;

}
