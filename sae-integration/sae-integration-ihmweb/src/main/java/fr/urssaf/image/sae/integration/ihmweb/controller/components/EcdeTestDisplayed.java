/**
 * 
 */
package fr.urssaf.image.sae.integration.ihmweb.controller.components;

import fr.urssaf.image.sae.integration.ihmweb.modele.ecde.EcdeTest;

/**
 * 
 * 
 */
public class EcdeTestDisplayed extends EcdeTest {

   private boolean checked;

   private String statusTraitement;
   
   private String erreur;

   /**
    * 
    */
   public EcdeTestDisplayed(EcdeTest ecdeTest) {
      super(ecdeTest.getName(), ecdeTest.getUrl());
   }

   /**
    * 
    */
   public EcdeTestDisplayed() {
      super();
   }

   /**
    * @return the checked
    */
   public boolean getChecked() {
      return checked;
   }

   /**
    * @param checked
    *           the checked to set
    */
   public void setChecked(boolean checked) {
      this.checked = checked;
   }

   /**
    * @return the statusTraitement
    */
   public String getStatusTraitement() {
      return statusTraitement;
   }

   /**
    * @param statusTraitement
    *           the statusTraitement to set
    */
   public void setStatusTraitement(String statusTraitement) {
      this.statusTraitement = statusTraitement;
   }

   /**
    * @return the erreur
    */
   public String getErreur() {
      return erreur;
   }

   /**
    * @param erreur the erreur to set
    */
   public void setErreur(String erreur) {
      this.erreur = erreur;
   }

}
