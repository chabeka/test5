package fr.urssaf.image.sae.ordonnanceur.service;

/**
 * service de coordination des différents traitements en masse
 * 
 * 
 */
public interface CoordinationService {

   /**
    * Méthode réalisant les opérations suivantes :
    * <ul>
    * <li>récupération de la liste des jobs (délégué)</li>
    * <li>vérification si un job doit être lancé ou non (délégué)</li>
    * <li>appel de l'exécution du job si nécessaire</li>
    * </ul>
    */
   void lancerTraitement();
}
