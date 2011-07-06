package fr.urssaf.image.sae.storage.dfce.messages;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Fournit des services qui retournent un message à partir de sa clés .
 * 
 * @author akenore 
 * 
 */
@Component
public class MessageHandler {
	@Autowired
	private MessageSource messageSource;
	// Le local par défaut
	public static final Locale LOCAL = Locale.FRENCH;
	// Message par défaut
	@SuppressWarnings("PMD.LongVariable")
	public static final String NO_MESSAGE_FOR_THIS_KEY = "Pas de méssage correspondant à cette clé";

	/**
	 * 
	 * @param messageKey
	 *            : La clé du message
	 * @param impactKey
	 *            : La clé de l'impact
	 * @param actionKey
	 *            : La clé de l'action
	 * @return Le message en faisant la concaténation du message |impact| action
	 */
	public final String getMessage(final String messageKey,
			final String impactKey, final String actionKey) {
		final StringBuilder strBuilder = new StringBuilder();
		strBuilder.setLength(0);

		strBuilder.append(messageSource.getMessage(messageKey, null,
				NO_MESSAGE_FOR_THIS_KEY, LOCAL));
		strBuilder
				.append(" | ")
				.append(messageSource.getMessage(impactKey, null,
						NO_MESSAGE_FOR_THIS_KEY, LOCAL))
				.append(" | ")
				.append(messageSource.getMessage(actionKey, null,
						NO_MESSAGE_FOR_THIS_KEY, LOCAL));
		return strBuilder.toString();
	}

	/**
	 * @param messageKey
	 *            : La clé du message
	 * @return Le message
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final String getMessage(final String messageKey) {
		return messageSource.getMessage(messageKey, null,
				NO_MESSAGE_FOR_THIS_KEY, LOCAL);
	}

	/**
	 * 
	 * @param messageSource
	 *            : Le message source
	 */
	public final void setMessageSource(final MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * 
	 * @return Le message source
	 */
	public final MessageSource getMessageSource() {
		return messageSource;
	}



}
