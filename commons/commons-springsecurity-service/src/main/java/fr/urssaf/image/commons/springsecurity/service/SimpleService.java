package fr.urssaf.image.commons.springsecurity.service;

import fr.urssaf.image.commons.springsecurity.service.modele.Modele;

public interface SimpleService {

   void save(Modele modele);

   Modele load();
}
