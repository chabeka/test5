package fr.urssaf.image.sae.ecde.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.ecde.exception.EcdeXsdException;
import fr.urssaf.image.sae.ecde.modele.sommaire.SommaireType;
import fr.urssaf.image.sae.ecde.service.SommaireXmlService;

/**
 * Service permettant la lecture des fichiers sommaire.xml 
 * <br><br>
 * Cette fonctionnalité permet la lecture des fichiers sommaires.xml. Un fichier sommaire.xml<br>
 * doit être representé via le modèle objet par un objet de type
 * <br>{@link fr.urssaf.image.sae.ecde.modele.sommaire.SommaireType} 
 * 
 */
@Service
public class SommaireXmlServiceImpl implements SommaireXmlService {

   
   // LOGGER
   private static final Logger LOG = Logger.getLogger(SommaireXmlServiceImpl.class);
   
   @Override
   public final SommaireType readSommaireXml(InputStream input) throws EcdeXsdException {
      // TODO Auto-generated method stub
      return null;
   }

   /**
    * Converti le fichier donné en paramètre en inputStream et fait appel à la methode
    * readSommairexml(InputStream input) 
    *
    *@param input le fichier en question
    *@throws EcdeXsdException exception levée
    *@return SommaireType 
    *
    */
   @Override
   public final SommaireType readSommaireXml(File input) throws EcdeXsdException {
      try {
         return readSommaireXml(convertFileToInputStream(input));
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         return null;
      }
   }
   
   
   /*
    * Methode permettant de convertir un objet de type File en InputStream
    */
   private InputStream convertFileToInputStream(File file) throws IOException {
      
      return FileUtils.openInputStream(file);
   }

}
