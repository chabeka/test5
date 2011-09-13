package fr.urssaf.image.sae.dfce.admin.services.connection.impl;

import net.docubase.toolkit.service.Authentication;

import org.junit.Assert;
import org.junit.Test;

import fr.urssaf.image.sae.dfce.admin.services.AbstractComponents;
import fr.urssaf.image.sae.dfce.admin.services.exceptions.ConnectionServiceEx;


public class ConnectionServiceTest extends AbstractComponents {

	/**
	 * Test de manière unitaire si la connexion est ouverte
	 * 
	 * @throws ConnectionServiceEx
	 *             Lorsque la connexion ne s'est pas bien déroulée
	 */
	@Test
	public final void openConnection() throws ConnectionServiceEx {
		getConnectionService().setConnectionParameter(getConnectionParameter());
		getConnectionService().openConnection();
		Assert.assertTrue(Authentication.isSessionActive());
	}

	/**
	 * Test de manière unitaire si la connexion est fermée
	 * 
	 * */
	@Test
	public final void closeConnection() throws ConnectionServiceEx {
		getConnectionService().setConnectionParameter(getConnectionParameter());
		getConnectionService().openConnection();
		getConnectionService().closeConnection();
		Assert.assertFalse(Authentication.isSessionActive());

	}

}
