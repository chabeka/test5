package fr.urssaf.image.commons.webservice.axis.client.modele;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import fr.urssaf.image.commons.webservice.axis.client.modele.SaeServiceStub.PingRequest;
import fr.urssaf.image.commons.webservice.axis.client.modele.SaeServiceStub.PingResponse;

public class SaeServiceTest {

   private SaeServiceStub service;

   private static final Logger LOG = Logger.getLogger(SaeServiceTest.class);

   @Before
   public void before() throws AxisFault {

      service = new SaeServiceStub();

   }

   @Test
   public void ping() throws RemoteException {

      PingRequest request = new PingRequest();

      PingResponse response = service.ping(request);

      LOG.debug(response.getPingString());

      assertEquals("Les services SAE sont en ligne", response.getPingString());
   }
}
