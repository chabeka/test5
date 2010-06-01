package fr.urssaf.image.commons.webservice.exemple.rpc.encoded.service;

import java.rmi.RemoteException;
import java.util.List;

import fr.urssaf.image.commons.webservice.exemple.rpc.encoded.modele.Document;

public interface DocumentService {

	public List<Document> allDocuments() throws RemoteException;

	public void save(Document document) throws RemoteException;

	public Document get(int id) throws RemoteException;
}
