package fr.urssaf.image.sae.integration.ihmweb.formulaire;

/**
 * Classe de formulaire
 */
public class Test269Formulaire extends TestWsParentFormulaire {

   private final CaptureMasseFormulaire captMasseDecl = new CaptureMasseFormulaire(this);

   private final CaptureMasseFormulaire captMasseDeclTwo = new CaptureMasseFormulaire(this);

   private final CaptureMasseResultatFormulaire captMasseResult = new CaptureMasseResultatFormulaire();
   
   private final RechercheFormulaire rechFormulaire = new RechercheFormulaire(this);

   private final RechercheFormulaire rechFormulaireParallele = new RechercheFormulaire(this);

   /**
    * Le sous-formulaire pour l'appel à l'opération "archivageMasse"
    * 
    * @return Le sous-formulaire pour l'appel à l'opération "archivageMasse"
    */
   public final CaptureMasseFormulaire getCaptureMasseDeclenchement() {
      return this.captMasseDecl;
   }

   /**
    * Le sous-formulaire pour l'appel à l'opération "archivageMasse" pour un
    * appel sur le meme serveur que l'appel précédent : utilisé pour réaliser
    * deux appels d'archivage de masse sur le meme serveur
    * 
    * @return Le sous-formulaire pour l'appel à l'opération "archivageMasse"
    */
   public final CaptureMasseFormulaire getCaptureMasseDeclenchementParallele() {
      return this.captMasseDeclTwo;
   }

   /**
    * Le sous-formulaire pour la lecture du résultat d'un traitement de masse, à
    * partir de l'ECDE
    * 
    * @return Le sous-formulaire pour la lecture du résultat d'un traitement de
    *         masse
    */
   public final CaptureMasseResultatFormulaire getCaptureMasseResultat() {
      return this.captMasseResult;
   }
   
   /**
    * @return the rechFormulaire
    */
   public RechercheFormulaire getRechFormulaire() {
      return rechFormulaire;
   }

   /**
    * @return the rechFormulaireParallele
    */
   public RechercheFormulaire getRechFormulaireParallele() {
      return rechFormulaireParallele;
   }

}
