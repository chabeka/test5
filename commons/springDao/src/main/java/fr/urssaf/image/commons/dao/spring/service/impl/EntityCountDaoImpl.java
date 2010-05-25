package fr.urssaf.image.commons.dao.spring.service.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.urssaf.image.commons.dao.spring.service.EntityCountDao;

@Transactional(propagation = Propagation.SUPPORTS)
public class EntityCountDaoImpl<P> extends AbstractEntityDao<P> implements
		EntityCountDao {

	public EntityCountDaoImpl(SessionFactory sessionFactory, Class<P> table) {
		super(sessionFactory, table);
	}

	@Override
	public int count() {
		return this.count(this.getCriteria());
	}

	/**
	 * Compte les résultats d'un objet criteria
	 * 
	 * @param criteria
	 *            objet criteria
	 * @return nombre de résultats
	 */
	public int count(Criteria criteria) {

		criteria.setProjection(Projections.rowCount());

		return ((Integer) criteria.uniqueResult());
	}

}
