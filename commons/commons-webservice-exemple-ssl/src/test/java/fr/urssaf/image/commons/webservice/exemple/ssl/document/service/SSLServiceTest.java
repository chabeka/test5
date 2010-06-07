package fr.urssaf.image.commons.webservice.exemple.ssl.document.service;

import static org.junit.Assert.assertEquals;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.junit.Test;

import fr.urssaf.image.commons.webservice.exemple.ssl.base.RPCJSSESocketFactory;
import fr.urssaf.image.commons.webservice.ssl.InitAxisProperties;

public class SSLServiceTest {

	private static final Logger log = Logger.getLogger(SSLServiceTest.class);

	private SSLService service;

	public SSLServiceTest() {
		service = new SSLServiceImpl();
		InitAxisProperties.initSoketSecureFactory(RPCJSSESocketFactory.class);

	}

	@Test
	public void wsTest1() throws RemoteException {

		String resultat = service.wsTest1("nom", "prenom");

		log.debug(resultat);

		assertEquals("Hello prenom nom (en document/literal)", resultat);

	}
}