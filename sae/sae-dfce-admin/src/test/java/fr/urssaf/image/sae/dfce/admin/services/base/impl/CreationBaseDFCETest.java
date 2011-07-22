package fr.urssaf.image.sae.dfce.admin.services.base.impl;

import java.io.FileNotFoundException;

import junit.framework.Assert;
import net.docubase.toolkit.service.ServiceProvider;

import org.junit.Test;

import fr.urssaf.image.sae.dfce.admin.model.DataBaseModel;
import fr.urssaf.image.sae.dfce.admin.services.CommonTestComponents;
import fr.urssaf.image.sae.dfce.admin.services.exceptions.BaseAdministrationServiceEx;
import fr.urssaf.image.sae.dfce.admin.services.exceptions.ConnectionServiceEx;
import fr.urssaf.image.sae.dfce.admin.utils.BaseUtils;


public class CreationBaseDFCETest extends CommonTestComponents {
	/**
	 * Test unitaire sur la création d'une base
	 * 
	 * @throws ConnectionServiceEx
	 *             Lorsque la connexion ne s'est pas bien déroulée
	 * @throws BaseAdministrationServiceEx
	 *             Lorsque le servive de création ne s'est pas bien déroulée
	 * @throws FileNotFoundException
	 */
	@Test
	public void createBase() throws ConnectionServiceEx,
			BaseAdministrationServiceEx, FileNotFoundException {
		getConnectionService().setConnectionParameter(getConnectionParameter());
		getConnectionService().openConnection();
		final DataBaseModel dataModel = getBaseAdmiService().getDataBaseModel(
				BaseUtils.BASE_XML_FILE, getXmlDBModelService());
		getBaseAdmiService().createBase(dataModel, getXmlDBModelService());
		final String baseSae = dataModel.getDataBase().getBaseId();
		Assert.assertEquals(baseSae.trim(), ServiceProvider
				.getBaseAdministrationService().getBase(baseSae.trim())
				.getBaseId());
	}

}
