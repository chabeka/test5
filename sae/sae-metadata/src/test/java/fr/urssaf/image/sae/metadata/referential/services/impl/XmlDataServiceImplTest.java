package fr.urssaf.image.sae.metadata.referential.services.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.metadata.referential.model.MetadataReference;
import fr.urssaf.image.sae.metadata.referential.services.XmlDataService;

/**
 * Contient les tests de désérialisation du referentiel des métadonnées
 * 
 * @author akenore
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-sae-metadata.xml" })
public class XmlDataServiceImplTest {
	@Autowired
	@Qualifier("xmlDataService")
	private XmlDataService xmlService;

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
	public XmlDataService getXmlService() {
		return xmlService;
	}
	@Test
	public void getReferentielMetaData() throws IOException {
		final File xmlFile = new File(getClass().getResource("/referentiel.xml")
				.getPath());
		final Map<String, MetadataReference> ref = xmlService
				.referentialReader(xmlFile);
		Assert.assertTrue("Le nombre de métadonnées doit être égal à 43",
				ref.size() == 43);
	}

}
