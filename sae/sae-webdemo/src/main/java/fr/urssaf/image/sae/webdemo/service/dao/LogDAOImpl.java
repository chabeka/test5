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
 * DAO d'implémentation du service {@link LogDAO}<br>
 * <br>
 * Implémentation Hibernate<br>
 * La classe hérite de {@link HibernateDaoSupport}
 * 
 * 
 */
@Repository
public class LogDAOImpl extends HibernateDaoSupport implements LogDAO {

   /**
    * initialisation de SessionFactory<br>
    * <br>
    * L'instanciation du session factory s'effectue dans le fichier
    * <code>applicationContext-sessionFactory</code>
    * 
    * <pre>
    * &lt;bean id="sessionFactory" class="...">
    *       ...
    * &lt/bean>
    * </pre>
    * 
    * @param sessionFactory
    *           sessionfactory
    */
   @Autowired
   public LogDAOImpl(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
      super();
      this.setSessionFactory(sessionFactory);
   }

   /**
    * {@inheritDoc}
    */
   @SuppressWarnings("unchecked")
   @Override
   public final List<Log> find(int firstResult, int maxResults, String order,
         Dir dir) {

      Criteria criteria = createCriteria();

      criteria.setFirstResult(firstResult);
      criteria.setMaxResults(maxResults);

      if (order != null) {

         if (dir == Dir.ASC) {
            criteria.addOrder(Order.asc(order));
         } else if (dir == Dir.DESC) {
            criteria.addOrder(Order.desc(order));
         }

      }

      return criteria.list();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final int count() {

      Criteria criteria = createCriteria();

      criteria.setProjection(Projections.rowCount());

      return ((Integer) criteria.uniqueResult());
   }

   private Criteria createCriteria() {
      return this.getSession().createCriteria(Log.class);
   }

}
