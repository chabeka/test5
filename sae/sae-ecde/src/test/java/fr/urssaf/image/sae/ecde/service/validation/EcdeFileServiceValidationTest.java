package fr.urssaf.image.sae.ecde.service.validation;


import static org.junit.Assert.fail;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.ecde.exception.EcdeBadFileException;
import fr.urssaf.image.sae.ecde.exception.EcdeBadURLException;
import fr.urssaf.image.sae.ecde.exception.EcdeBadURLFormatException;
import fr.urssaf.image.sae.ecde.modele.source.EcdeSource;
import fr.urssaf.image.sae.ecde.service.EcdeFileService;


/**
 * Classe permettant de tester l'aspect sur la validation des paramètres d'entree
 * des méthodes de la classe EcdeFileServiceValidation
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-sae-ecde.xml")
public class EcdeFileServiceValidationTest {
   
   @Autowired
   private EcdeFileService ecdeFileService;
   
   private URI uri;
   private static final String ECDECER69 = "ecde.cer69.recouv";
   private static final String ECDE = "ecde";
   private static final String ATTESTATION = "/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf";
   
   // utilisation pour la convertion
   private static EcdeSource ecde1,ecde2,ecde3,ecde4;
   
   // file attestation
   private static final File ATTESTATION_FILE = new File("/ecde/ecde_lyon/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf");
   
   @BeforeClass
   public static void init() throws URISyntaxException {
      ecde1 = new EcdeSource("ecde.hoth.recouv", new File("/ecde/ecde_host/"));
      ecde2 = new EcdeSource(ECDECER69, new File("/ecde/ecde_lyon/"));
      ecde3 = new EcdeSource("ecde.tatoine.recouv", new File("/ecde/ecde_tatoine/"));
      ecde4 = new EcdeSource("host4", null);
   }
   
   //--------------------------- CONVERT FILE TO URI ------------------------
   
   //----------EcdeFile n'est pas renseigné -------------
   @Test(expected = IllegalArgumentException.class)
   public void convertFileToURIEcdeFileNonRenseingeTest() throws EcdeBadFileException {
      ecdeFileService.convertFileToURI(null, ecde1, ecde3);
      
      fail("Une exception était attendue! L'exception IllegalArgumentException sur ecdeFile " +
           "non renseigne");
   }
   
   //----------Aucun ECDE n'est renseigne -------------
   @Test(expected = IllegalArgumentException.class)
   public void convertFileToURIEcdeNonRenseigneTest() throws EcdeBadFileException {
      ecdeFileService.convertFileToURI(ATTESTATION_FILE);
      
      fail("Une exception était attendue! L'exception IllegalArgumentException sur ecdeSource " +
           "Aucun ECDE n'est transmis en paramètre");
   }
   
   //----------L'attribut d'un ECDE n'est pas renseigne -------------
   @Test(expected = IllegalArgumentException.class)
   public void convertFileToURIAttributEcdeNonRenseigneTest() throws EcdeBadFileException {
      ecdeFileService.convertFileToURI(ATTESTATION_FILE, ecde1, ecde2, ecde4);
      
      fail("Une exception était attendue! L'exception IllegalArgumentException sur ecdeSource " +
           "L'attribut Base Path de l'ECDE No 2 n'est pas renseigné");
   }
   
   //----------Verficiation : dans le chemin de fichier on n'est pas ../
   @Test(expected = IllegalArgumentException.class)
   public void convertFileToURIBadURLFormatPathFileTest() throws EcdeBadFileException, URISyntaxException {
        ecdeFileService.convertFileToURI(new File("ecde/ecde_lyon/DCL001/19991200/3/documents/../attestation/1990/attestation1.pdf"),
                                         ecde1, ecde2, ecde3);
        
        fail("Une exception était attendue! L'exception IllegalArgumentException sur ../");
   }
   
   // ------------------------ URI TO FILE ----------------------------
   //test avec url null
   @Test(expected = IllegalArgumentException.class)
   public void convertUrlToFileTestUrlNotExist () throws EcdeBadURLException, EcdeBadURLFormatException {
      ecdeFileService.convertURIToFile(null, ecde1, ecde2, ecde3);
      fail("Une exception était attendue! L'exception IllegalArgumentException");
   }
   
   //test avec ECDESOURCE null
   @Test(expected = IllegalArgumentException.class)
   public void convertUrlToFileTestUrlNull () throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException {
      uri = new URI(ECDE, ECDECER69, ATTESTATION, null);
      ecdeFileService.convertURIToFile(uri);
      fail("Une exception était attendue! L'exception IllegalArgumentException");
   }
 
   //test avec ECDESOURCE vide
   @Test(expected = IllegalArgumentException.class)
   public void convertUrlToFileTestUrlEmpty () throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException {
      uri = new URI(ECDE, ECDECER69, ATTESTATION, null);
      ecdeFileService.convertURIToFile(uri);
      fail("Une exception était attendue! L'exception IllegalArgumentException sur ecdeSource vide");
   }
      
   // ne doit pas etre present de ../ dans le chemin
   @Test(expected = IllegalArgumentException.class)
   public void convertUrlToFileBadURLFormatPathFileTest() throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException {
        uri = new URI(ECDE, ECDECER69, "/DCL001/19991200/3/documents/../attestation/1990/attestation1.pdf", null);
        ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
        fail("Une exception était attendue! L'exception IllegalArgumentException sur ../");
   }

}