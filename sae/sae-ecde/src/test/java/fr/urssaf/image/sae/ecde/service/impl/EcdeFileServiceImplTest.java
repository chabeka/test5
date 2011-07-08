package fr.urssaf.image.sae.ecde.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.ecde.exception.EcdeBadURLException;
import fr.urssaf.image.sae.ecde.exception.EcdeBadURLFormatException;
import fr.urssaf.image.sae.ecde.modele.source.EcdeSource;
import fr.urssaf.image.sae.ecde.service.EcdeFileService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-sae-ecde.xml")
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
   private final EcdeSource ecde1 = new EcdeSource("ecde.hoth.recouv", new File("/ecde/ecde_host/"));
   private final EcdeSource ecde2 = new EcdeSource(ECDECER69, new File("/ecde/ecde_lyon/"));
   private final EcdeSource ecde3 = new EcdeSource("ecde.tatoine.recouv", new File("/ecde/ecde_tatoine/"));
      
   
   // pour la vérification que l'uri est bien absente de la liste des ecdesource fournit en param
   // donc génération d'une exception
   /*@Test
   public void convertUrlToFileUriNotExistTest()  {
  
      try {
         uri = new URI(ECDE, ECDECER69, ATTESTATION, "");
         ecdeFileService.convertURIToFile(uri, ecde1, ecde3);
         fail("Une exception était attendue! L'exception EcdeBadURLException");
      }catch (EcdeBadURLFormatException e){
         assertEquals("Message4 non attendu","L'URL ECDE ecde://"+ECDECER69+ATTESTATION+"# n'appartient à aucun ECDE transmis en paramètre du service." , e.getMessage());
      }catch (URISyntaxException e){
         assertEquals("Message5 non attendu","L'URL ECDE ecde://"+ECDECER69+ATTESTATION+"# n'appartient à aucun ECDE transmis en paramètre du service." , e.getMessage());// rien à faire, c'est normal.
      }catch (EcdeBadURLException e){
         assertEquals("Message6 non attendu","L'URL ECDE ecde://"+ECDECER69+ATTESTATION+"# n'appartient à aucun ECDE transmis en paramètre du service." , e.getMessage());
      }
   }*/
   @Test(expected = EcdeBadURLException.class)
   public void convertUrlToFileUriNotExistTest() throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException  {
      uri = new URI(ECDE, ECDECER69, ATTESTATION, "");
      ecdeFileService.convertURIToFile(uri, ecde1, ecde3);
      fail("Une exception était attendue! L'exception EcdeBadURLException");
   }
   
   
   
   //-------------------------------------------------------------------------------
   
   // conversion d'une url en fichier
   // l'url est bien en concordance avec la liste ECDESource donnee en paramètre
   @Test
   public void convertUrlToFileTest() throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException {
      uri = new URI(ECDE, "ecde.cer69.recouv", ATTESTATION, "");
      File messageObtenu = ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
      File messageAttendu = new File("/ecde/ecde_lyon/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf");
      
      assertEquals(MESSAGE_INNATENDU, messageObtenu, messageAttendu);
   }
   
   
   
 //-------------------------------------------------------------------------------
   
   
   //test avec url null
   /*@Test
   public void convertUrlToFileTestUrlNotExist () {
      try {
         ecdeFileService.convertURIToFile(null, ecde1, ecde2, ecde3);
         fail("Une exception était attendue! L'exception IllegalArgumentException");
      }catch (EcdeBadURLFormatException e){
         assertEquals(MESSAGE_INNATENDU, "L'argument 'ecdeURL' doit être renseigné.", e.getMessage());
      }catch (EcdeBadURLException e){
         assertEquals(MESSAGE_INNATENDU, "L'argument 'ecdeURL' doit être renseigné.", e.getMessage());
      }catch (IllegalArgumentException e){
         assertEquals(MESSAGE_INNATENDU, "L'argument 'ecdeURL' doit être renseigné.", e.getMessage());
      }
   }*/
   @Test(expected = IllegalArgumentException.class)
   public void convertUrlToFileTestUrlNotExist () throws EcdeBadURLException, EcdeBadURLFormatException {
      ecdeFileService.convertURIToFile(null, ecde1, ecde2, ecde3);
      fail("Une exception était attendue! L'exception IllegalArgumentException");
   }
   
   
   
 //-------------------------------------------------------------------------------
   
   
   
   //test avec ECDESOURCE null
   /*@Test
   public void convertUrlToFileTestUrlNull () throws URISyntaxException {
      try {
         uri = new URI(ECDE, ECDECER69, ATTESTATION, "");
         ecdeFileService.convertURIToFile(uri);
         fail("Une exception était attendue! L'exception IllegalArgumentException");
      }catch (EcdeBadURLFormatException e){
         assertEquals(MESSAGE_INNATENDU, AUCUN_ECDE, e.getMessage());
      }catch (EcdeBadURLException e){
         assertEquals(MESSAGE_INNATENDU, AUCUN_ECDE, e.getMessage());
      }catch (IllegalArgumentException e){
         assertEquals(MESSAGE_INNATENDU, AUCUN_ECDE, e.getMessage());
      }
   }*/
   @Test(expected = IllegalArgumentException.class)
   public void convertUrlToFileTestUrlNull () throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException {
      uri = new URI(ECDE, ECDECER69, ATTESTATION, "");
      ecdeFileService.convertURIToFile(uri);
      fail("Une exception était attendue! L'exception IllegalArgumentException");
   }
 //-------------------------------------------------------------------------------
   
   
   //test avec ECDESOURCE vide
   /*@Test
   public void convertUrlToFileTestUrlEmpty () throws URISyntaxException {
      try {
         uri = new URI(ECDE, ECDECER69, ATTESTATION, "");
         ecdeFileService.convertURIToFile(uri, new EcdeSource("", new File("")) );
         fail("Une exception était attendue! L'exception IllegalArgumentException sur url vide");
      }catch (EcdeBadURLFormatException e){
         assertEquals(MESSAGE_INNATENDU, "L'attribut Host de l'ECDE No 0 nest pas renseigné.", e.getMessage());
      }catch (EcdeBadURLException e){
         assertEquals(MESSAGE_INNATENDU, "L'attribut Host de l'ECDE No 0 nest pas renseigné.", e.getMessage());
      }catch (IllegalArgumentException e){
         assertEquals(MESSAGE_INNATENDU, "L'attribut Host de l'ECDE No 0 nest pas renseigné.", e.getMessage());
      }
   }*/
   //test avec ECDESOURCE vide
   @Test(expected = IllegalArgumentException.class)
   public void convertUrlToFileTestUrlEmpty () throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException {
      uri = new URI(ECDE, ECDECER69, ATTESTATION, "");
      ecdeFileService.convertURIToFile(uri, new EcdeSource("", new File("")) );
      fail("Une exception était attendue! L'exception IllegalArgumentException sur url vide");
   }   
   
   
   //-------------------------------------------------------------------------------
   
   
   // test pour montrer que la conversion ne fonctionne pas si dans l'URL 
   // ne respecte pas le format
   // ecde/authority/numeroCS/dateTraitement/idTraitement/documents/nom_du_fichier
   // scheme ne vaut pas ecde
   
   /*
   @Test
   public void convertUrlToFileBadURLFormatTest() throws URISyntaxException {
      try {
         uri = new URI("ecd", ECDECER69, ATTESTATION, "");
         ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
         fail("Une exception était attendue! L'exception IllegalArgumentException sur scheme");
      }catch (EcdeBadURLFormatException e){
         assertEquals(MESSAGE_INNATENDU, "L'URL ECDE ecd://ecde.cer69.recouv/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf est incorrecte.", e.getMessage());
      }catch (EcdeBadURLException e){
         assertEquals(MESSAGE_INNATENDU, "L'URL ECDE ecd://ecde.cer69.recouv/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf est incorrecte.", e.getMessage());
      }catch (IllegalArgumentException e){
         assertEquals(MESSAGE_INNATENDU, "L'URL ECDE ecd://ecde.cer69.recouv/DCL001/19991231/3/documents/attestation/1990/attestation1.pdf est incorrecte.", e.getMessage());
      }
   }*/
   
   @Test(expected = EcdeBadURLFormatException.class)
   public void convertUrlToFileBadURLFormatTest() throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException {
      uri = new URI("ecd", ECDECER69, ATTESTATION, "");
      ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
      fail("Une exception de type EcdeBadURLFormatException était attendue!");
   }
   
   
   
   //-------------------------------------------------------------------------------
   
   
   // path incorrect
   // document n'est pas égal à la constante 'documents'
   /*@Test
   public void convertUrlToFileBadURLFormatPathDocumentTest() throws URISyntaxException {
      try {
         uri = new URI(ECDE, ECDECER69, "/DCL001/19991231/3/document/attestation/1990/attestation1.pdf", "");
         ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
         fail("Une exception était attendue! L'exception IllegalArgumentException sur valeur constante documents");
      }catch (EcdeBadURLFormatException e){
         assertEquals(MESSAGE_INNATENDU, "L'URL ECDE ecde://ecde.cer69.recouv/DCL001/19991231/3/document/attestation/1990/attestation1.pdf est incorrecte.", e.getMessage());
      }catch (EcdeBadURLException e){
         assertEquals(MESSAGE_INNATENDU, "L'URL ECDE ecde://ecde.cer69.recouv/DCL001/19991231/3/document/attestation/1990/attestation1.pdf est incorrecte.", e.getMessage());
      }catch (IllegalArgumentException e){
         assertEquals(MESSAGE_INNATENDU, "L'URL ECDE ecde://ecde.cer69.recouv/DCL001/19991231/3/document/attestation/1990/attestation1.pdf est incorrecte.", e.getMessage());
      }
   }*/
   
   @Test(expected = EcdeBadURLFormatException.class)
   public void convertUrlToFileBadURLFormatPathDocumentTest() throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException {
      uri = new URI(ECDE, ECDECER69, "/DCL001/19991231/3/document/attestation/1990/attestation1.pdf", "");
      ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
      fail("Une exception était attendue! L'exception EcdeBadURLFormatException sur valeur constante documents");
   }
   
   //-------------------------------------------------------------------------------
   
   // path incorrect
   // date traitement ne respecte pas le format AAAAMMJJ erreur sur le mois 13
   /*@Test
   public void convertUrlToFileBadURLFormatPathDateTest() throws URISyntaxException {
      try {
         uri = new URI(ECDE, ECDECER69, "/DCL001/19991331/3/documents/attestation/1990/attestation1.pdf", "");
         ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
         fail("Une exception était attendue! L'exception IllegalArgumentException sur format du mois");
      }catch (EcdeBadURLFormatException e){
         assertEquals(MESSAGE_INNATENDU, "L'URL ECDE ecde://ecde.cer69.recouv/DCL001/19991331/3/document/attestation/1990/attestation1.pdf est incorrecte.", e.getMessage());
      }catch (EcdeBadURLException e){
         assertEquals(MESSAGE_INNATENDU, "L'URL ECDE ecde://ecde.cer69.recouv/DCL001/19991331/3/document/attestation/1990/attestation1.pdf est incorrecte.", e.getMessage());
      }catch (IllegalArgumentException e){
         assertEquals(MESSAGE_INNATENDU, "L'URL ECDE ecde://ecde.cer69.recouv/DCL001/19991331/3/document/attestation/1990/attestation1.pdf est incorrecte.", e.getMessage());
      }
   }*/
   @Test(expected = EcdeBadURLFormatException.class)
   public void convertUrlToFileBadURLFormatPathDateTest() throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException {
      uri = new URI(ECDE, ECDECER69, "/DCL001/19991331/3/documents/attestation/1990/attestation1.pdf", "");
      ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
      fail("Une exception était attendue! L'exception EcdeBadURLFormatException sur format du mois");
   }
   
   
   //-------------------------------------------------------------------------------
   
   // path incorrect
   // date traitement ne respecte pas le format AAAAMMJJ erreur sur le jour 00
   /*@Test
   public void convertUrlToFileBadURLFormatPathDate2Test() throws URISyntaxException {
      try {
         uri = new URI(ECDE, ECDECER69, "/DCL001/19991200/3/documents/attestation/1990/attestation1.pdf", "");
         ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
         fail("Une exception était attendue! L'exception IllegalArgumentException sur format du jour");
      }catch (EcdeBadURLFormatException e){
         assertEquals(MESSAGE_INNATENDU, "L'URL ECDE ecde://ecde.cer69.recouv/DCL001/19991200/3/document/attestation/1990/attestation1.pdf est incorrecte.", e.getMessage());
      }catch (EcdeBadURLException e){
         assertEquals(MESSAGE_INNATENDU, "L'URL ECDE ecde://ecde.cer69.recouv/DCL001/19991200/3/document/attestation/1990/attestation1.pdf est incorrecte.", e.getMessage());
      }catch (IllegalArgumentException e){
         assertEquals(MESSAGE_INNATENDU, "L'URL ECDE ecde://ecde.cer69.recouv/DCL001/19991200/3/document/attestation/1990/attestation1.pdf est incorrecte.", e.getMessage());
      }
   }*/
   @Test(expected = EcdeBadURLFormatException.class)
   public void convertUrlToFileBadURLFormatPathDate2Test() throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException {
      uri = new URI(ECDE, ECDECER69, "/DCL001/19991200/3/documents/attestation/1990/attestation1.pdf", "");
      ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
      fail("Une exception était attendue! L'exception EcdeBadURLFormatException sur format du jour");
   }
   //-------------------------------------------------------------------------------
   
   // path incorrect
   // ne doit pas etre present de ../ dans le chemin
   /*@Test
   public void convertUrlToFileBadURLFormatPathFileTest() throws URISyntaxException {
      try {
         uri = new URI(ECDE, ECDECER69, "/DCL001/19991200/3/documents/../attestation/1990/attestation1.pdf", "");
         ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
         fail("Une exception était attendue! L'exception IllegalArgumentException sur ../");
      }catch (EcdeBadURLFormatException e){
         assertEquals(MESSAGE_INNATENDU, "L'URL ECDE ecde://ecde.cer69.recouv/DCL001/19991200/3/document/../attestation/1990/attestation1.pdf est incorrecte.", e.getMessage());
      }catch (EcdeBadURLException e){
         assertEquals(MESSAGE_INNATENDU, "L'URL ECDE ecde://ecde.cer69.recouv/DCL001/19991200/3/document/../attestation/1990/attestation1.pdf est incorrecte.", e.getMessage());
      }catch (IllegalArgumentException e){
         assertEquals(MESSAGE_INNATENDU, "L'URL ECDE ecde://ecde.cer69.recouv/DCL001/19991200/3/document/../attestation/1990/attestation1.pdf est incorrecte.", e.getMessage());
      }
   }*/
   @Test(expected = EcdeBadURLFormatException.class)
   public void convertUrlToFileBadURLFormatPathFileTest() throws URISyntaxException, EcdeBadURLException, EcdeBadURLFormatException {
        uri = new URI(ECDE, ECDECER69, "/DCL001/19991200/3/documents/../attestation/1990/attestation1.pdf", "");
        ecdeFileService.convertURIToFile(uri, ecde1, ecde2, ecde3);
        fail("Une exception était attendue! L'exception EcdeBadURLFormatException sur ../");
   }
   
}
