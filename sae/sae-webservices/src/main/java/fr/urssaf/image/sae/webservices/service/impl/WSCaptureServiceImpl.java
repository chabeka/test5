package fr.urssaf.image.sae.webservices.service.impl;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cirtil.www.saeservice.ArchivageUnitaire;
import fr.cirtil.www.saeservice.ArchivageUnitaireResponse;
import fr.cirtil.www.saeservice.MetadonneeType;
import fr.urssaf.image.sae.services.capture.SAECaptureService;
import fr.urssaf.image.sae.services.exception.capture.DuplicatedMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.EmptyDocumentEx;
import fr.urssaf.image.sae.services.exception.capture.InvalidValueTypeAndFormatMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.NotArchivableMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.NotSpecifiableMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.RequiredArchivableMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.RequiredStorageMetadataEx;
import fr.urssaf.image.sae.services.exception.capture.SAECaptureServiceEx;
import fr.urssaf.image.sae.services.exception.capture.UnknownMetadataEx;
import fr.urssaf.image.sae.services.exception.enrichment.ReferentialRndException;
import fr.urssaf.image.sae.services.exception.enrichment.UnknownCodeRndEx;
import fr.urssaf.image.sae.webservices.exception.CaptureAxisFault;
import fr.urssaf.image.sae.webservices.service.WSCaptureService;
import fr.urssaf.image.sae.webservices.service.factory.ObjectArchivageUnitaireFactory;

/**
 * Implémentation de {@link WSCaptureService}<br>
 * L'implémentation est annotée par {@link Service}
 * 
 */
@Service
public class WSCaptureServiceImpl implements WSCaptureService {

   @Autowired
   private SAECaptureService captureService;

   /**
    * {@inheritDoc}
    * 
    */
   @Override
   public final ArchivageUnitaireResponse archivageUnitaire(
         ArchivageUnitaire request) throws CaptureAxisFault {

      // vérification que la liste des métadonnées n'est pas vide
      if (request.getArchivageUnitaire().getMetadonnees().getMetadonnee() == null) {

         throw new CaptureAxisFault("CaptureMetaDonneesVide",
               "Archivage impossible. La liste des métadonnées est vide.");
      }

      ArchivageUnitaireResponse response;

      URI ecdeURL = URI.create(request.getArchivageUnitaire().getEcdeUrl()
            .getEcdeUrlType().toString());

      Map<String, String> metadatas = new HashMap<String, String>();

      for (MetadonneeType metadonnee : request.getArchivageUnitaire()
            .getMetadonnees().getMetadonnee()) {

         metadatas.put(metadonnee.getCode().getMetadonneeCodeType(), metadonnee
               .getValeur().getMetadonneeValeurType());
      }

      UUID uuid = capture(metadatas, ecdeURL);

      response = ObjectArchivageUnitaireFactory
            .createArchivageUnitaireResponse(uuid);

      return response;
   }

   private UUID capture(Map<String, String> metadatas, URI ecdeURL)
         throws CaptureAxisFault {

      try {
         return captureService.capture(metadatas, ecdeURL);
      } catch (SAECaptureServiceEx e) {

         throw new CaptureAxisFault(
               "ErreurInterneCapture",
               "Une erreur interne à l'application est survenue dans la capture.",
               e);

      } catch (RequiredStorageMetadataEx e) {

         throw new CaptureAxisFault("ErreurInterneCapture", e.getMessage(), e);

      } catch (InvalidValueTypeAndFormatMetadataEx e) {

         throw new CaptureAxisFault("CaptureMetadonneesFormatTypeNonValide", e
               .getMessage(), e);

      } catch (UnknownMetadataEx e) {

         throw new CaptureAxisFault("CaptureMetaDonneesInconnu",
               e.getMessage(), e);

      } catch (DuplicatedMetadataEx e) {

         throw new CaptureAxisFault("CaptureMetadonneesDoublon",
               e.getMessage(), e);

      } catch (NotSpecifiableMetadataEx e) {

         throw new CaptureAxisFault("CaptureMetadonneesInterdites", e
               .getMessage(), e);

      } catch (EmptyDocumentEx e) {

         throw new CaptureAxisFault("CaptureFichierVide", e.getMessage(), e);

      } catch (RequiredArchivableMetadataEx e) {

         throw new CaptureAxisFault("CaptureMetadonneesArchivageObligatoire", e
               .getMessage(), e);

      } catch (ReferentialRndException e) {

         throw new CaptureAxisFault("ErreurInterne",
               "Erreur interne à l'application est survenue.", e);

      } catch (UnknownCodeRndEx e) {

         throw new CaptureAxisFault("CaptureCodeRndInterdit", e.getMessage(), e);

      } catch (NotArchivableMetadataEx e) {

         throw new CaptureAxisFault(
               "ErreurInterneCapture",
               "Une erreur interne à l'application est survenue dans la capture.",
               e);
      }
   }

}
