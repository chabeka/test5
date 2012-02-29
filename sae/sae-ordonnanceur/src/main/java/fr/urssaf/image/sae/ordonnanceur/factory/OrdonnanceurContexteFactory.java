package fr.urssaf.image.sae.ordonnanceur.factory;

import org.springframework.context.ApplicationContext;

/**
 * Classe de chargement du contexte de Spring
 * 
 * 
 */
public final class OrdonnanceurContexteFactory {

   private OrdonnanceurContexteFactory() {

   }

   /**
    * Charge le contexte Spring, en définissant le chemin du fichier de
    * propriétés passé en paramètre
    * 
    * @param cheminFichier
    *           Chemin vers le fichier de propriétés général
    * @return Contexte Spring
    */
   public static ApplicationContext creerContext(String cheminFichier) {
      // TODO Auto-generated method stub
      return null;
   }
}
