package fr.urssaf.image.commons.webservice.wssecurity.spring.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import fr.urssaf.image.commons.webservice.wssecurity.spring.modele.Document;

@WebService
public interface DocumentService {

	@WebMethod
	public List<Document> allDocuments();
	
	@WebMethod
	public Document getDocument(int id);
	
	@WebMethod
	public void save(Document document);
	
}
