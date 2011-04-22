package fr.urssaf.image.commons.jms.spring;

import javax.jms.JMSException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fr.urssaf.image.commons.jms.spring.consumer.AccountConsumer;

/**
 * Classe executable de consommation
 * 
 * 
 */
public final class Consumer {

   private Consumer() {

   }

   /**
    * En mode synchrone le client s'arrête quand il reçoit le message En mode
    * asynchrone le client s'arrête au bout du temps imparti qu'il est reçu oui
    * au non le message (il peut dans ce mode en recevoir plusieurs)
    * 
    * arguments :
    * <ul>
    * <li>mode : synchrone/asynchrone</li>
    * <li>time : temps d'attente en seconde (uniquement valable en mode
    * asynchrone)</li>
    * <ul>
    * 
    * @param args
    *           arguments pour la consommation
    */
   public static void main(String[] args) {

      if (args[0].equals("synchrone")) {

         ApplicationContext ctx = new ClassPathXmlApplicationContext(
               new String[] { "applicationContext.xml",
                     "applicationContext-jms.xml" });

         AccountConsumer consumer = (AccountConsumer) ctx
               .getBean("accountConsumer");

         try {
            consumer.receiveAccount();
         } catch (JMSException e) {
            throw new IllegalStateException(e);
         }
      }

      else if (args[0].equals("asynchrone")) {

         new ClassPathXmlApplicationContext(new String[] {
               "applicationContext.xml", "applicationContext-jms.xml",
               "applicationContext-consumer.xml" });

         long time = Long.parseLong(args[1]) * 1000;
         try {
            Thread.sleep(time);
         } catch (InterruptedException e) {
            throw new IllegalStateException(e);
         }

      }

   }

}
