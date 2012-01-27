/**
 * 
 */
package fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.webservices.service;

import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.webservices.exception.AdrnToRcndException;
import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.webservices.modele.RNDTypeDocument;

/**
 * Récupération des données du webservice
 * 
 */
public interface DuplicationInterface {

   /**
    * Récupération des types de documents supportés pour la version récupérée
    * par l'appel au WS donnant la version en cours
    * 
    * @return la liste des documents supportés
    * @throws AdrnToRcndException
    *            erreur lors du traitement
    */
   RNDTypeDocument[] getDocumentTypesFromWS() throws AdrnToRcndException;

   /**
    * Récupération des types de documents supportés pour la version récupérée
    * dans le fichier config.properties
    * 
    * @return la liste des documents supportés
    * @throws AdrnToRcndException
    *            erreur lors du traitement
    */
   RNDTypeDocument[] getDocumentTypesFromConfigFile() throws AdrnToRcndException;

   /**
    * Récupère le numéro de version dans le fichier de configuration
    * 
    * @return le numéro de version
    */
   String getVersionFromConfigFile();

   /**
    * Récupère le numéro de version par appel WS
    * 
    * @return le numéro de version
    * 
    * @throws AdrnToRcndException
    *            erreur lors du traitement
    */
   String getVersionFromWS() throws AdrnToRcndException;

}
