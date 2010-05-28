package fr.urssaf.image.commons.webservice.exemple.rpc.literal.service;

import java.rmi.RemoteException;
import java.util.List;

import fr.urssaf.image.commons.webservice.exemple.rpc.literal.modele.Document;

public interface DocumentService {

	public List<Document> allDocuments() throws RemoteException;
}
