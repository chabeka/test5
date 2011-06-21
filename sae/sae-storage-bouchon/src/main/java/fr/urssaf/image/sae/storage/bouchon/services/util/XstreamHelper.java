package fr.urssaf.image.sae.storage.bouchon.services.util;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import com.google.common.io.Closeables;
import com.google.common.io.Files;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
/**
 * 
 * @author akenore
 *
 */
public final class XstreamHelper {
	/** Le composant pour les traces */
	private static final Logger LOGGER = Logger.getLogger(XstreamHelper.class);

	/**
	 * Construit une instance de XStream capable desérialiser/désérialiser un
	 * ensemble de classes annotées.
	 * 
	 * @param pModelClasses
	 *            les classes
	 * @return l'instance
	 */
	public static XStream newXStream(final Class<?>... pModelClasses) {
		final XStream xstream = new XStream();
		xstream.processAnnotations(pModelClasses);
		return xstream;
	}

	/**
	 * Parse un fichier XML et renvoie l'objet qu'il repr�sente.
	 * 
	 * @param <T>
	 *            le type d'objet
	 * @param pXmlFile
	 *            le fichier
	 * @param pXmlCharset
	 *            l'encodage du fichier
	 * @param pResultClass
	 *            la classe du type d'objet
	 * @param pXStream
	 *            l'instance utilis�e pour le parsing
	 * @return l'objet
	 * 
	 */
	@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
	public static <T> T parse(final File pXmlFile, final Charset pXmlCharset,
			final Class<T> pResultClass, final XStream pXStream) {
		Reader reader = null;
		T result = null;
		try {
			reader = Files.newReader(pXmlFile, pXmlCharset);
			result = pResultClass.cast(pXStream.fromXML(reader));

		} catch (final IOException e) {
			LOGGER.error("Erreur d'E/S lors du parsing de " + pXmlFile + " : "
					+ e.getMessage());

		} catch (final XStreamException e) {
			LOGGER.error("Erreur XML lors du parsing de " + pXmlFile, e);

		} finally {
			Closeables.closeQuietly(reader);
		}
		return result;
	}

	/**
	 * Cette méthode permet de charger les données à partir d'un fichier xml
	 * 
	 * @param <T> : Le type
	 * @param dataFile :  Le file de données
	 * @param clazz :  La classe
	 * @param message : Le message
	 * @return les données à partir d'un fichier xml
	 */

	public static <T> T loadDataProcess(final File dataFile, final Class<T> clazz,
			final String message) {
		Validate.notNull(dataFile, message);
	
		return XstreamHelper.parse(dataFile, Charset.forName("UTF-8"), clazz,
				XstreamHelper.newXStream(clazz));
	}

	/** Cette classe n'est pas con�ue pour être instanciée. */
	private XstreamHelper() {
		assert false;
	}
}
