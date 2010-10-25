package fr.urssaf.image.commons.dao.spring.exemple.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import fr.urssaf.image.commons.dao.spring.exemple.dao.EtatDao;
import fr.urssaf.image.commons.dao.spring.exemple.modele.Etat;
import fr.urssaf.image.commons.dao.spring.support.MyHibernateDaoSupport;


@Repository
public class EtatDaoImpl
   extends
      MyHibernateDaoSupport<Etat,Integer>
   implements
      EtatDao
{

	@Autowired
	public EtatDaoImpl(
			@Qualifier("sessionFactory") SessionFactory sessionFactory) {
		super(sessionFactory,Etat.class,"id");
	}
	
}
