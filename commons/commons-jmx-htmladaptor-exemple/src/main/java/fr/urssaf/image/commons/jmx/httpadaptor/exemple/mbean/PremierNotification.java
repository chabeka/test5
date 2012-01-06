package fr.urssaf.image.commons.jmx.httpadaptor.exemple.mbean;

import java.util.ArrayList;
import java.util.List;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;


public class PremierNotification extends NotificationBroadcasterSupport implements PremierNotificationMBean {

   private static String nom            = "PremierNotificationMBean";

   private int           valeur         = 0;
   
   private static long   numeroSequence = 00;

   private List<String> listInfo = new ArrayList<String>();
   
   public String getNom() {
     return nom;
   }

   public int getValeur() {
     return valeur;
   }

   public void setValeur(int valeur) {
      this.valeur = valeur;
      
      numeroSequence++;
      Notification notif = new AttributeChangeNotification(this,
          numeroSequence, System.currentTimeMillis(),
          "Modification de l'attribut 'valeur' a " + valeur, "Valeur", "int", this.valeur, valeur);
      sendNotification(notif);
      listInfo.add(notif.getMessage());
   }

   public void rafraichir() {
    
      numeroSequence++;
      Notification notif = new AttributeChangeNotification(this,
          numeroSequence, System.currentTimeMillis(),
          "Un rafraichissement des donnees a ete fait !", "Data", "string", null, null);
      sendNotification(notif);
      listInfo.add(notif.getMessage());
   }
   
   @Override
   public int add(int a, int b) {
      int somme = a+ b ;   
      numeroSequence++;
      Notification notif = new AttributeChangeNotification(this,
          numeroSequence, System.currentTimeMillis(),
          "Une addition a ete realisee!\n Le resultat est : " + somme, "Data", "string", a, b);
      sendNotification(notif);
      listInfo.add(notif.getMessage());
      return somme;
   }
   
   @Override 
   public MBeanNotificationInfo[] getNotificationInfo() { 
       
      String[] types = getMessage();
      int taille = types.length;
      MBeanNotificationInfo[] mBeanNotificationInfo = new MBeanNotificationInfo[taille];
      
      String name = AttributeChangeNotification.class.getSimpleName();
      
      for(int i=0; i < taille; i++) {
         MBeanNotificationInfo info = 
                 new MBeanNotificationInfo(new String[] {types[i]}, 
                                           name, 
                                           types[i]);
         
         mBeanNotificationInfo[i] = info;
      }
      return mBeanNotificationInfo;
            
   }
   
   private String[] getMessage() {
      
      String[] strings = new String[listInfo.size()];
      
      for (int i = 0; i < listInfo.size(); i++) {
         strings[i] = listInfo.get(i);
      }
            
      return strings;
   }
   

}