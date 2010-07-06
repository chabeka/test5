package fr.urssaf.image.commons.dao.spring.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import fr.urssaf.image.commons.dao.spring.dao.DocumentDao;
import fr.urssaf.image.commons.dao.spring.modele.Auteur;
import fr.urssaf.image.commons.dao.spring.modele.Document;
import fr.urssaf.image.commons.dao.spring.support.MyHibernateDaoSupport;
import fr.urssaf.image.commons.dao.spring.util.CriteriaUtil;

/**
 * 
 * @author Bertrand BARAULT
 * 
 */
@Repository
@Transactional(propagation = Propagation.SUPPORTS)
@SuppressWarnings({"PMD.TooManyMethods","PMD.ConsecutiveLiteralAppends"})
public class DocumentDaoImpl extends MyHibernateDaoSupport<Document, Integer>
		implements DocumentDao {
	
	private static final Logger LOGGER = Logger.getLogger(DocumentDaoImpl.class);
	
	private static final String AUTEUR = "auteur";
	private static final String TITRE = "titre";
	private static final String DATE = "date";
	
	private static final String UNCHECKED = "unchecked";
	

	/**
	 * @param sessionFactory
	 */
	@Autowired
	public DocumentDaoImpl(
			@Qualifier("sessionFactory") SessionFactory sessionFactory) {
		super(sessionFactory, Document.class);
	}

	@Override
	public List<Document> find(int firstResult, int maxResult, String order,
			boolean inverse) {

		Criteria criteria = this.getCriteria();
		criteria.setFetchMode(AUTEUR, FetchMode.JOIN);

		return this.find(criteria, firstResult, maxResult, order, inverse);
	}

	@Override
	public void scroll() {

		Criteria criteria = this.getCriteria();

		ScrollableResults scroll = criteria.scroll();

		while (scroll.next()) {

			Document doc = (Document) scroll.get(0);

			if (scroll.getRowNumber() % 10000 == 0) {
				LOGGER.debug(scroll.getRowNumber());
			}
			this.getSession().evict(doc);

		}
		scroll.close();

	}

	@Override
	public List<Document> findOrderBy(int firstResult, int maxResult,
			String table, String order, boolean inverse) {

		Criteria criteria = this.getCriteria();
		CriteriaUtil.order(criteria.createCriteria(table, Criteria.LEFT_JOIN),
				order, inverse);

		return this.find(criteria, firstResult, maxResult);
	}

	@Override
	public List<Document> findEtats(int firstResult, int maxResult,
			String order, boolean inverse) {

		List<Document> documents = this.find(firstResult, maxResult, order,
				inverse);

		for (Document document : documents) {
			Hibernate.initialize(document.getEtats());
		}

		return documents;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void saveSQL(Document document) {

		StringBuffer sql = new StringBuffer(100);
		sql.append("insert into document ");
		sql.append("(id_auteur, titre, date) ");
		sql.append("values (:auteur, :titre, :date) ");

		Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(TITRE, document.getTitre());
		query.setParameter(DATE, document.getDate());
		if (document.getAuteur() == null) {
		   query.setParameter(AUTEUR, null);
		} else {
		   query.setParameter(AUTEUR, document.getAuteur().getId());
		}

		query.executeUpdate();

		BigInteger idUnique = (BigInteger) this.getSession().createSQLQuery(
				"select LAST_INSERT_ID()").list().iterator().next();

		document.setId(idUnique.intValue());
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void updateSQL(Document document) {

		StringBuffer sql = new StringBuffer(100);
		sql.append("update document ");
		sql.append("set ");
		sql.append("id_auteur =:auteur, ");
		sql.append("titre =:titre, ");
		sql.append("date =:date ");
		sql.append("where id =:id ");

		Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(TITRE, document.getTitre());
		query.setParameter(DATE, document.getDate());
		if (document.getAuteur() == null)
		{
		   query.setParameter(AUTEUR, null);
		}
		else
		{
		   query.setParameter(AUTEUR, document.getAuteur().getId());
		}
		query.setParameter("id", document.getId());

		query.executeUpdate();

	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void deleteSQL(Document document) {

		String sql = "delete from document where id =:id";
	      
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter("id", document.getId());

		query.executeUpdate();

	}

	@Override
	@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
	public Document findSQL(Integer idUnique) {

	   Document result = null;
	   
		StringBuffer sql = new StringBuffer(150);
		sql.append("select doc.id,doc.titre,doc.date,doc.id_auteur,aut.nom ");
		sql.append("from document doc ");
		sql.append("left outer join auteur aut on doc.id_auteur=aut.id ");
		sql.append("where doc.id=:id ");

		Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter("id", idUnique);

		Object[] obj = (Object[]) query.uniqueResult();

		if (obj != null)
		{
		   result = this.getDocument(obj);
		}
		
		return result;
		
	}

	private Document getDocument(Object[] obj) {

		Document document = new Document();
		document.setId((Integer) obj[0]);
		document.setTitre((String) obj[1]);
		document.setDate((Date) obj[2]);

		if (obj[4] != null) {

			Auteur auteur = new Auteur();
			auteur.setId((Integer) obj[3]);
			auteur.setNom((String) obj[4]);

			document.setAuteur(auteur);

		}

		return document;
	}

	@Override
	@SuppressWarnings(UNCHECKED)
	public List<Document> findSQL(int firstResult, int maxResults,
			String order, boolean inverse) {

	   String inv = booleanToInverse(inverse);

		StringBuffer sql = new StringBuffer(150);
		sql.append("select doc.id,doc.titre,doc.date,doc.id_auteur,aut.nom ");
		sql.append("from document doc ");
		sql.append("left outer join auteur aut on doc.id_auteur=aut.id ");

		sql.append("order by doc." + order + " " + inv + " ");
		sql.append("limit " + firstResult + "," + maxResults);
		
		Query query = this.getSession().createSQLQuery(sql.toString());

		List<Object[]> objects = (List<Object[]>) query.list();

		List<Document> documents = new ArrayList<Document>();

		for (Object[] obj : objects) {
			documents.add(this.getDocument(obj));
		}

		return documents;
	}

	@Override
	@SuppressWarnings(UNCHECKED)
	public List<Document> findHQL(int firstResult, int maxResults,
			String order, boolean inverse) {

	   String inv = booleanToInverse(inverse);

		StringBuffer hql = new StringBuffer(100);
		// l'ordre des éléments est important
		hql.append("select aut,doc ");
		hql.append("from Document doc left join doc.auteur aut ");
		hql.append("order by doc." + order + " " + inv);

		Query query = this.getSession().createQuery(hql.toString());

		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);

		query.setResultTransformer(Criteria.ROOT_ENTITY);

		return (List<Document>) query.list();
	}

	@Override
	public Document findHQL(Integer idUnique) {

		String sql = "from Document where id=:id";
	   
		Query query = this.getSession().createQuery(sql);
		query.setParameter("id", idUnique);

		return (Document) query.uniqueResult();
	}

	@SuppressWarnings(UNCHECKED)
	@Override
	public List<Document> findByCriteria() {

		Criteria criteria = this.getSession().createCriteria(Document.class);
		criteria.add(Restrictions.gt(DATE, new Date()));

		Criteria criteriaAuteur = criteria.createCriteria(AUTEUR);
		criteriaAuteur.add(Restrictions.in("nom", new Object[] { "auteur 1",
				"auteur 0" }));

		criteria.addOrder(Order.asc(TITRE));

		return criteria.list();
	}

	@SuppressWarnings(UNCHECKED)
	@Override
	public List<Document> findBySQL() {

		StringBuffer sql = new StringBuffer(200);
		sql.append("select doc.id,doc.titre,doc.date,doc.id_auteur,aut.nom ");
		sql.append("from document doc ");
		sql.append("left outer join auteur aut on doc.id_auteur=aut.id ");
		sql.append("where doc.date > :date ");
		sql.append("and aut.nom in (:nom) ");

		sql.append("order by doc.titre asc ");

		Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(DATE, new Date());
		query.setParameterList("nom", new Object[] { "auteur 1", "auteur 0" });

		List<Object[]> objects = (List<Object[]>) query.list();

		List<Document> documents = new ArrayList<Document>();

		for (Object[] obj : objects) {
			documents.add(this.getDocument(obj));
		}

		return documents;
	}

	@SuppressWarnings(UNCHECKED)
	@Override
	public List<Document> findByHQL() {

		StringBuffer hql = new StringBuffer(150);
		hql.append("select aut,doc ");
		hql.append("from Document doc left join doc.auteur aut ");
		hql.append("where date > :date ");
		hql.append("and aut.nom in (:nom) ");
		hql.append("order by doc.titre asc");

		Query query = this.getSession().createQuery(hql.toString());
		query.setParameter(DATE, new Date());
		query.setParameterList("nom", new Object[] { "auteur 1", "auteur 0" });

		query.setResultTransformer(Criteria.ROOT_ENTITY);

		return query.list();
	}

	@SuppressWarnings(UNCHECKED)
	@Override
	public List<Document> findByCriteriaWithEtats() {

		Criteria criteria = this.getSession().createCriteria(Document.class);
		Criteria criteriaEtat = criteria.createCriteria("etats",
				Criteria.LEFT_JOIN);
		criteriaEtat.add(Restrictions.ne("etat", "close"));
		criteria.addOrder(Order.asc(TITRE));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria.list();

	}

	@SuppressWarnings(UNCHECKED)
	@Override
	public List<Document> findByHQLWithEtats() {

		StringBuffer hql = new StringBuffer(120);
		hql.append("select doc ");
		hql.append("from Document doc join fetch doc.etats etat ");
		hql.append("where etat.etat != 'close'");
		hql.append("order by doc.titre asc");

		Query query = this.getSession().createQuery(hql.toString());

		query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return query.list();
	}
	
	
	private String booleanToInverse(boolean inverse)
	{
	   String inv;
      if (inverse)
      {
         inv = "desc";
      }
      else
      {
         inv = "asc";
      }
      return inv;
	}

}
