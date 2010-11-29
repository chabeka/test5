package fr.urssaf.image.sae.anais.framework.modele;

/**
 * Classe d'énumération des code d'environnement du serveur ANAIS
 * (Développement,Validation,Production)<br>
 * <br>
 * Codes prises en compte
 * <ul>
 * <li><code>Developpement</code>:<code>DEV</code></li>
 * <li><code>Validation</code>:<code>VAL</code></li>
 * <li><code>Production</code>:<code>PROD</code></li>
 * </ul>
 * 
 */
public enum SaeAnaisEnumCodesEnvironnement {

   // TODO La valeur pour developpement n'est pas définitive
   // TODO inverser le nom et le code
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
