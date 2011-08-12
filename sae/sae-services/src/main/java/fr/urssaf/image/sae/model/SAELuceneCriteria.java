package fr.urssaf.image.sae.model;

/**
 * <b>\\TODO à remplacer par les objets sae-bo</b> <br>
 * classe représentant le critère de recherche par requête lucène. Elle contient
 * la requête lucène et une liste de métadonnées souhaitées à retourner.
 */
public class SAELuceneCriteria {
   // Les attributs
   private String luceneQuery;
   private int limit;

   /**
    * Retourne la requête lucene.
    * 
    * @return La requête lucene
    */
   public final String getLuceneQuery() {
      return luceneQuery;
   }

   /**
    * Initialise la requête lucene
    * 
    * @param luceneQuery
    *           : Reaquête Lucene
    */
   public final void setLuceneQuery(final String luceneQuery) {
      this.luceneQuery = luceneQuery;
   }

   /**
    * Retourne la valeur maximum du nombre de document à retourner dans le cadre
    * d’une recherche
    * 
    * @return Le maximum de documents à retourner
    */
   public final Integer getLimit() {
      return limit;
   }

   /**
    * Initialise la valeur limite du nombre limite de document à retourner dans
    * le cadre d’une recherche
    * 
    * @param limit
    *           : Le nombre maximum de documents à retourner dans une recherche
    */
   public final void setLimit(final int limit) {
      this.limit = limit;
   }

}
