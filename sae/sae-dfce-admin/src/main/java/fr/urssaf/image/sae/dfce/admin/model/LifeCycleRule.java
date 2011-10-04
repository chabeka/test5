package fr.urssaf.image.sae.dfce.admin.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Classe représentant le référentiel des codes RND.
 * 
 * @author Rhofir
 */
@XStreamAlias("LifeCycleRule")
public class LifeCycleRule {
   @XStreamImplicit(itemFieldName = "TypeDocument")
   private List<TypeDocument> typeDocuments;

   /**
    * @return Liste de type {@link TypeDocument}
    */
   public final List<TypeDocument> getTypeDocuments() {
      return typeDocuments;
   }

   /**
    * @param typeDocuments
    *           : Liste de type {@link TypeDocument}.
    */
   public final void setTypeDocuments(final List<TypeDocument> typeDocuments) {
      this.typeDocuments = typeDocuments;
   }
}
