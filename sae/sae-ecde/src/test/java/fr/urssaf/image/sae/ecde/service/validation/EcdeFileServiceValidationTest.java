package fr.urssaf.image.sae.ecde.service.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.ecde.exception.EcdeBadFileException;
import fr.urssaf.image.sae.ecde.modele.source.EcdeSource;
import fr.urssaf.image.sae.ecde.service.EcdeFileService;
import fr.urssaf.image.sae.ecde.service.impl.EcdeFileServiceImpl;


/**
 * Classe permettant de tester l'aspect sur la validation des paramètres d'entree
 * des méthodes de la classe EcdeFileServiceValidation
 * 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-sae-ecde.xml")
public class EcdeFileServiceValidationTest {
   
   private final EcdeFileService ecde = new EcdeFileServiceImpl();
      
   private static File ecdeFile;
   private static EcdeSource ecdeSource, ecdeSource2, ecdeSource3, ecdeSource4 ;
   
   @BeforeClass
   public static void init() {
      ecdeFile = new File("");
      ecdeSource = new EcdeSource();
      ecdeSource2 = new EcdeSource();
      ecdeSource3 = new EcdeSource();
      ecdeSource4 = new EcdeSource();
      
      ecdeSource.setHost("host");
      ecdeSource.setBasePath(new File("C:\test\testlokmen.txt"));
      
      ecdeSource2.setHost("host2");
      ecdeSource2.setBasePath(new File("C:\test\testlokmen.txt"));
      
      ecdeSource3.setHost("host3");
      ecdeSource3.setBasePath(new File("C:\test\testlokmen.txt"));
      
      ecdeSource4.setHost("");
      ecdeSource4.setBasePath(new File("C:/test/testlokmen.txt"));
   }
   
   /**
    * Test pour que JUNIT genere bien une exception suite a la validation des parametres
    * Dois afficher un message d erreur.
    * 
    * Test reussi.
    */
   @Test
   public void convertFileToURITest() {
      try {
         ecde.convertFileToURI(ecdeFile, ecdeSource, ecdeSource2, ecdeSource3, ecdeSource4);
         fail("Test doit planter!");
      } catch (EcdeBadFileException e) {
         assertEquals("Message non attendu","L'attribut Host de l'ECDE No 3 nest pas renseigné." , e.getMessage());
      }
   }
   
   

}
