package fr.urssaf.image.sae.metadata.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.bo.model.bo.SAEDocument;
import fr.urssaf.image.sae.bo.model.untyped.UntypedDocument;
import fr.urssaf.image.sae.metadata.control.services.MetadataControlServices;

/**
 * 
 * @author akenore
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-sae-metadata.xml" })
public class ValidationServiceImplTest {
	@Autowired
	@Qualifier("metadataControlServices")
	private MetadataControlServices controlService;

	/**
	 * validation de l'argument du service
	 * {@link fr.urssaf.image.sae.metadata.control.services.impl.MetadataControlServicesImpl#checkArchivableMetadata(SAEDocument)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void checkArchivableDocument() {
		controlService.checkArchivableMetadata(null);
	}

	/**
	 * validation de l'argument du service
	 * {@link fr.urssaf.image.sae.metadata.control.services.impl.MetadataControlServicesImpl#checkArchivableMetadata(SAEDocument)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void checkArchivableMetadata() {
		controlService.checkArchivableMetadata(new SAEDocument());
	}

	/**
	 * validation de l'argument du service
	 * {@link fr.urssaf.image.sae.metadata.control.services.impl.MetadataControlServicesImpl#checkConsultableMetadata(java.util.List)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void checkConsultableMetadata() {
		controlService.checkConsultableMetadata(null);
	}

	/**
	 * validation de l'argument du service
	 * {@link fr.urssaf.image.sae.metadata.control.services.impl.MetadataControlServicesImpl#checkDuplicateMetadata(java.util.List)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void checkDuplicateMetadata() {
		controlService.checkDuplicateMetadata(null);
	}

	/**
	 * validation de l'argument du service
	 * {@link fr.urssaf.image.sae.metadata.control.services.impl.MetadataControlServicesImpl#checkExistingMetadata(UntypedDocument)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void checkExistingDocument() {
		controlService.checkExistingMetadata(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkExistingMetadata() {
		controlService.checkExistingMetadata(new UntypedDocument());
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkMetadataValueTypeAndFormatDoc() {
		controlService.checkMetadataValueTypeAndFormat(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkMetadataValueTypeAndFormatMetadata() {
		controlService.checkMetadataValueTypeAndFormat(new UntypedDocument());
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkRequiredMetadata() {
		controlService.checkRequiredMetadata(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkSearchableMetadata() {
		controlService.checkSearchableMetadata(null);
	}

	/**
	 * @return Le service de contrôle des métadonnées.
	 */
	public final MetadataControlServices getControlService() {
		return controlService;
	}

	/**
	 * @param controlService
	 *            : Le service de contrôle des métadonnées.
	 */
	public final void setControlService(
			final MetadataControlServices controlService) {
		this.controlService = controlService;
	}

}
