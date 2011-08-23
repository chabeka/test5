package fr.urssaf.image.sae.metadata.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import fr.urssaf.image.sae.bo.model.MetadataError;
import fr.urssaf.image.sae.bo.model.bo.SAEMetadata;
import fr.urssaf.image.sae.bo.model.untyped.UntypedMetadata;
import fr.urssaf.image.sae.metadata.constants.Constants;
import fr.urssaf.image.sae.metadata.referential.model.MetadataReference;

/**
 * Cette classe contient des méthodes utilitaires
 * 
 * @author akenore
 * 
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
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

	/** Cette classe n'est pas faite pour être instanciée. */
	private Utils() {
		assert false;
	}

	/**
	 * Vérifie qu'une date est bien au format souhaité.
	 * 
	 * @param date
	 *            : La date à contrôler.
	 * @param local
	 *            : Le locale.
	 * @param datePattern
	 *            :Le pattern de la date à contrôler.
	 * @return une date à partir d'une chaîne.
	 * @throws ParseException
	 *             Exception lorsque le parsing de la chaîne ne se déroule pas
	 *             bien.
	 */
	@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
	public static boolean checkDatePattern(final String date,
			final String datePattern, final Locale local) throws ParseException {
		boolean result = false;
		if (date != null) {
			final SimpleDateFormat formatter = new SimpleDateFormat(
					datePattern, local);
			formatter.setLenient(false);
			if (formatter.parse(date, new ParsePosition(0)) == null) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * Convertie une chaîne en Date
	 * 
	 * @param date
	 *            : La chaîne à convertir.
	 * @return une date à partir d'une chaîne.
	 * @throws ParseException
	 *             Exception lorsque le parsing de la chaîne ne se déroule pas
	 *             bien.
	 */
	@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
	public static Date formatStringToDate(final String date)
			throws ParseException {
		Date newDate = new Date();
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(
					Constants.DATE_FORMAT_ENTRY, Constants.DEFAULT_LOCAL);
			formatter.setLenient(false);
			newDate = formatter.parse(date);
			if (formatter.parse(date, new ParsePosition(0)) == null) {
				formatter = new SimpleDateFormat(Constants.DATE_FORMAT,
						Constants.DEFAULT_LOCAL);
				newDate = formatter.parse(date);
			}
		}
		return newDate;
	}

	/**
	 * Permet d'identifier les doublons
	 * 
	 * @param metadata
	 *            : La métadonnée
	 * @param metadatas
	 *            : La liste des métadonnées.
	 * @return True si la liste contient un doublon de la métadonnée fournit en
	 *         entrée.
	 */
	@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
	public static boolean hasDuplicate(final UntypedMetadata metadata,
			final List<UntypedMetadata> metadatas) {
		int count = 0;
		for (UntypedMetadata data : metadatas) {
			if (data.getLongCode().contains(metadata.getLongCode())) {
				count++;
			}
		}
		return count == 1;
	}

	/**
	 * Permet d'identifier les doublons dans la liste des errors ceci pour ne
	 * pas les déclarer en double.
	 * 
	 * @param error
	 *            : L'erreur
	 * @param errors
	 *            : La liste des erreurs.
	 * @return True si la liste contient un doublon de l'erreur fournit en
	 *         entrée.
	 */
	@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
	public static boolean exist(final MetadataError error,
			final List<MetadataError> errors) {
		int count = 0;
		for (MetadataError err : errors) {
			if (err.getLongCode().contains(error.getLongCode())) {
				count++;
			}
		}
		return count == 1;
	}

	/**
	 * Permet de vérifier qu'une métadonnée est bien dans une liste spécifique
	 * de métadonnée.
	 * 
	 * @param metadata
	 *            : La métadonnée
	 * @param metadatas
	 *            : La liste des métadonnées.
	 * @return True si la liste contient un bien de la métadonnée fournit en
	 *         entrée.
	 */
	@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
	public static boolean isInRequiredList(final MetadataReference metadata,
			final List<SAEMetadata> metadatas) {
		boolean found = false;
		for (SAEMetadata data : Utils.nullSafeIterable(metadatas)) {
			if (data.getLongCode().contains(metadata.getLongCode())) {
				found = true;
				break;
			}
		}
		return found;
	}
	
	/**
	 * Retourne un strean en lieu et place d'une chaîne de caractère
	 * 
	 * @param xlmlFlux
	 *            : la chaîne
	 * @return un strean en lieu et place d'une chaîne de caractère
	 */
	public static InputStreamReader getFileFromString(final String xlmlFlux) {
		final byte[] bytes = xlmlFlux.getBytes(Constants.ENCODING);
		final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		return new InputStreamReader(bais);
	}

}
