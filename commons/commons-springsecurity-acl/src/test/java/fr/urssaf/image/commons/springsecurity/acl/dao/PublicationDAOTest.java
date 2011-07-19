package fr.urssaf.image.commons.springsecurity.acl.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.urssaf.image.commons.springsecurity.acl.model.Person;
import fr.urssaf.image.commons.springsecurity.acl.model.Publication;
import fr.urssaf.image.commons.springsecurity.acl.security.AuthenticateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-acl.xml",
      "/applicationContext-security.xml" })
public class PublicationDAOTest {

   @Autowired
   private PublicationDAO dao;

   @After
   public void after() {
      AuthenticateUtils.logout();
   }

   @Test
   public void findById() {
      AuthenticateUtils.authenticateReader();
      Publication publication = dao.find(1);

      assertPublication(publication, 1, "Semantique RDF");

   }

   @Test
   public void findByAuthor() {
      AuthenticateUtils.authenticateReader();
      List<Publication> publications = dao.findByAuthor(1);

      assertPublication(publications.get(0), 1, "Semantique RDF");
      assertPublication(publications.get(1), 2, "HTML5 pour les web designers");

      assertEquals("nombre inattendu de publications", 2, publications.size());

   }

   @Test
   public void find() {
      AuthenticateUtils.authenticateReader();
      List<Publication> publications = dao.find();

      assertPublication(publications.get(0), 1, "Semantique RDF");
      assertPublication(publications.get(1), 2, "HTML5 pour les web designers");

      assertPublication(publications.get(2), 3, "Formatage des donnees en PHP");
   }

   private static void assertPublication(Publication publication,
         Integer idExpected, String titleExpected) {

      assertEquals("id non attendu", idExpected, publication.getId());
      assertEquals("title non attendu", titleExpected, publication.getTitle());

   }

   @Test
   @Transactional
   public void save() {
      AuthenticateUtils.authenticateAuthor();
      String title = "new publication";

      Person author = new Person();
      author.setId(1);
      Publication publication = new Publication(author, title);

      dao.save(publication);

      publication = dao.findById(4);

      assertPublication(publication, 4, title);
   }

   @Test
   @Transactional
   public void update() {
      AuthenticateUtils.authenticateEditor();
      String title = "new publication";

      Publication publication = dao.findById(1);
      publication.setTitle(title);

      dao.update(publication);

      publication = dao.findById(1);

      assertPublication(publication, 1, title);

   }
}
