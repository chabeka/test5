package fr.urssaf.image.sae.webdemo.service.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import fr.urssaf.image.sae.webdemo.modele.Log;
import fr.urssaf.image.sae.webdemo.resource.Dir;

/**
 * DAO des traces d'explotation<br>
 * 
 * 
 */
@Repository("logDAO")
public class LogDAOImpl extends HibernateDaoSupport implements LogDAO {

   /**
    * intitialisation de SessionFactory
    * 
    * @param sessionFactory
    *           sessionfactory
    */
   @Autowired
   public LogDAOImpl(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
      super();
      this.setSessionFactory(sessionFactory);
   }

   @SuppressWarnings("unchecked")
   @Override
   public final List<Log> find(int firstResult, int maxResults, String order,
         Dir dir) {

      Criteria criteria = createCriteria();

      criteria.setFirstResult(firstResult);
      criteria.setMaxResults(maxResults);

      switch (dir) {
      case ASC:
         criteria.addOrder(Order.asc(order));
         break;
      case DESC:
         criteria.addOrder(Order.desc(order));
         break;
      }

      return criteria.list();
   }

   @Override
   public int count() {

      Criteria criteria = createCriteria();

      criteria.setProjection(Projections.rowCount());

      return ((Integer) criteria.uniqueResult());
   }

   private Criteria createCriteria() {
      return this.getSession().createCriteria(Log.class);
   }

}
