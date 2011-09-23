package fr.urssaf.image.sae.ecde.modele.source;

import org.springframework.stereotype.Component;



/***
 * 
 * Classe permettant de recuperer un tableau de EcdeSource
 * <br> à savoir EcdeSource[]
 *
 */
@Component
@SuppressWarnings({"PMD.MethodReturnsInternalArray", "PMD.ArrayIsStoredDirectly"})
public class EcdeSources {
   
   /*
    * Objet sources
    */
   private EcdeSource[] sources;

   /**
    * @return the sources
    */
   public final EcdeSource[] getSources() {
      return sources;
   }

   /**
    * @param sources the sources to set
    */
   public final void setSources(EcdeSource[] sources) {
      this.sources = sources;
   }
   

}
