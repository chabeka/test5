package fr.urssaf.image.commons.dao.spring.exemple.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.commons.dao.spring.exemple.modele.Document;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-service.xml")
@SuppressWarnings("PMD")
public class DocumentFindDaoTest {

	@Autowired
	private DocumentDao documentDao;

	private static final int FIRST_RESULT = 1; // c'est à dire à partir du 2ème résultat

	private static final int MAX_RESULTS = 3;

	private static final String ORDER = "titre";

	private static final boolean INVERSE = true;


	@Test
	public void list() {
		
	   List<Document> documents = documentDao.find(
		      FIRST_RESULT, MAX_RESULTS, ORDER, INVERSE);
		
	   assertList(documents);
	   
	}

	@Test
	public void listSQL() {
		
	   List<Document> documents = documentDao.findSQL(
	         FIRST_RESULT, MAX_RESULTS, ORDER, INVERSE);

		assertList(documents);
	}

	@Test
	public void listHQL() {
		
	   List<Document> documents = documentDao.findHQL(
		      FIRST_RESULT, MAX_RESULTS, ORDER, INVERSE);

		assertList(documents);
		
	}

	@Test
	public void listEtats() {
		
	   List<Document> documents = documentDao.findEtats(
	         FIRST_RESULT, MAX_RESULTS, ORDER, INVERSE);
		
	   assertList(documents);
		
		//check etats
		assertEquals(0, documents.get(0).getEtats().size());
		assertEquals(2, documents.get(1).getEtats().size());
		assertEquals(3, documents.get(2).getEtats().size());
	}
	
	
	private void assertList(List<Document> documents) {

	   assertEquals(3, documents.size());

      //check doc
      assertEquals("titre 4", documents.get(0).getTitre());
      assertEquals("titre 3", documents.get(1).getTitre());
      assertEquals("titre 2", documents.get(2).getTitre());
      
      //check auteur
      assertEquals("auteur 2", documents.get(0).getAuteur().getNom());
      assertEquals("auteur 1", documents.get(1).getAuteur().getNom());
      assertEquals("auteur 4", documents.get(2).getAuteur().getNom());
      
   }
	
	@Test
	public void listOrderByAuteur() {
		
	   List<Document> documents = documentDao.findOrderBy(
		      FIRST_RESULT, MAX_RESULTS, "auteur","nom", INVERSE);
		
		assertEquals(3, documents.size());
		
		assertEquals("titre 1", documents.get(0).getTitre());
		assertEquals("titre 4", documents.get(1).getTitre());
		assertEquals("titre 3", documents.get(2).getTitre());
		
		
		assertEquals("auteur 3", documents.get(0).getAuteur().getNom());
		assertEquals("auteur 2", documents.get(1).getAuteur().getNom());
		assertEquals("auteur 1", documents.get(2).getAuteur().getNom());
		
	}
	
	
	@Test
   public void listOrderByAuteur2() {
      List<Document> documents = documentDao.findOrderBy(
            FIRST_RESULT, MAX_RESULTS,"auteur","nom", false);
      
      assertEquals(3, documents.size());
      
      assertEquals("titre 3", documents.get(0).getTitre());
      assertEquals("titre 4", documents.get(1).getTitre());
      assertEquals("titre 1", documents.get(2).getTitre());
      
      assertEquals("auteur 1", documents.get(0).getAuteur().getNom());
      assertEquals("auteur 2", documents.get(1).getAuteur().getNom());
      assertEquals("auteur 3", documents.get(2).getAuteur().getNom());
      
   }
	
	@Test
	public void findByCriteria() {
		List<Document> documents = documentDao.findByCriteria();
		
		assertList2(documents);
		
	}
	
	@Test
	public void findBySQL() {
		List<Document> documents = documentDao.findBySQL();
		
		assertList2(documents);
	}
	
	@Test
	public void findByHQL() {
		List<Document> documents = documentDao.findByHQL();
		
		assertList2(documents);
	}
	
	private void assertList2(List<Document> documents) {
		
		assertEquals(2, documents.size());
		
		assertEquals("titre 3", documents.get(0).getTitre());
		assertEquals("titre 5", documents.get(1).getTitre());
		
		
		assertEquals("auteur 1", documents.get(0).getAuteur().getNom());
		assertEquals("auteur 0", documents.get(1).getAuteur().getNom());
	}
}
