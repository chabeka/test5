package fr.urssaf.image.sae.dfce.admin.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.docubase.toolkit.model.ToolkitFactory;
import net.docubase.toolkit.model.base.Base;
import net.docubase.toolkit.model.base.BaseCategory;
import net.docubase.toolkit.model.reference.Category;
import net.docubase.toolkit.service.ServiceProvider;
import net.docubase.toolkit.service.administration.StorageAdministrationService;
import fr.urssaf.image.sae.dfce.admin.model.DataBaseModel;
import fr.urssaf.image.sae.dfce.admin.model.SaeCategory;

/**
 * Contient des services de gestion de bean
 * 
 * @author akenore
 * 
 */
@SuppressWarnings("PMD.TooManyMethods" )
public final class BaseUtils {
	/** le répertoire de base */
	private static final String BASE_DIR = "src/main/resources/saeBase/";

	/** le fichier xml de la base */
	public static final File BASE_XML_FILE = new File(BASE_DIR + "saeBase.xml");

	/**
	 * @param base
	 *            : La base DFCE.
	 * @param dataBaseModel
	 *            : Le modèle de base de donnée.
	 * @return La base
	 */
	public static Base initTechnicalMetadata(final Base base,
			final DataBaseModel dataBaseModel) {

		// on définit les caractéristiques de la base
		base.setDescription(dataBaseModel.getBase().getBaseDescription());
		base.setDocumentCreationDateConfiguration(dataBaseModel.getBase()
				.documentCreationDateConfiguration());
		base.setDocumentOverlayFormConfiguration(dataBaseModel.getBase()
				.documentOverlayFormConfiguration());
		base.setDocumentOwnerDefault(dataBaseModel.getBase()
				.documentOwnerType());
		base.setDocumentOwnerModify(dataBaseModel.getBase()
				.isDocumentOwnerModify());
		base.setDocumentTitleMask(dataBaseModel.getBase()
				.getDocumentTitleMask());
		base.setDocumentTitleMaxSize(dataBaseModel.getBase()
				.getDocumentTitleMaxSize());
		base.setDocumentTitleModify(dataBaseModel.getBase()
				.isDocumentTitleModify());
		base.setDocumentTitleSeparator(dataBaseModel.getBase()
				.getDocumentTitleSeparator());
		return base;
	}

	/**
	 * 
	 * @param dataBaseModel
	 *            : Le model de base
	 * @return une liste de baseCategories
	 */
	@SuppressWarnings({ "PMD.AvoidUsingShortType",
			"PMD.DataflowAnomalyAnalysis" })
	public static List<BaseCategory> initBaseCategories(
			final DataBaseModel dataBaseModel) {
		final List<BaseCategory> baseCategories = new ArrayList<BaseCategory>();
		final StorageAdministrationService stAdmiService = ServiceProvider
				.getStorageAdministrationService();
		final ToolkitFactory toolkit = ToolkitFactory.getInstance();
		for (SaeCategory category : Utils.nullSafeIterable(dataBaseModel
				.getDataBase().getSaeCategories().getCategories())) {
			final Category categoryDfce = stAdmiService.findOrCreateCategory(
					category.getName(), category.categoryDataType());
			final BaseCategory baseCategory = toolkit.createBaseCategory(
					categoryDfce, category.isIndex());
			baseCategory.setEnableDictionary(category.isEnableDictionary());
			baseCategory.setMaximumValues((short) category.getMaximumValues());
			baseCategory.setMinimumValues((short) category.getMinimumValues());
			baseCategory.setSingle(category.isSingle());
			baseCategories.add(baseCategory);
		}
		return baseCategories;
	}

	/**
	 * Contrôle la cohérence entre les catégories et les index composites
	 * 
	 * @param dataBaseModel
	 *            : Le model de base
	 * @return True si tous les index existent bien en temps que catégorie.
	 *         sinon false
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public static List<List<Category>> buildIndexComposite(
			final DataBaseModel dataBaseModel) {
		final List<List<Category>> indexComposites = new ArrayList<List<Category>>();
		for (String indexComp : Utils.nullSafeIterable(dataBaseModel
				.getIndexComposites())) {
			final List<Category> indexComposite = new ArrayList<Category>();
			final List<String> index = Arrays.asList(indexComp.split(","));
			if (index != null && index.size() > 1) {
				boolean find = true;
				for (String indexC : Utils.nullSafeIterable(index)) {
					find &= indexCompositeFinder(indexC, dataBaseModel);
				}
				if (find) {
					for (String indexC : Utils.nullSafeIterable(index)) {
						final Category category = ServiceProvider
								.getStorageAdministrationService().getCategory(
										indexC);
						indexComposite.add(category);
					}
				}
			}
			indexComposites.add(indexComposite);
		}
		return indexComposites;
	}

	/**
	 * 
	 * @param index
	 *            : l'indexComposite
	 * @param dataBaseModel
	 *            : Le model de base
	 * @return True si l'index définie par l'utilisateur existe dans les
	 *         catégories
	 */
	private static boolean indexCompositeFinder(final String index,
			final DataBaseModel dataBaseModel) {
		boolean find = false;
		for (SaeCategory category : Utils.nullSafeIterable(dataBaseModel
				.getDataBase().getSaeCategories().getCategories())) {
			if (category.getName().contains(index)) {
				find = true;
				break;
			}
		}
		return find;
	}

	/**
	 * Cette classe ne doit pas avoir de constructeur
	 */
	private BaseUtils() {
		assert false;
	}

	/**
	 * Crée les indexComposites d'une base
	 * 
	 * @param dataBaseModel
	 *            : Le model de base
	 */
	@SuppressWarnings({ "PMD.AvoidUsingShortType",
			"PMD.DataflowAnomalyAnalysis",
			"PMD.AvoidInstantiatingObjectsInLoops" })
	public static void createIndexComposite(final DataBaseModel dataBaseModel) {
		final List<List<Category>> indexComposites = BaseUtils
				.buildIndexComposite(dataBaseModel);
		for (List<Category> indexComposite : Utils
				.nullSafeIterable(indexComposites)) {
			final Category[] categories = new Category[indexComposite.size()];
			int position = 0;
			for (Category category : indexComposite) {
				categories[position] = category;
				position++;
			}
			ServiceProvider.getStorageAdministrationService()
					.findOrCreateCompositeIndex(categories);
		}
	}

}
