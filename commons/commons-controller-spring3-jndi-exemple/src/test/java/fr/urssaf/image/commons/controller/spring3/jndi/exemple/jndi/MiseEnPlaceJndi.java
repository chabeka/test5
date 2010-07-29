package fr.urssaf.image.commons.controller.spring3.jndi.exemple.jndi;

import java.sql.Driver;

import javax.sql.DataSource;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

public class MiseEnPlaceJndi {

   @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
   public MiseEnPlaceJndi()
   {
      try
      {

         // ----------------------------------------------------------------
         // Initialise
         // ----------------------------------------------------------------

         SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();

         // ----------------------------------------------------------------
         // title/default
         // ----------------------------------------------------------------

         // Valeur
         String titre = "Spring Web Exemple with JNDI";

         // Enregistrement JDNI
         builder.bind("java:comp/env/title/default", titre);

         // ----------------------------------------------------------------
         // jdbc/mysql
         // ----------------------------------------------------------------

         // Lecture des paramètres JDBC depuis un fichier properties
         PropertiesConfiguration jdbc = new PropertiesConfiguration("jdbc.properties");
         String driverClassName = jdbc.getString("jdbc.driverClassName");
         String url = jdbc.getString("jdbc.url");
         String username = jdbc.getString("jdbc.username");
         String password = jdbc.getString("jdbc.password");

         // Instanciation des objets
         Driver driver = (Driver) BeanUtils.instantiate(
               Class.forName(driverClassName));
         DataSource dataSource = new SimpleDriverDataSource(
               driver, url, username, password);

         // Enregistrement JDNI
         builder.bind("java:comp/env/jdbc/mysql", dataSource);

         // ----------------------------------------------------------------
         // Termine
         // ----------------------------------------------------------------

         builder.activate();

      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }
   
}
