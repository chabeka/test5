package fr.urssaf.image.commons.dao.spring.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import fr.urssaf.image.commons.dao.spring.jdbc.DocumentDao;
import fr.urssaf.image.commons.dao.spring.modele.Document;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-service.xml")
@TransactionConfiguration(transactionManager = "txManager")
@SuppressWarnings({"PMD.JUnitAssertionsShouldIncludeMessage"})
public class DocumentJdbcDaoTest {

	@Autowired
	private DocumentDao documentDao;

	@Test
	public void find() {
		Document document = documentDao.find(3);

		assertEquals("titre 3", document.getTitre());
		assertEquals("auteur 1", document.getAuteur().getNom());
	}

	@Test
	@Transactional
	public void save() {

		Document document = new Document();
		document.setTitre("titre test");
		document.setDate(new Date());

		documentDao.save(document);

		document = documentDao.find(document.getId());
		assertNotNull("la transaction doit être un succès", document);
		assertEquals("titre test", document.getTitre());
	}

	@Test
	public void saveFailure() {

		Document document = new Document();
		document.setTitre("titre test");
		try {
			documentDao.save(document);
			fail("le test doit être un échec");
		} catch (DataAccessException e) {
			
			assertEquals(5, documentDao.count());
			assertEquals("Column 'date' cannot be null", e.getCause()
					.getMessage());

		}
		
		

	}

}
