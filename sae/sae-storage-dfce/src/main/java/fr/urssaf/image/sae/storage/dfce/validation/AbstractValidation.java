package fr.urssaf.image.sae.storage.dfce.validation;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import fr.urssaf.image.sae.storage.dfce.messages.MessageHandler;

/**
 * Contient les éléments commun des différentes validations
 * 
 * @author akenore
 * 
 */
@Aspect
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class AbstractValidation {
	@Autowired
	private MessageHandler messageHandler ;

	/**
	 * 
	 * @param messageHandler
	 *            : initialise le message handler
	 */
	public final void setMessageHandler(final MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

	/**
	 * 
	 * @return le message handler
	 */
	public final MessageHandler getMessageHandler() {
		return messageHandler;
	}
}
