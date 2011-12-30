package fr.urssaf.image.commons.jmx.httpadaptor.exemple;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.sun.jdmk.comm.HtmlAdaptorServer;

import fr.urssaf.image.commons.jmx.httpadaptor.exemple.mbean.PremierNotification;

public class MainAdaptor {
   
   /**
    * @param args
    */
   public static void main(String[] args) {
      try {
         MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
         ObjectName name = new ObjectName("PremierNotification:type=PremierNotificationMBean");
         PremierNotification premier = new PremierNotification();
         premier.setValeur(150);
         mbs.registerMBean(premier, name);
         HtmlAdaptorServer adapter = new HtmlAdaptorServer();
         adapter.setPort(8000);
         mbs.registerMBean(adapter, new ObjectName("AgentAdaptor:name=htmladapter,port=8000"));
         adapter.start();
         while (premier.getValeur() < 460) {
            System.out.println(premier.getValeur());
            Thread.sleep(5500);
            premier.setValeur(premier.getValeur()+1);
         }
         adapter.stop();
      } catch (Exception e) {
      }

   }

}
