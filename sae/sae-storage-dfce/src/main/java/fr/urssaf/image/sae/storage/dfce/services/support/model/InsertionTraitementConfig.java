package fr.urssaf.image.sae.storage.dfce.services.support.model;

/**
 * Modèle de configuration pour programmer une interruption quotidienne dans un
 * traitement.<br>
 * <ul>
 * <li>start : Heure de programmation du début de l'interruption. Doit être au
 * format HH:mm:ss</li>
 * <li>delay : Durée de la pause</li>
 * <li>tentatives : Nombre de tentatives pour reprendre le traitement</li>
 * </ul>
 * 
 * 
 */
public class InsertionTraitementConfig {

   private String start;

   private long delay;

   private int tentatives;

   /**
    * @return the start
    */
   public final String getStart() {
      return start;
   }

   /**
    * @param start
    *           the start to set
    */
   public final void setStart(String start) {
      this.start = start;
   }

   /**
    * @return the delay
    */
   public final long getDelay() {
      return delay;
   }

   /**
    * @param delay
    *           the delay to set
    */
   public final void setDelay(long delay) {
      this.delay = delay;
   }

   /**
    * @return the tentatives
    */
   public final int getTentatives() {
      return tentatives;
   }

   /**
    * @param tentatives
    *           the tentatives to set
    */
   public final void setTentatives(int tentatives) {
      this.tentatives = tentatives;
   }

}
