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


public class DeletionBaseDFCETest extends CommonTestComponents {
	/**
	 * Permet de supprimer une base
	 * @throws ConnectionServiceEx Exception levée lorsque la connection ne se passe pas bien.
	 * @throws BaseAdministrationServiceEx  Exception levée lorsque la le service de suppression ne se passe pas bien.
	 * @throws FileNotFoundException Exception levée lorsque le fichier n'existe pas.
	 */
	@Test
	public void deleteBase() throws ConnectionServiceEx,
			BaseAdministrationServiceEx, FileNotFoundException {
		getConnectionService().setConnectionParameter(getConnectionParameter());
		getConnectionService().openConnection();
		final DataBaseModel dataModel = getBaseAdmiService().getDataBaseModel(BaseUtils.BASE_XML_FILE, getXmlDBModelService());
		getBaseAdmiService().deleteBase(dataModel);
		final String baseSae = dataModel.getDataBase().getBaseId();
		Assert.assertNull( ServiceProvider
				.getBaseAdministrationService().getBase(baseSae.trim()));
	}
	
	
}
