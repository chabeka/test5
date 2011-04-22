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
    * la méthode appelle {@link AccountProducer#sendAccount}
    * @param args aucun argument prise en compte
    */
   public static void main(String[] args) {
      ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] {
            "applicationContext.xml", "applicationContext-jms.xml" });

      AccountProducer producer = (AccountProducer) ctx
            .getBean("accountProducer");

      producer.sendAccount(AccountFactory.createAccount(args[0], args[1]));

   }

}
