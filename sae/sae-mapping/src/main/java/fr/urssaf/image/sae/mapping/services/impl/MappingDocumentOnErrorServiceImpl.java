package fr.urssaf.image.sae.mapping.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.bo.model.SAEError;
import fr.urssaf.image.sae.bo.model.bo.SAEDocumentOnError;
import fr.urssaf.image.sae.bo.model.bo.SAEMetadata;
import fr.urssaf.image.sae.bo.model.untyped.UntypedDocumentOnError;
import fr.urssaf.image.sae.bo.model.untyped.UntypedMetadata;
import fr.urssaf.image.sae.mapping.exception.InvalidSAETypeException;
import fr.urssaf.image.sae.mapping.exception.MappingFromReferentialException;
import fr.urssaf.image.sae.mapping.model.TechnicalMetadatas;
import fr.urssaf.image.sae.mapping.services.MappingDocumentOnErrorService;
import fr.urssaf.image.sae.mapping.utils.Utils;
import fr.urssaf.image.sae.metadata.exceptions.ReferentialException;
import fr.urssaf.image.sae.metadata.referential.model.MetadataReference;
import fr.urssaf.image.sae.metadata.referential.services.MetadataReferenceDAO;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocumentOnError;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;

/**
 * Classe qui fournit des services de conversion entre objet du modèle et objet
 * technique de stockage.
 * 
 * @author akenore
 * 
 */
@Service
@Qualifier("mappingDocumentOnErrorService")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class MappingDocumentOnErrorServiceImpl implements
		MappingDocumentOnErrorService {
	@Autowired
	@Qualifier("metadataReferenceDAO")
	private MetadataReferenceDAO referenceDAO;

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public UntypedDocumentOnError saeDocumentOnErrorToUntypedDocumentOnError(
			final SAEDocumentOnError saeDocOnError)
			throws InvalidSAETypeException, MappingFromReferentialException {
		final List<UntypedMetadata> metadatas = new ArrayList<UntypedMetadata>();
		for (SAEMetadata metadata : Utils.nullSafeIterable(saeDocOnError
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
		return new UntypedDocumentOnError(saeDocOnError.getContent(),
				metadatas, saeDocOnError.getErrors());
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public SAEDocumentOnError storageDocumentOnErrorToSaeDocumentOnError(
			final StorageDocumentOnError storageDocOnError)
			throws InvalidSAETypeException, MappingFromReferentialException {
		final SAEDocumentOnError saeDocOnError = new SAEDocumentOnError();
		final List<SAEMetadata> metadatas = new ArrayList<SAEMetadata>();
		final List<SAEError> errors = new ArrayList<SAEError>();
		saeDocOnError.setContent(storageDocOnError.getContent());
		errors.add(new SAEError(storageDocOnError.getCodeError(),
				storageDocOnError.getMessageError()));
		saeDocOnError.setErrors(errors);
		for (StorageMetadata sMetadata : Utils
				.nullSafeIterable(storageDocOnError.getMetadatas())) {
			metadatas.add(new SAEMetadata(null, sMetadata.getShortCode(),
					sMetadata.getValue()));
		}
		// Ajout des métadonnées techniques
		for (SAEMetadata metadata : Utils
				.nullSafeIterable(retrieveTechnicalMetadata(storageDocOnError))) {
			metadatas.add(metadata);
		}
		saeDocOnError.setMetadatas(metadatas);
		return saeDocOnError;

	}

	/**
	 * Permet de retourner les métadonnées techniques suite au
	 * dysfonctionnement.
	 * 
	 * @param storageDocOnError
	 *            : le document technique de stokage en erreur
	 * @return Les métadonnées techniques suite au dysfonctionnement.
	 * @throws MappingFromReferentialException
	 */
	@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
	private List<SAEMetadata> retrieveTechnicalMetadata(
			final StorageDocumentOnError storageDocOnError)
			throws MappingFromReferentialException {
		final List<SAEMetadata> metadatas = new ArrayList<SAEMetadata>();
		MetadataReference reference = null;
		try {
			if (storageDocOnError.getTitle() != null) {

				reference = referenceDAO
						.getByShortCode(TechnicalMetadatas.TITRE.getShortCode());
				metadatas
						.add(new SAEMetadata(reference.getLongCode(), reference
								.getShortCode(), storageDocOnError.getTitle()));
			}
			if (storageDocOnError.getCreationDate() != null) {
				reference = referenceDAO
						.getByShortCode(TechnicalMetadatas.DATECREATION
								.getShortCode());
				metadatas.add(new SAEMetadata(reference.getLongCode(),
						reference.getShortCode(), storageDocOnError
								.getCreationDate()));
			}
			if (storageDocOnError.getTypeDoc() != null) {
				reference = referenceDAO.getByShortCode(TechnicalMetadatas.TYPE
						.getShortCode());
				metadatas.add(new SAEMetadata(reference.getLongCode(),
						reference.getShortCode(), storageDocOnError
								.getTypeDoc()));
			}
		} catch (ReferentialException refExcpt) {
			throw new MappingFromReferentialException(refExcpt);
		}
		return metadatas;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public UntypedDocumentOnError storageDocumentOnErrorToUntypedDocumentOnError(
			final StorageDocumentOnError storageDocOnError)
			throws InvalidSAETypeException {
		final List<UntypedMetadata> metadatas = new ArrayList<UntypedMetadata>();
		for (StorageMetadata metadata : Utils
				.nullSafeIterable(storageDocOnError.getMetadatas())) {
			try {
				final MetadataReference reference = referenceDAO
						.getByShortCode(metadata.getShortCode());
				metadatas.add(new UntypedMetadata(metadata.getShortCode(),
						Utils.convertToString(metadata.getValue(), reference)));
			} catch (ParseException parseExcept) {
				throw new InvalidSAETypeException(parseExcept);
			} catch (ReferentialException referentielExcept) {
				throw new InvalidSAETypeException(referentielExcept);
			}
		}

		return new UntypedDocumentOnError(storageDocOnError.getContent(),
				metadatas, null);
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

}
