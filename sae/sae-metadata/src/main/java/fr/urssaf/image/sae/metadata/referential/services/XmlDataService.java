package fr.urssaf.image.sae.metadata.referential.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import fr.urssaf.image.sae.metadata.referential.model.MetadataReference;

/**
 * Fournit un service de désérialisation de la base
 * 
 * @author akenore
 * 
 */
public interface XmlDataService {
	/**
	 * 
	 * @param xmlFile
	 *            : Le fichier xml contenant les données
	 * @return Le Referentiel des métadonnées
	 * @throws FileNotFoundException
	 *             Lorsque le fichier n'est pas présent
	 */
	Map<String, MetadataReference> referentialReader(final File xmlFile)
			throws FileNotFoundException;
	
	/**
	 * 
	 * @return Le Référentiel des métadonnées
	 * @throws FileNotFoundException
	 *             Lorsque le fichier n'est pas présent
	 */
	Map<String, MetadataReference> referentialReader()
			throws FileNotFoundException;


}
