package fr.urssaf.image.sae.integration.ihmweb.formulaire;

import fr.urssaf.image.sae.integration.ihmweb.modele.ResultatTest;

/**
 * Classe de sous-formulaire pour un test du WS SaeService, opération
 * "archivageMasse"<br>
 * <br>
 * Un objet de cette classe s'associe au tag "captureMasse.tag" (attribut
 * "objetFormulaire")
 */
public class CaptureMasseFormulaire extends GenericForm {

   private ResultatTest resultats = new ResultatTest();

   private String urlSommaire;

   /**
    * constructeur
    * 
    * @param parent
    *           formulaire pere
    */
   public CaptureMasseFormulaire(TestWsParentFormulaire parent) {
      super(parent);
   }

   /**
    * Les résultats de l'appel à l'opération
    * 
    * @return Les résultats de l'appel à l'opération
    */
   public final ResultatTest getResultats() {
      return this.resultats;
   }

   /**
    * Les résultats de l'appel à l'opération
    * 
    * @param resultats
    *           Les résultats de l'appel à l'opération
    */
   public final void setResultats(ResultatTest resultats) {
      this.resultats = resultats;
   }

   /**
    * L'URL du fichier sommaire.xml
    * 
    * @return L'URL du fichier sommaire.xml
    */
   public final String getUrlSommaire() {
      return urlSommaire;
   }

   /**
    * L'URL du fichier sommaire.xml
    * 
    * @param urlSommaire
    *           L'URL du fichier sommaire.xml
    */
   public final void setUrlSommaire(String urlSommaire) {
      this.urlSommaire = urlSommaire;
   }

}
