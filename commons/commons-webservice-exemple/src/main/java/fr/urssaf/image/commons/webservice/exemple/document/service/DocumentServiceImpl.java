package fr.urssaf.image.commons.webservice.exemple.document.service;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.Logger;

import fr.urssaf.image.commons.webservice.exemple.document.modele.DocService;
import fr.urssaf.image.commons.webservice.exemple.document.modele.DocServiceDocument;
import fr.urssaf.image.commons.webservice.exemple.document.modele.Document;

public class DocumentServiceImpl implements DocumentService {

	private DocService port;

	protected static final Logger log = Logger
			.getLogger(DocumentServiceImpl.class);

	public DocumentServiceImpl() {

		DocServiceDocument service = new DocServiceDocument();

		port = service.getDocumentServicePort();

	}

	@Override
	public List<Document> allDocuments() throws RemoteException {
		return port.allDocuments();
	}

	@Override
	public Document get(
			int id) throws RemoteException {
		return port.getDocument(id);
	}

	@Override
	public void save(
			Document document)
			throws RemoteException {
		port.save(document);
		
	}

}
