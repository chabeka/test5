package fr.urssaf.image.commons.dao.spring.exemple.dao;



import fr.urssaf.image.commons.dao.spring.exemple.modele.Etat;
import fr.urssaf.image.commons.dao.spring.service.EntityFindDao;
import fr.urssaf.image.commons.dao.spring.service.EntityIdDao;


public interface EtatDao
   extends
      EntityIdDao<Etat, Integer>,
		EntityFindDao<Etat>
{ 
   
}
