package fr.urssaf.image.sae.webservices.security;

import java.util.List;

import org.apache.ws.security.WSUsernameTokenPrincipal;
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
    * instanciation d'un {@link AnonymousAuthenticationToken}
    * 
    * @param userPrincipal jeton UsernmaToken
    * @param authorities roles du jeton UsernameToken
    * @return AnonymousAuthenticationToken
    */
   public static Authentication createAuthentication(
         WSUsernameTokenPrincipal userPrincipal,
         List<GrantedAuthority> authorities) {

      return new AnonymousAuthenticationToken(userPrincipal.getName(),
            userPrincipal.getPassword(), authorities);
   }
}
