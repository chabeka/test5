package fr.urssaf.image.sae.webservices.impl;

import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;

import fr.urssaf.image.sae.webservices.SaeService;

/**
 * implémentation des services web du SAE
 * 
 * 
 */
@Service
public class SaeServiceImpl implements SaeService {

   @Override
   public final String ping() {

      return PING_MSG;
   }

   /**
    * roles autorisés: 'ROLE_TOUS'<br>
    * 
    */
   @RolesAllowed("ROLE_TOUS")
   @Override
   public final String pingSecure() {

      return PING_SECURE_MSG;
   }
}
