package fr.urssaf.image.commons.dao.spring.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.hibernate.PropertyValueException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.urssaf.image.commons.dao.spring.dao.DocumentDao;
import fr.urssaf.image.commons.dao.spring.modele.Document;
import fr.urssaf.image.commons.dao.spring.service.DocumentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-service.xml")
public class DocumentServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private DocumentService documentService;

	@Autowired
	private DocumentDao documentDao;

	@Test
	@Transactional
	public void save() {

		List<String> titres = new ArrayList<String>();
		titres.add("titre test 1");
		titres.add("titre test 2");
		titres.add("titres test 3");

		List<Document> documents = documentService.save(titres);

		assertDocument(documents.get(0).getId(), titres.get(0));
		assertDocument(documents.get(1).getId(), titres.get(1));
		assertDocument(documents.get(1).getId(), titres.get(1));

	}
	
	@Test
	public void saveFailure() {

		Map<String,Date> docs = new HashMap<String,Date>();
		docs.put("titre test 1",new Date());
		docs.put("titre test 2",null);
		docs.put("titres test 3",new Date());

		try {
			documentService.save(docs);
			fail("le test doit �tre un �chec");
		}catch(PropertyValueException e){
			assertEquals("l'entit� doit �tre document", Document.class
					.getCanonicalName(), e.getEntityName());
			assertEquals("la propri�t� doit �tre date", "date", e
					.getPropertyName());
		}

		
	}

	private void assertDocument(int id, String titre) {
		Document doc = documentDao.find(id);
		assertEquals(titre, doc.getTitre());
	}
}