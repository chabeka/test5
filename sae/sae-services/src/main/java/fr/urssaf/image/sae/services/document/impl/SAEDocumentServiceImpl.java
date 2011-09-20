package fr.urssaf.image.sae.services.document.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.bo.model.bo.SAELuceneCriteria;
import fr.urssaf.image.sae.bo.model.untyped.UntypedDocument;
import fr.urssaf.image.sae.exception.SAECaptureServiceEx;
import fr.urssaf.image.sae.exception.SAESearchServiceEx;
import fr.urssaf.image.sae.services.document.SAECaptureService;
import fr.urssaf.image.sae.services.document.SAEConsultationService;
import fr.urssaf.image.sae.services.document.SAEDocumentService;
import fr.urssaf.image.sae.services.document.SAESearchService;
import fr.urssaf.image.sae.services.document.exception.SAEConsultationServiceException;
import fr.urssaf.image.sae.storage.dfce.annotations.FacadePattern;

/**
 * Fournit la façade des implementations des services :<br>
 * <lu><br>
 * <li>{@link SAECaptureServiceImpl Capture}</li> <br>
 * <li>{@link SAESearchServiceImpl Recherche}</li><li>
 * {@link SAEConsultationServiceImpl Consultation}</li>
 * <ul>
 * 
 * @author akenore,rhofir.
 */
@Service
@Qualifier("saeDocumentService")
@SuppressWarnings( { "PMD.AvoidDuplicateLiterals", "PMD.LongVariable" })
@FacadePattern(participants = { SAECaptureServiceImpl.class,
      SAEConsultationServiceImpl.class, SAESearchServiceImpl.class }, comment = "Fournit les services des classes participantes")
public class SAEDocumentServiceImpl implements SAEDocumentService {

   //@Autowired
   //@Qualifier("saeCaptureService")
   private SAECaptureService saeCaptureService;
   @Autowired
   @Qualifier("saeConsultationService")
   private SAEConsultationService saeConsultationService;
   @Autowired
   @Qualifier("saeSearchService")
   private SAESearchService saeSearchService;

   /**
    * {@inheritDoc}
    */
   public final void bulkCapture(final String urlEcde)
         throws SAECaptureServiceEx {
      saeCaptureService.bulkCapture(urlEcde);
   }

   /**
    * {@inheritDoc}
    */
   public final List<UntypedDocument> search(
         final SAELuceneCriteria sAELuceneCriteria) throws SAESearchServiceEx {
      return saeSearchService.search(sAELuceneCriteria);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final UntypedDocument consultation(UUID idArchive)
         throws SAEConsultationServiceException {
      return saeConsultationService.consultation(idArchive);
   }

}
