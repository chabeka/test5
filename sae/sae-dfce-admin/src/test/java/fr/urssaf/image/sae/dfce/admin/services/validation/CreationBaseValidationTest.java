package fr.urssaf.image.sae.dfce.admin.services.validation;

import java.io.FileNotFoundException;

import org.junit.Test;

import fr.urssaf.image.sae.dfce.admin.model.DataBaseModel;
import fr.urssaf.image.sae.dfce.admin.services.CommonTestComponents;
import fr.urssaf.image.sae.dfce.admin.services.exceptions.BaseAdministrationServiceEx;
import fr.urssaf.image.sae.dfce.admin.services.exceptions.ConnectionServiceEx;
import fr.urssaf.image.sae.dfce.admin.utils.BaseUtils;

/**
 * Classe permettant de tester la validation par aspect du service de création.
 * 
 * @author akenore
 * 
 */
public class CreationBaseValidationTest extends CommonTestComponents {
	/**
	 * Test unitaire de validation de la création d'une base
	 * 
	 * @throws ConnectionServiceEx
	 *             Lorsque la connexion ne s'est pas bien déroulée
	 * @throws BaseAdministrationServiceEx
	 *             Lorsque le servive de création ne s'est pas bien déroulée
	 * @throws FileNotFoundException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void createBase_base() throws ConnectionServiceEx,
			BaseAdministrationServiceEx, FileNotFoundException {
		getConnectionService().setConnectionParameter(getConnectionParameter());
		getConnectionService().openConnection();
		getBaseAdmiService().createBase(null, getXmlDBModelService());

	}

	/**
	 * Test unitaire de validation sur la création d'une base
	 * 
	 * @throws ConnectionServiceEx
	 *             Lorsque la connexion ne s'est pas bien déroulée
	 * @throws BaseAdministrationServiceEx
	 *             Lorsque le servive de création ne s'est pas bien déroulée
	 * @throws FileNotFoundException
	 */
	@Test(expected = NullPointerException.class)
	public void createBase_service() throws ConnectionServiceEx,
			BaseAdministrationServiceEx, FileNotFoundException {
		getConnectionService().setConnectionParameter(getConnectionParameter());
		getConnectionService().openConnection();
		final DataBaseModel dataModel = getBaseAdmiService().getDataBaseModel(
				BaseUtils.BASE_XML_FILE, getXmlDBModelService());
		getBaseAdmiService().createBase(dataModel, null);
	}

}
