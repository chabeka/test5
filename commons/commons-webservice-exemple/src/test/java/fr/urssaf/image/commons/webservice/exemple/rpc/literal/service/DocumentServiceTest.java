package fr.urssaf.image.commons.webservice.exemple.rpc.literal.service;

import static org.junit.Assert.assertEquals;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import fr.urssaf.image.commons.webservice.exemple.rpc.literal.modele.Document;

public class DocumentServiceTest {

	private DocumentService service;
	
	protected static final Logger log = Logger.getLogger(DocumentServiceTest.class);
	
	public DocumentServiceTest(){
		service = new DocumentServiceImpl();
	}
	
	@Test
	public void allDocuments() throws RemoteException{
		
		List<Document> documents = service.allDocuments();
		
		assertEquals(3, documents.size());

		assertEquals("titre 0", documents.get(0).getTitre());
		assertEquals("titre 1", documents.get(1).getTitre());
		assertEquals("titre 2", documents.get(2).getTitre());
	}
}
