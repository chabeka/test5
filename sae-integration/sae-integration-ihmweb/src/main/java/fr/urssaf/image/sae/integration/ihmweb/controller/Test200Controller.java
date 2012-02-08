package fr.urssaf.image.sae.integration.ihmweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.urssaf.image.sae.integration.ihmweb.exception.IntegrationRuntimeException;
import fr.urssaf.image.sae.integration.ihmweb.formulaire.CaptureMasseFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.formulaire.CaptureMasseResultatFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.formulaire.Test200Formulaire;
import fr.urssaf.image.sae.integration.ihmweb.modele.TestStatusEnum;


/**
 * Test 200<br>
 * <br>
 * Test libre de la capture de masse => pas de résultat attendu
 */
@Controller
@RequestMapping(value = "test200")
public class Test200Controller extends AbstractTestWsController<Test200Formulaire> {

   
   /**
    * {@inheritDoc}
    */
   @Override
   protected final String getNumeroTest() {
      return "200";
   }
   
   
   /**
    * {@inheritDoc}
    */
   @Override
   protected final Test200Formulaire getFormulairePourGet() {
      
      Test200Formulaire formulaire = new Test200Formulaire();
      
      CaptureMasseFormulaire formCapture = formulaire.getCaptureMasseDeclenchement();
      formCapture.setUrlSommaire(getEcdeService().construitUrlEcde("SAE_INTEGRATION/20110829/IdTrait/sommaire.xml"));
      formCapture.getResultats().setStatus(TestStatusEnum.SansStatus);
      
      CaptureMasseResultatFormulaire formResultat = formulaire.getCaptureMasseResultat();
      formResultat.setUrlSommaire(formCapture.getUrlSommaire());
      formResultat.getResultats().setStatus(TestStatusEnum.SansStatus);
      
      return formulaire;
      
   }
   
   
   /**
    * {@inheritDoc}
    */
   @Override
   protected final void doPost(Test200Formulaire formulaire) {
      
      String etape = formulaire.getEtape();
      if ("1".equals(etape)) {
         
         etape1captureMasseAppelWs(
               formulaire.getUrlServiceWeb(),
               formulaire);
         
      } else if ("2".equals(etape)) {
         
         etape2captureMasseResultats(
               formulaire.getCaptureMasseResultat());
         
      } else{
         
         throw new IntegrationRuntimeException("L'étape " + etape + " est inconnue !");
         
      }
      
   }
   
   
   private void etape1captureMasseAppelWs(
         String urlWebService,
         Test200Formulaire formulaire) {
      
      // Vide le résultat du test précédent de l'étape 2
      CaptureMasseResultatFormulaire formCaptMassRes = formulaire.getCaptureMasseResultat();
      formCaptMassRes.getResultats().clear();
      formCaptMassRes.setUrlSommaire(null);
      
      // Appel de la méthode de test
      getCaptureMasseTestService().appelWsOpArchiMasseTestLibre(
            urlWebService, 
            formulaire.getCaptureMasseDeclenchement());
      
   }
   
   
   
   private void etape2captureMasseResultats(
         CaptureMasseResultatFormulaire formulaire) {
      
      getCaptureMasseTestService().regardeResultatsTdm(formulaire);
      
   }
   
 
}
