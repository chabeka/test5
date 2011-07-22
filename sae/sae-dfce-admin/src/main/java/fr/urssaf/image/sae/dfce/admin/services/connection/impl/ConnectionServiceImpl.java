package fr.urssaf.image.sae.dfce.admin.services.connection.impl;

import java.net.MalformedURLException;
import java.net.URL;

import net.docubase.toolkit.service.Authentication;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.dfce.admin.messages.MessageHandler;
import fr.urssaf.image.sae.dfce.admin.model.ConnectionParameter;
import fr.urssaf.image.sae.dfce.admin.services.AbstractService;
import fr.urssaf.image.sae.dfce.admin.services.connection.ConnectionService;
import fr.urssaf.image.sae.dfce.admin.services.exceptions.ConnectionServiceEx;
/**
 *  Implémente l'interface : {@link ConnectionService}
 * @author akenore
 *
 */
@Service
@Qualifier("connectionService")
public class ConnectionServiceImpl extends AbstractService implements
		ConnectionService {
	
	@SuppressWarnings("PMD.LongVariable")
	private ConnectionParameter connectionParameter;

	/**
	 * 
	 * @return Retourne les paramètres de connexion à la base de stockage
	 */
	public final ConnectionParameter getConnectionParameter() {
		return connectionParameter;
	}

	/**
	 * 
	 * @param connectionParameter
	 *            : Initialise les paramètres de connexion à la base de stockage
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void setConnectionParameter(
			final ConnectionParameter connectionParameter) {
		this.connectionParameter = connectionParameter;
	}

	/**
	 * {@inheritDoc}
	 * @throws ConnectionServiceEx 
	 */
	public final void openConnection() throws ConnectionServiceEx {
		try {
			Authentication.openSession(
					connectionParameter.getUser().getLogin(),
					connectionParameter.getUser().getPassword(),
					buildUrlForConnection());
		} catch (MalformedURLException malURLException) {
			throw new ConnectionServiceEx(MessageHandler.getMessage("url.connection.malformed",
							"connection.impact", "connection.action"), malURLException);
			
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public final void closeConnection() {
		if (Authentication.isSessionActive()) {
			Authentication.closeSession();
		}
	}

	/**
	 * 
	 * @return L'url de connection
	 * @throws MalformedURLException
	 *             : L'exception lorsque la construction de l'url ne s'est pas
	 *             bien construite
	 */
	@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
	public final String buildUrlForConnection() throws MalformedURLException {

		URL urlConnection = new URL("http", connectionParameter.getHost()
				.getHostName(), connectionParameter.getHost().getHostPort(),
				connectionParameter.getHost().getContextRoot());
		if (connectionParameter.getHost().isSecure()) {
			urlConnection = new URL("https", connectionParameter.getHost()
					.getHostName(),
					connectionParameter.getHost().getHostPort(),
					connectionParameter.getHost().getContextRoot());
		}
		return urlConnection.toString();
	}
}
