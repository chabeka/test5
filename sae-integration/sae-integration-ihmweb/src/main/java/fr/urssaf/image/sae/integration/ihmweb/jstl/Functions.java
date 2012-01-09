package fr.urssaf.image.sae.integration.ihmweb.jstl;

import fr.urssaf.image.sae.integration.ihmweb.utils.BooleanUtils;


/**
 * Fonctions JSTL
 */
public final class Functions {

   
   private Functions() {
      
   }
   

   /**
    * Convertit une valeur de type boolean en chaîne de caractères "Oui" ou "Non"
    * 
    * @param value le boolean
    * @return la chaîne de caractères "Oui" ou "Non"
    */
   public static String booleanToOuiNon(boolean value) {
      return BooleanUtils.booleanToOuiNon(value);
   }
   
}
