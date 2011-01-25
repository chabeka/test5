package fr.urssaf.image.commons.springsecurity.webservice.authenticate;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public final class AuthenticateService {
   
   public void authenticate(String... roles) {

      Authentication authentication = new AnonymousAuthenticationToken(
            "login", "password", AuthorityUtils.createAuthorityList(roles));

      SecurityContextHolder.getContext().setAuthentication(authentication);

   }
}
