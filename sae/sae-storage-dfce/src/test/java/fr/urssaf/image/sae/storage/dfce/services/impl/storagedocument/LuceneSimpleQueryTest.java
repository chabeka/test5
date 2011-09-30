package fr.urssaf.image.sae.storage.dfce.services.impl.storagedocument;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import fr.urssaf.image.sae.storage.dfce.data.constants.Constants;
import fr.urssaf.image.sae.storage.dfce.data.model.LuceneQueries;
import fr.urssaf.image.sae.storage.dfce.data.model.LuceneQuery;
import fr.urssaf.image.sae.storage.dfce.services.StorageServices;
import fr.urssaf.image.sae.storage.exception.SearchingServiceEx;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.LuceneCriteria;

/**
 * Classe de test des services de recherche.
 * 
 * @author Rhofir, aknore.
 * 
 */

public class LuceneSimpleQueryTest extends StorageServices {
	/**
	 * Test de recherche par requête Lucene.
	 * 
	 * @throws FileNotFoundException
	 *             L'exception levé lorsque le fichier n'existe pas.
	 * @throws SearchingServiceEx
	 */
	@Test
	@Ignore
	public void luceneQueriesWithWildcard() throws FileNotFoundException,
			SearchingServiceEx {
		final Map<String, LuceneCriteria> queries = buildQueries("wildcard");
		for (Map.Entry<String, LuceneCriteria> query : queries.entrySet()) {
			Assert.assertTrue(query.getKey(), getSearchingService()
					.searchStorageDocumentByLuceneCriteria(query.getValue())
					.getAllStorageDocuments().size() > 0);
		}
	}

	/**
	 * Test de recherche par requête Lucene.
	 * 
	 * @throws FileNotFoundException
	 *             L'exception levé lorsque le fichier n'existe pas.
	 * @throws SearchingServiceEx
	 */
	@Test
	@Ignore
	public void luceneQueries() throws FileNotFoundException,
			SearchingServiceEx {
		final Map<String, LuceneCriteria> queries = buildQueries("simple");
		for (Map.Entry<String, LuceneCriteria> query : queries.entrySet()) {
			Assert.assertTrue(query.getKey(), getSearchingService()
					.searchStorageDocumentByLuceneCriteria(query.getValue())
					.getAllStorageDocuments().size() > 0);
		}
	}

	/**
	 * Test de recherche par requête Lucene.
	 * 
	 * @throws FileNotFoundException
	 *             L'exception levé lorsque le fichier n'existe pas.
	 * @throws SearchingServiceEx
	 */
	@Test
	@Ignore
	public void luceneQueriesWithRange() throws FileNotFoundException,
			SearchingServiceEx {
		final Map<String, LuceneCriteria> queries = buildQueries("range");
		for (Map.Entry<String, LuceneCriteria> query : queries.entrySet()) {
			Assert.assertTrue(query.getKey(), getSearchingService()
					.searchStorageDocumentByLuceneCriteria(query.getValue())
					.getAllStorageDocuments().size() > 0);
		}
	}

	/**
	 * Test de recherche par requête Lucene.
	 * 
	 * @throws FileNotFoundException
	 *             L'exception levé lorsque le fichier n'existe pas.
	 * @throws SearchingServiceEx
	 */
	@Test
	@Ignore
	public void luceneQueriesWithOperatorAnd() throws FileNotFoundException,
			SearchingServiceEx {
		final Map<String, LuceneCriteria> queries = buildQueries("withOperatorAnd");
		for (Map.Entry<String, LuceneCriteria> query : queries.entrySet()) {
			Assert.assertTrue(query.getKey(), getSearchingService()
					.searchStorageDocumentByLuceneCriteria(query.getValue())
					.getAllStorageDocuments().size() > 0);
		}
	}

	/**
	 * Test de recherche par requête Lucene avec l'opérateur OR.
	 * 
	 * @throws FileNotFoundException
	 *             L'exception levé lorsque le fichier n'existe pas.
	 * @throws SearchingServiceEx
	 */
	@Test
	@Ignore
	public void luceneQueriesWithOperatorOr() throws FileNotFoundException,
			SearchingServiceEx {
		final Map<String, LuceneCriteria> queries = buildQueries("withOperatorOr");
		for (Map.Entry<String, LuceneCriteria> query : queries.entrySet()) {
			Assert.assertTrue(query.getKey(), getSearchingService()
					.searchStorageDocumentByLuceneCriteria(query.getValue())
					.getAllStorageDocuments().size() > 0);
		}
	}

	/**
	 * Test de recherche par requête Lucene avec l'opérateur OR.
	 * 
	 * @throws FileNotFoundException
	 *             L'exception levé lorsque le fichier n'existe pas.
	 * @throws SearchingServiceEx
	 */
	@Test
	@Ignore
	public void luceneQueriesWithOperatorAndOr() throws FileNotFoundException,
			SearchingServiceEx {
		final Map<String, LuceneCriteria> queries = buildQueries("withOperatorAndOr");
		for (Map.Entry<String, LuceneCriteria> query : queries.entrySet()) {
			Assert.assertTrue(query.getKey(), getSearchingService()
					.searchStorageDocumentByLuceneCriteria(query.getValue())
					.getAllStorageDocuments().size() > 0);
		}
	}

	/**
	 * Test de recherche par requête Lucene avec l'opérateur OR.
	 * 
	 * @throws FileNotFoundException
	 *             L'exception levé lorsque le fichier n'existe pas.
	 * @throws SearchingServiceEx
	 */
	@Test
	@Ignore
	public void luceneQueriesWithOperatorNot() throws FileNotFoundException,
			SearchingServiceEx {
		final Map<String, LuceneCriteria> queries = buildQueries("withOperatorNot");
		for (Map.Entry<String, LuceneCriteria> query : queries.entrySet()) {
			Assert.assertTrue(query.getKey(), getSearchingService()
					.searchStorageDocumentByLuceneCriteria(query.getValue())
					.getAllStorageDocuments().size() > 0);
		}

	}

	/**
	 * Permet de construire une serie de requête lucene à partir d'un type.
	 * 
	 * @param queryType
	 *            : Le type de requête.
	 * @return Une liste de requête suivant le type de requête en entrée.
	 * @throws FileNotFoundException
	 *             L'exception levé lorsque le fichier n'existe pas.
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public Map<String, LuceneCriteria> buildQueries(final String queryType)
			throws FileNotFoundException {

		final LuceneQueries queries = getXmlDataService().queriesReader(
				new File(Constants.XML_FILE_QUERIES[0]));
		final Map<String, LuceneCriteria> luceneCriteria = new HashMap<String, LuceneCriteria>();
		for (LuceneQuery query : queries.getLuceneQuery()) {
			if (query.getQueryType().contains(queryType)) {
				final String saeQuery = query.getQuery();
				final List<String> metadatas = Arrays.asList(query
						.getDesiredMetadata().split(","));
				final List<StorageMetadata> stMetadata = new ArrayList<StorageMetadata>();
				for (String metadata : metadatas) {
					stMetadata.add(new StorageMetadata(metadata.trim()));
				}
				luceneCriteria.put(query.getQueryId(), new LuceneCriteria(
						saeQuery, 100, stMetadata));
			}
		}
		return luceneCriteria;
	}
}
