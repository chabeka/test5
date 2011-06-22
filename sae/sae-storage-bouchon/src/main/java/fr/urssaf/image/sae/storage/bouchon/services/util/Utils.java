package fr.urssaf.image.sae.storage.bouchon.services.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.io.Files;

/**
 * Cette classe contient des méthodes utilitaires
 * 
 * @author akenore
 * 
 */
public final class Utils {
	/** Le composant pour les traces */
	private static final Logger LOGGER = Logger.getLogger(Utils.class);

	/**
	 * Simplifie l'écriture des boucles foreach quand l'argument peut être
	 * {@code null}.
	 * 
	 * @param <T>
	 *            le type des éléments
	 * @param anIterable
	 *            les éléments à parcourir
	 * @return les éléments, ou une collection vide si l'argument était null
	 */
	@SuppressWarnings("PMD.OnlyOneReturn")
	public static <T> Iterable<T> nullSafeIterable(final Iterable<T> anIterable) {
		if (anIterable == null) {
			return Collections.emptyList();
		} else {
			return anIterable;
		}
	}

	/**
	 * Simplifie l'écriture des map
	 * 
	 * @param map
	 *            le type des éléments
	 * @param <K>
	 *            : type
	 * @param <V>
	 *            : type
	 * @return les éléments, ou une map vide si l'argument était null
	 */
	@SuppressWarnings("PMD.OnlyOneReturn")
	public static <K, V> Map<K, V> nullSafeMap(final Map<K, V> map) {
		if (map == null) {
			return Collections.emptyMap();
		} else {
			return map;
		}
	}

	/**
	 * Retourne le contenu d'un fichier
	 * 
	 * @param path
	 *            : Le chemin du fichier
	 * @return le contenu d'un fichier
	 */
	@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
	public static byte[] fileContent(final String path) {
		byte[] content = null;
		try {
			final File file = new File(path);
			content = Files.toByteArray(file);
		} catch (FileNotFoundException e) {
			LOGGER.error("Erreur le n'existe pas " + path + " : "
					+ e.getMessage());
		} catch (IOException ioe) {
			LOGGER.error("Erreur d'E/S lors de la lecture de " + path + " : "
					+ ioe.getMessage());
		}
		return content;
	}

	/**
	 * 
	 * @param map
	 *            : une map
	 * @return Une chaîne de caractère
	 */
	@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
	public static String mapToString(final Map<?, ?> map) {
		final StringBuilder stringBuilder = new StringBuilder("[");
		String sep = "";
		for (Object object : map.keySet()) {
			stringBuilder.append(sep).append(object.toString()).append("=")
					.append(map.get(object).toString());
			sep = "|";
		}
		return stringBuilder.append("]").toString();

	}
/**
 * 
 */
	public static InputStreamReader getFileFromClassPath(final String xlmlFlux) {
		final byte[] bytes = xlmlFlux.getBytes(Charset.forName("UTF-8"));
		final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		return new InputStreamReader(bais);
	}

	/** Cette classe n'est pas faite pour être instanciée. */
	private Utils() {
		assert false;
	}
}
