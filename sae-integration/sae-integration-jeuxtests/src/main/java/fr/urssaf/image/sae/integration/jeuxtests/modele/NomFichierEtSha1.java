package fr.urssaf.image.sae.integration.jeuxtests.modele;

public class NomFichierEtSha1 {

   private String nomFichier;
   private String sha1;
   
   
   public NomFichierEtSha1(String nomFichier, String sha1) {
      this.nomFichier = nomFichier;
      this.sha1 = sha1;
   }


   public final String getNomFichier() {
      return nomFichier;
   }


   public final void setNomFichier(String nomFichier) {
      this.nomFichier = nomFichier;
   }


   public final String getSha1() {
      return sha1;
   }


   public final void setSha1(String sha1) {
      this.sha1 = sha1;
   }
   
}
