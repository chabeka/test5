package fr.urssaf.image.sae.webdemo.form;

import fr.urssaf.image.commons.web.validator.NotEmpty;

/**
 * Classe de formulaire pour la connection
 * 
 * 
 */
public class ConnectionForm {

   private String relay;

   private String saml;

   /**
    * Retourne le contenu du paramètre <code>RelayState</code><br>
    * <br>
    * La valeur n'est pas valide si il n'y a aucun text.<br>
    * Dans ce cas un message est renvoyé: user.login.empty
    * 
    * @return la servlet demandé la connection
    */
   @NotEmpty
   public final String getRelayState() {
      return relay;
   }

   /**
    * Initialise le contenu du paramètre <code>RelayState</code>
    * 
    * @param relay
    *           la servlet demandé la connection
    */
   public final void setRelayState(String relay) {
      this.relay = relay;
   }

   /**
    * Retourne le contenu du paramètre <code>SAMLResponse</code><br>
    * <br>
    * La valeur n'est pas valide si il n'y a aucun text.<br>
    * Dans ce cas un message est renvoyé: user.login.empty
    * 
    * @return le jeton VI de la connection
    */
   @NotEmpty
   public final String getSAMLResponse() {
      return saml;
   }

   /**
    * Initialise le contenu du paramètre <code>SAMLResponse</code>
    * 
    * @param saml
    *           le jeton VI de la connection
    */
   public final void setSAMLResponse(String saml) {
      this.saml = saml;
   }

}
