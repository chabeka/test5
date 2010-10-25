package fr.urssaf.image.commons.dao.spring.exemple.dao;

import fr.urssaf.image.commons.dao.spring.exemple.modele.Auteur;
import fr.urssaf.image.commons.dao.spring.service.EntityFindDao;
import fr.urssaf.image.commons.dao.spring.service.EntityIdDao;


public interface AuteurDao
   extends
      EntityIdDao<Auteur, Integer>,
		EntityFindDao<Auteur> 
{

}
