package fr.urssaf.image.sae.anais.framework.modele;

/**
 * Code d'environnement du serveur ANAIS
 * 
 * 
 */
public enum SaeAnaisEnumCodesEnvironnement {

   // TODO La valeur pour developpement n'est pas définitive
   // TODO inverser  le nom et le code
   Developpement("DEV"), Validation("VAL"), Production("PROD");

   private final String code;

   SaeAnaisEnumCodesEnvironnement(String code) {
      this.code = code;
   }

   /**
    * @return retourne le code de l'environnement
    */
   public String code() {
      return this.code;
   }

}
