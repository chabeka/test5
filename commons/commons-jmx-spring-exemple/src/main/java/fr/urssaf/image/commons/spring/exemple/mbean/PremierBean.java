package fr.urssaf.image.commons.spring.exemple.mbean;

import javax.management.Notification;

import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;

public class PremierBean implements IPremierBean, NotificationPublisherAware {

   private String helloWord;
   private int valeur;
   private NotificationPublisher publisher;
   
   @Override
   public int add(int x, int y) {
      int somme = x + y;
      this.publisher.sendNotification(new Notification("add", this, 0));
      return somme;
   }

   @Override
   public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
      this.publisher = notificationPublisher;
   }
   
   @Override
   public String getHelloWord() {
      
      return helloWord;
   }

   @Override
   public void sayHello() {
      System.out.println(helloWord);
   }

   @Override
   public void setHelloWord(String hello) {
      helloWord = hello;
   }

   @Override
   public int getValeur() {
      return valeur;
   }

   @Override
   public void setValeur(int valeur) {
      this.valeur = valeur;
   }
  
}
