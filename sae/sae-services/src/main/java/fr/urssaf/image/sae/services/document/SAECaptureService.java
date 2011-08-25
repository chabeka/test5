package fr.urssaf.image.sae.services.document;

import fr.urssaf.image.sae.bo.model.untyped.UntypedDocument;
import fr.urssaf.image.sae.exception.SAECaptureServiceEx;


/**
 * Fournit l’ensemble des services :<BR/>
 * <lu> <li>Capture unitaire,</li> <br>
 * <li>Capture en masse.</li> </lu>
 * 
 * @author rhofir.
 */
public interface SAECaptureService {
   /**
    * Service pour l'opération <b>capture unitaire</b>
    * 
    * @param untypedDoc
    *           : Un objet de type {@link UntypedDocument}.
    * @return Une chaîne dont le contenu est un UUID.
    * @throws SAECaptureServiceEx
    *            Exception levée lorsque la capture ne se déroule pas bien.
    */
   String capture(UntypedDocument untypedDoc) throws SAECaptureServiceEx;

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
