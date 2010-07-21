package fr.urssaf.image.commons.controller.spring3.jndi.exemple.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import fr.urssaf.image.commons.controller.spring3.jndi.exemple.modele.Document;

@Repository
public class DocumentDaoImpl extends HibernateDaoSupport implements DocumentDao {
   
   @Autowired
   public DocumentDaoImpl(
         @Qualifier("sessionFactory") SessionFactory sessionFactory) {
      super();
      this.setSessionFactory(sessionFactory);
   }

   @SuppressWarnings("unchecked")
   public List<Document> allDocuments() {
      Criteria criteria = this.getSession().createCriteria(Document.class);
      return criteria.list();
   }
}
