package fr.urssaf.image.commons.jms.spring.consumer;

import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
   @Qualifier("queue")
   private Destination destinataire;

   /**
    * Reçoit les message du destinataire en mode synchronisé
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

         Account account = createAccount(idAccount, firstname, lastname);

         LOG.info("receiving account: " + account.toString());

      }

      else {
         LOG.info("no message received");
      }
   }

   /**
    * Reçoit les message du destinataire en mode asynchronisé
    * 
    * <pre>
    * &lt;jms:listener-container connection-factory="connectionFactory"
    *       acknowledge="auto" transaction-manager="transactionManager" >
    *       &lt;jms:listener destination="account-test"
    *          ref="accountConsumer" method="receiveAccount" />
    * &lt;/jms:listener-container>
    * </pre>
    * 
    * 
    * @param message message JMS
    * @throws JMSException exception levée par jms
    */
   @Transactional
   public final void receiveAccount(Map<String, Object> message)
         throws JMSException {

      String firstname = (String) message.get("firstname");
      String lastname = (String) message.get("lastname");
      long idAccount = (Long) message.get("idAccount");

      Account account = createAccount(idAccount, firstname, lastname);

      LOG.info("receiving account: " + account.toString());
   }

   private Account createAccount(long idAccount, String firstname,
         String lastname) {

      Account account = new Account();
      account.setIdAccount(idAccount);
      account.setFirstname(firstname);
      account.setLastname(lastname);

      return account;
   }

}
