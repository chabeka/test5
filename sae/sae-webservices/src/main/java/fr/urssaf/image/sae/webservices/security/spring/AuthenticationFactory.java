package fr.urssaf.image.sae.webservices.security.spring;

import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * Classe d'instanciation d'objet de type {@link Authentication}
 * 
 * 
 */
public final class AuthenticationFactory {

   private AuthenticationFactory() {

   }

   /**
    * 
    * @param key
    *           {@link AnonymousAuthenticationToken#getKeyHash()}
    * @param principal
    *           {@link AnonymousAuthenticationToken#getPrincipal()}
    * @param authorities
    *           {@link AnonymousAuthenticationToken#getAuthorities()}
    * @return instance de {@link AnonymousAuthenticationToken}
    */
   public static Authentication createAuthentication(String key,
         Object principal, List<GrantedAuthority> authorities) {

      return new AnonymousAuthenticationToken(key, principal, authorities);
   }
}
