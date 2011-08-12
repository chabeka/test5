package fr.urssaf.image.sae.services.document;

import fr.urssaf.image.sae.exception.SAEConsultationServiceEx;
import fr.urssaf.image.sae.model.UntypedDocument;

/**
 * Fournit l’ensemble des services pour la consultation.<br/>
 * 
 * @author rhofir.
 */
public interface SAEConsultationService {

   /**
    * 
    * Service pour l'opération <b>Consultation</b>
    * 
    * @param untypedDoc
    *           : Un objet de type {@link UntypedDocument} .
    * @return Un objet de type {@link UntypedDocument}.
    * @throws SAEConsultationServiceEx
    *            Exception levée lorsque la consultation ne se déroule pas bien.
    */
   UntypedDocument consultation(UntypedDocument untypedDoc)
         throws SAEConsultationServiceEx;
}
