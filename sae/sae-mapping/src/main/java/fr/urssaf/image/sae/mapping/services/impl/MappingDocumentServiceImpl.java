package fr.urssaf.image.sae.mapping.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.bo.model.bo.SAEDocument;
import fr.urssaf.image.sae.bo.model.bo.SAEMetadata;
import fr.urssaf.image.sae.bo.model.untyped.UntypedDocument;
import fr.urssaf.image.sae.bo.model.untyped.UntypedMetadata;
import fr.urssaf.image.sae.mapping.exception.InvalidSAETypeException;
import fr.urssaf.image.sae.mapping.exception.MappingFromReferentialException;
import fr.urssaf.image.sae.mapping.model.TechnicalMetadatas;
import fr.urssaf.image.sae.mapping.services.MappingDocumentService;
import fr.urssaf.image.sae.mapping.utils.Utils;
import fr.urssaf.image.sae.metadata.exceptions.ReferentialException;
import fr.urssaf.image.sae.metadata.referential.model.MetadataReference;
import fr.urssaf.image.sae.metadata.referential.services.MetadataReferenceDAO;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;

/**
 * Classe qui fournit des services de conversion entre objet du modèle et objet
 * technique de stockage.
 * 
 * @author akenore
 * 
 */
@Service
@Qualifier("mappingDocumentService")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class MappingDocumentServiceImpl implements MappingDocumentService {
	@Autowired
	@Qualifier("metadataReferenceDAO")
	private MetadataReferenceDAO referenceDAO;

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "PMD.AvoidInstantiatingObjectsInLoops",
			"PMD.DataflowAnomalyAnalysis" })
	public StorageDocument saeDocumentToStorageDocument(final SAEDocument saeDoc)
			throws InvalidSAETypeException {
		final StorageDocument storageDoc = new StorageDocument();
		final List<StorageMetadata> sMetadata = new ArrayList<StorageMetadata>();
		storageDoc.setContent(saeDoc.getContent());
		storageDoc.setFilePath(saeDoc.getFilePath());
		for (SAEMetadata metadata : Utils.nullSafeIterable(saeDoc
				.getMetadatas())) {
			final TechnicalMetadatas technical = Utils
					.technicalMetadataFinder(metadata.getLongCode());
			switch (technical) {
			case DATECREATION:
				storageDoc.setCreationDate((Date) metadata.getValue());
				break;
			case TITRE:
				storageDoc.setTitle(String.valueOf(metadata.getValue()));
				break;
			case TYPE:
				storageDoc.setTypeDoc(String.valueOf(metadata.getValue()));
				break;
			case NOVALUE:
			default:
				sMetadata.add(new StorageMetadata(metadata.getShortCode(),
						metadata.getValue()));
			}
			storageDoc.setMetadatas(sMetadata);

		}
		return storageDoc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public SAEDocument storageDocumentToSaeDocument(
			final StorageDocument storageDoc) throws InvalidSAETypeException,
			MappingFromReferentialException {
		final SAEDocument saeDoc = new SAEDocument();
		final List<SAEMetadata> metadatas = new ArrayList<SAEMetadata>();
		saeDoc.setContent(storageDoc.getContent());
		saeDoc.setFilePath(storageDoc.getFilePath());
		for (StorageMetadata sMetadata : Utils.nullSafeIterable(storageDoc
				.getMetadatas())) {
			try {
				final MetadataReference reference = referenceDAO
						.getByShortCode(sMetadata.getShortCode());
				metadatas.add(new SAEMetadata(reference.getLongCode(),
						reference.getShortCode(), sMetadata.getValue()));
			} catch (ReferentialException refExcpt) {
				throw new MappingFromReferentialException(refExcpt);
			}

		}
		saeDoc.setMetadatas(metadatas);
		return saeDoc;

	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public UntypedDocument saeDocumentToUntypedDocument(final SAEDocument saeDoc)
			throws InvalidSAETypeException, MappingFromReferentialException {
		final List<UntypedMetadata> metadatas = new ArrayList<UntypedMetadata>();

		for (SAEMetadata metadata : Utils.nullSafeIterable(saeDoc
				.getMetadatas())) {
			try {
				final MetadataReference reference = referenceDAO
						.getByLongCode(metadata.getLongCode());

				metadatas.add(new UntypedMetadata(metadata.getLongCode(), Utils
						.convertToString(metadata.getValue(), reference)));
			} catch (ParseException parseExcept) {
				throw new InvalidSAETypeException(parseExcept);
			} catch (ReferentialException refExcpt) {
				throw new MappingFromReferentialException(refExcpt);
			}
		}

		return new UntypedDocument(saeDoc.getFilePath(), saeDoc.getContent(),
				metadatas);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public SAEDocument untypedDocumentToSaeDocument(
			final UntypedDocument untyped) throws InvalidSAETypeException,
			MappingFromReferentialException {
		final List<SAEMetadata> metadatas = new ArrayList<SAEMetadata>();
		for (UntypedMetadata metadata : Utils.nullSafeIterable(untyped
				.getUMetadatas())) {
			try {
				final MetadataReference reference = referenceDAO
						.getByLongCode(metadata.getLongCode());
				metadatas.add(new SAEMetadata(reference.getLongCode(),
						reference.getShortCode(), Utils.conversionToObject(
								metadata.getValue(), reference)));
			} catch (ParseException parseExcept) {
				throw new InvalidSAETypeException(parseExcept);
			} catch (ReferentialException refExcpt) {
				throw new MappingFromReferentialException(refExcpt);
			}
		}

		return new SAEDocument(untyped.getFilePath(), untyped.getContent(),
				metadatas);
	}

	/**
	 * @param referenceDAO
	 *            : Le service du référentiel
	 */
	public void setReferenceDAO(final MetadataReferenceDAO referenceDAO) {
		this.referenceDAO = referenceDAO;
	}

	/**
	 * @return Le service du référentiel
	 */
	public MetadataReferenceDAO getReferenceDAO() {
		return referenceDAO;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public UntypedDocument storageDocumentToUntypedDocument(
			final StorageDocument storage) throws InvalidSAETypeException,
			MappingFromReferentialException {
		final List<UntypedMetadata> metadatas = new ArrayList<UntypedMetadata>();
		for (StorageMetadata metadata : Utils.nullSafeIterable(storage
				.getMetadatas())) {
			try {
				final MetadataReference reference = referenceDAO
						.getByShortCode(metadata.getShortCode());
				metadatas.add(new UntypedMetadata(metadata.getShortCode(),
						Utils.convertToString(metadata.getValue(), reference)));
			} catch (ParseException parseExcept) {
				throw new InvalidSAETypeException(parseExcept);
			} catch (ReferentialException refExcpt) {
				throw new MappingFromReferentialException(refExcpt);
			}
		}

		return new UntypedDocument(storage.getFilePath(), storage.getContent(),
				metadatas);
	}

}
