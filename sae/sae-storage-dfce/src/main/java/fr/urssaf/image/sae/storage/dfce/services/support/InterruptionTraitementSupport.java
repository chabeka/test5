package fr.urssaf.image.sae.storage.dfce.services.support;

/**
 * Service pour permettre d'interrompre un traitement
 * 
 * 
 */
public interface InterruptionTraitementSupport {

   /**
    * L'interruption du service DFCE est parfois pour des questions de
    * maintenance, et programmé à des moments précis pour être redémarré
    * ensuite. Cette méthode permet de mettre en pause le
    * {@link java.lang.Thread} courant pendant cette période puis de se
    * reconnecter au service DFCE
    * 
    * @param start
    *           Heure de programmation du début de l'interruption. Doit être au
    *           format HH:mm:ss
    * @param delay
    *           Temps d'attente entre chaque tentative de reconnexion
    * @param tentatives
    *           Nombre de tentatives de reconnexion
    */
   void interruption(String start, long delay, int tentatives);
}
