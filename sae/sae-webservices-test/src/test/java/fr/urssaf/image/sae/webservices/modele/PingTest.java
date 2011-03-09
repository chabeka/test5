package fr.urssaf.image.sae.webservices.modele;

import static org.junit.Assert.assertEquals;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.PingRequest;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.PingResponse;

public class PingTest {

   private SaeServiceStub service;

   private static final Logger LOG = Logger.getLogger(PingTest.class);

   @Before
   public void before() throws AxisFault, ConfigurationException {

      Configuration config = new PropertiesConfiguration(
            "sae-webservices-test.properties");
      service = new SaeServiceStub(config.getString("urlServiceWeb"));

   }

   @Test
   public void ping() throws RemoteException {

      PingRequest request = new PingRequest();

      PingResponse response = service.ping(request);

      LOG.debug(response.getPingString());

      assertEquals("Les services SAE sont en ligne", response.getPingString());
   }
}
