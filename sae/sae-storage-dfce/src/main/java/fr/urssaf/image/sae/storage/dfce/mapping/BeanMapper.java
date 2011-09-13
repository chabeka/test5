package fr.urssaf.image.sae.storage.dfce.mapping;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.docubase.toolkit.model.ToolkitFactory;
import net.docubase.toolkit.model.base.Base;
import net.docubase.toolkit.model.base.BaseCategory;
import net.docubase.toolkit.model.document.Criterion;
import net.docubase.toolkit.model.document.Document;
import net.docubase.toolkit.service.ServiceProvider;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import fr.urssaf.image.sae.storage.dfce.model.StorageTechnicalMetadatas;
import fr.urssaf.image.sae.storage.dfce.utils.Utils;
import fr.urssaf.image.sae.storage.exception.StorageException;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;

/**
 * Fournit des méthodes statiques de conversion des elements DFCE ceux du SAE.
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
public final class BeanMapper {

	/**
	 * Permet de convertir un {@link Document} en {@link StorageDocument}.<br/>
	 * 
	 * @param document
	 *            : Le document DFCE.
	 * @param desiredMetaDatas
	 *            : Les métadonnées souhaitées.
	 * @return une occurrence de StorageDocument
	 * @throws StorageException
	 *             : Exception levée lorsque qu'un dysfonctionnement se produit.
	 * @throws IOException
	 *             : Exception levée lorsque qu'un dysfonctionnement se produit
	 *             lors des I/O.
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public static StorageDocument dfceDocumentToStorageDocument(
			final Document document,
			final List<StorageMetadata> desiredMetaDatas)
			throws StorageException, IOException {
		// on construit la liste des métadonnées à partir de la liste des
		// métadonnées souhaitées.
		final List<StorageMetadata> metaDatas = storageMetaDatasFromCriterions(
				document, desiredMetaDatas);
		return buildStorageDocument(document, metaDatas);
	}

	/**
	 * Permet de convertir les métadonnées DFCE vers les métadonnées
	 * StorageDocument.<br/>
	 * 
	 * @param document
	 *            : Le document DFCE.
	 * @param desiredMetaData
	 *            : Les métadonnées souhaitées.
	 * @return une occurrence de StorageDocument contenant uniquement les
	 *         métadonnées.
	 * @throws StorageException
	 *             : Exception levée lorsque qu'un dysfonctionnement se produit.
	 * @throws IOException
	 *             : Exception levée lorsque qu'un dysfonctionnement se produit
	 *             lors des I/O.
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public static StorageDocument dfceMetaDataToStorageDocument(
			final Document document, final List<StorageMetadata> desiredMetaData)
			throws StorageException, IOException {
		List<StorageMetadata> metaDatas = storageMetaDatasFromCriterions(
				document, desiredMetaData);
		return new StorageDocument(metaDatas);
	}

	/**
	 * Construit la liste des {@link StorageMetadata} à partir de la liste des
	 * {@link Criterion}.
	 * 
	 * @param document
	 *            : Le document DFCE.
	 * @param desiredMetaData
	 *            : La liste des métadonnées souhaitées.
	 * @return La liste des {@link StorageMetadata} à partir de la liste des
	 *         {@link Criterion}.
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	private static List<StorageMetadata> storageMetaDatasFromCriterions(
			final Document document, final List<StorageMetadata> desiredMetaData) {
		List<StorageMetadata> metadatas = new ArrayList<StorageMetadata>();
		if (document != null) {
			List<Criterion> criterions = document.getAllCriterions();
			// dans le cas de l'insertion d'un document
			if (desiredMetaData == null) {
				for (Criterion criterion : Utils.nullSafeIterable(criterions)) {
					metadatas.add(new StorageMetadata(criterion
							.getCategoryName(), criterion.getWord()));
				}
			} else {
				// Traitement pour filtrer sur la liste des métadonnées
				// souhaitées.
				for (StorageMetadata metadata : Utils
						.nullSafeIterable(desiredMetaData)) {
					boolean found = false;
					for (Criterion criterion : Utils
							.nullSafeIterable(criterions)) {
						if (criterion.getCategoryName().contains(
								metadata.getShortCode())) {
							metadatas.add(new StorageMetadata(metadata
									.getShortCode(), criterion.getWord()));
							found = true;
							break;
						}
					}
					// si les métadonnées ne sont pas dans DFCE on vérifie si
					// ces
					// métadonnées sont des métadonnées techniques sinon on les
					// retourne avec la valeur vide
					if (!found) {
						metadatas.add(completedMetadatas(document, metadata));
					}
				}

			}
		}
		return metadatas;
	}

	/**
	 * Construit une occurrence de storageDocument à partir d'un document DFCE.
	 * 
	 * @param document
	 *            : Le document DFCE.
	 * @param listMetaData
	 *            : La liste des métadonnées.
	 * @throws IOException
	 *             Exception levée lorsque qu'un dysfonctionnement se produit
	 *             lors des I/O.
	 */
	private static StorageDocument buildStorageDocument(
			final Document document, List<StorageMetadata> listMetaData)
			throws IOException {
		// Instance de StorageDocument
		StorageDocument storageDocument = new StorageDocument();
		InputStream docContent = ServiceProvider.getStoreService()
				.getDocumentFile(document);
		storageDocument.setCreationDate(document.getCreationDate());
		storageDocument.setTitle(document.getTitle());
		storageDocument.setContent(IOUtils.toByteArray(docContent));
		storageDocument.setUuid(document.getUuid());
		storageDocument.setMetadatas(listMetaData);
		return storageDocument;
	}

	/**
	 * Permet de convertir {@link StorageDocument} en {@link Document}.
	 * 
	 * @param baseDFCE
	 *            : La base dfce
	 * @param storageDocument
	 *            : Un StorageDocment.
	 * @return Un document DFCE à partir d'un storageDocment.
	 * @throws ParseException
	 *             Exception si le parsing de la date ne se passe pas bien.
	 */
	// CHECKSTYLE:OFF
	public static Document storageDocumentToDfceDocument(final Base baseDFCE,
			final StorageDocument storageDocument) throws ParseException {
		BaseCategory baseCategory = null;
		Date dateCreation = new Date();
		Base base = ServiceProvider.getBaseAdministrationService().getBase(
				baseDFCE.getBaseId());
		Document document = ToolkitFactory.getInstance()
				.createDocumentTag(base);

		for (StorageMetadata storageMetadata : Utils
				.nullSafeIterable(storageDocument.getMetadatas())) {
			// ici on exclut toutes les métadonnées techniques
			if (!StringUtils.isEmpty(storageMetadata.getShortCode())
					&& storageMetadata.getValue() != null) {
				final StorageTechnicalMetadatas technical = Utils
						.technicalMetadataFinder(storageMetadata.getShortCode());
				switch (technical) {
				case DATECREATION:
					// Si la date de creation est définie on remplace la date du
					// jour par la
					// dite date
					if (storageMetadata.getValue() != null) {
						dateCreation = (Date) storageMetadata.getValue();
					}
					document.setCreationDate(dateCreation);
					break;
				case TITRE:
					document.setTitle(String.valueOf(storageMetadata.getValue()));
					break;
				case TYPE:
					document.setType(String.valueOf(storageMetadata.getValue()));
					break;
				case TYPEHASH:
				case OBJECTTYPE:
				case TRACABILITEPOSTARCHIVAGE:
				case GEL:
				case HASH:
				case REFERENCEUUID:
				case DATEARCHIVE:
				case CURRENTVERSIONNUMBER:
				case VERSIONNUMBER:
				case STARTPAGE:
				case DUREECONSERVATION:
				case NOMFICHIER:
				case ENDPAGE:
				case TAILLEFICHIER:
				case DATEMODIFICATION:
					break;
				case NOVALUE:
					baseCategory = base.getBaseCategory(storageMetadata
							.getShortCode());
					document.addCriterion(baseCategory,
							storageMetadata.getValue());
					break;
				default:
					break;
				}
			}
		}
		return document;
	}

	// CHECKSTYLE:ON
	/** Cette classe n'est pas faite pour être instanciée. */
	private BeanMapper() {
		assert false;
	}

	/**
	 * Permet d'extraire la métadonnée technique à partir de la métadonnée
	 * souhaitée.
	 * 
	 * @param document
	 *            : le document retourné par DFCE.
	 * @param metadata
	 *            : La métadonnée désirés.
	 * @return
	 */
	// CHECKSTYLE:OFF
	private static StorageMetadata completedMetadatas(final Document document,
			final StorageMetadata metadata) {
		StorageMetadata metadataFound = null;

		final StorageTechnicalMetadatas technical = Utils
				.technicalMetadataFinder(metadata.getShortCode());

		switch (technical) {
		case DATECREATION:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.getCreationDate());
			break;
		case TITRE:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.getTitle());
			break;
		case TYPE:
			// TODO : pour l'instant pas de getter pour le type du document
			// par défaut ca va être du pdf.
			metadataFound = new StorageMetadata(metadata.getShortCode(), "PDF");
			break;
		case TYPEHASH:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.getDigestAlgorithm());
			break;
		case HASH:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.getDigest());
			break;
		case REFERENCEUUID:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.getVirtualReferenceUUID());
			break;
		// TODO : pour l'instant pas de getter pour la date d'archivage
		case DATEARCHIVE:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.getCreationDate());
			break;
		case CURRENTVERSIONNUMBER:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.getCurrentVersionNumber());
			break;
		// TODO : pour l'instant pas de getter pour récupérer la version
		case VERSIONNUMBER:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.getCurrentVersionNumber());
			break;
		case STARTPAGE:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.getVirtualStartPage());
			break;
		case ENDPAGE:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.getVirtualEndPage());
			break;
		// Pour l'instant pas de getters pour ces métadonnées
		case TAILLEFICHIER:
		case NOVALUE:
		case DATEMODIFICATION:
		case NOMFICHIER:
		case GEL:
		case OBJECTTYPE:
		default:
			metadataFound = new StorageMetadata(metadata.getShortCode(), "");

		}
		return metadataFound;
	}
	// CHECKSTYLE:ON
}
