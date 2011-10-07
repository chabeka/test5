package fr.urssaf.image.sae.storage.dfce.mapping;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.docubase.toolkit.model.ToolkitFactory;
import net.docubase.toolkit.model.base.Base;
import net.docubase.toolkit.model.base.BaseCategory;
import net.docubase.toolkit.model.document.Criterion;
import net.docubase.toolkit.model.document.Document;
import net.docubase.toolkit.service.ServiceProvider;

import org.apache.commons.io.FilenameUtils;
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
	 * @param serviceDFCE
	 *            : Les services DFCE.
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
			final List<StorageMetadata> desiredMetaDatas,
			final ServiceProvider serviceDFCE) throws StorageException,
			IOException {
		// on construit la liste des métadonnées à partir de la liste des
		// métadonnées souhaitées.
		final List<StorageMetadata> metaDatas = storageMetaDatasFromCriterions(
				document, desiredMetaDatas, serviceDFCE);
		return buildStorageDocument(document, metaDatas, serviceDFCE);
	}

	/**
	 * Permet de convertir les métadonnées DFCE vers les métadonnées
	 * StorageDocument.<br/>
	 * 
	 * @param serviceDFCE
	 *            : Les services DFCE.
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
			final Document document,
			final List<StorageMetadata> desiredMetaData,
			final ServiceProvider serviceDFCE) throws StorageException,
			IOException {
		final List<StorageMetadata> metaDatas = storageMetaDatasFromCriterions(
				document, desiredMetaData, serviceDFCE);
		return new StorageDocument(metaDatas);
	}

	/**
	 * Construit la liste des {@link StorageMetadata} à partir de la liste des
	 * {@link Criterion}.
	 * 
	 * @param serviceDFCE
	 *            : Les services DFCE.
	 * @param document
	 *            : Le document DFCE.
	 * @param desiredMetaData
	 *            : La liste des métadonnées souhaitées.
	 * @return La liste des {@link StorageMetadata} à partir de la liste des
	 *         {@link Criterion}.
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	private static List<StorageMetadata> storageMetaDatasFromCriterions(
			final Document document,
			final List<StorageMetadata> desiredMetaData,
			final ServiceProvider serviceDFCE) {
		final Set<StorageMetadata> metadatas = new HashSet<StorageMetadata>();
		if (document != null) {
			final List<Criterion> criterions = document.getAllCriterions();
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
						if (criterion.getCategoryName().equalsIgnoreCase(
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
						metadatas.add(completedMetadatas(document, metadata,
								serviceDFCE));
					}
				}

			}
		}
		return new ArrayList<StorageMetadata>(metadatas);
	}

	/**
	 * @param shortCode
	 *            : Le code court.
	 * @param storageDocument
	 *            : Le document
	 * @return le nom du fichier ainsi que l'extension
	 * @throws ParseException
	 *             Exception lévée lorsque le parsing du nom du fichier ne se
	 *             passe pas bien.
	 */
	public static String[] findFileNameAndExtension(
			final StorageDocument storageDocument, final String shortCode)
			throws ParseException {
		String value = null;
		for (StorageMetadata storageMetadata : Utils
				.nullSafeIterable(storageDocument.getMetadatas())) {
			// ici on exclut toutes les métadonnées techniques
			if (shortCode.equals(storageMetadata.getShortCode().trim())
					&& storageMetadata.getValue() != null) {
				value = String.valueOf(storageMetadata.getValue());
				break;
			}
		}

		return new String[] { FilenameUtils.getBaseName(value),
				FilenameUtils.getExtension(value) };
	}

	/**
	 * Construit une occurrence de storageDocument à partir d'un document DFCE.
	 * 
	 * @param serviceDFCE
	 *            : Les services DFCE.
	 * @param document
	 *            : Le document DFCE.
	 * @param listMetaData
	 *            : La liste des métadonnées.
	 * @throws IOException
	 *             Exception levée lorsque qu'un dysfonctionnement se produit
	 *             lors des I/O.
	 */
	private static StorageDocument buildStorageDocument(
			final Document document, final List<StorageMetadata> listMetaData,
			final ServiceProvider serviceDFCE) throws IOException {
		// Instance de StorageDocument
		final StorageDocument storageDocument = new StorageDocument();
		if (document != null) {
			final InputStream docContent = serviceDFCE.getStoreService()
					.getDocumentFile(document);
			storageDocument.setCreationDate(document.getCreationDate());
			storageDocument.setTitle(document.getTitle());
			storageDocument.setContent(IOUtils.toByteArray(docContent));
			storageDocument.setUuid(document.getUuid());
			storageDocument.setMetadatas(listMetaData);
		}
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
		final Document document = ToolkitFactory.getInstance()
				.createDocumentTag(baseDFCE);
		for (StorageMetadata storageMetadata : Utils
				.nullSafeIterable(storageDocument.getMetadatas())) {
			// ici on exclut toutes les métadonnées techniques
			if (!StringUtils.isEmpty(storageMetadata.getShortCode())
					&& storageMetadata.getValue() != null) {
				final StorageTechnicalMetadatas technical = Utils
						.technicalMetadataFinder(storageMetadata.getShortCode());
				switch (technical) {
				case TITRE:
					document.setTitle(String.valueOf(storageMetadata.getValue()));
					break;
				case DATE_CREATION:
					// Si la date de creation est définie on remplace la date du
					// jour par la dite date
					if (storageMetadata.getValue() != null) {
						dateCreation = (Date) storageMetadata.getValue();
					}
					document.setCreationDate(dateCreation);
					break;
				case TYPE:
					document.setType(String.valueOf(storageMetadata.getValue()));
					break;

				case DATE_DEBUT_CONSERVATION:
					document.setLifeCycleReferenceDate((Date) storageMetadata
							.getValue());
					break;

				// Pas de mutateur pour ces métadonnées ci-dessous par
				// conséquent on ne va fait rien.
				case GEL:
				case DUREE_CONSERVATION:
				case TRACABILITE_POST_ARCHIVAGE:
				case HASH:
				case TYPE_HASH:
				case NOM_FICHIER:
				case TAILLE_FICHIER:
				case DOCUMENT_VIRTUEL:
				case START_PAGE:
				case END_PAGE:
				case DATE_ARCHIVE:
				case VERSION_NUMBER:
				case DATE_MODIFICATION:
					break;
				// pour les autres métadonnées métiers on crée des bases
				// catégories.
				case NOVALUE:
					baseCategory = baseDFCE.getBaseCategory(storageMetadata
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
	 * @param serviceDFCE
	 *            : Les services DFCE.
	 * @param document
	 *            : le document retourné par DFCE.
	 * @param metadata
	 *            : La métadonnée désirés.
	 * @return
	 */
	// CHECKSTYLE:OFF
	private static StorageMetadata completedMetadatas(final Document document,
			final StorageMetadata metadata, final ServiceProvider serviceDFCE) {
		StorageMetadata metadataFound = null;

		final StorageTechnicalMetadatas technical = Utils
				.technicalMetadataFinder(metadata.getShortCode());

		switch (technical) {
		// Lors de la capture les trois dates ci-dessous ont la même valeur ce
		// qui implique qu'a la consultation elles ont également la même valeur.
		case DATE_MODIFICATION:
		case DATE_CREATION:
		case DATE_ARCHIVE:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.getCreationDate());
			break;
		case DATE_DEBUT_CONSERVATION:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.getLifeCycleReferenceDate());
			break;
		case DUREE_CONSERVATION:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					serviceDFCE.getStorageAdministrationService()
							.getLifeCycleRule(document.getType()).getLifeCycleLength());
			break;
		case TITRE:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.getTitle());
			break;
		case TYPE:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.getType());
			break;
		case TYPE_HASH:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.getDigestAlgorithm());
			break;
		case HASH:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.getDigest());
			break;
		case VERSION_NUMBER:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.getVersion());
			break;
		case START_PAGE:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.getStartPage());
			break;
		case END_PAGE:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.getEndPage());
			break;
		case TAILLE_FICHIER:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.getSize());
			break;
		case NOM_FICHIER:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.getFilename().concat(".").concat( document.getExtension()));
			break;
		case DOCUMENT_VIRTUEL:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					document.isVirtual());
			break;
		case GEL:
			metadataFound = new StorageMetadata(metadata.getShortCode(),
					serviceDFCE.getStoreService().isFrozen(document));
			break;

		case TRACABILITE_POST_ARCHIVAGE:
		case NOVALUE:

			// Lorsque la métadonnée n'existe pas il faut la retourner avec une
			// valeur vide
		default:
			metadataFound = new StorageMetadata(metadata.getShortCode(), "");
		}
		return metadataFound;
	}
	// CHECKSTYLE:ON

}
