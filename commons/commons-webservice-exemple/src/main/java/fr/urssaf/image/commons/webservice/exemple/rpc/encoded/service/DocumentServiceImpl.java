package fr.urssaf.image.commons.webservice.exemple.rpc.encoded.service;

import java.rmi.RemoteException;
import java.util.List;

import fr.urssaf.image.commons.webservice.exemple.rpc.encoded.modele.DocService;
import fr.urssaf.image.commons.webservice.exemple.rpc.encoded.modele.DocServiceRpcEncoded;
import fr.urssaf.image.commons.webservice.exemple.rpc.encoded.modele.Document;

public class DocumentServiceImpl implements DocumentService {

	private DocService port;

	public DocumentServiceImpl() {

		DocServiceRpcEncoded service = new DocServiceRpcEncoded();

		port = service.getDocumentServicePort();

	}

	@Override
	public List<Document> allDocuments() throws RemoteException {
		return port.allDocuments().getItem();
	}

}
