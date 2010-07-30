package fr.urssaf.image.commons.webservice.exemple.ssl.rpc.service;

import java.rmi.RemoteException;

public interface SSLService {

	String wsTest1(String nom, String prenom) throws RemoteException;
}
