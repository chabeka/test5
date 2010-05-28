package fr.urssaf.image.commons.dao.spring.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.urssaf.image.commons.dao.spring.dao.DocumentDao;
import fr.urssaf.image.commons.dao.spring.modele.Document;
import fr.urssaf.image.commons.dao.spring.service.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	private DocumentDao documentDao;
	
	@Override
	@Transactional
	public List<Document> save(List<String> titres) {

		List<Document> documents = new ArrayList<Document>();
		
		Date date = new Date();
		
		for (String titre : titres) {

			Document document = new Document();
			document.setTitre(titre);
			document.setDate(date);
			documents.add(document);

			documentDao.save(document);
			
		}
		
		return documents;

	}

	@Override
	@Transactional
	public List<Document> save(Map<String, Date> documents) {

		List<Document> docs = new ArrayList<Document>();
		
		for (String titre : documents.keySet()) {

			Document document = new Document();
			document.setTitre(titre);
			document.setDate(documents.get(titre));
			
			docs.add(document);

			documentDao.save(document);
			
		}
		
		return docs;
	}

}
