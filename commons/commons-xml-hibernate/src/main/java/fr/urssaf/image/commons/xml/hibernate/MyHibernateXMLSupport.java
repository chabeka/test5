package fr.urssaf.image.commons.xml.hibernate;

import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.hibernate.EntityMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MyHibernateXMLSupport<T> {

   private final SessionFactory sessionFactory;

   private final Class<T> table;
   
   private static final int FLUSH = 20;

   public MyHibernateXMLSupport(Class<T> table, SessionFactory sessionFactory) {
      this.table = table;
      this.sessionFactory = sessionFactory;
   }

   public void writeAll(String file, String balise) throws IOException {

      org.dom4j.Document doc = DocumentHelper.createDocument();
      OutputFormat format = OutputFormat.createPrettyPrint();
      XMLWriter writer = new XMLWriter(new FileWriter(file), format);
      Element root = doc.addElement(balise);
      writer.writeOpen(root);

      Session session = sessionFactory.openSession();
      Session dom4jSession = session.getSession(EntityMode.DOM4J);

      ScrollableResults docs = dom4jSession.createCriteria(table).scroll();

      int index = 0;
      while (docs.next()) {

         Element document = (Element) docs.get(0);

         writer.write(document);
         dom4jSession.evict(document);

         if (index % FLUSH == 0) {
            writer.flush();
         }

         index++;
      }

      session.close();

      writer.writeClose(root);
      writer.close();

   }
}
