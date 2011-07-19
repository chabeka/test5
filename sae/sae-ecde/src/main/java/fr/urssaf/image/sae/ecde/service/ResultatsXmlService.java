package fr.urssaf.image.sae.ecde.service;

import java.io.File;
import java.io.OutputStream;

import fr.urssaf.image.sae.ecde.exception.EcdeXsdException;
import fr.urssaf.image.sae.ecde.modele.resultats.ResultatsType;

/**
 * Service permettant l'ecriture des fichiers resultat.xml  
 * 
 */
public interface ResultatsXmlService {
   
   
   /**
    * Methode permettant l'ecriture du fichier resultats.xml
    * 
    * @param resultatsXml objet representant le contenu du resultats.xml
    * @param output flux dans lequel écrire resultats.xml
    * @throws EcdeXsdException erreur de structure a été détectée sur le resultats.xml
    */
   void writeResultatsXml(ResultatsType resultatsXml, OutputStream output) throws EcdeXsdException;
   
   /**
    * Methode permettant l'ecriture du fichier resultats.xml
    * 
    * @param resultatsXml objet representant le contenu du resultats.xml
    * @param output fichier dans lequel écrire resultats.xml
    * @throws EcdeXsdException erreur de structure a été détectée sur le resultats.xml
    */
   void writeResultatsXml(ResultatsType resultatsXml, File output)  throws EcdeXsdException;
   
}
