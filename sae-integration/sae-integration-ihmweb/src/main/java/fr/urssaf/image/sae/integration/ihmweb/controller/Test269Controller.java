package fr.urssaf.image.sae.integration.ihmweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.urssaf.image.sae.integration.ihmweb.exception.IntegrationRuntimeException;
import fr.urssaf.image.sae.integration.ihmweb.formulaire.CaptureMasseFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.formulaire.CaptureMasseResultatFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.formulaire.RechercheFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.formulaire.Test269Formulaire;
import fr.urssaf.image.sae.integration.ihmweb.modele.TestStatusEnum;

/**
 * Test 269<br>
 * <br>
 * La Capture de masse échoue sur le deuxième lancement si on a un seul serveur
 * d’application
 */
@Controller
@RequestMapping(value = "test269")
public class Test269Controller extends
      AbstractTestWsController<Test269Formulaire> {

   /**
    * URL du répertoire contenant les fichiers de données
    */
   private static final String URL_DIRECTORY = "ecde://ecde.cer69.recouv/SAE_INTEGRATION/20110822/CaptureMasse-269-CaptureMasse-KO-Deux-Lancements-1/";

   /**
    * URL du répertoire contenant les fichiers de données
    */
   private static final String URL_DIRECTORY_TWO = "ecde://ecde.cer69.recouv/SAE_INTEGRATION/20110822/CaptureMasse-269-CaptureMasse-KO-Deux-Lancements-2/";

   /**
    * {@inheritDoc}
    */
   @Override
   protected final String getNumeroTest() {
      return "269";
   }

   /**
    * {@inheritDoc}
    */
   @Override
   protected final Test269Formulaire getFormulairePourGet() {

      Test269Formulaire formulaire = new Test269Formulaire();

      CaptureMasseFormulaire formCapture = formulaire
            .getCaptureMasseDeclenchement();
      formCapture.setUrlSommaire(URL_DIRECTORY + "sommaire.xml");
      formCapture.getResultats().setStatus(TestStatusEnum.SansStatus);

      CaptureMasseFormulaire formCapture2 = formulaire
            .getCaptureMasseDeclenchementParallele();
      formCapture2.setUrlSommaire(URL_DIRECTORY_TWO + "sommaire.xml");
      formCapture2.getResultats().setStatus(TestStatusEnum.SansStatus);

      CaptureMasseResultatFormulaire formResultat = formulaire
            .getCaptureMasseResultat();
      formResultat.setUrlSommaire(URL_DIRECTORY + "resultat.xml");
      formResultat.getResultats().setStatus(TestStatusEnum.SansStatus);

      RechercheFormulaire rechercheFormulaire = formulaire.getRechFormulaire();
      rechercheFormulaire
            .setRequeteLucene("Denomination:\"Test 269-CaptureMasse-KO-Deux-Lancements-1\"");

      RechercheFormulaire rechercheFormulaireParallele = formulaire
            .getRechFormulaireParallele();
      rechercheFormulaireParallele
            .setRequeteLucene("Denomination:\"Test 269-CaptureMasse-KO-Deux-Lancements-2\"");

      return formulaire;

   }

   /**
    * {@inheritDoc}
    */
   @Override
   protected final void doPost(Test269Formulaire formulaire) {

      String etape = formulaire.getEtape();
      if ("1".equals(etape)) {

         etape1captureMasseAppelWs(formulaire.getUrlServiceWeb(), formulaire
               .getCaptureMasseResultat(), formulaire
               .getCaptureMasseDeclenchement());

      } else if ("2".equals(etape)) {

         etape2captureMasseAppelWs(formulaire.getUrlServiceWeb(), formulaire
               .getCaptureMasseResultat(), formulaire
               .getCaptureMasseDeclenchementParallele());

      } else if ("3".equals(etape)) {

         etape3captureMasseResultats(formulaire.getCaptureMasseResultat());

      } else if ("4".equals(etape)) {

         etape4Recherche(formulaire);

      } else if ("5".equals(etape)) {

         etape5Recherche(formulaire);

      } else {

         throw new IntegrationRuntimeException("L'étape " + etape
               + " est inconnue !");

      }

   }

   private void etape1captureMasseAppelWs(String urlWebService,
         CaptureMasseResultatFormulaire formCaptMassRes,
         CaptureMasseFormulaire captureMasseFormulaire) {

      // Vide le résultat du test précédent de l'étape 2
      formCaptMassRes.getResultats().clear();
      formCaptMassRes.setUrlSommaire(captureMasseFormulaire.getUrlSommaire());

      // Appel de la méthode de test
      getCaptureMasseTestService().appelWsOpArchiMasseOKAttendu(urlWebService,
            captureMasseFormulaire);

   }

   private void etape2captureMasseAppelWs(String urlWebService,
         CaptureMasseResultatFormulaire formCaptMassRes,
         CaptureMasseFormulaire captureMasseFormulaire) {

      // Vide le résultat du test précédent de l'étape 2
      formCaptMassRes.getResultats().clear();
      formCaptMassRes.setUrlSommaire(captureMasseFormulaire.getUrlSommaire());

      // Appel de la méthode de test
      getCaptureMasseTestService().appelWsOpArchiMasseSoapFaultAttendue(
            urlWebService, captureMasseFormulaire, "sae_CaptureMasseRefusee", null);

   }

   private void etape3captureMasseResultats(
         CaptureMasseResultatFormulaire formulaire) {

      getCaptureMasseTestService()
            .testResultatsTdmReponseOKAttendue(formulaire);

   }

   private final void etape4Recherche(Test269Formulaire formulaire) {

      getRechercheTestService().appelWsOpRechercheReponseCorrecteAttendue(
            formulaire.getUrlServiceWeb(), formulaire.getRechFormulaire(), 200,
            true, null);
   }

   private final void etape5Recherche(Test269Formulaire formulaire) {

      getRechercheTestService().appelWsOpRechercheReponseCorrecteAttendue(
            formulaire.getUrlServiceWeb(),
            formulaire.getRechFormulaireParallele(), 0, false, null);

   }

}
