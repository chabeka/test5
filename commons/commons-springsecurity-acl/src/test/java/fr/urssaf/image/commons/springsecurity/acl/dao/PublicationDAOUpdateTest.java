package fr.urssaf.image.commons.springsecurity.acl.dao;

import static fr.urssaf.image.commons.springsecurity.acl.dao.util.AssertPublication.assertPublication;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.urssaf.image.commons.springsecurity.acl.model.Publication;
import fr.urssaf.image.commons.springsecurity.acl.security.AuthenticateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-acl.xml",
      "/applicationContext-security.xml" })
@SuppressWarnings("PMD.MethodNamingConventions")
public class PublicationDAOUpdateTest {

   private static final String TEST_TITLE = "new publication";

   @Autowired
   private PublicationDAO dao;

   @After
   public void after() {
      AuthenticateUtils.logout();
   }

   @Test
   @Transactional
   public void update_success_editor() {
      AuthenticateUtils.authenticateEditor();

      update(1, TEST_TITLE);

      Publication publication = dao.findById(1);

      assertPublication(publication, 1, TEST_TITLE);

   }
   
   @Test
   @Transactional
   public void update_success_author() {
      AuthenticateUtils.authenticate(2, "ROLE_AUTHOR");

      update(3, TEST_TITLE);

      Publication publication = dao.findById(3);

      assertPublication(publication, 3, TEST_TITLE);

   }

   @Test(expected = AccessDeniedException.class)
   @Transactional
   public void update_failure_accessDenied_reader() {
      AuthenticateUtils.authenticateReader();

      update(1, TEST_TITLE);

   }

   @Test(expected = AccessDeniedException.class)
   @Transactional
   public void update_failure_accessDenied_author() {
      AuthenticateUtils.authenticate(1, "ROLE_AUTHOR");

      update(3, TEST_TITLE);

   }

   private void update(int identity, String title) {

      Publication publication = dao.findById(identity);
      publication.setTitle(title);

      dao.update(publication);

   }
}
