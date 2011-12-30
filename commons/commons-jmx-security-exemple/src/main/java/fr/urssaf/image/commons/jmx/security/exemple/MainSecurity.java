package fr.urssaf.image.commons.jmx.security.exemple;

import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServer;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class MainSecurity {
   
   public static void main(String[] args) throws Exception {
      int i = 10;
      MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
      LocateRegistry.createRegistry(9999);
      
      Map<String, String> env = new HashMap<String, String>();
      env.put("jmx.remote.x.password.file", "src/main/resources/jmx.password");
      env.put("jmx.remote.x.access.file", "src/main/resources/jmx.access");
      
      JMXServiceURL url = new JMXServiceURL("service:jmx:rmi://localhost/jndi/rmi://localhost:9999/server");
      JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(url, env, mbs);
      
      cs.start();
      while (i < 50) {
         Thread.sleep(1500);
      }
      cs.stop();
   }

}
