package fr.urssaf.image.sae.storage.dfce.data.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import fr.urssaf.image.sae.storage.dfce.utils.Utils;

/**
 * Classe permettant de désérialiser les catégories.<BR />
 *<ul>
 * <li>saeCategories : les catégories.</li>
 * </ul>
 * 
 * @author akenore,rhofir.
 * 
 */

@XStreamAlias("categories")
public class SaeCategories {
   @XStreamImplicit(itemFieldName = "category")
   private List<SaeCategory> categoriesSae;

   /**
    * @param categories
    *           : La liste des catégories.
    */
   public final void setSaeCategories(final List<SaeCategory> categories) {
      this.categoriesSae = categories;
   }

   /**
    * @return La liste des catégories.
    */
   public final List<SaeCategory> getSaeCategories() {
      return categoriesSae;
   }

   /**
    * {@inheritDoc}
    */
   public final String toString() {
      final ToStringBuilder toStringBuilder = new ToStringBuilder(this);
      for (SaeCategory saeCategory : Utils.nullSafeIterable(categoriesSae)) {
         toStringBuilder.append("SaeCategory", saeCategory.toString());
      }
      return toStringBuilder.toString();
   }

}
