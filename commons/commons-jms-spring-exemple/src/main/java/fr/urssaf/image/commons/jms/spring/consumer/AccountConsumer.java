package fr.urssaf.image.commons.jms.spring.consumer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.urssaf.image.commons.jms.spring.modele.Account;

/**
 * Classe de consommation d'un message jms
 * 
 * 
 */
@Component
public class AccountConsumer {

   private static final Logger LOG = Logger.getLogger(AccountConsumer.class);

   @Autowired
   private JmsTemplate jmsTemplate;

   @Autowired
   private Destination destinataire;

   /**
    * Reçoit les message du destinataire
    * 
    * <pre>
    * &lt;bean id="queue" class="org.apache.activemq.command.ActiveMQQueue">
    *       &lt;constructor-arg value="account-test" />
    * &lt;/bean>
    * </pre>
    * 
    * @throws JMSException
    *            exception levée par jms
    */
   @Transactional
   public final void receiveAccount() throws JMSException {
      Message message = this.jmsTemplate.receive(this.destinataire);
      if (message instanceof MapMessage) {

         MapMessage mapMessage = (MapMessage) message;
         String firstname = mapMessage.getString("firstname");
         String lastname = mapMessage.getString("lastname");
         long idAccount = mapMessage.getLong("idAccount");

         Account account = new Account();
         account.setIdAccount(idAccount);
         account.setFirstname(firstname);
         account.setLastname(lastname);

         LOG.info("receiving account: " + account.toString());

      }
   }

}
