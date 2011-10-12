package fr.urssaf.image.sae.services.util;

import java.util.Collections;
import java.util.List;

import fr.urssaf.image.sae.metadata.utils.Utils;

/**
 * Classe utilitaire.
 * 
 *
 */
public class FormatUtils {
   /**
    * Formatter la liste des code erreurs.
    * 
    * @param list
    * @return Une Chaîne de code erreur.
    */
   public static String formattingDisplayList(List<String> listCodeErrors) {
      StringBuilder builder = new StringBuilder();
      Collections.sort(listCodeErrors);
      int i = 0;
      for (String value : Utils.nullSafeIterable(listCodeErrors)) {
         builder.append(value);
         i++;
         if (i < listCodeErrors.size() - 1) {
            builder.append(", ");
         }
      }
      return builder.toString();

   }

   /** Cette classe n'est pas concue pour être instanciée. */
   private FormatUtils() {
      assert false;
   }
}
