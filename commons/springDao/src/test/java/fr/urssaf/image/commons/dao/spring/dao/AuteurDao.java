package fr.urssaf.image.commons.dao.spring.dao;


import fr.urssaf.image.commons.dao.spring.modele.Auteur;
import fr.urssaf.image.commons.dao.spring.service.EntityFindDao;
import fr.urssaf.image.commons.dao.spring.service.EntityIdDao;

/**
 * 
 * @author Bertrand BARAULT
 *
 */
public interface AuteurDao extends EntityIdDao<Auteur, Integer>,
		EntityFindDao<Auteur> {

}
