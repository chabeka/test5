package fr.urssaf.image.sae.ecde.service.impl;

import java.io.File;
import java.io.OutputStream;

import fr.urssaf.image.sae.ecde.exception.EcdeXsdException;
import fr.urssaf.image.sae.ecde.modele.resultats.ResultatsType;
import fr.urssaf.image.sae.ecde.service.ResultatsXmlService;

/**
 * Implementation de la methode writeResultatsXml pour l'ecriture du fichier <br>
 * resultats.xml.
 * 
 */
public class ResultatsXmlServiceImpl implements ResultatsXmlService {

   /**
    * Methode permettant l'ecriture du fichier resultats.xml
    * 
    * @param resultatsXml objet representant le contenu du resultats.xml
    * @param output flux dans lequel écrire resultats.xml
    * @throws EcdeXsdException erreur de structure a été détectée sur le resultats.xml
    */
   @Override
   public void writeResultatsXml(ResultatsType resultatsXml, OutputStream output)
         throws EcdeXsdException {
      // TODO Auto-generated method stub
      
   }

   /**
    * Methode permettant l'ecriture du fichier resultats.xml
    * 
    * @param resultatsXml objet representant le contenu du resultats.xml
    * @param output fichier dans lequel écrire resultats.xml
    * @throws EcdeXsdException erreur de structure a été détectée sur le resultats.xml
    */
   @Override
   public void writeResultatsXml(ResultatsType resultatsXml, File output)
         throws EcdeXsdException {
      // TODO Auto-generated method stub
      
   }

   

}
