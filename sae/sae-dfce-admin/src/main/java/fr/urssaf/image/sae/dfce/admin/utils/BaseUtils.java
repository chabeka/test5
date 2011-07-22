package fr.urssaf.image.sae.dfce.admin.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.docubase.toolkit.model.ToolkitFactory;
import net.docubase.toolkit.model.base.Base;
import net.docubase.toolkit.model.base.BaseCategory;
import net.docubase.toolkit.model.reference.Category;
import net.docubase.toolkit.service.ServiceProvider;
import net.docubase.toolkit.service.administration.StorageAdministrationService;
import fr.urssaf.image.sae.dfce.admin.model.Applications;
import fr.urssaf.image.sae.dfce.admin.model.CodeImage;
import fr.urssaf.image.sae.dfce.admin.model.Codes;
import fr.urssaf.image.sae.dfce.admin.model.Contrats;
import fr.urssaf.image.sae.dfce.admin.model.DataBaseModel;
import fr.urssaf.image.sae.dfce.admin.model.Objects;
import fr.urssaf.image.sae.dfce.admin.model.Organisme;
import fr.urssaf.image.sae.dfce.admin.model.Organismes;
import fr.urssaf.image.sae.dfce.admin.model.SaeCategory;
import fr.urssaf.image.sae.dfce.admin.services.xml.XmlDataService;

/**
 * Contient des services de gestion de bean
 * 
 * @author akenore
 * 
 */
@SuppressWarnings({ "PMD.LongVariable", "PMD.DataflowAnomalyAnalysis",
		"AvoidInstantiatingObjectsInLoops" })
public final class BaseUtils {
	/** le répertoire de base */
	private static final String BASE_DIR = "src/main/resources/saeBase/";
	/** le répertoire de base */
	private static final String DICTIONARY_DIR = BASE_DIR + "dictionary/";
	/** le fichier xml des application sources */
	private static final File APPLICATIONS_XML_FILE = new File(DICTIONARY_DIR
			+ "applications.xml");
	/** le fichier xml des contrats de services */
	private static final File CONTRAT_XML_FILE = new File(DICTIONARY_DIR
			+ "contrats.xml");
	/** le fichier xml des organismes */
	private static final File ORGANISME_XML_FILE = new File(DICTIONARY_DIR
			+ "organisme.xml");
	/** le fichier xml des object types */
	private static final File OBJECT_TYPE_XML_FILE = new File(DICTIONARY_DIR
			+ "objetType.xml");
	/** le fichier xml des code: Code RND, Code Domaine, Code activite */
	private static final File RND_XML_FILE = new File(DICTIONARY_DIR
			+ "codesfonctions.xml");

	/** le fichier xml de la base */
	public static final File BASE_XML_FILE = new File(BASE_DIR
			+ "saeBase.xml");

	/**
	 * @param base : la base.
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
	 * 
	 * @param baseCategory
	 *            : La base catégories
	 * @param xmlDataService
	 *            le service de désérialisation des flux xml
	 * @throws FileNotFoundException
	 *             Lorsque le fichier n'existe pas
	 */

	public static void addDictionnary(final BaseCategory baseCategory,
			final XmlDataService xmlDataService) throws FileNotFoundException {
		final String catName = baseCategory.getCategory().getName();
		// Ajout des Code RND ,Code Domaine, CodeActivite
		if (catName.contains("RND") || catName.contains("DOM")
				|| catName.contains("ACT")) {
			addRndDictionary(baseCategory, xmlDataService);
		}
		// Ajout des contrat de services
		if (catName.contains("CSE")) {
			final Contrats contrats = xmlDataService
					.contratReader(CONTRAT_XML_FILE);
			for (String contrt : Utils.nullSafeIterable(contrats.getContrats())) {
				ServiceProvider.getStorageAdministrationService()
						.addDictionaryTerm(baseCategory, contrt);
			}
		}
		// Ajout des applications
		if (catName.contains("ASO")) {
			final Applications applications = xmlDataService
					.applicationsReader(APPLICATIONS_XML_FILE);
			for (String application : Utils.nullSafeIterable(applications
					.getApplications())) {
				ServiceProvider.getStorageAdministrationService()
						.addDictionaryTerm(baseCategory, application);
			}
		}
		addDicoOtyOrg(baseCategory, xmlDataService, catName);

	}

	/**
	 * Permet d'ajouter les termes OTY et COP, COG SAC.
	 * 
	 * @param baseCategory
	 *            : La base catégorie
	 * @param xmlDataService
	 *            : le service de désérialisation.
	 * @param catName
	 *            : Le nom de la catégorie.
	 * @throws FileNotFoundException
	 *             Exception levée lorsque le fichier n'existye pas.
	 */
	private static void addDicoOtyOrg(final BaseCategory baseCategory,
			final XmlDataService xmlDataService, final String catName)
			throws FileNotFoundException {
		// Ajout des objets
		if (catName.contains("OTY")) {
			final Objects objectsTypes = xmlDataService
					.objectTypeReader(OBJECT_TYPE_XML_FILE);
			for (String object : Utils.nullSafeIterable(objectsTypes
					.getObjects())) {
				ServiceProvider.getStorageAdministrationService()
						.addDictionaryTerm(baseCategory, object);
			}
		}

		if (catName.contains("COP") || catName.contains("COG")
				|| catName.contains("SAC")) {
			final Organismes codeOrganismes = xmlDataService
					.organismesReader(ORGANISME_XML_FILE);
			for (Organisme org : Utils.nullSafeIterable(codeOrganismes
					.getOrganismes())) {
				ServiceProvider.getStorageAdministrationService()
						.addDictionaryTerm(baseCategory, org.getCode());
			}
		}
	}

	/**
	 * 
	 * @param baseCategory
	 *            : La base catégories
	 * @param xmlDataService
	 *            le service de désérialisation des flux xml
	 * @throws FileNotFoundException
	 *             Lorsque le fichier n'existe pas
	 */
	private static void addRndDictionary(final BaseCategory baseCategory,
			final XmlDataService xmlDataService) throws FileNotFoundException {
		final String catName = baseCategory.getCategory().getName();
		final Codes codes = xmlDataService.rndReader(RND_XML_FILE);
		if (catName.contains("RND")) {
			for (CodeImage codeImage : Utils.nullSafeIterable(codes
					.getCodeImages())) {
				if (!StringUtils.isEmpty(codeImage.getCodeRnd())) {
					ServiceProvider.getStorageAdministrationService()
							.addDictionaryTerm(baseCategory,
									codeImage.getCodeRnd());
				}
			}

		}
		addDictionnaryDomAct(baseCategory, catName, codes);
	}

	/**
	 * 
	 * @param baseCategory
	 *            : La base catégorie
	 * @param catName
	 *            : le nom de la catégorie
	 * @param codes
	 *            : les codes
	 */
	private static void addDictionnaryDomAct(final BaseCategory baseCategory,
			final String catName, final Codes codes) {
		if (catName.contains("DOM")) {
			for (CodeImage codeImage : Utils.nullSafeIterable(codes
					.getCodeImages())) {
				if (!StringUtils.isEmpty(codeImage.getCodeFonction())) {
					ServiceProvider.getStorageAdministrationService()
							.addDictionaryTerm(baseCategory,
									codeImage.getCodeFonction());
				}
			}
		}
		if (catName.contains("ACT")) {
			for (CodeImage codeImage : Utils.nullSafeIterable(codes
					.getCodeImages())) {
				if (!StringUtils.isEmpty(codeImage.getCodeActivite())) {
					ServiceProvider.getStorageAdministrationService()
							.addDictionaryTerm(baseCategory,
									codeImage.getCodeActivite());
				}
			}
		}
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
