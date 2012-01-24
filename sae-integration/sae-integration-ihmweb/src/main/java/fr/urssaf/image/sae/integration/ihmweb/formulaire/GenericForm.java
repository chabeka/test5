/**
 * 
 */
package fr.urssaf.image.sae.integration.ihmweb.formulaire;

/**
 * 
 * 
 */
public class GenericForm {

   /**
    * Formulaire parent
    */
   private TestWsParentFormulaire parent;

   /**
    * Constructeur
    * 
    * @param parent
    */
   public GenericForm(TestWsParentFormulaire parent) {
      this.parent = parent;
   }

   /**
    * @return the parent
    */
   public TestWsParentFormulaire getParent() {
      return parent;
   }

}
