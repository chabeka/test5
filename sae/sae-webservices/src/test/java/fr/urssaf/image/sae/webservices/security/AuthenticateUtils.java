package fr.urssaf.image.sae.webservices.security;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Classe d'authentification pour Spring Security<br>
 * 
 * 
 */
public final class AuthenticateUtils {

   private AuthenticateUtils() {

   }

   /**
    * autorisation : ROLE_TOUS
    */
   public static void authenticate() {

      authenticate("ROLE_TOUS");

   }

   /**
    * 
    * @param roles
    *           autorisations attribuées à l'identification
    */
   public static void authenticate(String... roles) {

      Authentication authentication = new TestingAuthenticationToken(
            "login_test", "password_test", roles);

      SecurityContextHolder.getContext().setAuthentication(authentication);

   }
}
