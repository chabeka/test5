package fr.urssaf.image.commons.controller.springsecurity.exemple.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.urssaf.image.commons.controller.springsecurity.exemple.authenticate.SecurityUser;

@Service
public class UserService {

   public SecurityUser findByLoginPasswd(String login, String password) {

      SecurityUser user = AuthenticateService.find(login);
      
      if (user == null) {
         throw new UsernameNotFoundException("le compte n'existe pas");
      }
      
      return user;
   }

}
