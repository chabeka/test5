package fr.urssaf.image.commons.controller.spring3.jndi.exemple.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.sql.Driver;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.urssaf.image.commons.controller.spring3.jndi.exemple.modele.Document;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContextTest.xml")
public class DocumentDaoTest {

   private static final Logger LOG = Logger.getLogger(DocumentDaoTest.class);

   @Autowired
   private DocumentDao documentDao;

   @BeforeClass
   public static void init() throws ConfigurationException,
         BeanInstantiationException, ClassNotFoundException,
         IllegalStateException, NamingException {

      // creation d'un dataSource
      PropertiesConfiguration jdbc = new PropertiesConfiguration(
            "jdbc.properties");
      String password = jdbc.getString("jdbc.password");
      String url = jdbc.getString("jdbc.url");
      String username = jdbc.getString("jdbc.username");
      Driver driverClassName = (Driver) BeanUtils.instantiate(Class
            .forName(jdbc.getString("jdbc.driverClassName")));

      DataSource dataSource = new SimpleDriverDataSource(driverClassName, url,
            username, password);

      // activation du JNDI pour la dateSource
      SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
      builder.bind("java:comp/env/jdbc/mysql", dataSource);
      builder.activate();
   }

   @Test
   public void list() {
      List<Document> documents = documentDao.allDocuments();
      assertFalse("la liste des documents doit être non vide", documents
            .isEmpty());

      for (Document document : documents) {
         LOG.debug(document.getId() + ":" + document.getTitre());
      }

      assertEquals("le nombre d'enregistrement est incorrect", 5, documents
            .size());
   }

   @Test
   @Transactional
   public void save() {

      Date date = new Date();
      String titre = "titre test";

      Document document = new Document(titre, date);
      documentDao.save(document);

      Document documentBase = documentDao.find(document.getId());

      assertEquals("titre est incorrecte", titre, documentBase.getTitre());
      assertEquals("date est incorrecte", date, documentBase.getDate());

   }

}
