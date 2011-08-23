package fr.urssaf.image.sae.metadata.referential.services.impl;

import java.util.Map;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.metadata.exceptions.ReferentialException;
import fr.urssaf.image.sae.metadata.referential.model.MetadataReference;
import fr.urssaf.image.sae.metadata.referential.services.MetadataReferenceDAO;
import fr.urssaf.image.sae.metadata.referential.services.XmlDataService;

/**
 * Contient les tests sur les services de désérialisation du referentiel des
 * métadonnées
 * 
 * @author akenore
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-sae-metadata.xml" })
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class AbstractService {
	@Autowired
	@Qualifier("metadataReferenceDAO")
	private MetadataReferenceDAO referenceDAO;
	

	@Autowired
	@Qualifier("xmlDataService")
	private XmlDataService xmlService;
	private Map<String, MetadataReference> allMetadatas;
	private Map<String, MetadataReference> consultable;
	private Map<String, MetadataReference> searchable;
	private Map<String, MetadataReference> archivable;

	

	/**
	 * @param xmlService
	 *            : Le service de lecture du flux Xml.
	 */
	public final void setXmlService(final XmlDataService xmlService) {
		this.xmlService = xmlService;
	}

	/**
	 * @return Le service de lecture du flux Xml.
	 */
	public final XmlDataService getXmlService() {
		return xmlService;
	}
	
	
	/**
	 * @return Le service de manipulation du referentiel des métadonnées.
	 */
	public final MetadataReferenceDAO getMetadataReferenceDAO() {
		return referenceDAO;
	}

	/**
	 * @param referenceDAO
	 *            : Le service de manipulation du referentiel des métadonnées.
	 */
	public final void setMetadataReferenceDAO(
			final MetadataReferenceDAO referenceDAO) {
		this.referenceDAO = referenceDAO;
	}

	/**
	 * @return Les métadonnées du referentiel
	 */
	public final Map<String, MetadataReference> getAllMetadatas() {
		return allMetadatas;
	}

	/**
	 * @param allMetadatas
	 *            : Les métadonnées du referentiel
	 */
	public final void setAllMetadatas(final Map<String, MetadataReference> allMetadatas) {
		this.allMetadatas = allMetadatas;
	}

	/**
	 * @return Les métadonnées autorisées à la consultation.
	 */
	public final Map<String, MetadataReference> getConsultable() {
		return consultable;
	}

	/**
	 * @param consultable
	 *            Les métadonnées autorisées à la consultation.
	 */
	public final void setConsultable(final Map<String, MetadataReference> consultable) {
		this.consultable = consultable;
	}

	/**
	 * @return Les métadonnées autorisées à la recherche.
	 */
	public final Map<String, MetadataReference> getSearchable() {
		return searchable;
	}

	/**
	 * @param searchable
	 *            : Les métadonnées autorisées à la recherche.
	 */
	public final void setSearchable(final Map<String, MetadataReference> searchable) {
		this.searchable = searchable;
	}

	/**
	 * @return Les métadonnées autorisées à l'archivage.
	 */
	public final Map<String, MetadataReference> getArchivable() {
		return archivable;
	}

	/**
	 * @param archivable
	 *            : Les métadonnées autorisées à l'archivage.
	 */
	public final void setArchivable(final Map<String, MetadataReference> archivable) {
		this.archivable = archivable;
	}
/**
 * 
 * @throws ReferentialException Exception 
 */
	@Before
	public final void initReferentielMetaData() throws ReferentialException {
		allMetadatas = referenceDAO.getAllMetadataReferences();
		consultable = referenceDAO.getConsultableMetadataReferences();
		searchable = referenceDAO.getSearchableMetadataReferences();
		archivable = referenceDAO.getArchivableMetadataReferences();
	}
	/**
	 * @return Le service d'accès au référentiel des métadonnées.
	 */
	public final MetadataReferenceDAO getReferenceDAO() {
		return referenceDAO;
	}

	/**
	 * @param referenceDAO Le service d'accès au référentiel des métadonnées.
	 */
	public final void setReferenceDAO(final MetadataReferenceDAO referenceDAO) {
		this.referenceDAO = referenceDAO;
	}
}
