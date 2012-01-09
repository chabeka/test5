package fr.urssaf.image.sae.integration.ihmweb.injection;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.integration.ihmweb.exception.IntegrationRuntimeException;
import fr.urssaf.image.sae.integration.ihmweb.modele.MetadonneeDefinition;
import fr.urssaf.image.sae.integration.ihmweb.modele.somres.commun_sommaire_et_resultat.DocumentType;
import fr.urssaf.image.sae.integration.ihmweb.modele.somres.commun_sommaire_et_resultat.MetadonneeType;
import fr.urssaf.image.sae.integration.ihmweb.modele.somres.sommaire.SommaireType;
import fr.urssaf.image.sae.integration.ihmweb.service.ecde.EcdeService;
import fr.urssaf.image.sae.integration.ihmweb.service.referentiels.ReferentielMetadonneesService;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;



/**
 * Injection des données pour initialiser la base de test<br>
 * <br>
 * Remplace la capture de masse, d'ici à ce qu'on l'ait
 */
@SuppressWarnings("PMD")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-sae-integration-ihmweb-injection.xml")
// @Ignore("Ce ne sont pas des TU, mais des méthodes pour injecter des jeux de test dans DFCE")
public class InitialisationBaseIntegration110950Test {


   private static final Logger LOG = LoggerFactory.getLogger(InitialisationBaseIntegration110950Test.class);
   
   
   @Autowired
   private EcdeService ecdeService;
   
   
   @Autowired
   private ReferentielMetadonneesService refMetaService;
   
   
   @Autowired
   private InjectionTools injectionTools;
   
   
   private static boolean activerInjectionDansDfce = false;
   
   
   @Autowired
   private InjectionService injectionService;
   
   

   private void affiche(StringBuffer sBuffer) {
      affiche(sBuffer.toString());
   }
   
   
   private void affiche(String data) {
      // System.out.println(data);
      LOG.debug(data);
   }
   
   
   private void injecteSommaire(
         String urlEcdeSommaire) {
      
      // Lecture du fichier sommaire.xml
      SommaireType sommaire = ecdeService.chargeSommaireXml(urlEcdeSommaire);
      
      // Injection des documents 1 par 1 du sommaire.xml
      String cheminFichierSommaire = ecdeService.convertUrlEcdeToPath(urlEcdeSommaire);
      injecteSommaire(sommaire,cheminFichierSommaire);
      
   }
         
   
   
//   private void injecteSommaire(
//         SommaireType sommaire,
//         String cheminFichierSommaire) {
//      
//      try {
//      
//         if ((sommaire!=null) && (sommaire.getDocuments()!=null)) {
//         
//            List<DocumentType> documents = sommaire.getDocuments().getDocument();
//            
//            if (CollectionUtils.isNotEmpty(documents)) {
//               
//               // LOG
//               affiche(StringUtils.EMPTY);
//               affiche("Nombre de documents à injecter : " + documents.size());
//               affiche(StringUtils.EMPTY);
//               affiche(StringUtils.EMPTY);
//               
//               // Construit le chemin complet du répertoire "documents" du répertoire
//               // de traitement sur l'ECDE
//               String repTraitement = FilenameUtils.getFullPath(cheminFichierSommaire);
//               String repDocuments = FilenameUtils.concat(repTraitement, "documents"); 
//               
//               // Boucle sur chaque document <=> boucle d'injection unitaire
//               int numeroDocumentPourLog = 1;
//               String cheminRelatif;
//               String cheminAbsolu;
//               String nomFichierAvecExtension;
//               byte[] contenu;
//               List<StorageMetadata> metadonnees;
//               UUID uuid; 
//               for(DocumentType document: documents) {
//                  
//                  // Trace
//                  affiche(StringUtils.EMPTY);
//                  affiche("Document #" + numeroDocumentPourLog);
//                  numeroDocumentPourLog++;
//                  
//                  // Récupère le chemin relatif du document
//                  cheminRelatif = document.getObjetNumerique().getCheminEtNomDuFichier();
//                  // affiche(cheminRelatif);
//                  
//                  // Contruit le chemin absolu
//                  cheminAbsolu = FilenameUtils.concat(repDocuments, cheminRelatif);
//                  affiche("Chemin absolu : " + cheminAbsolu);
//                  
//                  // Lecture du contenu
//                  contenu = FileUtils.readFileToByteArray(new File(cheminAbsolu));
//                  affiche("Taille du contenu : " + contenu.length);
//                  
//                  // Les métadonnées
//                  nomFichierAvecExtension = FilenameUtils.getName(cheminAbsolu);
//                  metadonnees = traiteLesMetadonnees(
//                        document.getMetadonnees().getMetadonnee(),
//                        nomFichierAvecExtension);
//                  affiche("Métadonnées : ");
//                  affiche(InjectionTools.metadonneesPourAffichage(metadonnees));
//                  
//                  // Injection du document dans DFCE
//                  if (activerInjectionDansDfce) {
//                     uuid = injectionService.injecteDocument(contenu, metadonnees);
//                     affiche(uuid.toString());
//                  }
//                  
//               }
//               
//               affiche("Injection terminée de " + documents.size() + " document(s).");
//               
//            } else {
//               affiche("Aucun document à injecter");
//            }
//            
//            
//         } else {
//            affiche("Le sommaire est null (problème technique ?)");
//         }
//      
//      } catch (Exception ex) {
//         throw new IntegrationRuntimeException(ex);
//      }
//      
//   }
   
   
   private void appendLn(StringBuffer sBuffer, String ligne) {
      sBuffer.append(ligne);
      sBuffer.append("\r\n");
   }
   
   
   private void videStringBuffer(StringBuffer sBuffer) {
      sBuffer.setLength(0);
   }
   
   
   private void injecteSommaire(
         SommaireType sommaire,
         String cheminFichierSommaire) {
      
      StringBuffer sBufferLog = new StringBuffer();
      
      try {
      
         if ((sommaire!=null) && (sommaire.getDocuments()!=null)) {
         
            List<DocumentType> documents = sommaire.getDocuments().getDocument();
            
            if (CollectionUtils.isNotEmpty(documents)) {
               
               // Traces
               appendLn(sBufferLog,StringUtils.EMPTY);
               appendLn(sBufferLog,"Fichier sommaire à injecter : " + cheminFichierSommaire);
               appendLn(sBufferLog,StringUtils.EMPTY);
               appendLn(sBufferLog,"Nombre de documents à injecter : " + documents.size());
               appendLn(sBufferLog,StringUtils.EMPTY);
               appendLn(sBufferLog,StringUtils.EMPTY);
               affiche(sBufferLog);
               videStringBuffer(sBufferLog);
               
               // Construit le chemin complet du répertoire "documents" du répertoire
               // de traitement sur l'ECDE
               String repTraitement = FilenameUtils.getFullPath(cheminFichierSommaire);
               String repDocuments = FilenameUtils.concat(repTraitement, "documents"); 
               
               // Boucle sur chaque document <=> boucle d'injection unitaire
               int numeroDocumentPourLog = 1;
               String cheminRelatif;
               String cheminAbsolu;
               String nomFichierAvecExtension;
               byte[] contenu;
               List<StorageMetadata> metadonnees;
               UUID uuid; 
               for(DocumentType document: documents) {
                  
                  // Trace
                  appendLn(sBufferLog,StringUtils.EMPTY);
                  appendLn(sBufferLog,"Document #" + numeroDocumentPourLog);
                  numeroDocumentPourLog++;
                  
                  // Récupère le chemin relatif du document
                  cheminRelatif = document.getObjetNumerique().getCheminEtNomDuFichier();
                  // affiche(cheminRelatif);
                  
                  // Contruit le chemin absolu
                  cheminAbsolu = FilenameUtils.concat(repDocuments, cheminRelatif);
                  appendLn(sBufferLog,"Chemin absolu : " + cheminAbsolu);
                  
                  // Lecture du contenu
                  contenu = FileUtils.readFileToByteArray(new File(cheminAbsolu));
                  appendLn(sBufferLog,"Taille du contenu : " + contenu.length);
                  
                  // Les métadonnées
                  nomFichierAvecExtension = FilenameUtils.getName(cheminAbsolu);
                  metadonnees = traiteLesMetadonnees(
                        document.getMetadonnees().getMetadonnee(),
                        nomFichierAvecExtension);
                  appendLn(sBufferLog,"Métadonnées : ");
                  sBufferLog.append(InjectionTools.metadonneesPourAffichage(metadonnees));
                  
                  // Injection du document dans DFCE
                  if (activerInjectionDansDfce) {
                     // uuid = injectionService.injecteDocument(contenu, metadonnees);
                     uuid = injectionService.injecteDocument(cheminAbsolu, metadonnees);
                     appendLn(sBufferLog,"=> UUID du document injecté : " + uuid.toString());
                  }
                  
                  // Affichage des traces
                  affiche(sBufferLog);
                  videStringBuffer(sBufferLog);
                  
                  
               }
               
               // Traces
               appendLn(sBufferLog,"Injection terminée de " + documents.size() + " document(s).");
               affiche(sBufferLog);
               videStringBuffer(sBufferLog);
               
               
            } else {
               
               appendLn(sBufferLog,"Aucun document à injecter");
               affiche(sBufferLog);
               videStringBuffer(sBufferLog);

            }
            
            
         } else {
            
            appendLn(sBufferLog,"Le sommaire est null (problème technique ?)");
            affiche(sBufferLog);
            videStringBuffer(sBufferLog);
         }
      
      } catch (Exception ex) {
         
         affiche("Une exception a été levée");
         affiche(sBufferLog);
         
         throw new IntegrationRuntimeException(ex);
      }
      
   }

   
   
   private List<StorageMetadata> traiteLesMetadonnees(
         List<MetadonneeType> metadonnees,
         String nomFichierAvecExtension) {
      
      // Codes longs => Codes courts
      // Structure XML => Structure Storage
      // Enrichissement
      // Suppression
      
      // Initialise le résultat
      List<StorageMetadata> storeMeta = new ArrayList<StorageMetadata>();
      
      // Boucle sur les métadonnées fournies
      if (CollectionUtils.isNotEmpty(metadonnees)) {
         
         String codeCourt;
         MetadonneeDefinition metaDef;
         String codeLong;
         Object valeurMetadonnee;
         for (MetadonneeType metadonnee: metadonnees) {
            
            // Lit le code long de la métadonnée
            codeLong = metadonnee.getCode();
            
            // Ne traite pas certaines métadonnées
            if (codeLong.equals("Hash")) {
               // Le hash stocké est celui recalculé automatiquement par DFCE
               continue;
            } else if (codeLong.equals("TypeHash")) {
               // Le type de hash stocké est celui utilisé en automatique par DFCE
               continue;
            }
            
            // Récupère la définition de la métadonnée depuis le référentiel des métadonnées
            metaDef = refMetaService.findMeta(codeLong);
            if (metaDef==null) {
               throw new IntegrationRuntimeException(
                     "Impossible de retrouver la définition de la métadonnée " + 
                     codeLong + 
                     " dans le référentiel des métadonnées !");
            }
            
            // Vérifie que la métadonnée est spécifiable à l'archivage
            // (problème éventuel sur le jeu de test à injecter)
            if (!metaDef.isArchivablePossible()) {
               throw new IntegrationRuntimeException(
                     "Erreur dans le jeu de test : la métadonnée " + 
                     codeLong + 
                     " n'est pas autorisé à l'archivage");
            }
            
            // Récupère le code court
            codeCourt = refMetaService.getCodeCourt(metadonnee.getCode());
            
            // Fait un typage fort des métadonnées
            valeurMetadonnee = null;
            if (metaDef.getTypeDfce().equals("date")) {
               valeurMetadonnee = typageMetadonneeDate(metadonnee.getValeur());
            } else if (metaDef.getTypeDfce().equals("integer")) {
               valeurMetadonnee = new Integer(metadonnee.getValeur());
            } else if (metaDef.getTypeDfce().equals("long")) {
               valeurMetadonnee = new Long(metadonnee.getValeur());
            } else if (metaDef.getTypeDfce().equals("string")) {
               valeurMetadonnee = metadonnee.getValeur();
            } else {
               throw new IntegrationRuntimeException(
                     "Erreur : le typage fort en " + 
                     metaDef.getTypeDfce() + 
                     " n'est pas implémenté (métadonnée concernée : " + 
                     codeLong + 
                     ")");
            }
            
            // Ajoute la métadonnée pour la couche storage
            storeMeta.add(new StorageMetadata(codeCourt, valeurMetadonnee)); 
            
         }
         
      }
      
      // Ajout des métadonnées pour le stockage
      injectionTools.ajouteMetadonneesObligatoireAuStockage(storeMeta,nomFichierAvecExtension);
      
      // Renvoie du résultat
      return storeMeta;
      
   }
   
   
   private Date typageMetadonneeDate(String dateString) {
      
      // Le format de la date est censé être AAAA-MM-JJ

      int annee = new Integer(dateString.substring(0, 4));
      int mois = new Integer(dateString.substring(5, 7));
      int jour = new Integer(dateString.substring(8, 10));
      
      
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
   
   
   @Test
   public void injecte_jeux_tests() {
      
      activerInjectionDansDfce = true;
      
      // Vérification de la génération du fichier de log
//      LOG.debug("Toto");
      
//      injecteSommaire("ecde://ecde.cer69.recouv/SAE_INTEGRATION/20110822/Recherche-301-Recherche-OK-Standard/sommaire.xml");
//      injecteSommaire("ecde://ecde.cer69.recouv/SAE_INTEGRATION/20110822/Recherche-302-Recherche-OK-Tronquee/sommaire.xml");
//      injecteSommaire("ecde://ecde.cer69.recouv/SAE_INTEGRATION/20110822/Recherche-303-Recherche-OK-EnSpecifiantMetadonnees/sommaire.xml");
//      injecteSommaire("ecde://ecde.cer69.recouv/SAE_INTEGRATION/20110822/Recherche-304-Recherche-OK-Complexe-Dates/sommaire.xml");
//      injecteSommaire("ecde://ecde.cer69.recouv/SAE_INTEGRATION/20110822/Recherche-305-Recherche-OK-RequeteLuceneAvecDeuxPointsDansValeurRecherchee/sommaire.xml");
//      injecteSommaire("ecde://ecde.cer69.recouv/SAE_INTEGRATION/20110822/Recherche-306-Recherche-OK-RequeteLuceneAvecJokerDansValeurRecherchee/sommaire.xml");
//      injecteSommaire("ecde://ecde.cer69.recouv/SAE_INTEGRATION/20110822/Recherche-307-Recherche-OK-RND-et-Titre/sommaire.xml");
//      injecteSommaire("ecde://ecde.cer69.recouv/SAE_INTEGRATION/20110822/Recherche-308-Recherche-OK-avec-espace-et-joker/sommaire.xml");
      
   }
      
   
}
