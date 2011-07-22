package fr.urssaf.image.sae.dfce.admin.services.base.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import net.docubase.toolkit.exception.ObjectAlreadyExistsException;
import net.docubase.toolkit.model.base.Base;
import net.docubase.toolkit.model.base.BaseCategory;
import net.docubase.toolkit.service.ServiceProvider;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.dfce.admin.messages.MessageHandler;
import fr.urssaf.image.sae.dfce.admin.model.DataBaseModel;
import fr.urssaf.image.sae.dfce.admin.services.AbstractService;
import fr.urssaf.image.sae.dfce.admin.services.base.BaseAdministrationService;
import fr.urssaf.image.sae.dfce.admin.services.exceptions.BaseAdministrationServiceEx;
import fr.urssaf.image.sae.dfce.admin.services.xml.XmlDataService;
import fr.urssaf.image.sae.dfce.admin.utils.BaseUtils;
import fr.urssaf.image.sae.dfce.admin.utils.Utils;

/**
 * Implémente l'interface : {@link BaseAdministrationService}
 * <ul>
 * <li>
 * createBase : Service de création de la base.</li>
 * <li>
 * deleteBase : Service de suppression de la base.</li>
 * </ul>
 * 
 * @author akenore
 * 
 */
@Service
@Qualifier("baseAdministrationService")
public class BaseAdministrationServiceImpl extends AbstractService implements
		BaseAdministrationService {
	private static final Logger LOGGER = Logger
			.getLogger("BaseAdministrationService");

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @throws FileNotFoundException
	 *             Exception levée lorsque le fichier n'existe pas.
	 * 
	 * 
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void createBase(final DataBaseModel dataBaseModel,
			final XmlDataService xmlDataService)
			throws BaseAdministrationServiceEx, FileNotFoundException {
		try {
			LOGGER.info(MessageHandler.getMessage("database.initialization",dataBaseModel.toString()));
			// Instantiation d'une base
			final Base base = getBaseDfce(dataBaseModel.getBase().getBaseId());
			LOGGER.info(MessageHandler
					.getMessage("database.initialization.technical.metadatas"));
			// Création des métadonnées techniques
			final Base baseDfce = BaseUtils.initTechnicalMetadata(base,
					dataBaseModel);
			LOGGER.info(MessageHandler
					.getMessage("database.initialization.base.categories"));
			// Création des baseCatégories
			final List<BaseCategory> baseCategories = BaseUtils
					.initBaseCategories(dataBaseModel);
			LOGGER.info(MessageHandler
					.getMessage("database.dictionnary.creation"));
			for (BaseCategory baseCategory : Utils
					.nullSafeIterable(baseCategories)) {
				baseDfce.addBaseCategory(baseCategory);
				BaseUtils.addDictionnary(baseCategory, xmlDataService);
			}
			LOGGER.info(MessageHandler.getMessage("database.creation"));
			// Création de la base
			ServiceProvider.getBaseAdministrationService().createBase(baseDfce);
			LOGGER.info(MessageHandler
					.getMessage("database.index.composite.creation"));
			// Création des indexes composites
			BaseUtils.createIndexComposite(dataBaseModel);
			ServiceProvider.getBaseAdministrationService().startBase(base);

		} catch (ObjectAlreadyExistsException objAlreadyExistsEx) {
			throw new BaseAdministrationServiceEx(
					MessageHandler
							.getMessage(
									MessageHandler
											.getMessage(
													"database.already.exists",
													dataBaseModel.getBase()
															.getBaseId()),
									MessageHandler
											.getMessage("database.already.exist.impact"),
									MessageHandler
											.getMessage("database.alread.yexist.action")),
					objAlreadyExistsEx);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public final void deleteBase(final DataBaseModel dataBaseModel) {
		final Base base = getBaseDfce(dataBaseModel.getBase().getBaseId());
		ServiceProvider.getBaseAdministrationService().stopBase(base);
		ServiceProvider.getBaseAdministrationService().deleteBase(base);
	}

	/**
	 * {@inheritDoc}
	 */
	public final DataBaseModel getDataBaseModel(final File xmlBaseModel,
			final XmlDataService xmlDataService) throws FileNotFoundException {

		return xmlDataService.baseModelReader(xmlBaseModel);
	}

}
