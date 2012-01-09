package fr.urssaf.image.sae.integration.ihmweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.urssaf.image.sae.integration.ihmweb.formulaire.RechercheFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.formulaire.TestWsRechercheFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.modele.CodeMetadonneeList;
import fr.urssaf.image.sae.integration.ihmweb.modele.TestStatusEnum;
import fr.urssaf.image.sae.integration.ihmweb.utils.ViUtils;


/**
 * Test 354-Recherche-KO-MetadonneeConsultNonAutorisee<br>
 * <br>
 * On vérifie que la recherche renvoie la bonne erreur lorsque la liste des 
 * métadonnées souhaitées en retour de la recherche contient un code de 
 * métadonnées qui n’est pas consultable
 */
@Controller
@RequestMapping(value = "test354")
public class Test354Controller extends AbstractTestWsController<TestWsRechercheFormulaire> {

   
   /**
    * {@inheritDoc}
    */
   @Override
   protected final String getNumeroTest() {
      return "354";
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
      codesMeta.add("Titre"); // <= Consultable OK
      codesMeta.add("VersionNumber"); // <= Consultable KO
      codesMeta.add("CodeRND"); // <= Consultable OK
      codesMeta.add("StartPage"); // <= Consultable KO
      
            
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
            "sae_ConsultationMetadonneesInterdite",
            new Object[] {"StartPage, VersionNumber"});
    
   }
   
}
