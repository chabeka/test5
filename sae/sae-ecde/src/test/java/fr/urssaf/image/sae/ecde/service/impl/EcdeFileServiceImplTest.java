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
import fr.urssaf.image.sae.ecde.modele.source.EcdeSource;
import fr.urssaf.image.sae.ecde.service.EcdeFileService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-sae-ecde.xml")
@SuppressWarnings("PMD.TooManyMethods")
public class EcdeFileServiceImplTest {
   
   private URI uri;
      
   @Autowired
   private EcdeFileService ecdeFileService;
   
   private static final String MESSAGE_INNATENDU = "message inattendu";
   //private static final String AUCUN_ECDE = "Aucun ECDE n'est transmis en paramètre.";

   
   private static final String ECDECER69 = "ecde.cer69.recouv";
   private static final String ECDE = "ecde";
   private static final String ATTESTATION = "/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf";
   
   // utilisation pour la convertion
   private static EcdeSource ecde1,ecde2,ecde3, ecde4;
   
   // file attestation
   private static final File ATTESTATION_FILE = new File("/ecde/ecde_lyon/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf");
   private static final File ATTESTATION_FILE3 = new File("\\ecde/ecde_lyon/DCL001\\19991231/3/documents/attestation/1990\\attestation1.pdf");
   private static final File ATTESTATION_FILE2 = new File("/mnt/ai/ecde/ecde_lokmen/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf");
   
   @BeforeClass
   public static void init() throws URISyntaxException {
      ecde1 = new EcdeSource("ecde.hoth.recouv", new File("/ecde/ecde_host/"));
      ecde2 = new EcdeSource(ECDECER69, new File("/ecde/ecde_lyon/"));
      ecde3 = new EcdeSource("ecde.tatoine.recouv", new File("/ecde/ecde_tatoine/"));
      ecde4 = new EcdeSource("ecde.tatoine2.recouv", new File("/mnt/ai/ecde/ecde_lokmen/"));
      
   }
   
   
   // l'url est bien en concordance avec la liste ECDESource donnee en paramètre
   @Test
   public void convertUrlToFileTest() throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException {
      uri = new URI(ECDE, "ecde.cer69.recouv", ATTESTATION, null);
      File messageObtenu = ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
      File messageAttendu = new File("/ecde/ecde_lyon/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf");
      
      assertEquals(MESSAGE_INNATENDU, messageObtenu, messageAttendu);
   }
   
   // exception levée si l'URI est absente de la liste des ECDESources donnee en param
   @Test(expected = EcdeBadURLException.class)
   public void convertUrlToFileUriNotExistTest() throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException  {
      uri = new URI(ECDE, ECDECER69, ATTESTATION, null);
      ecdeFileService.convertURIToFile(uri, ecde1, ecde3);
      fail("Une exception était attendue! L'exception EcdeBadURLException");
   }   
   
   
   // test pour montrer que la conversion ne fonctionne pas si dans l'URL 
   // ne respecte pas le format
   // ecde/authority/numeroCS/dateTraitement/idTraitement/documents/nom_du_fichier
   // scheme ne vaut pas ecde
   @Test(expected = EcdeBadURLFormatException.class)
   public void convertUrlToFileBadURLFormatTest() throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException {
      uri = new URI("ecd", ECDECER69, ATTESTATION, null);
      ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
      fail("Une exception de type EcdeBadURLFormatException était attendue!");
   }
   
     
   // document n'est pas égal à la constante 'documents'
   @Test(expected = EcdeBadURLFormatException.class)
   public void convertUrlToFileBadURLFormatPathDocumentTest() throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException {
      uri = new URI(ECDE, ECDECER69, "/DCL001/19991231/3/document/attestation/1990/attestation1.pdf", null);
      ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
      fail("Une exception était attendue! L'exception EcdeBadURLFormatException sur valeur constante documents");
   }
   
   
   // date traitement ne respecte pas le format AAAAMMJJ erreur sur le mois 13
   @Test(expected = EcdeBadURLFormatException.class)
   public void convertUrlToFileBadURLFormatPathDateTest() throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException {
      uri = new URI(ECDE, ECDECER69, "/DCL001/19991331/3/documents/attestation/1990/attestation1.pdf", null);
      ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
      fail("Une exception était attendue! L'exception EcdeBadURLFormatException sur format du mois");
   }
   
    
   // date traitement ne respecte pas le format AAAAMMJJ erreur sur le jour 00
   @Test(expected = EcdeBadURLFormatException.class)
   public void convertUrlToFileBadURLFormatPathDate2Test() throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException {
      uri = new URI(ECDE, ECDECER69, "/DCL001/19991200/3/documents/attestation/1990/attestation1.pdf", null);
      ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
      fail("Une exception était attendue! L'exception EcdeBadURLFormatException sur format du jour");
   }
   
   
   //-------------------------- FILE TO URI -------------------------------------------------
   
   //-------- Conversion OK  --------------------------------------------------------------
   @Test
   public void convertFileToURITest() throws EcdeBadFileException {
      URI uri = ecdeFileService.convertFileToURI(ATTESTATION_FILE, ecde1, ecde2, ecde3);
      String resultatAttendu = "ecde://ecde.cer69.recouv/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf";
      String resultatObtenu = uri.toString();
      
      assertEquals("resultat non attendu", resultatAttendu, resultatObtenu);
   }
   
   //----------Chemin de fichier non présent dans ECDE sources donne en param -------------
   @Test(expected = EcdeBadFileException.class)
   public void convertFileToURIEcdeNotExistTest() throws EcdeBadFileException {
      ecdeFileService.convertFileToURI(ATTESTATION_FILE, ecde1, ecde3);
      
      fail("Une exception était attendue! L'exception EcdeBadFileException sur File " +
      	  "non retrouvé dans liste des Ecdes donne en param");
   }
   
   //--------- Conversion OK avec /mnt/ai/ecde/ecde_lyon
   @Test
   public void convertFileToURI2Test() throws EcdeBadFileException {
      URI uri = ecdeFileService.convertFileToURI(ATTESTATION_FILE2, ecde1, ecde2, ecde3, ecde4);
      String resultatAttendu = "ecde://ecde.tatoine2.recouv/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf";
      String resultatObtenu = uri.toString();
      
      assertEquals("resultat non attendu", resultatAttendu, resultatObtenu);
   }
   
   //----------Test avec un nom de fichier avec des \ et des /
   @Test
   public void convertFileToURISlashTest() throws EcdeBadFileException {
      URI uri = ecdeFileService.convertFileToURI(ATTESTATION_FILE3, ecde1, ecde2, ecde3);
      
      String resultatAttendu = "ecde://ecde.cer69.recouv/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf";
      String resultatObtenu = uri.toString();
      
      assertEquals("resultat non attendu", resultatAttendu, resultatObtenu);
   }
   
   
}
