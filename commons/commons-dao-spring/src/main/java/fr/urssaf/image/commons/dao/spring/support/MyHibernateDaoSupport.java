package fr.urssaf.image.commons.dao.spring.support;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.urssaf.image.commons.dao.spring.service.EntityCountDao;
import fr.urssaf.image.commons.dao.spring.service.EntityFindDao;
import fr.urssaf.image.commons.dao.spring.service.EntityIdDao;
import fr.urssaf.image.commons.dao.spring.service.EntityModifyDao;
import fr.urssaf.image.commons.dao.spring.service.impl.EntityCountDaoImpl;
import fr.urssaf.image.commons.dao.spring.service.impl.EntityFindDaoImpl;
import fr.urssaf.image.commons.dao.spring.service.impl.EntityIdDaoImpl;
import fr.urssaf.image.commons.dao.spring.service.impl.EntityModifyDaoImpl;

/**
 * Support pour les fonctions typiques d'une classe persistante M avec un
 * identifiant I
 * 
 * @author Bertrand BARAULT
 * 
 * @param <T>
 *            classe persistante
 * @param <I>
 *            identifiant de la classe persistante
 */
@Transactional(propagation = Propagation.SUPPORTS)
@SuppressWarnings("PMD.TooManyMethods")
public class MyHibernateDaoSupport<T, I extends Serializable> extends
		HibernateDaoSupport implements EntityCountDao, EntityModifyDao<T>,
		EntityFindDao<T>, EntityIdDao<T, I> {

	/**
	 * classe persistante
	 */
	private Class<T> table;

	private EntityModifyDaoImpl<T> modifyDaoImpl;

	@SuppressWarnings("PMD.ImmutableField")
	private EntityIdDaoImpl<T, I> idDaoImpl;

	private EntityFindDaoImpl<T> findDaoImpl;

	private EntityCountDaoImpl<T> countDaoImpl;

	/**
	 * @param sessionFactory
	 *            session factory d'hibernate
	 * @param table
	 *            classe persistante
	 */
	public MyHibernateDaoSupport(SessionFactory sessionFactory, Class<T> table) {
		super();
		setSessionFactory(sessionFactory);
		this.table = table;
		this.modifyDaoImpl = new EntityModifyDaoImpl<T>(sessionFactory);
		this.idDaoImpl = new EntityIdDaoImpl<T, I>(sessionFactory, this.table);
		this.countDaoImpl = new EntityCountDaoImpl<T>(sessionFactory,
				this.table);
		this.findDaoImpl = new EntityFindDaoImpl<T>(sessionFactory, this.table);
	}

	/**
	 * 
	 * @param sessionFactory
	 *            session factory d'hibernate
	 * @param table
	 *            classe persistante
	 * @param identifiant
	 *            nom de l'attribut de la clé primaire
	 */
	public MyHibernateDaoSupport(SessionFactory sessionFactory, Class<T> table,
			String identifiant) {
		this(sessionFactory, table);
		this.idDaoImpl = new EntityIdDaoImpl<T, I>(sessionFactory, this.table,
				identifiant);
	}

	/**
	 * Renvoie le nom de la classe persistante
	 * 
	 * @return nom de la classe persistante
	 */
	protected String getTable() {
		return this.table.getCanonicalName();
	}

	/**
	 * Renvoie un objet criteria de la classe persistante
	 * 
	 * @return objet criteria de la classe persistante
	 */
	protected Criteria getCriteria() {
		return this.getSession().createCriteria(table);
	}

	@Override
	public void save(T obj) {
		this.modifyDaoImpl.save(obj);
	}

	@Override
	public void delete(T obj) {
		this.modifyDaoImpl.delete(obj);
	}

	@Override
	public void update(T obj) {
		this.modifyDaoImpl.update(obj);
	}

	@Override
	public T get(I identifiant) {
		return idDaoImpl.get(identifiant);
	}

	@Override
	public T find(I identifiant) {
		return idDaoImpl.find(identifiant);
	}

	@Override
	public List<T> find(int firstResult, int maxResult, String order,
			boolean inverse) {
		return this.findDaoImpl.find(firstResult, maxResult, order, inverse);

	}

	@Override
	public List<T> find(String order, boolean inverse) {
		return this.findDaoImpl.find(order, inverse);
	}

	@Override
	public List<T> find(String order) {
		return this.findDaoImpl.find(order);

	}

	@Override
	public List<T> find() {
		return this.findDaoImpl.find();
	}

	protected List<T> find(Criteria criteria, int firstResult, int maxResult,
			String order, boolean inverse) {
		return this.findDaoImpl.find(criteria, firstResult, maxResult, order,
				inverse);

	}

	protected List<T> find(Criteria criteria, String order, boolean inverse) {
		return this.findDaoImpl.find(criteria, order, inverse);

	}

	protected List<T> find(Criteria criteria, int firstResult, int maxResult) {
		return this.findDaoImpl.find(criteria, firstResult, maxResult);
	}

	protected List<T> find(Criteria criteria) {
		return this.findDaoImpl.find(criteria);
	}

	/**
	 * Renvoie tous les objets persistants filtrés par un objet exemple
	 * 
	 * @param obj
	 *            objet exemple
	 * @return liste des objets persistants
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByExample(T obj) {
		return this.getCriteria().add(Example.create(obj)).list();
	}

	@Override
	public int count() {
		return countDaoImpl.count();
	}

	protected int count(Criteria criteria) {
		return countDaoImpl.count(criteria);
	}

}
