package fr.urssaf.image.sae.webdemo.form;

import fr.urssaf.image.sae.webdemo.validation.NotEmpty;


public class ConnectionForm {

   private String relay;
   
   private String saml;

   @NotEmpty  
   public final String getRelayState() {
      return relay;
   }

   public final void setRelayState(String relay) {
      this.relay = relay;
   }

   @NotEmpty
   public final String getSAMLResponse() {
      return saml;
   }

   public final void setSAMLResponse(String saml) {
      this.saml = saml;
   }
   
   
}
