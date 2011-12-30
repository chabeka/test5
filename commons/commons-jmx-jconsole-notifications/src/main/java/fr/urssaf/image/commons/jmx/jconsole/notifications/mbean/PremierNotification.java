package fr.urssaf.image.commons.jmx.jconsole.notifications.mbean;

import java.util.ArrayList;
import java.util.List;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;


public class PremierNotification extends NotificationBroadcasterSupport implements PremierNotificationMBean {

   private static String nom            = "PremierNotificationMBean";

   private int           valeur         = 100;

   private static long   numeroSequence = 00;
   
   private List<String> listInfo = new ArrayList<String>();

   public String getNom() {
     return nom;
   }

   public int getValeur() {
     return valeur;
   }

   public synchronized void setValeur(int valeur) {

     this.valeur = valeur;

     numeroSequence++;
     Notification notif = new AttributeChangeNotification(this,
         numeroSequence, System.currentTimeMillis(),
         "Attention ! Modification de l'attribut 'valeur'", "Valeur", "int", this.valeur, valeur);

     sendNotification(notif);
     
     listInfo.add(notif.getMessage());
   }

   public void rafraichir() {
     
     System.out.println("Rafraichir les donnees");
     
     numeroSequence++;
     Notification notif = new AttributeChangeNotification(this,
         numeroSequence, System.currentTimeMillis(),
         "Un rafraichissement des donnees a ete fait !", "Data", "string", null, null);
     
     sendNotification(notif);
     
     listInfo.add(notif.getMessage());
   }
   
   @Override
   public int add(int a, int b) {
      
      numeroSequence++;
      Notification notif = new AttributeChangeNotification(this,
          numeroSequence, System.currentTimeMillis(),
          "Une addition a ete realisee!", "Data", "string", null, null);
      
      sendNotification(notif);
      
      return a + b;
   }
   
   
   
   @Override 
   public MBeanNotificationInfo[] getNotificationInfo() { 
       String[] types = getMessage();
       String name = AttributeChangeNotification.class.getName(); 
       String description = "Un attribut du MBean a ete modifie"; 
       MBeanNotificationInfo info = 
           new MBeanNotificationInfo(types, name, description); 
       return new MBeanNotificationInfo[] {info}; 
   }

   private String[] getMessage() {
      
      String[] strings = new String[listInfo.size()];
      
      for (int i = 0; i < listInfo.size(); i++) {
         strings[i] = listInfo.get(i);
      }
      return strings;
   }

}