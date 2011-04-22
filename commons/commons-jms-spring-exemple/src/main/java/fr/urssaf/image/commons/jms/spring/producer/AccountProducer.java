package fr.urssaf.image.commons.jms.spring.producer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.urssaf.image.commons.jms.spring.modele.Account;

/**
 * classe de fabrique des messages à envoyer
 * 
 * 
 */
@Component
public class AccountProducer {

   private static final Logger LOG = Logger.getLogger(AccountProducer.class);

   @Autowired
   private JmsTemplate jmsTemplate;

   @Autowired
   private Destination destination;

   /**
    * envoie le contenu d'un {@link Account}<br>
    * paramètres:
    * <ul>
    * <li>idAccount : {@link Account#getIdAccount()}</li>
    * <li>firstname : {@link Account#getFirstname()}</li>
    * <li>lastname : {@link Account#getLastname()}</li>
    * </ul>
    * 
    * @param account
    *           information du compte à envoyer
    */
   @Transactional
   public final void sendAccount(final Account account) {
      this.jmsTemplate.send(destination, new MessageCreator() {
         @Override
         public Message createMessage(Session session) throws JMSException {
            LOG.info("Sending account: " + account.toString());
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setLong("idAccount", account.getIdAccount());
            mapMessage.setString("firstname", account.getFirstname());
            mapMessage.setString("lastname", account.getLastname());

            return mapMessage;

         }
      });
   }

}
