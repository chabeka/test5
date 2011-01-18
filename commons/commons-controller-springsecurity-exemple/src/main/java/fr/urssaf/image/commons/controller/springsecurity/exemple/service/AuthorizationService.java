package fr.urssaf.image.commons.controller.springsecurity.exemple.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import fr.urssaf.image.commons.controller.springsecurity.exemple.modele.Role;

@Service
public class AuthorizationService {

   private static Map<String, Role[]> pages = new HashMap<String, Role[]>();

   private static final Logger LOG = Logger
         .getLogger(AuthorizationService.class);

   static {

      pages.put("page1", null);
      pages.put("page2", new Role[] { Role.role_user, Role.role_admin });
      pages.put("page3", new Role[] { Role.role_admin });

      for (String page : pages.keySet()) {

         LOG.debug(page + " "
               + ArrayUtils.toString(pages.get(page), "sans authorisation"));
      }

   }

   public Boolean isAuthorized(String page, Collection<String> roles) {

      Boolean authorized = null;

      if (pages.containsKey(page)) {

         Role[] authorizedRoles = pages.get(page);
         authorized = isAuthorized(authorizedRoles, roles);

      }

      return authorized;
   }

   private Boolean isAuthorized(Role[] authorizedRoles, Collection<String> roles) {

      Boolean authorized = null;

      if (authorizedRoles != null) {

         for (Role authorizedRole : authorizedRoles) {
            authorized = false;
            if (roles.contains(authorizedRole.getAuthority())) {
               authorized = true;
               break;
            }

         }

      }

      return authorized;
   }
}
