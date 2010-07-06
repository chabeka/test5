package fr.urssaf.image.commons.dao.spring.service.impl;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import fr.urssaf.image.commons.dao.spring.service.EntityIdDao;

/**
 * 
 * @author Bertrand BARAULT
 *
 * @param <P> classe persistante
 * @param <I> identifiant de la classe persistante
 */
public class EntityIdDaoImpl<P, I extends Serializable> extends AbstractEntityDao<P> implements
		EntityIdDao<P, I> {

	private String identifiant;
	
	private Class<P> table;

	/**
	 * 
	 * @param session session factory d'hibernate
	 * @param table classe persistante
	 */
	public EntityIdDaoImpl(SessionFactory sessionFactory, Class<P> table) {
		this(sessionFactory, table, "id");
	}

	/**
	 * 
	 * @param session session factory d'hibernate
	 * @param table classe persistante
	 * @param identifiant nom de l'attribut de la clé primaire
	 */
	public EntityIdDaoImpl(SessionFactory sessionFactory, Class<P> table, String identifiant) {
		super(sessionFactory,table);
		this.identifiant = identifiant;
		this.table = table;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public P find(I identifiant) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq(this.identifiant, identifiant));

		return (P) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public P get(I identifiant) {
		return (P) this.getSession().get(this.table, identifiant);
	}

}
