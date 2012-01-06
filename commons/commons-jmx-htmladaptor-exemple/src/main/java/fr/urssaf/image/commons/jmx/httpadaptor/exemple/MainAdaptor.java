package fr.urssaf.image.commons.jmx.httpadaptor.exemple;

import java.lang.management.ManagementFactory;
import java.util.Date;

import javax.management.MBeanServer;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;

import com.sun.jdmk.comm.HtmlAdaptorServer;

import fr.urssaf.image.commons.jmx.httpadaptor.exemple.mbean.PremierNotification;

public class MainAdaptor implements NotificationListener {
   
   public MainAdaptor() {
      try {
         MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
         ObjectName name = new ObjectName("PremierNotification:type=PremierNotificationMBean");
         
         PremierNotification premier = new PremierNotification();
         
         mbs.registerMBean(premier, name);
         HtmlAdaptorServer adapter = new HtmlAdaptorServer();
         // il est necessaire d’ouvrir un port
         adapter.setPort(8000);
         
         adapter.start();
         
         mbs.registerMBean(adapter, new ObjectName("AgentAdaptor:name=htmladapter,port=8000"));
         
         premier.addNotificationListener(this, null, null);
                  
         while (true) {
            Thread.sleep(1500);
         }
      } catch (Exception e) {
      }
   }
   public void handleNotification(Notification notif, Object handback ) {
      System.out.println( new Date() );
      System.out.println("Une notification a été envoyée..." );
      System.out.println( notif.getMessage() );
   }
   
   /**
    * @param args
    */
   public static void main(String[] args) {
      MainAdaptor mainAdaptor = new MainAdaptor();
   }
      
}
