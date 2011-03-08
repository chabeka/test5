package fr.urssaf.image.sae.webservices.impl;

import org.springframework.stereotype.Service;

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

      return PING_MESSAGE;
   }
}
