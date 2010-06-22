package fr.urssaf.image.commons.dao.spring.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

import fr.urssaf.image.commons.dao.spring.service.EntityFindDao;
import fr.urssaf.image.commons.dao.spring.util.CriteriaUtil;

public class EntityFindDaoImpl<P> extends AbstractEntityDao<P> implements
		EntityFindDao<P> {

	public EntityFindDaoImpl(SessionFactory sessionFactory, Class<P> table) {
		super(sessionFactory, table);
	}

	@Override
	public List<P> find(int firstResult, int maxResult, String order,
			boolean inverse) {
		Criteria criteria = this.getCriteria();
		return this.find(criteria, firstResult, maxResult, order, inverse);

	}

	@Override
	public List<P> find(String order, boolean inverse) {
		Criteria criteria = this.getCriteria();
		CriteriaUtil.order(criteria, order, inverse);
		return this.find(criteria);
	}

	@Override
	public List<P> find(String order) {
		return this.find(order, false);

	}

	@Override
	public List<P> find() {
		Criteria criteria = this.getCriteria();
		return this.find(criteria);
	}

	/**
	 * Renvoie tous les objets persistants triés et filtrés sur un interval
	 * 
	 * @param criteria
	 *            object criteria
	 * @param firstResult
	 *            index du premier objet
	 * @param maxResult
	 *            nombre de résutats
	 * @param order
	 *            nom de la colonne à trier
	 * @param inverse
	 *            sens du tri
	 * @return liste des objets persistants
	 */
	public List<P> find(Criteria criteria, int firstResult, int maxResult,
			String order, boolean inverse) {
		CriteriaUtil.order(criteria, order, inverse);
		return this.find(criteria, firstResult, maxResult);

	}

	/**
	 * Renvoie tous les objets persistants triés
	 * 
	 * @param criteria
	 *            object criteria
	 * @param order
	 *            nom de la colonne à trier
	 * @param inverse
	 *            sens du tri
	 * @return liste des objets persistants
	 */
	public List<P> find(Criteria criteria, String order, boolean inverse) {
		CriteriaUtil.order(criteria, order, inverse);
		return this.find(criteria);

	}

	/**
	 * Renvoie tous les objets persistants filtrés sur un interval
	 * 
	 * @param criteria
	 *            object criteria
	 * @param firstResult
	 *            index du premier objet
	 * @param maxResult
	 *            nombre de résutats
	 * @return liste des objets persistants
	 */
	public List<P> find(Criteria criteria, int firstResult, int maxResult) {
		CriteriaUtil.resultats(criteria, firstResult, maxResult);
		return find(criteria);
	}

	/**
	 * Renvoie tous les objets persistants
	 * 
	 * @param criteria
	 *            object criteria
	 * @return liste des objets persistants
	 */
	@SuppressWarnings("unchecked")
	public List<P> find(Criteria criteria) {
		return criteria.list();
	}

}
