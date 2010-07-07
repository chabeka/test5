package fr.urssaf.image.commons.dao.spring.test;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.commons.dao.spring.dao.DocumentDao;
import fr.urssaf.image.commons.dao.spring.modele.Document;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-service.xml")
public class DocumentScrollDaoTest {

	private static final Logger LOG = Logger
			.getLogger(DocumentScrollDaoTest.class);

	@Autowired
	private DocumentDao documentDao;

	@Test(timeout=15000)
	public void scroll(){
		documentDao.scroll();
	}
	
	@Test(timeout=15000)
	public void list() {
		List<Document> documents = documentDao.find(150000, 1,
				"titre", true);
		LOG.debug(documents.size());
	}
}
