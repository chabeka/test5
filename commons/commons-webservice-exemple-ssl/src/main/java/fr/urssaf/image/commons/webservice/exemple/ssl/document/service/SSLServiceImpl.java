package fr.urssaf.image.commons.webservice.exemple.ssl.document.service;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import fr.urssaf.image.commons.webservice.exemple.ssl.document.modele.Service1Locator;
import fr.urssaf.image.commons.webservice.exemple.ssl.document.modele.Service1PortType;
import fr.urssaf.image.commons.webservice.exemple.ssl.document.modele.Ws_test1RequestType;

public class SSLServiceImpl implements SSLService {

	private Service1PortType port;
	private static final Logger LOG = Logger.getLogger(SSLServiceImpl.class);

	public SSLServiceImpl() {
		
		Service1Locator locator = new Service1Locator();//new Service1();
		try {
			port = locator.getservice1Port();
		} catch (ServiceException e) {
		   LOG.error(e);
		}
	

	}

	@Override
	public String wsTest1(String nom, String prenom) throws RemoteException {

		Ws_test1RequestType request = new Ws_test1RequestType(nom,prenom);
		
		return port.ws_test1(request).getResultat();
	}

}
