package fr.urssaf.image.commons.dao.spring.service.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.SUPPORTS)
public abstract class AbstractEntityDao<P> extends HibernateDaoSupport{

	private final Class<P> table;
	
	public AbstractEntityDao(SessionFactory sessionFactory,Class<P> table){
		super();
		setSessionFactory(sessionFactory);
		this.table  = table;
	}
	
	protected Criteria getCriteria(){
		return this.getSession().createCriteria(this.table);
	}

}
