package fr.urssaf.image.commons.webservice.exemple.ssl.rpc.service;

import static org.junit.Assert.assertEquals;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import fr.urssaf.image.commons.webservice.exemple.ssl.base.RPCJSSESocketFactory;
import fr.urssaf.image.commons.webservice.exemple.ssl.rpc.service.SSLService;
import fr.urssaf.image.commons.webservice.exemple.ssl.rpc.service.SSLServiceImpl;
import fr.urssaf.image.commons.webservice.ssl.InitAxisProperties;

@Ignore
public class SSLServiceTest {

	private static final Logger log = Logger.getLogger(SSLServiceTest.class);

	private SSLService service;

	public SSLServiceTest() {

		service = new SSLServiceImpl();
		InitAxisProperties.initSoketSecureFactory(RPCJSSESocketFactory.class);

	}

	@Test
	public void ws_test1() throws RemoteException {

		String resultat = service.ws_test1("nom", "prenom");

		log.debug(resultat);

		assertEquals("Hello prenom nom (en rpc/literal)", resultat);

	}

}
