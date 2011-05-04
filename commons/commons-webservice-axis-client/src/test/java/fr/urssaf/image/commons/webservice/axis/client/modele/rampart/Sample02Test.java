package fr.urssaf.image.commons.webservice.axis.client.modele.rampart;

import static org.junit.Assert.assertEquals;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.junit.Before;
import org.junit.Test;

import fr.urssaf.image.commons.webservice.axis.client.modele.rampart.Sample02Stub.Echo;

@SuppressWarnings( { "PMD.MethodNamingConventions",
      "PMD.JUnitAssertionsShouldIncludeMessage" })
public class Sample02Test {

   private static final String SECURITY_PATH = "src/main/resources/META-INF";

   private final static String HTTP = "http://localhost:8082/axis2/services/sample02.sample02HttpSoap12Endpoint/";

   private final static String JMS = "jms:/sample02?transport.jms.DestinationType=queue&transport.jms.ContentTypeProperty=Content-Type&java.naming.provider.url=tcp://localhost:61616&java.naming.factory.initial=org.apache.activemq.jndi.ActiveMQInitialContextFactory&transport.jms.ConnectionFactoryJNDIName=QueueConnectionFactory";

   private static ConfigurationContext ctx;

   private Sample02Stub service;

   @Before
   public void before() throws AxisFault  {

      ctx = ConfigurationContextFactory
            .createConfigurationContextFromFileSystem(SECURITY_PATH,
                  SECURITY_PATH + "/axis2-rampart.xml");

   }

   @Test
   public void echo_jms() throws RemoteException {

      service = new Sample02Stub(ctx, JMS);
      assertEcho(service);
   }

   @Test
   public void echo_http() throws RemoteException {

      service = new Sample02Stub(ctx, HTTP);
      assertEcho(service);
   }

   private void assertEcho(Sample02Stub service) throws RemoteException {

      String msg = "test pour echo";

      Echo echo = new Echo();
      echo.setArgs0(msg);
      assertEquals(msg, service.echo(echo).get_return());
   }

}
