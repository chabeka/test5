package fr.urssaf.image.commons.jmx.httpadaptor.exemple.mbean;

import java.util.ArrayList;
import java.util.List;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.NotificationBroadcasterSupport;


public class PremierNotification extends NotificationBroadcasterSupport implements PremierNotificationMBean {

   private static String nom            = "PremierNotificationMBean";

   private int           valeur         = 0;
      
   private List<MBeanNotificationInfo> listInfo = new ArrayList<MBeanNotificationInfo>();

   public String getNom() {
     return nom;
   }

   public int getValeur() {
     return valeur;
   }

   public synchronized void setValeur(int valeur) {
      this.valeur = valeur;
      
      String name = AttributeChangeNotification.class.getName();
      String string = "Un ou des attribut(s) du MBean ont ete modifie(s)."; 
      String description = "La valeur de l'attribut 'valeur' a ete modifie";
      String[] strings = new String[]{string};
      MBeanNotificationInfo info = new MBeanNotificationInfo(strings, name, description);
      listInfo.add(info);
      
   }

   public void rafraichir() {
      System.out.println("Rafraichir les donnees");

      String name = AttributeChangeNotification.class.getName();
      String string = "Un rafraichissement des donnees a ete fait."; 
      String description = "Un rafraichissement des donnees a ete fait.";
      MBeanNotificationInfo info = new MBeanNotificationInfo(new String[]{string}, name, description);
      listInfo.add(info); 
   }
   
   @Override
   public int add(int a, int b) {
      
      String name = AttributeChangeNotification.class.getName();
      String string = "Une addition a ete realisee."; 
      String description = "Une addition a ete realisee.";
      MBeanNotificationInfo info = new MBeanNotificationInfo(new String[]{string}, name, description);
      listInfo.add(info);
      
      return a + b;
   }
   
   @Override 
   public MBeanNotificationInfo[] getNotificationInfo() { 
       
      return getAllNotif();
      
   }
   
   private MBeanNotificationInfo[] getAllNotif() {
       MBeanNotificationInfo[] mBeanNotificationInfo = new MBeanNotificationInfo[listInfo.size()];
       
       for(int i=0; i < listInfo.size(); i++) {
          mBeanNotificationInfo[i] = listInfo.get(i);
       }
       return mBeanNotificationInfo;
   }
   

}