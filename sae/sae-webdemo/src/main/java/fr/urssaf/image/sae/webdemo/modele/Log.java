package fr.urssaf.image.sae.webdemo.modele;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import fr.urssaf.image.sae.webdemo.resource.CustomDateSerializer;

/**
 * Modèlisation des traces du service d'exploitation<br>
 * 
 * 
 */
public class Log {

   private long idseq;

   private Date horodatage;

   private int occurences;

   private String probleme;

   private String action;

   private String infos;

   /**
    * @return the idseq
    */
   public final long getIdseq() {
      return idseq;
   }

   /**
    * @param idseq
    *           the idseq to set
    */
   public final void setIdseq(long idseq) {
      this.idseq = idseq;
   }

   /**
    * @return the horodatage
    */
   @JsonSerialize(using = CustomDateSerializer.class)
   public final Date getHorodatage() {
      return horodatage;
   }

   /**
    * @param horodatage
    *           the horodatage to set
    */
   public final void setHorodatage(Date horodatage) {
      this.horodatage = horodatage;
   }

   /**
    * @return the occurences
    */
   public final int getOccurences() {
      return occurences;
   }

   /**
    * @param occurences
    *           the occurences to set
    */
   public final void setOccurences(int occurences) {
      this.occurences = occurences;
   }

   /**
    * @return the probleme
    */
   public final String getProbleme() {
      return probleme;
   }

   /**
    * @param probleme
    *           the probleme to set
    */
   public final void setProbleme(String probleme) {
      this.probleme = probleme;
   }

   /**
    * @return the action
    */
   public final String getAction() {
      return action;
   }

   /**
    * @param action
    *           the action to set
    */
   public final void setAction(String action) {
      this.action = action;
   }

   /**
    * @return the infos
    */
   public final String getInfos() {
      return infos;
   }

   /**
    * @param infos
    *           the infos to set
    */
   public final void setInfos(String infos) {
      this.infos = infos;
   }

}
