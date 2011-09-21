package fr.urssaf.image.sae.services.enrichment.xml.model;

/**
 * Énumération contenant la listes des codes long des métadonnées à enrichir.<br/>
 * 
 * @author rhofir.
 */
public enum SAEArchivalMetadatas {
   // Code RND
   CODERND("CodeRND"),
   // Code fonction
   CODEFONCTION("CodeFonction"),
   // CodeActivite
   CODEACTIVITE("CodeActivite"),
   // DureeConservation
   DUREECONSERVATION("DureeConservation"),
   // DateArchivage
   DATEARCHIVAGE("DateArchivage"),
   // DateDebutConservation
   DATEDEBUTCONSERVATION("DateDebutConservation"),
   // DateFinConservation
   DATEFINCONSERVATION("DateFinConservation"),
   // Gel
   GEL("Gel"),
   // ObjectType
   OBJECTTYPE("ObjectType"),
   // type
   TYPE("type"),
   // ContratDeService
   CONTRATDESERVICE("ContratDeService"),
   // pas de valeur
   NOVALUE(""),
   //
   HASHDOC("Hash");

   // Le code long de la métadonnée.
   private String longCode;

   /**
    * 
    * @param shortCode
    *           . Le code court
    */
   SAEArchivalMetadatas(final String longCode) {
      this.longCode = longCode;
   }

   /**
    * @param longCode
    *           : Le code long de la métadonnée.
    */
   public void setLongCode(final String longCode) {
      this.longCode = longCode;
   }

   /**
    * @return : Le code long de la métadonnée.
    */
   public String getLongCode() {
      return longCode;
   }

}
