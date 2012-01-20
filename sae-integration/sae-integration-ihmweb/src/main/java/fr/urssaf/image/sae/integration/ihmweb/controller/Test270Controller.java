package fr.urssaf.image.sae.integration.ihmweb.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.urssaf.image.sae.integration.ihmweb.exception.IntegrationRuntimeException;
import fr.urssaf.image.sae.integration.ihmweb.formulaire.CaptureMasseFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.formulaire.CaptureMasseResultatFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.formulaire.RechercheFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.formulaire.Test270Formulaire;
import fr.urssaf.image.sae.integration.ihmweb.modele.TestStatusEnum;
import fr.urssaf.image.sae.integration.ihmweb.modele.somres.commun_sommaire_et_resultat.ErreurType;
import fr.urssaf.image.sae.integration.ihmweb.modele.somres.commun_sommaire_et_resultat.FichierType;
import fr.urssaf.image.sae.integration.ihmweb.modele.somres.commun_sommaire_et_resultat.ListeErreurType;
import fr.urssaf.image.sae.integration.ihmweb.modele.somres.commun_sommaire_et_resultat.NonIntegratedDocumentType;

/**
 * Test 270<br>
 * <br>
 * La Capture de masse échoue suite à la suppression d’un fichier image après
 * l’analyse
 */
@Controller
@RequestMapping(value = "test270")
public class Test270Controller extends
      AbstractTestWsController<Test270Formulaire> {

   /**
    * URL du répertoire contenant les fichiers de données
    */
   private static final String URL_DIRECTORY = "ecde://ecde.cer69.recouv/SAE_INTEGRATION/20110822/CaptureMasse-270-CaptureMasse-KO-Tor-3000-Rollback/";

   /**
    * {@inheritDoc}
    */
   @Override
   protected final String getNumeroTest() {
      return "270";
   }

   /**
    * {@inheritDoc}
    */
   @Override
   protected final Test270Formulaire getFormulairePourGet() {

      Test270Formulaire formulaire = new Test270Formulaire();

      CaptureMasseFormulaire formCapture = formulaire
            .getCaptureMasseDeclenchement();
      formCapture.setUrlSommaire(URL_DIRECTORY + "sommaire.xml");
      formCapture.getResultats().setStatus(TestStatusEnum.SansStatus);

      CaptureMasseResultatFormulaire formResultat = formulaire
            .getCaptureMasseResultat();
      formResultat.setUrlSommaire(URL_DIRECTORY + "resultat.xml");
      formResultat.getResultats().setStatus(TestStatusEnum.SansStatus);

      RechercheFormulaire rechFormulaire = formulaire.getRechFormulaire();
      rechFormulaire
            .setRequeteLucene("Denomination:\"Test 270-CaptureMasse-KO-Tor-3000-Rollback\"");

      return formulaire;

   }

   /**
    * {@inheritDoc}
    */
   @Override
   protected final void doPost(Test270Formulaire formulaire) {

      String etape = formulaire.getEtape();
      if ("1".equals(etape)) {

         etape1captureMasseAppelWs(formulaire.getUrlServiceWeb(), formulaire);

      } else if ("2".equals(etape)) {

         etape2captureMasseResultats(formulaire.getCaptureMasseResultat());

      } else if ("3".equals(etape)) {

         etape3Recherche(formulaire);

      } else {

         throw new IntegrationRuntimeException("L'étape " + etape
               + " est inconnue !");

      }

   }

   private void etape1captureMasseAppelWs(String urlWebService,
         Test270Formulaire formulaire) {

      // Vide le résultat du test précédent de l'étape 2
      CaptureMasseResultatFormulaire formCaptMassRes = formulaire
            .getCaptureMasseResultat();
      formCaptMassRes.getResultats().clear();
      formCaptMassRes.setUrlSommaire(formulaire.getCaptureMasseDeclenchement()
            .getUrlSommaire());

      // Appel de la méthode de test
      getCaptureMasseTestService().appelWsOpArchiMasseOKAttendu(urlWebService,
            formulaire.getCaptureMasseDeclenchement());

      if (TestStatusEnum.Succes.equals(formulaire
            .getCaptureMasseDeclenchement().getResultats().getStatus())) {

         String path = getEcdeService().convertUrlEcdeToPath(
               URL_DIRECTORY + "debut_traitement.flag");

         File file = new File(path);

         try {
            int i = 0;
            while (!file.exists() && i < 10) {
               Thread.sleep(1001);
               i++;
            }
         } catch (InterruptedException e) {
            formulaire.getCaptureMasseDeclenchement().getResultats().getLog()
                  .appendLog(e.toString());
         }

         try {
            if (file.exists()) {
               Properties properties = new Properties();
               FileInputStream fileInputStream = new FileInputStream(file);
               properties.load(fileInputStream);
               
               String host = properties.getProperty("hostnameServeurAppli");
               host = "http://" + host + ":8080/sae/SAETest.do";
               
               formulaire.setLinkToMonitoring(host);
            }
         } catch (FileNotFoundException e) {
            formulaire.getCaptureMasseDeclenchement().getResultats().getLog()
                  .appendLog(e.toString());
         } catch (IOException e) {
            formulaire.getCaptureMasseDeclenchement().getResultats().getLog()
                  .appendLog(e.toString());
         }

      }

   }

   private void etape2captureMasseResultats(
         CaptureMasseResultatFormulaire formulaire) {

      FichierType fichierType = new FichierType();
      fichierType.setCheminEtNomDuFichier("doc0.PDF");

      ErreurType erreurType = new ErreurType();
      erreurType.setCode("SAE-EC-SOM002");
      erreurType
            .setLibelle("Impossible d'accéder au document doc0.PDF. Détails : "
                  + "L'objet numérique : doc0.PDF ne représente pas un fichier existant.");

      ListeErreurType listeErreurType = new ListeErreurType();
      listeErreurType.getErreur().add(erreurType);

      NonIntegratedDocumentType documentType = new NonIntegratedDocumentType();
      documentType.setErreurs(listeErreurType);
      documentType.setObjetNumerique(fichierType);

      getCaptureMasseTestService().testResultatsTdmReponseKOAttendue(
            formulaire, 3, documentType, 2001);

   }

   private void etape3Recherche(Test270Formulaire formulaire) {

      getRechercheTestService().appelWsOpRechercheReponseCorrecteAttendue(
            formulaire.getUrlServiceWeb(), formulaire.getRechFormulaire(), 0,
            false, null);

   }
}
