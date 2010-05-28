package fr.urssaf.image.commons.dao.spring.dao;


import fr.urssaf.image.commons.dao.spring.modele.Etat;
import fr.urssaf.image.commons.dao.spring.service.EntityFindDao;
import fr.urssaf.image.commons.dao.spring.service.EntityIdDao;

/**
 * 
 * @author Bertrand BARAULT
 *
 */
public interface EtatDao extends EntityIdDao<Etat, Integer>,
		EntityFindDao<Etat> {

}
