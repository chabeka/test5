package fr.urssaf.image.sae.webdemo.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;

/**
 * Classe de vérification pour l'identification et les autorisation de
 * l'utilisateur
 * 
 * 
 */
public class SecurityProvider implements AuthenticationProvider {

   @Override
   public final Authentication authenticate(Authentication authentication) {
      return authentication;
   }

   @Override
   public final boolean supports(Class<? extends Object> authentication) {
      return SecurityAuthentication.class.isAssignableFrom(authentication);
   }

}
