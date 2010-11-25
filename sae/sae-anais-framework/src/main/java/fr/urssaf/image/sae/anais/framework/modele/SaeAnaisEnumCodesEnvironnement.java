package fr.urssaf.image.sae.anais.framework.modele;

/**
 * Code d'environnement du serveur ANAIS
 * 
 * 
 */
public enum SaeAnaisEnumCodesEnvironnement {

   // TODO La valeur pour developpement n'est pas définitive
   Developpement("DEV"), Validation("VAL"), Production("PROD");

   /**
    * 
    */
   public final String code;

   SaeAnaisEnumCodesEnvironnement(String code) {
      this.code = code;
   }

}
