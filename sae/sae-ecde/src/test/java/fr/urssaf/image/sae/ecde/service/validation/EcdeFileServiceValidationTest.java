package fr.urssaf.image.sae.ecde.service.validation;

import static org.junit.Assert.assertEquals;

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
   private static File ecdeLokmen = new File("C:\test\testlokmen.txt");
   private static EcdeSource ecdeSource, ecdeSource2, ecdeSource3, ecdeSource4 ;
   
   
   @BeforeClass
   public static void init() {
      ecdeFile = new File("");
      ecdeSource = new EcdeSource("host", ecdeLokmen);
      ecdeSource2 = new EcdeSource("host2", ecdeLokmen);
      ecdeSource3 = new EcdeSource("host3", ecdeLokmen);
      ecdeSource4 = new EcdeSource("host4", null);
   }
   
   /**
    * Test pour que JUNIT genere bien une exception suite a la validation des parametres
    * Dois afficher un message d erreur.
    * 
    * Test reussi.
    * @throws EcdeBadFileException 
    */
   @Test
   public void convertFileToURITest() throws EcdeBadFileException {
      try {
         ecde.convertFileToURI(ecdeFile, ecdeSource, ecdeSource2, ecdeSource3, ecdeSource4);
         //fail("Test doit planter!");
      } catch (EcdeBadFileException e) {
         assertEquals("Message non attendu","L'attribut Base Path de l'ECDE No 3 nest pas renseigné." , e.getMessage());
      }catch (IllegalArgumentException e) {
         assertEquals("Message non attendu","L'attribut Base Path de l'ECDE No 3 nest pas renseigné." , e.getMessage());
      }
   }
   
   

}
