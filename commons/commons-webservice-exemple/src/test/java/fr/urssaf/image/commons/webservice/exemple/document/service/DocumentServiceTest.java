package fr.urssaf.image.commons.webservice.exemple.document.service;

import static org.junit.Assert.assertEquals;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import fr.urssaf.image.commons.webservice.exemple.document.modele.Document;
import fr.urssaf.image.commons.webservice.exemple.document.service.DocumentService;
import fr.urssaf.image.commons.webservice.exemple.document.service.DocumentServiceImpl;

public class DocumentServiceTest {

	private DocumentService service;

	protected static final Logger log = Logger
			.getLogger(DocumentServiceTest.class);

	public DocumentServiceTest() {
		service = new DocumentServiceImpl();
	}

	@Test
	public void allDocuments() throws RemoteException {

		List<Document> documents = service.allDocuments();

		assertEquals(3, documents.size());

		assertEquals("titre 0", documents.get(0).getTitre());
		assertEquals("titre 1", documents.get(1).getTitre());
		assertEquals("titre 2", documents.get(2).getTitre());
	}
	
	@Test
	public void getDocument() throws RemoteException {

		Document document = service.get(0);
		assertEquals("titre 0", document.getTitre());
		
	}
	
	@Test
	public void saveDocument() throws RemoteException {

		Document document = new Document();
		document.setTitre("titre test");
		service.save(document);
		
		List<Document> documents = service.allDocuments();
		assertEquals(4, documents.size());
		document = service.get(3);
		assertEquals("titre test", document.getTitre());
		
	}
}