package fr.urssaf.image.sae.integration.ihmweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.urssaf.image.sae.integration.ihmweb.formulaire.RechercheFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.formulaire.TestWsRechercheFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.modele.CodeMetadonneeList;
import fr.urssaf.image.sae.integration.ihmweb.modele.TestStatusEnum;
import fr.urssaf.image.sae.integration.ihmweb.utils.ViUtils;


/**
 * Test 353-Recherche-KO-MetadonneeConsultInexistante
 * <br>
 * On vérifie que la recherche renvoie la bonne erreur 
 * lorsque la liste des métadonnées souhaitées en retour 
 * de la recherche contient un code de métadonnées non 
 * reconnu par le SAE
 */
@Controller
@RequestMapping(value = "test353")
public class Test353Controller extends AbstractTestWsController<TestWsRechercheFormulaire> {

   
   /**
    * {@inheritDoc}
    */
   @Override
   protected final String getNumeroTest() {
      return "353";
   }
   
   
   /**
    * {@inheritDoc}
    */
   @Override
   protected final TestWsRechercheFormulaire getFormulairePourGet() {
      
      TestWsRechercheFormulaire formulaire = new TestWsRechercheFormulaire();
      RechercheFormulaire formRecherche = formulaire.getRecherche();
      
      formRecherche.setRequeteLucene(getCasTest().getLuceneExemple());
      
      CodeMetadonneeList codesMeta = new CodeMetadonneeList() ;
      formRecherche.setCodeMetadonnees(codesMeta);
      codesMeta.add("CodeRND");
      codesMeta.add("Gloubi");
      codesMeta.add("Siret"); 
      codesMeta.add("Boulga");
            
      formRecherche.getResultats().setStatus(TestStatusEnum.SansStatus);
      
      return formulaire;
      
   }
   
   
   /**
    * {@inheritDoc}
    */
   @Override
   protected final void doPost(TestWsRechercheFormulaire formulaire) {
      recherche(
            formulaire.getUrlServiceWeb(),
            formulaire.getRecherche());
   }
   
   
   private void recherche(
         String urlServiceWeb,
         RechercheFormulaire formulaire) {
      
      // Appel de la méthode de test
      this.getRechercheTestService().appelWsOpRechercheSoapFault(
            urlServiceWeb, 
            formulaire,
            ViUtils.FIC_VI_OK,
            "sae_ConsultationMetadonneesInconnues",
            new Object[] {"Boulga, Gloubi"});
    
   }

   
}
