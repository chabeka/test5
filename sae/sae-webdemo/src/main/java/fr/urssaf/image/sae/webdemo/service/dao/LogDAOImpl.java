package fr.urssaf.image.sae.webdemo.service.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import fr.urssaf.image.sae.webdemo.modele.Log;

/**
 * DAO des traces d'explotation<br>
 * 
 * 
 */
@Repository("logDAO")
public class LogDAOImpl extends HibernateDaoSupport implements LogDAO {

   /**
    * intitialisation de SessionFactory
    * @param sessionFactory sessionfactory
    */
   @Autowired
   public LogDAOImpl(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
      super();
      this.setSessionFactory(sessionFactory);
   }

   @SuppressWarnings("unchecked")
   @Override
   public final List<Log> find() {

      Criteria criteria = this.getSession().createCriteria(Log.class);

      return criteria.list();
   }

//   private Log factory(long idseq, Date horodatage, int occurences,
//         String probleme, String action, String infos) {
//
//      Log log = new Log();
//      log.setAction(action);
//      log.setHorodatage(horodatage);
//      log.setIdseq(idseq);
//      log.setInfos(infos);
//      log.setOccurences(occurences);
//      log.setProbleme(probleme);
//
//      return log;
//   }
}
