package fr.urssaf.image.commons.webservice.spring.exemple.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import fr.urssaf.image.commons.webservice.spring.exemple.modele.Document;

@WebService
public interface DocumentService {

	@WebMethod
	public List<Document> allDocuments();
}
