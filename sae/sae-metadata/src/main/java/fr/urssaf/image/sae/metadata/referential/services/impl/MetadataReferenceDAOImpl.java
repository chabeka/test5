package fr.urssaf.image.sae.metadata.referential.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;



import fr.urssaf.image.sae.control.messages.MessageHandler;
import fr.urssaf.image.sae.metadata.exceptions.ReferentialException;
import fr.urssaf.image.sae.metadata.referential.model.MetadataReference;
import fr.urssaf.image.sae.metadata.referential.services.MetadataReferenceDAO;
import fr.urssaf.image.sae.metadata.referential.services.XmlDataService;
import fr.urssaf.image.sae.metadata.utils.Utils;

/**
 * Classe qui implémente l'interface
 * {@link fr.urssaf.image.sae.metadata.referential.services.MetadataReferenceDAO
 * MetadataReferenceService}
 * 
 * @author akenore
 * 
 */
@Service
@Qualifier("metadataReferenceDAO")
public class MetadataReferenceDAOImpl implements MetadataReferenceDAO {
	@Autowired
	@Qualifier("xmlDataService")
	private XmlDataService xmlDataService;

	/**
	 * @return Le service Xml
	 */
	public final XmlDataService getXmlDataService() {
		return xmlDataService;
	}

	/**
	 * @param xmlDataService
	 *            : Le service Xml
	 */
	public final void setXmlDataService(final XmlDataService xmlDataService) {
		this.xmlDataService = xmlDataService;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws ReferentialException
	 *             Exception lever lorsque la récupération des métadonnées ne
	 *             sont pas disponibles.
	 */
	public final Map<String, MetadataReference> getAllMetadataReferences()
			throws ReferentialException {
		final File xmlFile = new File(getClass()
				.getResource("/referentiel.xml").getPath());
		try {
			return xmlDataService.referentialReader(xmlFile);
		} catch (FileNotFoundException fileNotFoundExcpt) {
			throw new ReferentialException(MessageHandler.getMessage(
					"referential.file.notfound", xmlFile.getAbsolutePath()),
					fileNotFoundExcpt);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	public final Map<String, MetadataReference> getConsultableMetadataReferences()
			throws ReferentialException {
		final Map<String, MetadataReference> csltMetaDatas = new HashMap<String, MetadataReference>();
		final Map<String, MetadataReference> referentiel = getAllMetadataReferences();

		for (Map.Entry<String, MetadataReference> metaData : Utils.nullSafeMap(
				referentiel).entrySet()) {
			if (metaData.getValue().isConsultable()) {
				csltMetaDatas.put(metaData.getKey(), metaData.getValue());
			}
		}
		return csltMetaDatas;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	public final Map<String, MetadataReference> getSearchableMetadataReferences()
			throws ReferentialException {
		final Map<String, MetadataReference> srchMetaDatas = new HashMap<String, MetadataReference>();
		final Map<String, MetadataReference> referentiel = getAllMetadataReferences();
		for (Map.Entry<String, MetadataReference> metaData : Utils.nullSafeMap(
				referentiel).entrySet()) {
			if (metaData.getValue().isSearchable()) {
				srchMetaDatas.put(metaData.getKey(), metaData.getValue());
			}
		}
		return srchMetaDatas;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	public final Map<String, MetadataReference> getArchivableMetadataReferences()
			throws ReferentialException {
		final Map<String, MetadataReference> archMetaDatas = new HashMap<String, MetadataReference>();
		final Map<String, MetadataReference> referentiel = getAllMetadataReferences();
		for (Map.Entry<String, MetadataReference> metaData : Utils.nullSafeMap(
				referentiel).entrySet()) {
			if (metaData.getValue().isArchivable()) {
				archMetaDatas.put(metaData.getKey(), metaData.getValue());
			}
		}
		return archMetaDatas;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	public final MetadataReference getByLongCode(final String longCode)
			throws ReferentialException {

		return getAllMetadataReferences().get(longCode);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
	public final MetadataReference getByShortCode(final String shortCode)
			throws ReferentialException {
		MetadataReference metadatafound = null;
		for (Entry<String, MetadataReference> reference : Utils.nullSafeMap(
				getAllMetadataReferences()).entrySet()) {
			if (reference.getValue().getShortCode().equals(shortCode)) {
				metadatafound = reference.getValue();
			}
		}
		return metadatafound;
	}

	/**
	 * {@inheritDoc}
	 */
	public final Map<String, MetadataReference> getRequiredMetadataReferences()
			throws ReferentialException {
		final Map<String, MetadataReference> reqMetaDatas = new HashMap<String, MetadataReference>();
		final Map<String, MetadataReference> referentiel = getAllMetadataReferences();
		for (Map.Entry<String, MetadataReference> metaData : Utils.nullSafeMap(
				referentiel).entrySet()) {
			if (metaData.getValue().isRequired()) {
				reqMetaDatas.put(metaData.getKey(), metaData.getValue());
			}
		}
		return reqMetaDatas;
	}

}
