package fr.urssaf.image.sae.saml.service;

import java.security.KeyStore;

import fr.urssaf.image.sae.saml.params.SamlAssertionParams;

/**
 * Classe de création de jeton SAML 2.0
 * 
 * 
 */
public class SamlAssertionCreationService {

   /**
    * Génération d'une assertion SAML 2.0 signée électroniquement, pour être
    * utilisée dans le cadre de l'authentification aux services web du SAE
    * 
    * @param assertionParams
    *           Les paramètres de génération de l'assertion SAML
    * @param keyStore
    *           La clé privée et sa chaîne de certification pour la signature de
    *           l'assertion SAML
    * @return L'assertion SAML 2.0 signée électroniquement
    */
   public final String genererAssertion(SamlAssertionParams assertionParams,
         KeyStore keyStore) {

      // TODO genererAssertion à implémenter

      return null;

   }
}
