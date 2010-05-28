package fr.urssaf.image.commons.webservice.spring.exemple.service.document;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import fr.urssaf.image.commons.webservice.spring.exemple.modele.Document;

@WebService
public interface DocService {

	@WebMethod
	public List<Document> allDocuments();
}
