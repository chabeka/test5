package fr.urssaf.image.commons.dao.spring.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


import fr.urssaf.image.commons.dao.spring.dao.AuteurDao;
import fr.urssaf.image.commons.dao.spring.modele.Auteur;
import fr.urssaf.image.commons.dao.spring.support.MyHibernateDaoSupport;

/**
 * 
 * @author Bertrand BARAULT
 *
 */
@Repository
public class AuteurDaoImpl extends MyHibernateDaoSupport<Auteur,Integer> implements AuteurDao {

	
	/**
	 * @param sessionFactory
	 */
	@Autowired
	public AuteurDaoImpl(
			@Qualifier("sessionFactory") SessionFactory sessionFactory) {
		super(sessionFactory,Auteur.class);
	}
	
	
}
