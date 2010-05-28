package fr.urssaf.image.commons.webservice.rpc.aed.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.junit.Test;

import fr.urssaf.image.commons.webservice.rpc.aed.context.AEDJSSESocketFactory;
import fr.urssaf.image.commons.webservice.rpc.aed.service.modele.ParametrageTransfertModele;
import fr.urssaf.image.commons.webservice.ssl.InitAxisProperties;

public class AEServiceTest {

	protected static final Logger log = Logger.getLogger(AEServiceTest.class);

	private AEDService service;

	public AEServiceTest() {

		service = new AEDServiceImpl();
		InitAxisProperties.initSoketSecureFactory(AEDJSSESocketFactory.class);

	}

	@Test
	public void ping() throws RemoteException {

		log.debug(service.ping());
		assertEquals("Les fonctions SAEL organisme sont en ligne.", service
				.ping());

	}

	@Test
	public void parametrageTransfert() throws RemoteException,
			UnknownHostException {

		String applicationSource = "SAEL";
		String IP = InetAddress.getLocalHost().getHostAddress();

		ParametrageTransfertModele modele = service.getParametrageTransfert(
				applicationSource, IP);

		log.debug(modele.getUrlFTP().value);
		log.debug(modele.getTypeTransfert().value);
		log.debug(modele.getErreur().value);
		log.debug(modele.getChemin().value);
		log.debug(modele.getLogin().value);
		log.debug(modele.getPassword().value);
		log.debug(modele.getIdTransfert().value);

		//assertEquals("cer69imageint4.cer69.recouv", modele.getUrlFTP().value);
		assertNotNull(modele.getUrlFTP().value);
		assertEquals("LOCAL", modele.getTypeTransfert().value);
		assertNotNull(modele.getErreur().value);
		assertNotNull(modele.getChemin().value);
		//assertEquals("/SAEL/"+modele.getIdTransfert().value, modele.getChemin().value);
		//assertEquals("ftpaed", modele.getLogin().value);
		//assertEquals("ftpaed", modele.getPassword().value);
		assertNotNull(modele.getLogin().value);
		assertNotNull(modele.getPassword().value);
		// assertTrue(!"".equals(modele.getIdTransfert().value));

	}
}
