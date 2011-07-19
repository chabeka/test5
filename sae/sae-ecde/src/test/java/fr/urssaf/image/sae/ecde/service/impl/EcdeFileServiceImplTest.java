package fr.urssaf.image.sae.ecde.service.impl;

import static org.junit.Assert.assertEquals;
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
import fr.urssaf.image.sae.ecde.exception.EcdeRuntimeException;
import fr.urssaf.image.sae.ecde.modele.source.EcdeSource;
import fr.urssaf.image.sae.ecde.service.EcdeFileService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-sae-ecde.xml")
@SuppressWarnings({"PMD.MethodNamingConventions","PMD.TooManyMethods"})
public class EcdeFileServiceImplTest {
   
   private URI uri;
      
   @Autowired
   private EcdeFileService ecdeFileService;
   
   private static final String MESSAGE_INNATENDU = "message inattendu";
   
   private static final String ECDECER69 = "ecde.cer69.recouv";
   private static final String ECDE = "ecde";
   private static final String ATTESTATION = "/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf";
   // utilisation pour la convertion
   private static EcdeSource ecde1,ecde2,ecde3, ecde4, ecde5, ecde6, ecde7;
   // file attestation
   private static final File ATTESTATION_FILE = new File("/ecde/ecde_lyon/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf");
   private static final File ATTESTATION_FILE3 = new File("\\ecde/ecde_lyon/DCL001\\19991231/3/documents/attestation/1990\\attestation1.pdf");
   private static final File ATTESTATION_FILE2 = new File("/mnt/ai/ecde/ecde_lokmen/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf");
   private static final File ATTESTATION_FILE5 = new File("/temp/text.txt");
   private static final File ATTESTATION_FILE6 = new File("archive/temp/text.txt");
   private static final File ATTESTATION_FILE7 = new File("/ecde_lokmen/ecde_lyon/DCL001/");
   
   private static final String SEPARATOR = "://";
   
   @BeforeClass
   public static void init() throws URISyntaxException {
      ecde1 = new EcdeSource("ecde.hoth.recouv", new File("/ecde/ecde_host/"));
      ecde2 = new EcdeSource(ECDECER69, new File("/ecde/ecde_lyon/"));
      ecde3 = new EcdeSource("ecde.tatoine.recouv", new File("/ecde/ecde_tatoine/"));
      ecde4 = new EcdeSource("ecde.tatoine2.recouv", new File("/mnt/ai/ecde/ecde_lokmen/"));
      ecde5 = new EcdeSource("ecde.temp.recouv", new File("/temp/"));
      ecde6 = new EcdeSource("ecde.temp.recouv", new File("temp"));
      ecde7 = new EcdeSource("ecde._.recouv", new File("/ecde_lokmen/ecde_lyon/"));
   }
   
   // l'url est bien en concordance avec la liste ECDESource donnee en paramètre
   @Test
   public void convertUrlToFile_success() throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException {
      uri = new URI(ECDE, "ecde.cer69.recouv", ATTESTATION, null);
      File messageObtenu = ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
      File messageAttendu = new File("/ecde/ecde_lyon/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf");
      
      assertEquals(MESSAGE_INNATENDU, messageObtenu, messageAttendu);
   }
   
   // exception levée si l'URI est absente de la liste des ECDESources donnee en param
   @Test
   public void convertUrlToFile_failure_UriNotExist() throws EcdeBadURLFormatException, URISyntaxException {
      try {
         uri = new URI(ECDE, ECDECER69, ATTESTATION, null);
         ecdeFileService.convertURIToFile(uri, ecde1, ecde3);
         fail("Une exception était attendue! L'exception EcdeBadURLException");
      }catch (EcdeBadURLException e) {
         assertEquals("message inattendu","L'URL ECDE ecde://ecde.cer69.recouv/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf n'appartient à aucun ECDE transmis en paramètre du service.",e.getMessage());
      }
      
   }   
   
   // test pour montrer que la conversion ne fonctionne pas si dans l'URL 
   // ne respecte pas le format
   // ecde/authority/numeroCS/dateTraitement/idTraitement/documents/nom_du_fichier
   // scheme ne vaut pas ecde
   @Test
   public void convertUrlToFile_failure_BadURLFormat() throws EcdeBadURLException, URISyntaxException {
      try {
         uri = new URI("ecd", ECDECER69, ATTESTATION, null);
         ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
         fail("exception attendue!"+EcdeBadURLFormatException.class);
      }catch (EcdeBadURLFormatException e) {
         assertEquals(MESSAGE_INNATENDU,"L'URL ECDE ecd://ecde.cer69.recouv/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf est incorrecte.",e.getMessage());
      }      
   }
     
   // document n'est pas égal à la constante 'documents'
   @Test
   public void convertUrlToFile_failure_BadURLFormatPath_Document() throws EcdeBadURLException, URISyntaxException {
      try {
         uri = new URI(ECDE, ECDECER69, "/DCL001/19991231/3/document/attestation/1990/attestation1.pdf", null);
         ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
         fail("exception attendue!"+EcdeBadURLFormatException.class);
      }catch (EcdeBadURLFormatException e) {  
         assertEquals(MESSAGE_INNATENDU,"L'URL ECDE ecde://ecde.cer69.recouv/DCL001/19991231/3/document/attestation/1990/attestation1.pdf est incorrecte.",e.getMessage());
      }
   }   
   
   // date traitement ne respecte pas le format AAAAMMJJ erreur sur le mois 13
   @Test(expected = EcdeBadURLFormatException.class)
   public void convertUrlToFile_failure_BadURLFormat_PathDate() throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException {
      uri = new URI(ECDE, ECDECER69, "/DCL001/19991331/3/documents/attestation/1990/attestation1.pdf", null);
      ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
   }
    
   // date traitement ne respecte pas le format AAAAMMJJ erreur sur le jour 00
   @Test
   public void convertUrlToFile_failure_BadURLFormat_PathDate2() throws EcdeBadURLException, URISyntaxException {
      try {
         uri = new URI(ECDE, ECDECER69, "/DCL001/19991200/3/documents/attestation/1990/attestation1.pdf", null);
         ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
         fail("exception attendue!"+EcdeBadURLFormatException.class);
      }catch (EcdeBadURLFormatException e) {  
         assertEquals(MESSAGE_INNATENDU,"L'URL ECDE ecde://ecde.cer69.recouv/DCL001/19991200/3/documents/attestation/1990/attestation1.pdf est incorrecte.",e.getMessage());
      }
   }
   
   //-------------------------- FILE TO URI -------------------------------------------------
   //-------- Conversion OK  -----------------------------------------------------------
   @Test
   public void convertFileToURITest() throws EcdeBadFileException {
      URI uri = ecdeFileService.convertFileToURI(ATTESTATION_FILE, ecde1, ecde2, ecde3);
      String resultatAttendu = "ecde://ecde.cer69.recouv/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf";
      String resultatObtenu = uri.getScheme() + SEPARATOR + uri.getAuthority() + uri.getPath();
      
      assertEquals(MESSAGE_INNATENDU, resultatAttendu, resultatObtenu);
   }
   
   //----------Chemin de fichier non présent dans ECDE sources donne en param -------------
   @Test(expected = EcdeBadFileException.class)
   public void convertFileToURI_failure_EcdeNotExist() throws EcdeBadFileException {
      ecdeFileService.convertFileToURI(ATTESTATION_FILE, ecde1, ecde3);
   }
   
   //--------- Conversion OK avec /mnt/ai/ecde/ecde_lyon
   @Test
   public void convertFileToURI_success_2Test() throws EcdeBadFileException {
      URI uri = ecdeFileService.convertFileToURI(ATTESTATION_FILE2, ecde1, ecde2, ecde3, ecde4);
      String resultatAttendu = "ecde://ecde.tatoine2.recouv/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf";
      String resultatObtenu = uri.getScheme() + SEPARATOR + uri.getAuthority() + uri.getPath();
      
      assertEquals(MESSAGE_INNATENDU, resultatAttendu, resultatObtenu);
   }
   
   //----------Test avec un nom de fichier avec des \ et des /
   @Test
   public void convertFileToURI_success_SlashTest() throws EcdeBadFileException {
      URI uri = ecdeFileService.convertFileToURI(ATTESTATION_FILE3, ecde1, ecde2, ecde3);
      
      String resultatAttendu = "ecde://ecde.cer69.recouv/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf";
      String resultatObtenu = uri.getScheme() + SEPARATOR + uri.getAuthority() + uri.getPath();
      
      assertEquals(MESSAGE_INNATENDU, resultatAttendu, resultatObtenu);
   }
   
   //----------Test success
   //-------- nom fichier : /temp/text.txt  ECDESOURCE.BasePath = /temp
   @Test
   public void convertFileToURI_success() throws EcdeBadFileException {
      URI uri = ecdeFileService.convertFileToURI(ATTESTATION_FILE5, ecde5);
      
      String resultatAttendu = "ecde://ecde.temp.recouv/text.txt";
      String resultatObtenu = uri.getScheme() + SEPARATOR + uri.getAuthority() + uri.getPath();
      
      assertEquals(MESSAGE_INNATENDU, resultatAttendu, resultatObtenu);
   }
   
   //----------Test failure
   //-------- nom fichier : archive/temp/text.txt  ECDESOURCE.BasePath = /temp
   @Test(expected = EcdeBadFileException.class)
   public void convertFileToURI_failure() throws EcdeBadFileException {
      ecdeFileService.convertFileToURI(ATTESTATION_FILE6, ecde5);
   }
   
   //-------- nom fichier : archive/temp/text.txt  ECDESOURCE.BasePath = /temp
   @Test
   public void convertFileToURI_failure_badURLFormat() {
      try {
         ecdeFileService.convertFileToURI(ATTESTATION_FILE6, ecde6);
         fail("Une exception était attendue! " + EcdeBadFileException.class);
      }catch (EcdeBadFileException e) {
         assertEquals(MESSAGE_INNATENDU,"Le chemin du document 'archive"+File.separator+"temp"+File.separator+"text.txt' n'appartient à aucun ECDE transmis en paramètre du service.",e.getMessage());
      }
   }
   
   //---------- URI ne respectant pas le format RFC3986 -------------
   // Generation d'une runtimeException
   @Test(expected = EcdeRuntimeException.class)
   public void convertFileToURI_failure_URIFormat() throws EcdeBadFileException {
         ecdeFileService.convertFileToURI(ATTESTATION_FILE7, ecde1, ecde7);
         fail("Erreur attendu de type ECDERuntimeException.");
   }
   
}