package fr.urssaf.image.sae.ecde.service.validation;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.ecde.exception.EcdeXsdException;
import fr.urssaf.image.sae.ecde.modele.resultats.ResultatsType;
import fr.urssaf.image.sae.ecde.service.ResultatsXmlService;
import fr.urssaf.image.sae.ecde.service.impl.ResultatsXmlServiceImpl;

/**
 * Classe permettant de tester l'aspect sur la validation des paramètres d'entree
 * des méthodes de la classe ResultatsXmlServiceValidation
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-sae-ecde.xml")
@SuppressWarnings({"PMD.MethodNamingConventions","PMD.TooManyMethods", "PMD"})
public class ResultatsXmlServiceValidataionTest {
   
   private static ResultatsXmlService service;
   
   // utilisation pour la convertion
   private static OutputStream output1;
   
   private static String MESSAGE_INATTENDU = "message inattendu";
   
   private static ResultatsType type1;
   
   
   @BeforeClass
   public static void init() throws URISyntaxException, FileNotFoundException {
      service = new ResultatsXmlServiceImpl();
      output1 = null;
      type1 = new ResultatsType();
   }
   
   // inputStream à null
   @Test
   public void writeResultatsXml() throws EcdeXsdException {
      try {
         service.writeResultatsXml(type1, output1);
      } catch (IllegalArgumentException e) {
         assertEquals(MESSAGE_INATTENDU, "Les arguments entrées dans la méthode ne sont pas correctement renseignées.", e.getMessage());
      }
      
   }
   
   // file à null
   @Test
   public void writeResultatsXmlFile() throws EcdeXsdException {
      try {
         File outputFile = null;
         service.writeResultatsXml(type1, outputFile);
      } catch (IllegalArgumentException e) {
         assertEquals(MESSAGE_INATTENDU, "Les arguments entrées dans la méthode ne sont pas correctement renseignées.", e.getMessage());
      }
      
   }
   
   
}

 
