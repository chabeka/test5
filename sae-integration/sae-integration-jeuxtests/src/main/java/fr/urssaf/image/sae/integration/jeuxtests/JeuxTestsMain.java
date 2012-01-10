package fr.urssaf.image.sae.integration.jeuxtests;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.urssaf.image.sae.integration.jeuxtests.services.RandomPdfFileService;
import fr.urssaf.image.sae.integration.jeuxtests.services.SommaireService;

public final class JeuxTestsMain {

   private static final Logger LOG = LoggerFactory.getLogger(JeuxTestsMain.class);
   
   private JeuxTestsMain() {

   }
   
   public static void main(String[] args) throws IOException {
      
      String casUtilisation = args[0];
      
      String chemin1 = null;
      if (args.length>1) {
         chemin1 = args[1];
      }
      
      String chemin2 = null;
      if (args.length>2) {
         chemin2 = args[2];
      }
      
      LOG.debug("Cas d'utilisation no : " + casUtilisation);
//      LOG.debug("Chemin 1 : " + chemin1);
//      LOG.debug("Chemin 2 : " + chemin2);
      
      
      if (casUtilisation.equals("1")) {
         cas1(chemin1);
      } else if (casUtilisation.equals("2")) {
         cas2(chemin1,chemin2);
      } else if (casUtilisation.equals("3")) {
         cas3(chemin1,chemin2);
      } else if (casUtilisation.equals("4")) {
         cas4(chemin1,chemin2);
      } else {
         LOG.debug("Le cas d'utilisation " + casUtilisation + " n'est pas implémenté.");
      }
      
   }
   
   
   private static boolean continerOuiNon() {
      
      System.out.print("Voulez-vous continuer ? => non par defaut, saisir OUI en majuscules : ");
      
      String ligne = System.console().readLine();
      
      return ligne.equals("OUI");
      
   }
   
   
   /**
    * Génération de 10 000 PDF au contenu aléatoire de 75 Ko
    * 
    * @throws IOException 
    * 
    */
   private static void cas1(String repertoireFichiers) throws IOException {
      
      // Cas d'utilisation
      
      int tailleFichierEnKo = 75;
      int nbFichiers = 10000;
      
      LOG.debug("Cas d'utilisation : Génération de " + nbFichiers + " PDF au contenu aléatoire, de " + tailleFichierEnKo + " ko");
      LOG.debug("Répertoire de sortie des fichiers : " + repertoireFichiers) ;
      
      if (!continerOuiNon()) {
         return;
      }
            

      // Contrôles
      
      if (repertoireFichiers==null){
         LOG.debug("Erreur : le répertoire de sortie des fichiers n'est pas précisé");
         return;
      }
      
      
      // Exécution
      
      RandomPdfFileService service = new RandomPdfFileService() ;
      
      service.genererDocumentsContenuAleatoire(
            tailleFichierEnKo, 
            nbFichiers, 
            repertoireFichiers);
      
      
      // Fin
      LOG.debug("Opération terminé") ;
      
   }
   
   
   /**
    * Génération d'un sommaire.xml pluri-pdf de 100 000 documents
    * avec un Siren aléatoire
    * 
    * @throws IOException 
    */
   private static void cas2(
         String cheminEcritureFichierSommaire,
         String cheminFichierDesSha1) throws IOException {
     
      cas2_3_4(
            cheminEcritureFichierSommaire,
            cheminFichierDesSha1,
            100000);
      
   }
   
   
   /**
    * Génération d'un sommaire.xml pluri-pdf de 150 000 documents
    * avec un Siren aléatoire
    * 
    * @throws IOException 
    */
   private static void cas3(
         String cheminEcritureFichierSommaire,
         String cheminFichierDesSha1) throws IOException {
     
      cas2_3_4(
            cheminEcritureFichierSommaire,
            cheminFichierDesSha1,
            150000);
      
   }
   
   
   /**
    * Génération d'un sommaire.xml pluri-pdf de 200 000 documents
    * avec un Siren aléatoire
    * 
    * @throws IOException 
    */
   private static void cas4(
         String cheminEcritureFichierSommaire,
         String cheminFichierDesSha1) throws IOException {
     
      cas2_3_4(
            cheminEcritureFichierSommaire,
            cheminFichierDesSha1,
            200000);
      
   }
   
   
   
   private static void cas2_3_4(
         String cheminEcritureFichierSommaire,
         String cheminFichierDesSha1,
         int nbDocs) throws IOException {
     
      // Cas d'utilisation
      
      String denomination = null;
      String siren = null;
      boolean sirenAleatoire = true;
      
      LOG.debug("Cas d'utilisation : Génération d'un sommaire.xml pluri-PDF avec Siren alétoires de " + nbDocs + " documents");
      LOG.debug("Chemin complet de sortie du sommaire.xml : " + cheminEcritureFichierSommaire) ;
      LOG.debug("Fichier contenant la liste des SHA-1 : " + cheminFichierDesSha1) ;
      
      if (!continerOuiNon()) {
         return;
      }
      
      
      // Contrôles
      
      if (cheminEcritureFichierSommaire==null){
         LOG.debug("Erreur : le chemin complet de sortie du fichier sommaire.xml n'est pas précisé");
         return;
      }
      if (cheminFichierDesSha1==null){
         LOG.debug("Erreur : le chemin complet du fichier des SHA-1 n'est pas précisé");
         return;
      }
      
      
      // Exécution
      
      SommaireService service = new SommaireService();
      
      service.genereSommairePluriPdf(
            nbDocs, 
            cheminEcritureFichierSommaire, 
            cheminFichierDesSha1, 
            denomination, 
            siren, 
            sirenAleatoire);
      
      // Fin
      LOG.debug("Opération terminé") ;
      
   }
   
   
   
}
