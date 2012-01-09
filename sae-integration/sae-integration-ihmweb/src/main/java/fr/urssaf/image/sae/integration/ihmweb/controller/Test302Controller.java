package fr.urssaf.image.sae.integration.ihmweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.urssaf.image.sae.integration.ihmweb.formulaire.RechercheFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.formulaire.TestWsRechercheFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.modele.CodeMetadonneeList;
import fr.urssaf.image.sae.integration.ihmweb.modele.TestStatusEnum;


/**
 * Test 302-Recherche-OK-Tronquee<br>
 * <br>
 * On vérifie que la recherche fonctionne avec une requête de recherche simple et 
 * peu restrictive, de sorte à provoquer la troncature du résultat de recherche.
 * 
 */
@Controller
@RequestMapping(value = "test302")
public class Test302Controller extends AbstractTestWsController<TestWsRechercheFormulaire> {

   
   /**
    * {@inheritDoc}
    */
   @Override
   protected final String getNumeroTest() {
      return "302";
   }
   
   
   /**
    * {@inheritDoc}
    */
   @Override
   protected final TestWsRechercheFormulaire getFormulairePourGet() {
      
      TestWsRechercheFormulaire formulaire = new TestWsRechercheFormulaire();
      RechercheFormulaire formRecherche = formulaire.getRecherche();
      formRecherche.getResultats().setStatus(TestStatusEnum.SansStatus);
      
      // Requête de recherche correspondant au jeu de test inséré en base d'intégration
      formRecherche.setRequeteLucene(getCasTest().getLuceneExemple());
      
      // Pas de métadonnées spécifiques à récupérer
      CodeMetadonneeList codesMeta = new CodeMetadonneeList() ;
      formRecherche.setCodeMetadonnees(codesMeta);
      
            
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
      
      // Résultats attendus
      int nbResultatsAttendus = 200 ; 
      boolean flagResultatsTronquesAttendu = true;
      
      // Appel de la méthode de test
      getRechercheTestService().appelWsOpRechercheReponseCorrecteAttendue(
            urlServiceWeb,
            formulaire,
            nbResultatsAttendus,
            flagResultatsTronquesAttendu,
            null);
      
   }
   
   
  
 
}
