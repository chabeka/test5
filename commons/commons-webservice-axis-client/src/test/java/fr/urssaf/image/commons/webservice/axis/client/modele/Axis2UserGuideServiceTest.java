package fr.urssaf.image.commons.webservice.axis.client.modele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.rmi.RemoteException;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
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

   private static final String SECURITY_PATH = "src/main/resources/META-INF";

   @Before
   public void before() throws Exception {

      ConfigurationContext ctx = ConfigurationContextFactory
            .createConfigurationContextFromFileSystem(SECURITY_PATH,
                  SECURITY_PATH + "/axis2.xml");

      service = new Axis2UserGuideServiceStub(ctx,
            "http://localhost:8082/axis2/services/Axis2UserGuideService/");

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

   public static void main(String[] args) throws Exception {

      if (args.length != 2) {
         System.out
               .println("Usage: $java Client endpoint_address client_repo_path");
      }

      ConfigurationContext ctx = ConfigurationContextFactory
            .createConfigurationContextFromFileSystem("C:\\axis2.xml");

      ServiceClient client = new ServiceClient(ctx, null);
      Options options = new Options();
      options.setAction("urn:echo");
      options.setTo(new EndpointReference(
            "http://localhost:8082/axis2/services/Axis2UserGuideService/"));
      client.setOptions(options);

      OMElement response = client.sendReceive(getPayload("Hello world"));

      System.out.println(response);

   }

   private static OMElement getPayload(String value) {
      OMFactory factory = OMAbstractFactory.getOMFactory();
      OMNamespace ns = factory.createOMNamespace(
            "http://sample02.samples.rampart.apache.org", "ns1");
      OMElement elem = factory.createOMElement("echo", ns);
      OMElement childElem = factory.createOMElement("param0", null);
      childElem.setText(value);
      elem.addChild(childElem);

      return elem;
   }
}
