package fr.urssaf.image.sae.ecde.service;

import java.io.File;
import java.net.URI;

import fr.urssaf.image.sae.ecde.exception.EcdeBadFileException;
import fr.urssaf.image.sae.ecde.exception.EcdeBadURLException;
import fr.urssaf.image.sae.ecde.exception.EcdeBadURLFormatException;
import fr.urssaf.image.sae.ecde.modele.source.EcdeSource;

/**
 * Service de manipulation des URL ECDE et des chemins de fichiers.
 * 
 */


public interface EcdeFileService {
   
   
   /**
    * Conversion d'un chemin absolu de fichier dans un ECDE vers l'URL ECDE correspondante
    * 
    * 
    * @param ecdeFile Fichier dans l'ECDE courant à convertir en URL ECDE
    * @param sources Liste des ECDE
    *   
    * @throws EcdeBadFileException Mauvais chemin de fichier
    * 
    * @return URL ECDE
    */
    URI convertFileToURI (File ecdeFile, EcdeSource... sources) throws EcdeBadFileException;
   
   
   /**
    * Conversion d'une URL ECDE vers un chemin absolu de fichier dans l'ECDE correspondant
    * 
    *  
    * @param ecdeURL URL ECDE à convertir en chemin de fichier dans son ECDE correspondant
    * @param sources Liste des ECDE
    *   
    * @throws EcdeBadURLException mauvaise url 
    * @throws EcdeBadURLFormatException mauvais format d'url
    * 
    * @return Chemin du fichier dans ECDE correspondant
    *  
    */
    File convertURIToFile (URI ecdeURL, EcdeSource... sources) throws EcdeBadURLException, EcdeBadURLFormatException;

}
