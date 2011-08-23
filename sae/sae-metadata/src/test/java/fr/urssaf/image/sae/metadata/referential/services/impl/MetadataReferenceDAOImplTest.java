package fr.urssaf.image.sae.metadata.referential.services.impl;

import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import fr.urssaf.image.sae.metadata.exceptions.ReferentialException;
import fr.urssaf.image.sae.metadata.referential.model.MetadataReference;
import fr.urssaf.image.sae.metadata.utils.Utils;

/**
 * Contient les tests sur les services de manipulation du referentiel des
 * métadonnées.
 * 
 * @author akenore
 */

public class MetadataReferenceDAOImplTest extends AbstractService {
	/**
	 * Permet de tester la récupération des métadonnées du référentiel.
	 * 
	 * @throws IOException
	 *             Exception levée lorsqu'il y'a un dysfonctionnement.
	 * @throws ReferentialException
	 *             Exception levée lorsqu'il y'a un dysfonctionnement.
	 */
	@Test
	public void getAllMetadataReferences() throws IOException,
			ReferentialException {
		Assert.assertTrue(getAllMetadatas().size() == 43);
	}

	/**
	 * Permet de tester la récupération des métadonnées consultables du
	 * référentiel.
	 * 
	 * @throws IOException
	 *             Exception levée lorsqu'il y'a un dysfonctionnement.
	 * @throws ReferentialException
	 *             Exception levée lorsqu'il y'a un dysfonctionnement.
	 */
	@Test
	public void getConsultableMetadataReferences() throws IOException,
			ReferentialException {
		Assert.assertTrue(getConsultable().size() == 40);
	}

	/**
	 * Permet de valider la liste des métadonnées consultables du référentiel.
	 * 
	 * @throws IOException
	 *             Exception levée lorsqu'il y'a un dysfonctionnement.
	 * @throws ReferentialException
	 *             Exception levée lorsqu'il y'a un dysfonctionnement.
	 */
	@Test
	public void getValidateConsultableMetadata() throws IOException,
			ReferentialException {
		for (Map.Entry<String, MetadataReference> metaData : Utils.nullSafeMap(
				getConsultable()).entrySet()) {
			Assert.assertTrue(metaData.getValue().isConsultable());
		}
	}

	/**
	 * Permet de tester la récupération des métadonnées autorisée à la
	 * rechercher du référentiel.
	 * 
	 * @throws IOException
	 *             Exception levée lorsqu'il y'a un dysfonctionnement.
	 * @throws ReferentialException
	 *             Exception levée lorsqu'il y'a un dysfonctionnement.
	 */
	@Test
	public void getSearchableMetadataReferences() throws IOException,
			ReferentialException {
		Assert.assertTrue(getSearchable().size() == 24);
	}

	/**
	 * Permet de valider la liste des métadonnées autorisées à la recherche du
	 * référentiel.
	 * 
	 * @throws IOException
	 *             Exception levée lorsqu'il y'a un dysfonctionnement.
	 * @throws ReferentialException
	 *             Exception levée lorsqu'il y'a un dysfonctionnement.
	 */
	@Test
	public void getValidateSearchableMetadata() throws IOException,
			ReferentialException {
		for (Map.Entry<String, MetadataReference> metaData : Utils.nullSafeMap(
				getSearchable()).entrySet()) {
			Assert.assertTrue(metaData.getValue().isSearchable());
		}
	}

	/**
	 * Permet de tester la récupération des métadonnées archivables du
	 * référentiel.
	 * 
	 * @throws IOException
	 *             Exception levée lorsqu'il y'a un dysfonctionnement.
	 * @throws ReferentialException
	 *             Exception levée lorsqu'il y'a un dysfonctionnement.
	 */
	@Test
	public void getArchivableMetadataReferences() throws IOException,
			ReferentialException {
		Assert.assertTrue(getArchivable().size() == 36);
	}

	/**
	 * Permet de valider la liste des métadonnées archivable du référentiel.
	 * 
	 * @throws IOException
	 *             Exception levée lorsqu'il y'a un dysfonctionnement.
	 * @throws ReferentialException
	 *             Exception levée lorsqu'il y'a un dysfonctionnement.
	 */
	@Test
	public void getValidateArchivableMetadata() throws IOException,
			ReferentialException {
		for (Map.Entry<String, MetadataReference> metaData : Utils.nullSafeMap(
				getArchivable()).entrySet()) {
			Assert.assertTrue(metaData.getValue().isArchivable());
		}
	}

	/**
	 * Permet de tester la récupération d'une métadonnées du référentiel à
	 * partir du code long.
	 * 
	 * @throws IOException
	 *             Exception levée lorsqu'il y'a un dysfonctionnement.
	 * @throws ReferentialException
	 *             Exception levée lorsqu'il y'a un dysfonctionnement.
	 */
	@Test
	public void getByLongCode() throws IOException, ReferentialException {
		Assert.assertTrue(getMetadataReferenceDAO().getByLongCode("CodeRND") != null);
		Assert.assertTrue(getMetadataReferenceDAO().getByLongCode("CodeRND")
				.getLongCode().equalsIgnoreCase("CodeRND"));
	}

	/**
	 * Permet de tester la récupération d'une métadonnées du référentiel à
	 * partir du code court.
	 * 
	 * @throws IOException
	 *             Exception levée lorsqu'il y'a un dysfonctionnement.
	 * @throws ReferentialException
	 *             Exception levée lorsqu'il y'a un dysfonctionnement.
	 */
	@Test
	public void getByShortCode() throws IOException, ReferentialException {
		Assert.assertTrue(getMetadataReferenceDAO().getByShortCode("RND") != null);
		Assert.assertTrue(getMetadataReferenceDAO().getByShortCode("RND")
				.getShortCode().equalsIgnoreCase("RND"));
	}

}
