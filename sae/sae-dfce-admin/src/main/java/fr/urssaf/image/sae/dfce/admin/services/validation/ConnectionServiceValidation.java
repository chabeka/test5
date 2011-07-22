package fr.urssaf.image.sae.dfce.admin.services.validation;

import org.apache.commons.lang.Validate;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import fr.urssaf.image.sae.dfce.admin.messages.MessageHandler;
import fr.urssaf.image.sae.dfce.admin.model.ConnectionParameter;
import fr.urssaf.image.sae.dfce.admin.services.AbstractService;

/**
 * 
 * @author akenore
 * 
 */
@Aspect
public class ConnectionServiceValidation extends AbstractService {
	/**
	 * Valide les paramètres de connexion .
	 * 
	 * 
	 * @param connectionParameter
	 *            : Les paramètres de connexion
	 */
	@SuppressWarnings("PMD.LongVariable")
	@Before(value = "execution( void fr.urssaf.image.sae.dfce.admin.services.connection.ConnectionService.setConnectionParameter(..)) && args(connectionParameter)")
	public final void setConnectionParameter(
			final ConnectionParameter connectionParameter) {
		Validate.notNull(
				connectionParameter,
				MessageHandler.getMessage(
						"connection.parameters.required", "connection.impact",
						"connection.action"));
		Validate.notNull(
				connectionParameter.getHost(),
				MessageHandler.getMessage(
						"connection.parameters.host.required", "connection.impact",
						"connection.action"));
		Validate.notNull(
				connectionParameter.getUser(),
				MessageHandler.getMessage(
						"connection.parameters.user.required", "connection.impact",
						"connection.action"));
	}

}
