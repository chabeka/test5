package fr.urssaf.image.commons.webservice.axis.client.modele.rampart;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.junit.Before;
import org.junit.Test;

import fr.urssaf.image.commons.webservice.axis.client.modele.rampart.Sample02Stub.Echo;

public class Sample02Test {

   private static final String SECURITY_PATH = "src/main/resources/META-INF";

   private Sample02Stub service;
   
   @Before
   public void before() throws Exception {

      ConfigurationContext ctx = ConfigurationContextFactory
            .createConfigurationContextFromFileSystem(SECURITY_PATH,
                  SECURITY_PATH + "/axis2-rampart.xml");

      service = new Sample02Stub(ctx,
            "http://localhost:8082/axis2/services/sample02/");
      
     

   }
   
   @Test
   public void echo() throws RemoteException{
      
      String msg = "test pour echo";
      
      Echo echo = new Echo();
      echo.setArgs0(msg);
      assertEquals(msg,service.echo(echo).get_return());
   }

}
