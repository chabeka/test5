package fr.urssaf.image.commons.webservice.exemple.ssl.rpc.service;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import fr.urssaf.image.commons.webservice.exemple.ssl.rpc.modele.Service1Locator;
import fr.urssaf.image.commons.webservice.exemple.ssl.rpc.modele.Service1PortType;

public class SSLServiceImpl implements SSLService {

	private static final Logger LOG = Logger.getLogger(SSLServiceImpl.class);

	private Service1PortType port;

	public SSLServiceImpl() {
		Service1Locator locator = new Service1Locator();
		try {
			port = locator.getservice1Port();
		} catch (ServiceException e) {
		   LOG.error(e);
		}
	}

	@Override
	public String wsTest1(String nom, String prenom) throws RemoteException {
		return port.ws_test1(nom, prenom);
	}

}
