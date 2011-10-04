package fr.urssaf.image.sae.dfce.admin.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Cette classe représente un type de document du référentiel des codes RND. <BR />
 * Elle contient les attributs :
 * <ul>
 * <li>rndCode : Référentiel National de Documents.</li>
 * <li>activityCode : Le Code d'activité.</li>
 * <li>fonctionCode : Le Code fonction.</li>
 * <li>rndDescription : Le libelle RND.</li>
 * <li>storageDuration : La durée de conservation.</li>
 * </ul>
 * 
 * 
 * @author rhofir.
 * 
 */
@XStreamAlias("TypeDocument")
public class TypeDocument {
   @XStreamAlias("CodeRND")
   private String rndCode;
   @XStreamAlias("CodeFonction")
   private String fonctionCode;
   @XStreamAlias("CodeActivite")
   private String activityCode;
   @XStreamAlias("LibelleRND")
   private String rndDescription;
   @XStreamAlias("DureeConservation")
   private int storageDuration;
   @XStreamAlias("VersionRND")
   private String versionRnd;

   /**
    * @return Référentiel National de Documents
    */
   public final String getRndCode() {
      return rndCode;
   }

   /**
    * @param rndCode
    *           : Référentiel National de Documents
    */
   public final void setRndCode(final String rndCode) {
      this.rndCode = rndCode;
   }

   /**
    * @return Le Code fonction.
    */
   public final String getFonctionCode() {
      return fonctionCode;
   }

   /**
    * @param fonctionCode
    *           : Le Code fonction.
    */
   public final void setFonctionCode(final String fonctionCode) {
      this.fonctionCode = fonctionCode;
   }

   /**
    * @return Le Code d'activité
    */
   public final String getActivityCode() {
      return activityCode;
   }

   /**
    * @param activityCode
    *           : Le Code d'activité
    */
   public final void setActivityCode(final String activityCode) {
      this.activityCode = activityCode;
   }

   /**
    * @return Le libelle RND.
    */
   public final String getRndDescription() {
      return rndDescription;
   }

   /**
    * @param rndDescription
    *           : Le libelle RND.
    */
   public final void setRndDescription(final String rndDescription) {
      this.rndDescription = rndDescription;
   }

   /**
    * @return La durée de conservation.
    */
   public final int getStorageDuration() {
      return storageDuration;
   }

   /**
    * @param storageDuration
    *           : La durée de conservation.
    */
   public final void setStorageDuration(final int storageDuration) {
      this.storageDuration = storageDuration;
   }

   /**
    * @return La version du Rnd.
    */
   public final String getVersionRnd() {
      return versionRnd;
   }

   /**
    * @param versionRnd
    *           : La version du Rnd.
    */
   public final void setVersionRnd(final String versionRnd) {
      this.versionRnd = versionRnd;
   }

}
