package fr.urssaf.image.sae.saml.component;

import org.opensaml.DefaultBootstrap;
import org.opensaml.xml.ConfigurationException;

/**
 * Classe d'initialisation de la librairie OpenSAML
 * 
 * 
 */
public final class SAMLConfiguration {

   private SAMLConfiguration() {

   }

   /**
    * appel de la méthode {@link DefaultBootstrap#bootstrap()}
    */
   public static void init() {

      try {
         DefaultBootstrap.bootstrap();
      } catch (ConfigurationException e) {
         throw new IllegalStateException(e);
      }

   }
}
