package fr.urssaf.image.commons.controller.springsecurity.exemple.authenticate;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import fr.urssaf.image.commons.controller.springsecurity.exemple.service.UserService;

public class SecurityProvider extends AbstractUserDetailsAuthenticationProvider {

   private final UserService userService;

   protected SecurityProvider(UserService userService) {
      super();
      if (userService == null) {
         throw new IllegalStateException("'userService' is required");
      }

      this.userService = userService;
      this.setHideUserNotFoundExceptions(false);
   }

   @Override
   protected void additionalAuthenticationChecks(UserDetails userDetails,
         UsernamePasswordAuthenticationToken authentication)
         throws AuthenticationException {

      if (!StringUtils.equals(userDetails.getPassword(),
            (String) authentication.getCredentials())) {
         throw new BadCredentialsException("Mauvais mot de passe");
      }

   }

   @Override
   protected UserDetails retrieveUser(String username,
         UsernamePasswordAuthenticationToken authentication)
         throws AuthenticationException {

      SecurityUser user = userService.findByLoginPasswd((String) authentication
            .getPrincipal(), (String) authentication.getCredentials());

      if (user == null) {
         throw new UsernameNotFoundException("le compte n'existe pas");
      }

      return user;
   }

}
