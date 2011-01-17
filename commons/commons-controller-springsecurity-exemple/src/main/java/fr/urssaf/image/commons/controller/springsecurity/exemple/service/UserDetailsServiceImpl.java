package fr.urssaf.image.commons.controller.springsecurity.exemple.service;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.urssaf.image.commons.controller.springsecurity.exemple.authenticate.SecurityUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

   @Override
   public UserDetails loadUserByUsername(String username)
         throws UsernameNotFoundException, DataAccessException {
      
      SecurityUser user = AuthenticateService.find(username);

      if (user == null) {
         throw new UsernameNotFoundException("le compte n'existe pas");
      }

      return user;
   }

}
