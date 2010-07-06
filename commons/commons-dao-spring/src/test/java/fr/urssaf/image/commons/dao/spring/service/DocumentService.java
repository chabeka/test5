package fr.urssaf.image.commons.dao.spring.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import fr.urssaf.image.commons.dao.spring.modele.Document;

public interface DocumentService {

	List<Document> save(List<String> titres);
	
	List<Document> save(Map<String,Date> documents);
}
