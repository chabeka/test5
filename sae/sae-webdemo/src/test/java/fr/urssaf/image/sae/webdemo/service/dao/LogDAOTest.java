package fr.urssaf.image.sae.webdemo.service.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateQueryException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.webdemo.resource.Dir;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-test.xml")
@SuppressWarnings("PMD")
public class LogDAOTest {

   private static final int FIRST_RESULT = 10;
   private static final int MAX_RESULTS = 100;
   private static final int RESULTS = 100;

   @Autowired
   private LogDAO service;

   private final static String[] COLONNES = new String[] { "idseq",
         "horodatage", "occurences", "probleme", "action", "infos" };

   @Test
   public void findSuccess() {

      for (String sort : COLONNES) {

         assertFind(sort, Dir.DESC);
         assertFind(sort, Dir.ASC);
      }

      assertFind(null, null);

   }

   @Test
   public void findSuccess_nosort() {

      assertFind(null, null);

   }

   private void assertFind(String sort, Dir dir) {

      assertEquals("la colonne '" + sort + "' n'est pas triable", RESULTS,
            service.find(FIRST_RESULT, MAX_RESULTS, sort, dir).size());
   }

   @Test(expected = HibernateQueryException.class)
   public void findFailure() {

      service.find(FIRST_RESULT, MAX_RESULTS, "inconnu", Dir.DESC);

   }
}
