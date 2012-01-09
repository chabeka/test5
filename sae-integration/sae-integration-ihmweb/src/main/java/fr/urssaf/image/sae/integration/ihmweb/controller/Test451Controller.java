package fr.urssaf.image.sae.integration.ihmweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.urssaf.image.sae.integration.ihmweb.formulaire.ConsultationFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.formulaire.TestWsConsultationFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.utils.ViUtils;


/**
 * Test 451-Consultation-KO-IdArchivageInconnu<br>
 * <br>
 * On vérifie que la bonne erreur est renvoyée lorsque l'identifiant 
 * d'archivage ne correspond à aucune archive du SAE. 
 */
@Controller
@RequestMapping(value = "test451")
public class Test451Controller extends AbstractTestWsController<TestWsConsultationFormulaire> {

   
   private static final String ID_ARCHIVE_TEST = 
      "00000000-0000-0000-0000-000000000000";
   
   
   /**
    * {@inheritDoc}
    */
   @Override
   protected final String getNumeroTest() {
      return "451";
   }
   
   
   /**
    * {@inheritDoc}
    */
   @Override
   protected final TestWsConsultationFormulaire getFormulairePourGet() {
      
      TestWsConsultationFormulaire formulaire = new TestWsConsultationFormulaire();
      ConsultationFormulaire formConsult = formulaire.getConsultation();
      formConsult.setIdArchivage(ID_ARCHIVE_TEST);
      
      return formulaire;
      
   }
   
   
   /**
    * {@inheritDoc}
    */
   @Override
   protected final void doPost(TestWsConsultationFormulaire formulaire) {
      consultation(
            formulaire.getUrlServiceWeb(),
            formulaire.getConsultation());
   }
   
   
   private void consultation(
         String urlServiceWeb,
         ConsultationFormulaire formulaire) {
      
      // Appel de la méthode de test
      this.getConsultationTestService().appelWsOpConsultationSoapFault(
            urlServiceWeb, 
            formulaire,
            ViUtils.FIC_VI_OK,
            "sae_ArchiveNonTrouvee",
            new String[] {formulaire.getIdArchivage()});
      
   }

   
}
