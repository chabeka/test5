package fr.urssaf.image.sae.storage.dfce.messages;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-sae-storage-dfce.xml" })
public class MessageTest {
	@Autowired
	private MessageHandler messageHandler;

	@Test
	public final void getMessageFromFile() {
		Assert.assertNotNull(messageHandler.getMessage("insertion.document.required"));
	}

	/**
	 * 
	 * @param messageHandler
	 *            : Le gestionnaire des message
	 */
	public void setMessageHandler(final MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

	/**
	 * 
	 * @return une instance du gestionnaire de message
	 */
	public MessageHandler getMessageHandler() {
		return messageHandler;
	}
}
