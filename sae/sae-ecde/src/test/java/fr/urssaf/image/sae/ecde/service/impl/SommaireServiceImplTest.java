package fr.urssaf.image.sae.ecde.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.ecde.exception.EcdeBadSummaryException;
import fr.urssaf.image.sae.ecde.exception.EcdeGeneralException;
import fr.urssaf.image.sae.ecde.modele.sommaire.Sommaire;
import fr.urssaf.image.sae.ecde.modele.source.EcdeSource;
import fr.urssaf.image.sae.ecde.modele.source.EcdeSources;
import fr.urssaf.image.sae.ecde.service.SommaireService;

/**
 * Classe permettant de tester l'implémentation
 * des méthodes de la classe SommaireXmlServiceImpl
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-sae-ecde-test.xml")
@SuppressWarnings({"PMD.MethodNamingConventions", "PMD.UncommentedEmptyMethod"})
public class SommaireServiceImplTest {

   @Autowired
   public EcdeSources ecdeSources;
   
   private static final String ECDE = "ecde";
   private static final String SOMMAIRE = getSommaire() + "sommaire.xml";
   private static final String FILE_SOM1 = "/sommaire/sommaire-test001.xml";
   private static final String FILE_SOM1_BIS = "/sommaire/sommaire-test001Bis.xml";
   private static final String FILE_SOM3 = "/sommaire/sommaire-test003.xml";
   private static final String FILE_SOM4 = "/sommaire/sommaire-test004.xml";
   private static final String FILE_SOM5 = "/sommaire/sommaire-test005.xml";
   private static final String FILE_TEMP = "/1/20110101/3/documents/repertoire/testunitaire.txt";
   private static final String TEST_UNIT = "ecde.testunit.recouv";
   
   private static final String INATTENDU = "Message Inattendu";
   private static final String BATCH_MODE = "TOUT_OU_RIEN";
   private static final String SOM = "/sommaire.xml";
   
   private static File repertoireTemp = new File("");
   private static File repertoireFinal = new File("");
   
   
   private static String getSommaire() {
      return "/1/20110101/3/";
   }
   
   private static URI uri, uri2, uri3, uri4;
   
   @Autowired
   private SommaireService sommaireService;
   
   
   @BeforeClass
   public static void init() {  
   }
   
   private void createAbo() throws URISyntaxException, IOException {
      
      ecdeSources.getSources();
      for (EcdeSource ecde : ecdeSources.getSources()) {
         if (TEST_UNIT.equals(ecde.getHost())) {
            repertoireTemp = ecde.getBasePath();
         }
      }
      repertoireFinal = new File(repertoireTemp.getAbsolutePath() + "/1/20110101/3/");
      FileUtils.forceMkdir(repertoireFinal);
   }
   
   private static void createSom(String sommaire, String som) throws URISyntaxException, IOException {
      ClassPathResource classPath = new ClassPathResource(sommaire);
      File sommaire1 = classPath.getFile();
      
      File somCopy1 = new File(repertoireFinal.getAbsolutePath() + som);
      FileUtils.copyFile(sommaire1, somCopy1);
      
      somCopy1.createNewFile();
   }
   
   private static void createFileTemp() throws URISyntaxException, IOException {
      
      ClassPathResource classPath = new ClassPathResource(FILE_TEMP);
      File sommaire1 = classPath.getFile();
     
      File rep = new File(repertoireFinal.getAbsolutePath() + System.getProperty("file.separator") + "documents" + System.getProperty("file.separator") + "repertoire");
      FileUtils.forceMkdir(rep);
      
      
      File somCopy1 = new File(rep.getAbsolutePath() + System.getProperty("file.separator") + "testunitaire.txt");
      FileUtils.copyFile(sommaire1, somCopy1);
      
      somCopy1.createNewFile();      
   }
   
// Le test doit echouer car notre objet numérique a un fichier qui n'existe pas d'ou fichier introuvable
// Le fichier resultats.xml est généré mais contenant un message d'erreur.
// Ici le sommaire.xml contient plusieurs documents en erreur mais on s'arrete des la premiere erreur pour afficher l'erreur   
   @Test(expected = EcdeBadSummaryException.class)
   public void fetchSommaireByUriFailurePlsrFileNotFound() throws EcdeGeneralException, URISyntaxException, IOException  {
      uri = new URI(ECDE, TEST_UNIT, SOMMAIRE, null);
      createAbo();
      createSom(FILE_SOM1, "/sommaire.xml");
      Sommaire sommaire = sommaireService.fetchSommaireByUri(uri);
      assertEquals(INATTENDU, BATCH_MODE, sommaire.getBatchMode());
   }
// Le test doit echouer car notre objet numérique a un fichier qui n'existe pas d'ou fichier introuvable
// Le fichier resultats.xml est généré mais contenant un message d'erreur.
// Ici le sommaire.xml contient un document en erreur   
   @Test(expected = EcdeBadSummaryException.class)
   public void fetchSommaireByUriFailureOneFileNotFound() throws EcdeGeneralException, URISyntaxException, IOException  {
      uri = new URI(ECDE, TEST_UNIT, SOMMAIRE, null);
      createAbo();
      createSom(FILE_SOM1_BIS, SOM);
      Sommaire sommaire = sommaireService.fetchSommaireByUri(uri);
      assertEquals(INATTENDU, BATCH_MODE, sommaire.getBatchMode());
   }
   
   
// Verification que l'objet resultat cree est correct.
   @Test
   public void fetchSommaireByUriSuccess() throws EcdeGeneralException, URISyntaxException, IOException  {
      uri2 = new URI(ECDE, TEST_UNIT, SOMMAIRE, null);
      createAbo();
      createSom(FILE_SOM3, SOM);
      createFileTemp();
      Sommaire sommaire = sommaireService.fetchSommaireByUri(uri2);
      assertEquals(INATTENDU, BATCH_MODE, sommaire.getBatchMode());
   }
// Le sommaire XMl n'a pas son batchMode a TOUT ou RIEN
// Erreur genéré SAXParseException : non respect de l'enumeration TOUT_OU_RIEN ou PARTIEL 
// Cette exception est catché et genere une exception de type EcdeBadSummaryException dans le code   
   @Test(expected = EcdeBadSummaryException.class)
   public void fetchSommaireByUriFailureBM() throws EcdeGeneralException, URISyntaxException, IOException  {
      uri3 = new URI(ECDE, TEST_UNIT, SOMMAIRE, null);
      createAbo();
      createSom(FILE_SOM4, SOM);
      createFileTemp();
      Sommaire sommaire = sommaireService.fetchSommaireByUri(uri3);
      assertEquals(INATTENDU, BATCH_MODE, sommaire.getBatchMode());
   }
   
// Le sommaire XML ne respecte pas la syntaxe XSD
// Test doit echouer car erreur de structure  car manque nombreDePages 
   @Test(expected = EcdeBadSummaryException.class)
   public void fetchSommaireByUriFailureStructure() throws EcdeGeneralException, URISyntaxException, IOException  {
      uri4 = new URI(ECDE, TEST_UNIT, SOMMAIRE, null);
      createAbo();
      createSom(FILE_SOM5, SOM);
      createFileTemp();
      Sommaire sommaire = sommaireService.fetchSommaireByUri(uri4);
      assertEquals(INATTENDU, BATCH_MODE, sommaire.getBatchMode());
   }
}
