/**
 * 
 */
package fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.service;

import java.util.List;

import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.exception.AdrnToRcndException;
import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.modele.BeanRNDTypeDocument;

/**
 * Stockage des données
 * 
 */
public interface DataManagementInterface {

   /**
    * Sauvegarde dans un fichier XML la miste des documents supportés
    * 
    * @param typesDocuments
    *           les documents à sauvegarder
    * @param version
    *           version de compatibilité
    * @throws AdrnToRcndException
    *            exception lors du traitement
    */
   void saveDocuments(List<BeanRNDTypeDocument> listDoc, String version)
         throws AdrnToRcndException;

}
