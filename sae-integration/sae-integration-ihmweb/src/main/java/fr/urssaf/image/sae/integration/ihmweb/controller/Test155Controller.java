package fr.urssaf.image.sae.integration.ihmweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.urssaf.image.sae.integration.ihmweb.formulaire.CaptureUnitaireFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.formulaire.TestWsCaptureUnitaireFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.modele.MetadonneeValeurList;
import fr.urssaf.image.sae.integration.ihmweb.utils.ViUtils;


/**
 * Test 155<br>
 * <br>
 * On vérifie que la bonne erreur est renvoyée lorsque la liste des 
 * métadonnées contient une métadonnée inconnue du SAE
 */
@Controller
@RequestMapping(value = "test155")
public class Test155Controller extends AbstractTestWsController<TestWsCaptureUnitaireFormulaire> {


   /**
    * {@inheritDoc}
    */
   @Override
   protected final String getNumeroTest() {
      return "155";
   }
   
   
   /**
    * {@inheritDoc}
    */
   @Override
   protected final TestWsCaptureUnitaireFormulaire getFormulairePourGet() {
      
      TestWsCaptureUnitaireFormulaire formulaire = new TestWsCaptureUnitaireFormulaire();
      
      CaptureUnitaireFormulaire formCapture = formulaire.getCaptureUnitaire();
      
      
      // URL ECDE
      formCapture.setUrlEcde("ecde://ecde.cer69.recouv/SAE_INTEGRATION/20110822/CaptureUnitaire-155-CaptureUnitaire-KO-MetadonneeInconnue/documents/doc1.PDF");
      
      
      // Métadonnées
      MetadonneeValeurList metadonnees = new MetadonneeValeurList();
      formCapture.setMetadonnees(metadonnees);
      metadonnees.add("Titre","Attestation de vigilance");
      metadonnees.add("DateCreation","2011-09-01");
      metadonnees.add("ApplicationProductrice","ADELAIDE");
      metadonnees.add("CodeOrganismeProprietaire","AC750");
      metadonnees.add("CodeOrganismeGestionnaire","CER69");
      metadonnees.add("CodeRND","2.3.1.1.12");
      metadonnees.add("Hash","a2f93f1f121ebba0faef2c0596f2f126eacae77b");
      metadonnees.add("TypeHash","SHA-1");
      metadonnees.add("NbPages","2");
      metadonnees.add("FormatFichier","fmt/354");
      metadonnees.add("Denomination","Test 155-CaptureUnitaire-KO-MetadonneeInconnue");
      metadonnees.add("Titi","ValeurDeTiti");
      metadonnees.add("Tata","ValeurDeTata");

      
      return formulaire;
      
   }
   
   
   /**
    * {@inheritDoc}
    */
   @Override
   protected final void doPost(TestWsCaptureUnitaireFormulaire formulaire) {
      
      captureUnitaire(
            formulaire.getUrlServiceWeb(),
            formulaire.getCaptureUnitaire());
      
   }
   

   private void captureUnitaire(
         String urlServiceWeb,
         CaptureUnitaireFormulaire formulaire) {
      
      // Appel de la méthode de test
      getCaptureUnitaireTestService().appelWsOpCaptureUnitaireSoapFault(
            urlServiceWeb, 
            formulaire,
            ViUtils.FIC_VI_OK,
            "sae_CaptureMetadonneesInconnu",
            new String[] {"Tata, Titi"});
      
   }
   
 
}
