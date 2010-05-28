package fr.urssaf.image.commons.webservice.rpc.ged.service;

import static org.junit.Assert.assertEquals;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.junit.Test;

import fr.urssaf.image.commons.webservice.rpc.ged.modele.AuthInfo;
import fr.urssaf.image.commons.webservice.rpc.ged.service.GedImageService;
import fr.urssaf.image.commons.webservice.rpc.ged.service.GedImageServiceImpl;

public class GedImageServiceTest {
	
	protected static final Logger log = Logger.getLogger(GedImageServiceTest.class);

	private GedImageService service;
	
	public GedImageServiceTest() {
		this.service = new GedImageServiceImpl();
	}

	@Test
	public void ping() throws RemoteException {

		log.debug(service.ping());
		assertEquals("GedImage en ligne !",service.ping());

	}
	
	@Test
	public void getDictionnaire() throws RemoteException {

		 AuthInfo auth = new AuthInfo();
         auth.setLogin("SAEL");
         auth.setPassword("SAEL2013");
         auth.setAppli("AppliSAEL");
         auth.setCodeOrga("CER69");

         String[] items = service.getDictionnaire(auth,"NCOTI","ApplicationSource","");
         for(String item:items)
         {
        	 log.debug(item);
         }
		
         assertEquals("AUTRE",items[0]);
         assertEquals("BCC",items[1]);
         assertEquals("GED",items[2]);
         assertEquals("LAD",items[3]);
         assertEquals("SNV2",items[4]);
         assertEquals("VEGA",items[5]);
         assertEquals("WATT",items[6]);
	

	}
}
