package fr.urssaf.image.commons.webservice.exemple.ssl.rpc.service;

import static org.junit.Assert.assertEquals;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import fr.urssaf.image.commons.webservice.exemple.ssl.base.RPCJSSESocketFactory;
import fr.urssaf.image.commons.webservice.ssl.InitAxisProperties;

@Ignore
public class SSLServiceTest {

	private static final Logger LOG = Logger.getLogger(SSLServiceTest.class);

	private final SSLService service;

	public SSLServiceTest() {

		service = new SSLServiceImpl();
		InitAxisProperties.initSoketSecureFactory(RPCJSSESocketFactory.class);

	}

	@Test
	public void wsTest1() throws RemoteException {

		String resultat = service.wsTest1("nom", "prenom");

		LOG.debug(resultat);

		assertEquals("échec du test","Hello prenom nom (en rpc/literal)", resultat);

	}

}
