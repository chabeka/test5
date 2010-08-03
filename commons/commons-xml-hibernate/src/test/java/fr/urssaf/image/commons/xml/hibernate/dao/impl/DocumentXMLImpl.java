package fr.urssaf.image.commons.xml.hibernate.dao.impl;

import java.io.IOException;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import fr.urssaf.image.commons.xml.hibernate.MyHibernateXMLSupport;
import fr.urssaf.image.commons.xml.hibernate.dao.DocumentXML;
import fr.urssaf.image.commons.xml.hibernate.modele.Document;

@Repository
public class DocumentXMLImpl extends MyHibernateXMLSupport<Document> implements
      DocumentXML {

   @Autowired
   public DocumentXMLImpl(
         @Qualifier("sessionFactory") SessionFactory sessionFactory) {
      super(Document.class, sessionFactory);
   }

   @Override
   public void writeAllDocument(String file) throws IOException {

      this.writeAll(file, "documents");

   }

}
