package fr.urssaf.image.sae.lotinstallmaj.service;


/**
 * Pour chaque lot, il y aura une classe principale<br>
 * à lancer pour déclencher des opérations de maj, classe qui devra<br>
 * implémenter cette interface. 
 *
 */
public interface MajLotService {

   /**
    * Réalise les opérations de mise à jour.
    * 
    * @param args arguments de la ligne de commande.
    */
   void demarre(String[] args);
}
