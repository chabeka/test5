package fr.urssaf.image.sae.integration.ihmweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.urssaf.image.sae.integration.ihmweb.formulaire.RechercheFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.formulaire.TestWsRechercheFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.modele.CodeMetadonneeList;
import fr.urssaf.image.sae.integration.ihmweb.utils.ViUtils;


/**
 * Test 350<br>
 * <br>
 * On vérifie que l'authentification est activée sur l'opération "recherche"
 * du service web SaeService<br>
 * <br>
 * Pour cela, on invoque l'opération "recherche" en omettant le VI dans le message SOAP.
 */
@Controller
@RequestMapping(value = "test350")
public class Test350Controller extends AbstractTestWsController<TestWsRechercheFormulaire> {

   
   /**
    * {@inheritDoc}
    */
   @Override
   protected final String getNumeroTest() {
      return "350";
   }
   
   
   /**
    * {@inheritDoc}
    */
   @Override
   protected final TestWsRechercheFormulaire getFormulairePourGet() {
      
      TestWsRechercheFormulaire formulaire = new TestWsRechercheFormulaire();
      RechercheFormulaire formRecherche = formulaire.getRecherche();
      
      formRecherche.setRequeteLucene(getCasTest().getLuceneExemple());
      
      CodeMetadonneeList codesMeta = new CodeMetadonneeList();
      formRecherche.setCodeMetadonnees(codesMeta);
      codesMeta.add("CodeRND");
      codesMeta.add("NumeroCotisant");
      codesMeta.add("Siret");
      codesMeta.add("DenominationCompte");
      codesMeta.add("CodeOrganisme");
      
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
         String urlWebService,
         RechercheFormulaire formulaire) {
      
      // Appel de la méthode de test
      getRechercheTestService().appelWsOpRechercheSoapFault(
            urlWebService, 
            formulaire,
            ViUtils.FIC_VI_SANS_VI,
            "wsse_SecurityTokenUnavailable",
            null);
      
   }
   
 
}