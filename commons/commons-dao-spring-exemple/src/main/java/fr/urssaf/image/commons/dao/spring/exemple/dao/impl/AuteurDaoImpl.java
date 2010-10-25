package fr.urssaf.image.commons.dao.spring.exemple.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import fr.urssaf.image.commons.dao.spring.exemple.dao.AuteurDao;
import fr.urssaf.image.commons.dao.spring.exemple.modele.Auteur;
import fr.urssaf.image.commons.dao.spring.support.MyHibernateDaoSupport;


@Repository
public class AuteurDaoImpl
   extends
      MyHibernateDaoSupport<Auteur,Integer>
   implements
      AuteurDao
{

	@Autowired
	public AuteurDaoImpl(
			@Qualifier("sessionFactory") SessionFactory sessionFactory) {
		super(sessionFactory,Auteur.class,"id");
	}
	
}
