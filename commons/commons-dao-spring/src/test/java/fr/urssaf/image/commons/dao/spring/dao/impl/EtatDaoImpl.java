package fr.urssaf.image.commons.dao.spring.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


import fr.urssaf.image.commons.dao.spring.dao.EtatDao;
import fr.urssaf.image.commons.dao.spring.modele.Etat;
import fr.urssaf.image.commons.dao.spring.support.MyHibernateDaoSupport;

/**
 * 
 * @author Bertrand BARAULT
 *
 */
@Repository
public class EtatDaoImpl extends MyHibernateDaoSupport<Etat,Integer> implements EtatDao {

	
	/**
	 * @param sessionFactory
	 */
	@Autowired
	public EtatDaoImpl(
			@Qualifier("sessionFactory") SessionFactory sessionFactory) {
		super(sessionFactory,Etat.class);
	}
	
	
}
