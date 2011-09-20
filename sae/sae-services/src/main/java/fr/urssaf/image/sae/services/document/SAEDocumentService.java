package fr.urssaf.image.sae.services.document;

/**
 * Fournit l’ensemble des services : <br>
 * <li>{@link SAECaptureService Capture}</li><br>
 * <li>
 * {@link SAESearchService Recherche}</li><br>
 * <li>
 * {@link SAEConsultationService Consultation}</li>
 */
public interface SAEDocumentService extends SAESearchService,
      SAEConsultationService {
}
