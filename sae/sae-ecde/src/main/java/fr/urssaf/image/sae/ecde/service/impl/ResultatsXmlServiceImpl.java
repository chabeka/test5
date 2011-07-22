package fr.urssaf.image.sae.ecde.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.xml.sax.SAXException;

import fr.urssaf.image.sae.ecde.exception.EcdeXsdException;
import fr.urssaf.image.sae.ecde.modele.resultats.ObjectFactory;
import fr.urssaf.image.sae.ecde.modele.resultats.ResultatsType;
import fr.urssaf.image.sae.ecde.service.ResultatsXmlService;
import fr.urssaf.image.sae.ecde.util.JAXBUtils;
import fr.urssaf.image.sae.ecde.util.MessageRessourcesUtils;

/**
 * Implementation de la methode writeResultatsXml pour l'ecriture du fichier <br>
 * resultats.xml.
 * 
 */
public class ResultatsXmlServiceImpl implements ResultatsXmlService {

   // pour la creation d'un object JAXBElement resultatsType
   private static ObjectFactory object = new ObjectFactory();
   
   /**
    * Methode permettant l'ecriture du fichier resultats.xml
    * 
    * @param resultatsXml objet representant le contenu du resultats.xml
    * @param output flux dans lequel écrire resultats.xml
    * @throws EcdeXsdException erreur de structure a été détectée sur le resultats.xml
    */
   @Override
   public final void writeResultatsXml(ResultatsType resultatsXml, OutputStream output) throws EcdeXsdException {
      try {
         File xsdSchema = new File("src/main/resources/xsd_som_res/resultats.xsd");
         JAXBElement<ResultatsType> resultats = object.createResultats(resultatsXml);
         
         JAXBUtils.marshal(resultats, output, xsdSchema);
      } catch (SAXException e) {
         throw new EcdeXsdException(MessageRessourcesUtils.recupererMessage("resultatsEcritureException.message", null), e);
      } catch (JAXBException e) {
         throw new EcdeXsdException(MessageRessourcesUtils.recupererMessage("resultatsEcritureException.message", null), e);
      }
      
   }

   /**
    * Methode permettant l'ecriture du fichier resultats.xml
    * 
    * @param resultatsXml objet representant le contenu du resultats.xml
    * @param output fichier dans lequel écrire resultats.xml
    * @throws EcdeXsdException erreur de structure a été détectée sur le resultats.xml
    */
   @Override
   @SuppressWarnings("PMD.PreserveStackTrace")
   public final void writeResultatsXml(ResultatsType resultatsXml, File output) throws EcdeXsdException {
      try {
         writeResultatsXml(resultatsXml, convertFileToOutputStream(output));
      } catch (IOException e) {
         throw new IllegalArgumentException("Erreur d'argument en entrée.");
      }
      
   }
   
   /*
    * Methode permettant de convertir un objet de type File en OutputStream
    */
   private OutputStream convertFileToOutputStream(File file) throws IOException {
      
      return FileUtils.openOutputStream(file);
   }

   

}
