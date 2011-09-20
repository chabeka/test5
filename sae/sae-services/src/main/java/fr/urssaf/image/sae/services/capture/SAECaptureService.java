package fr.urssaf.image.sae.services.capture;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

import fr.urssaf.image.sae.services.capture.exception.SAECaptureException;

/**
 * Service pour l'opération : capture unitaire
 * 
 * 
 */
public interface SAECaptureService {

   /***
    * Service pour l'opération : capture unitaire
    * 
    * @param metadatas
    *           liste des métadonnées à archiver
    * @param ecdeURL
    *           url ECDE du fichier numérique à archiver
    * @return Identifiant unique du document dans le SAE
    * @throws SAECaptureException
    *            exception levée lors de la capture
    */
   UUID capture(Map<String, String> metadatas, URI ecdeURL)
         throws SAECaptureException;
}
