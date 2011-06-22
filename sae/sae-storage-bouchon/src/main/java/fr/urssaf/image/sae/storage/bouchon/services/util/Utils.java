package fr.urssaf.image.sae.storage.bouchon.services.util;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import fr.urssaf.image.sae.storage.bouchon.data.model.DocPdf;

/**
 * Cette classe contient des méthodes utilitaires
 * 
 * @author akenore
 * 
 */
public final class Utils {


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
	 * 
	 *        
	 * @return le contenu d'un fichier
	 */
	@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
	public static byte[] fileContent() {
		return Base64.decodeBase64(DocPdf.getDoc().getBytes(
				Charset.forName("UTF-8")));
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
	 * Retourne un strean en lieu et place d'une chaîne de caractère
	 * 
	 * @param xlmlFlux
	 *            : la chaîne
	 * @return un strean en lieu et place d'une chaîne de caractère
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
