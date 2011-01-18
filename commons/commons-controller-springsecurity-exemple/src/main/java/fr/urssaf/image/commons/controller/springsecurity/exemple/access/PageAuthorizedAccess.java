package fr.urssaf.image.commons.controller.springsecurity.exemple.access;

import java.util.Collection;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.FilterInvocation;
import org.springframework.web.util.WebUtils;

import fr.urssaf.image.commons.controller.springsecurity.exemple.service.AuthorizationService;

public class PageAuthorizedAccess implements AccessDecisionVoter {

   public final AuthorizationService authService;

   public PageAuthorizedAccess(AuthorizationService authService) {

      if (authService == null) {
         throw new IllegalStateException("'authService' is required");
      }
      this.authService = authService;
   }

   @Override
   public boolean supports(ConfigAttribute attribute) {

      return true;
   }

   @Override
   public boolean supports(Class<?> clazz) {

      return FilterInvocation.class.isAssignableFrom(clazz);
   }

   @Override
   public int vote(Authentication authentication, Object object,
         Collection<ConfigAttribute> attributes) {

      FilterInvocation filter = (FilterInvocation) object;
      String url = WebUtils.extractFilenameFromUrlPath(filter.getRequestUrl());
    
      int access = ACCESS_ABSTAIN;

      Boolean authorized = authService.isAuthorized(url, AuthorityUtils
            .authorityListToSet(authentication.getAuthorities()));
      if (BooleanUtils.isTrue(authorized)) {

         access = ACCESS_GRANTED;

      }

      else if (BooleanUtils.isFalse(authorized)) {

         access = ACCESS_DENIED;
      }
     
      return access;
   }

}
