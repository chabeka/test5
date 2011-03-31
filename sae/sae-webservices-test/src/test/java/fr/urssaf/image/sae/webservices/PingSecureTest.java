package fr.urssaf.image.sae.webservices;

import static org.junit.Assert.assertEquals;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import fr.urssaf.image.sae.webservices.modele.SaeServiceStub;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.PingSecureRequest;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.PingSecureResponse;

public class PingSecureTest {

   private SaeServiceStub service;

   private static final Logger LOG = Logger.getLogger(PingSecureTest.class);

   @Before
   public void before() throws AxisFault, ConfigurationException {

      Configuration config = new PropertiesConfiguration(
            "sae-webservices-test.properties");
      service = new SaeServiceStub(config.getString("urlServiceWeb"));

   }

   @Test
   @Ignore
   public void pingSecure_success() throws RemoteException {

      PingSecureRequest request = new PingSecureRequest();

      PingSecureResponse response = service.pingSecure(request);

      LOG.debug(response.getPingString());

      assertEquals("Test du ping securisé",
            "Les services du SAE sécurisés par authentification sont en ligne",
            response.getPingString());
   }

   @Test
   public void pingSecure_failure() throws RemoteException {

      PingSecureRequest request = new PingSecureRequest();

      try {
         service.pingSecure(request);
      } catch (AxisFault fault) {
         assertEquals(
               "An Authentication object was not found in the SecurityContext",
               fault.getMessage());
      }

   }
}
