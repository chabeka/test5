package fr.urssaf.image.sae.ecde.service.validation;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import org.apache.commons.lang.SystemUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.ecde.exception.EcdeXsdException;
import fr.urssaf.image.sae.ecde.modele.resultats.ResultatsType;
import fr.urssaf.image.sae.ecde.service.ResultatsXmlService;

/**
 * Classe permettant de tester l'aspect sur la validation des paramètres d'entree
 * des méthodes de la classe ResultatsXmlServiceValidation
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-sae-ecde.xml")
@SuppressWarnings({"PMD.MethodNamingConventions"})
public class ResultatsXmlServiceValidataionTest {
   
   @Autowired
   private ResultatsXmlService service;
   
   private static final String MESSAGE_INATTENDU = "message inattendu";
   
   private static ResultatsType type1;
   
// Recupération repertoire temp
   private static final String REPERTORY;
   static {
       REPERTORY = SystemUtils.getJavaIoTmpDir().getAbsolutePath();
       System.setProperty("file.encoding", "UTF-8");
   }
   // nom du répertoire creer dans le repertoire temp de l'os
   private static String resultatsEcde = "resultatsEcde";
   // separateur de fichier
   private static final String FILE_SEPARATOR = System.getProperty("file.separator");
   // declaration d'un répertoire dans le repertoire temp de l'os
   private static final String REPERTOIRE = REPERTORY + FILE_SEPARATOR + resultatsEcde;
   
   
   @BeforeClass
   public static void init() throws URISyntaxException, FileNotFoundException {
      type1 = new ResultatsType();
      
      File rep = new File(REPERTOIRE); 
      //nettoyage du repertoire present dans le rep temp de l'os
      for(File file : rep.listFiles()){
         file.delete();
      }  
      // creation d'un repertoire dans le rep temp de l'os
      rep.mkdirs();
   }
   
   // inputStream à null
   @Test
   public void writeResultatsXml_failure_output() throws EcdeXsdException {
      try {
         OutputStream output1 = null;
         service.writeResultatsXml(type1, output1);
      } catch (IllegalArgumentException e) {
         assertEquals(MESSAGE_INATTENDU, "L'argument 'output' doit être renseigné.", e.getMessage());
      }
      
   }
   
   // file à null
   @Test
   public void writeResultatsXml_failure_File() throws EcdeXsdException {
      try {
         File outputFile = null;
         service.writeResultatsXml(type1, outputFile);
      } catch (IllegalArgumentException e) {
         assertEquals(MESSAGE_INATTENDU, "L'argument 'output' doit être renseigné.", e.getMessage());
      }
      
   }
}

 
