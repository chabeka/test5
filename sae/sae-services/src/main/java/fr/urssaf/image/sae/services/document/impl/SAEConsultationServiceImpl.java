package fr.urssaf.image.sae.services.document.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.exception.SAEConsultationServiceEx;
import fr.urssaf.image.sae.model.UntypedDocument;
import fr.urssaf.image.sae.services.document.SAEConsultationService;

/**
 * Fournit l'implémentation des services pour la consultation.<br/>
 * 
 * @author akenore,rhofir.
 */
@Service
@Qualifier("saeConsultationService")
public class SAEConsultationServiceImpl extends AbstractSAEServices implements
      SAEConsultationService {
   /**
    * {@inheritDoc}
    */
   public final UntypedDocument consultation(UntypedDocument untypedDoc)
         throws SAEConsultationServiceEx {
      // TODO
      throw new SAEConsultationServiceEx("Fonction non implémenter");
   }
}
