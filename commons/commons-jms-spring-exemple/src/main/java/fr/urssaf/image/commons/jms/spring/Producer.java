package fr.urssaf.image.commons.jms.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fr.urssaf.image.commons.jms.spring.modele.AccountFactory;
import fr.urssaf.image.commons.jms.spring.producer.AccountProducer;

/**
 * Classe exécutable d'envoi des messages
 * 
 * 
 */
public final class Producer {

   private Producer() {

   }

   /**
    * arguments
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

      AccountProducer producer = (AccountProducer) ctx
            .getBean("accountProducer");

      producer.sendAccount(AccountFactory.createAccount(args[0], args[1]));

   }

}
