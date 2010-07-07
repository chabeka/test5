package fr.urssaf.image.commons.dao.spring.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LazyInitializationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.urssaf.image.commons.dao.spring.dao.DocumentDao;
import fr.urssaf.image.commons.dao.spring.modele.Document;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-service.xml")
@SuppressWarnings({"PMD.JUnitAssertionsShouldIncludeMessage","PMD.ConsecutiveLiteralAppends"})
public class DocumentLazyLoadingTest {

	private static final Logger LOG= Logger
			.getLogger(DocumentLazyLoadingTest.class);

	@Autowired
	private DocumentDao documentDao;

	@Test
	public void getFailure() {

		Document document = documentDao.get(3);
		try {
			document.getAuteur().getNom();
			fail("une erreur Lazy doit être levé");
		} catch (LazyInitializationException e) {
			assertEquals("could not initialize proxy - no Session", e
					.getMessage());
		}

	}

	@Test
	@Transactional(propagation = Propagation.SUPPORTS)
	public void getInSession() {

		Document document = documentDao.get(3);
		assertNotNull(document.getAuteur().getNom());

	}

	@Test
	public void getCollection() {

		Document document = documentDao.get(3);
		try {
			
			assertCollection(document);
			fail("une erreur Lazy doit être levé");

		} catch (LazyInitializationException e) {

			StringBuffer message = new StringBuffer(140);
			message.append("failed to lazily initialize ");
			message.append("a collection of role: ");
			message.append("fr.urssaf.image.commons.dao.spring.modele.Document.etats, ");
			message.append("no session or session was closed");

			assertEquals(message.toString(), e.getMessage());
		}

	}

	@Test
	@Transactional(propagation = Propagation.SUPPORTS)
	public void getCollectionInSession() {

		Document document = documentDao.get(3);
		LOG.debug(document.getEtats().size());
		
		assertCollection(document);

	}
	
	@Test
	public void getCollectionByHQL() {
		
		List<Document> documents = documentDao.findByHQLWithEtats();
		assertCollection(documents);
	}
	
	@Test
	public void getCollectionByCriteria() {
		
		List<Document> documents = documentDao.findByCriteriaWithEtats();
		assertCollection(documents);
	}
	
	private void assertCollection(Document document){
		assertEquals(2, document.getEtats().size());
	}
	
	private void assertCollection(List<Document> documents) {

		assertEquals(3, documents.size());

		//check doc
		assertEquals("titre 1", documents.get(0).getTitre());
		assertEquals("titre 2", documents.get(1).getTitre());
		assertEquals("titre 3", documents.get(2).getTitre());
		
		//check etats
		assertEquals(1, documents.get(0).getEtats().size());
		assertEquals(2, documents.get(1).getEtats().size());
		assertEquals(2, documents.get(2).getEtats().size());
		
	}
	

}
