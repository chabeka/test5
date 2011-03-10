package fr.urssaf.image.commons.webservice.axis.client.modele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import fr.urssaf.image.commons.webservice.axis.client.modele.Axis2UserGuideServiceStub.DoInOnlyRequest;
import fr.urssaf.image.commons.webservice.axis.client.modele.Axis2UserGuideServiceStub.MultipleParametersAddItemRequest;
import fr.urssaf.image.commons.webservice.axis.client.modele.Axis2UserGuideServiceStub.MultipleParametersAddItemResponse;
import fr.urssaf.image.commons.webservice.axis.client.modele.Axis2UserGuideServiceStub.NoParametersRequest;
import fr.urssaf.image.commons.webservice.axis.client.modele.Axis2UserGuideServiceStub.TwoWayOneParameterEchoRequest;
import fr.urssaf.image.commons.webservice.axis.client.modele.Axis2UserGuideServiceStub.TwoWayOneParameterEchoResponse;

public class Axis2UserGuideServiceTest {

   private Axis2UserGuideServiceStub service;

   private static final Logger LOG = Logger
         .getLogger(Axis2UserGuideServiceTest.class);

   @Before
   public void before() throws AxisFault {

      service = new Axis2UserGuideServiceStub(
            "http://localhost:8080/axis2/services/Axis2UserGuideService/");

   }

   @Test
   public void doInOnly() throws RemoteException {

      DoInOnlyRequest request = new DoInOnlyRequest();
      request.setMessageString("message");

      service.doInOnly(request);
   }

   @Test
   public void twoWayOneParameterEcho() throws RemoteException {

      String echo = "echo";

      TwoWayOneParameterEchoRequest request = new TwoWayOneParameterEchoRequest();
      request.setEchoString(echo);

      TwoWayOneParameterEchoResponse response = service
            .twoWayOneParameterEcho(request);

      LOG.debug(response.getEchoString());
      assertEquals(echo, response.getEchoString());
   }

   @Test
   public void noParameters() throws RemoteException {

      NoParametersRequest request = new NoParametersRequest();

      assertNotNull(service.noParameters(request));

   }

   @Test
   public void ets() throws RemoteException {

      int id = 1;

      MultipleParametersAddItemRequest request = new MultipleParametersAddItemRequest();
      request.setDescription("description");
      request.setItemId(1);
      request.setItemName("name");
      request.setPrice(new Float(24.2));

      MultipleParametersAddItemResponse response = service
            .multipleParametersAddItem(request);

      LOG.debug(response.getItemId());
      LOG.debug(response.getSuccessfulAdd());

      assertEquals(id, response.getItemId());
      assertEquals(true, response.getSuccessfulAdd());

   }
}
