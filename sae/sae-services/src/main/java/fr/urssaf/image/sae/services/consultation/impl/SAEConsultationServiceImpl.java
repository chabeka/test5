package fr.urssaf.image.sae.services.consultation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.bo.model.untyped.UntypedDocument;
import fr.urssaf.image.sae.mapping.exception.InvalidSAETypeException;
import fr.urssaf.image.sae.mapping.exception.MappingFromReferentialException;
import fr.urssaf.image.sae.mapping.services.MappingDocumentService;
import fr.urssaf.image.sae.metadata.exceptions.ReferentialException;
import fr.urssaf.image.sae.metadata.referential.model.MetadataReference;
import fr.urssaf.image.sae.metadata.referential.services.MetadataReferenceDAO;
import fr.urssaf.image.sae.services.consultation.SAEConsultationService;
import fr.urssaf.image.sae.services.dispatchers.SAEServiceDispatcher;
import fr.urssaf.image.sae.services.document.impl.AbstractSAEServices;
import fr.urssaf.image.sae.services.exception.consultation.SAEConsultationServiceException;
import fr.urssaf.image.sae.services.factory.SAEStorageFactory;
import fr.urssaf.image.sae.storage.exception.ConnectionServiceEx;
import fr.urssaf.image.sae.storage.exception.RetrievalServiceEx;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;

/**
 * Implémentation du service {@link SAEConsultationService}
 * 
 */
@Service
@Qualifier("saeConsultationService")
public class SAEConsultationServiceImpl extends AbstractSAEServices implements
		SAEConsultationService {

	private final MetadataReferenceDAO referenceDAO;
	private final SAEServiceDispatcher serviceDispatcher;
	private final MappingDocumentService mappingService;

	/**
	 * attribution des paramètres de l'implémentation
	 * 
	 * @param referenceDAO
	 *            instance du service des métadonnées de référence
	 * @param mappingService
	 *            instance du service de mapping du SAE
	 * @param serviceDispatcher
	 *            Le dispatcher
	 * 
	 */
	@Autowired
	public SAEConsultationServiceImpl(
			 @Qualifier("saeServiceDispatcher") final SAEServiceDispatcher serviceDispatcher,
			final MetadataReferenceDAO referenceDAO,
			final MappingDocumentService mappingService) {
		super();
		this.referenceDAO = referenceDAO;
		this.mappingService = mappingService;
		this.serviceDispatcher = serviceDispatcher;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final UntypedDocument consultation(UUID idArchive)
			throws SAEConsultationServiceException {
		UntypedDocument untypedDocument = null;
		try {
			this.getStorageServiceProvider().openConnexion();
			final List<StorageMetadata> metadatas = new ArrayList<StorageMetadata>();

			for (Entry<String, MetadataReference> reference : this.referenceDAO
					.getDefaultConsultableMetadataReferences().entrySet()) {

				metadatas.add(SAEStorageFactory.createStorageMetadata(reference
						.getValue().getShortCode()));
			}
			final UUIDCriteria uuidCriteria = new UUIDCriteria(idArchive,
					metadatas);

			final StorageDocument storageDocument = this
					.getStorageServiceProvider().getStorageDocumentService()
					.retrieveStorageDocumentByUUID(uuidCriteria);
			if (storageDocument != null) {
				untypedDocument = this.mappingService
						.storageDocumentToUntypedDocument(storageDocument);
			}

		} catch (RetrievalServiceEx e) {
			serviceDispatcher.dispatch(new SAEConsultationServiceException(e));

		} catch (ReferentialException e) {
			serviceDispatcher.dispatch(new SAEConsultationServiceException(e));

		} catch (InvalidSAETypeException e) {
			serviceDispatcher.dispatch(new SAEConsultationServiceException(e));

		} catch (MappingFromReferentialException e) {
			serviceDispatcher.dispatch(new SAEConsultationServiceException(e));

		} catch (ConnectionServiceEx e) {
			serviceDispatcher.dispatch(new SAEConsultationServiceException(e));
		} finally {
			this.getStorageServiceProvider().closeConnexion();
		}
		return untypedDocument;
	}

}
