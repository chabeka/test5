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
    * agurgments
    * <ul>
    * <li>prénom</li>
    * <li>nom</li>
    * </ul>
    * 
    * @param args
    *           arguments de l'expéditeur du message
    */
   public static void main(String[] args) {
      ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] {
            "applicationContext.xml", "applicationContext-jms.xml" });

      AccountConsumer consumer = (AccountConsumer) ctx
            .getBean("accountConsumer");

      try {
         consumer.receiveAccount();
      } catch (JMSException e) {
         throw new IllegalStateException(e);
      }

   }

}
