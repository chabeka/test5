package fr.urssaf.image.sae.integration.ihmweb.injection;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.integration.ihmweb.exception.IntegrationRuntimeException;
import fr.urssaf.image.sae.integration.ihmweb.service.referentiels.ReferentielMetadonneesService;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;

@Service
public class InjectionTools {

   
   @Autowired
   private ReferentielMetadonneesService refMetaService;
   
   
   public static String metadonneesPourAffichage(List<StorageMetadata> metadonnees) {
      
      StringBuffer sBuffer = new StringBuffer();
      
      for (StorageMetadata metadonnee: metadonnees) {
         sBuffer.append(metadonnee.getShortCode());
         sBuffer.append("=");
         sBuffer.append(metadonnee.getValue());
         sBuffer.append(" (");
         sBuffer.append(metadonnee.getValue().getClass().getSimpleName());
         sBuffer.append(")");
         sBuffer.append("\r\n");
      }
      
      return sBuffer.toString();
      
   }
   
   
   
   public void ajouteMetadonneesObligatoireAuStockage(
         List<StorageMetadata> metadonnees,
         String nomFichierAvecExtension) {
      
      
      // VersionRND
      StorageMetadata metaVersionRnd = findStorageMetadata(
            metadonnees,
            refMetaService.getCodeCourt("VersionRND"));
      if (metaVersionRnd==null) {
         metadonnees.add(new StorageMetadata(
               refMetaService.getCodeCourt("VersionRND"),
               "11.1"));
      }
      
      
      // CodeFonction
      metadonnees.add(new StorageMetadata(
            refMetaService.getCodeCourt("CodeFonction"),
            "2"));
      
      
      // CodeActivite
      metadonnees.add(new StorageMetadata(
            refMetaService.getCodeCourt("CodeActivite"),
            "3"));
      
      
      // DureeConservation => gestion auto DFCE à la capture
//      metadonnees.add(new StorageMetadata(
//            refMetaService.getCodeCourt("DureeConservation"),
//            )); 
      
     
      
      // DateDebutConservation
      StorageMetadata metaDateDebutConservation = findStorageMetadata(
            metadonnees,
            refMetaService.getCodeCourt("DateDebutConservation"));
      if (metaDateDebutConservation==null) {
         metadonnees.add(new StorageMetadata(
               refMetaService.getCodeCourt("DateDebutConservation"),
               buildDate(2011,10,02)));
      }
      
      
      
      // DateFinConservation
      Date dateFinConservation = calculeDateFinConservation(metadonnees);
      metadonnees.add(new StorageMetadata(
            refMetaService.getCodeCourt("DateFinConservation"),
            dateFinConservation));
      
      
      // Gel => gestion auto DFCE à la capture
//      metadonnees.add(new StorageMetadata(
//            refMetaService.getCodeCourt("Gel"),
//            false)); 
      
      
      // NomFichier
      if (StringUtils.isBlank(nomFichierAvecExtension)) {
         metadonnees.add(new StorageMetadata(
               refMetaService.getCodeCourt("NomFichier"),
               "attestation.pdf")); 
      } else {
         metadonnees.add(new StorageMetadata(
               refMetaService.getCodeCourt("NomFichier"),
               nomFichierAvecExtension)); 
      }
      
            
      // DocumentVirtuel => gestion auto DFCE à la capture
//      metadonnees.add(new StorageMetadata(
//            refMetaService.getCodeCourt("DocumentVirtuel"),
//            false)); 
      

      // ContratDeService
      metadonnees.add(new StorageMetadata(
            refMetaService.getCodeCourt("ContratDeService"),
            "ATT_PROD_001")); 
      
      
      // DateArchivage => gestion auto DFCE
//      metadonnees.add(new StorageMetadata(
//            refMetaService.getCodeCourt("DateArchivage"),
//            )); 
      
      
   }
   
   
   
   public static StorageMetadata findStorageMetadata(
         List<StorageMetadata> metadonnees,
         String codeCourt) {
      
      StorageMetadata result = null;
      
      if (CollectionUtils.isNotEmpty(metadonnees)) {
         for (StorageMetadata meta: metadonnees) {
            if (meta.getShortCode().equals(codeCourt)) {
               result = meta;
               break;
            }
         }
      }
      
      return result;
      
   }
   
   
   public static Date buildDate(int annee, int mois, int jour) {
      Calendar calendar = Calendar.getInstance();

      calendar.set(Calendar.YEAR,annee);
      calendar.set(Calendar.MONTH,mois-1); // les numéros de mois commencent à 0
      calendar.set(Calendar.DAY_OF_MONTH,jour);
      calendar.set(Calendar.HOUR_OF_DAY, 0);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      calendar.set(Calendar.MILLISECOND, 0);
      
      Date laDate = calendar.getTime();
      
      return laDate;

   }
   
   
   private Date calculeDateFinConservation(
         List<StorageMetadata> metadonnees) {
      
      
      // Récupère la date de début de conservation
      StorageMetadata metaDateDebutConservation = findStorageMetadata(
            metadonnees,
            refMetaService.getCodeCourt("DateDebutConservation"));
      Date dateDebutConservation = (Date)metaDateDebutConservation.getValue();
      
      
      // Récupère le CodeRND
      StorageMetadata metaCodeRnd = findStorageMetadata(
            metadonnees,
            refMetaService.getCodeCourt("CodeRND"));
      String codeRnd = (String)metaCodeRnd.getValue();
      
      
      // Détermine la durée de conservation à partir du RND
      int dureeConservation;
      if (
            codeRnd.equals("2.3.1.1.12") || 
            codeRnd.equals("2.3.1.1.8") || 
            codeRnd.equals("2.3.1.1.3")) {
         dureeConservation = 1825;
      } else if (codeRnd.equals("3.1.3.1.1")) {
         dureeConservation = 1643;
      } else {
         throw new IntegrationRuntimeException(
               "La durée de conservation du type de document suivant est inconnu : " + 
               codeRnd);
      }
      
      
      // Calcul de la date de fin de conservation
      Date dateFinConservation = DateUtils.addDays(dateDebutConservation, dureeConservation); 
      
      // Renvoie du résultat
      return dateFinConservation;
      
   }
   
   
}
