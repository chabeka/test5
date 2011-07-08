package fr.urssaf.image.sae.ecde.service.validation;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.ecde.exception.EcdeBadFileException;
import fr.urssaf.image.sae.ecde.exception.EcdeBadURLException;
import fr.urssaf.image.sae.ecde.exception.EcdeBadURLFormatException;
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
   
   private static EcdeFileService ecde;
   
   private static final String MESSAGE = "Message non attendu";
   private static final String ATT_NONRENSEIG = "L'attribut Base Path de l'ECDE No 3 nest pas renseigné.";
      
   private static File ecdeFile;
   private static File ecdeLokmen = new File("C:\test\testlokmen.txt");
   private static EcdeSource ecdeSource, ecdeSource2, ecdeSource3, ecdeSource4 ;
   
   
   private static final String ECDECER69 = "ecde.cer69.recouv";
   private static final String ECDE = "ecde";
   private static final String ATTESTATION = "/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf";
   
   private static URI uri;
   
   
   @BeforeClass
   public static void init() throws URISyntaxException {
      ecdeFile = new File("");
      ecdeSource = new EcdeSource("host", ecdeLokmen);
      ecdeSource2 = new EcdeSource("host2", ecdeLokmen);
      ecdeSource3 = new EcdeSource("host3", ecdeLokmen);
      ecdeSource4 = new EcdeSource("host4", null);
      ecde = new EcdeFileServiceImpl();
      
      uri = new URI(ECDE, ECDECER69, ATTESTATION, "");
   }
   
   
   /**
    * Test pour que JUNIT genere bien une exception suite a la validation des parametres
    * Dois afficher un message d erreur.
    *  
    * @throws EcdeBadFileException 
    */
   @Test
   public void convertFileToURITest() throws EcdeBadFileException {
      try {
         ecde.convertFileToURI(ecdeFile, ecdeSource, ecdeSource2, ecdeSource3, ecdeSource4);
         fail("Test doit planter!");
      } catch (EcdeBadFileException e) {
         assertEquals(MESSAGE,ATT_NONRENSEIG , e.getMessage());
      }catch (IllegalArgumentException e) {
         assertEquals(MESSAGE,ATT_NONRENSEIG , e.getMessage());
      }
   }
   
   /**
    * Test pour que JUNIT genere bien une exception suite a la validation des parametres
    * Dois afficher un message d erreur.
    *  
    * @throws EcdeBadFileException 
    */
   @Test
   public void convertURIToFileTest() {
      try {
         ecde.convertURIToFile(uri, ecdeSource, ecdeSource2, ecdeSource3, ecdeSource4);
         fail("Test doit planter!");
      } catch (EcdeBadURLFormatException e) {
         assertEquals(MESSAGE,ATT_NONRENSEIG , e.getMessage());
      }catch (EcdeBadURLException e) {
         assertEquals(MESSAGE,ATT_NONRENSEIG , e.getMessage());
      }catch (IllegalArgumentException e) {
         assertEquals(MESSAGE,ATT_NONRENSEIG , e.getMessage());
      }
   }
   
   

}
