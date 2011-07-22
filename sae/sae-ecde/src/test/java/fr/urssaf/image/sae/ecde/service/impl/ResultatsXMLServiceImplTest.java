package fr.urssaf.image.sae.ecde.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.ecde.exception.EcdeXsdException;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.BatchModeType;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.ListeDocumentsType;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.ListeDocumentsVirtuelsType;
import fr.urssaf.image.sae.ecde.modele.resultats.ResultatsType;
import fr.urssaf.image.sae.ecde.service.ResultatsXmlService;

/**
 * Classe permettant de tester l'implémentation
 * des méthodes de la classe ResultatsXmlServiceImpl
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-sae-ecde.xml")
@SuppressWarnings({"PMD.MethodNamingConventions","PMD.TooManyMethods", "PMD"})

public class ResultatsXMLServiceImplTest {

   private static ResultatsXmlService service;
   
   private static ResultatsType resultats;
   
   private static OutputStream output;
   private static final String MESSAGE_INATTENDU = "message inattendu"; 
   
   private static final String REPERTORY;

   
   static {
       REPERTORY = SystemUtils.getJavaIoTmpDir().getAbsolutePath();

         System.setProperty("file.encoding", "UTF-8");

   }
   
   
   @BeforeClass
   public static void init() throws FileNotFoundException {
      resultats = new ResultatsType();
      
      resultats.setBatchMode(BatchModeType.fromValue("PARTIEL"));
      resultats.setInitialDocumentsCount(0);
      resultats.setInitialVirtualDocumentsCount(0);
      resultats.setIntegratedDocumentsCount(5);
      resultats.setIntegratedVirtualDocumentsCount(18);
      resultats.setNonIntegratedDocuments(new ListeDocumentsType());
      resultats.setNonIntegratedDocumentsCount(18);
      resultats.setNonIntegratedVirtualDocuments(new ListeDocumentsVirtuelsType());
      resultats.setNonIntegratedVirtualDocumentsCount(6);
      
      output = new FileOutputStream(FilenameUtils.concat(REPERTORY,"resultatsLokmen.xml"));
      
      service = new ResultatsXmlServiceImpl();
   }
   
   // Test simple avec un outputStream
   @Test
   public void writeResultatsXml_success() throws EcdeXsdException, JAXBException {
      service.writeResultatsXml(resultats, output);
      //TODO assert
   }
   
   // Test avec un fichier en deuxieme argument
   // on vient verifier que le fichier a bien ete cree
   @Test
   public void writeResutatsXml_success_file() throws EcdeXsdException {
      File outputFile = new File(FilenameUtils.concat(REPERTORY,"resultatsLokmenFile.xml"));
      service.writeResultatsXml(resultats, outputFile);
      boolean exist = false;
      if(outputFile.exists()) {
         exist = true;
      }
      assertEquals(MESSAGE_INATTENDU, true, exist);
   }
   
   
   
   //------------------- calcul checkSum
//   private static long doChecksum(String fileName) {
//
//      try {
//
//          CheckedInputStream cis = null;
//          long fileSize = 0;
//          try {
//              // Computer CRC32 checksum
//              cis = new CheckedInputStream(new FileInputStream(fileName), new CRC32());
//              fileSize = new File(fileName).length();
//          } catch (FileNotFoundException e) {
//              return 0;
//          }
//          byte[] buf = new byte[128];
//          while(cis.read(buf) >= 0) {
//          }
//
//          long checksum = cis.getChecksum().getValue();
//          return checksum;
//
//      } catch (IOException e) {
//          return 0;
//      }
//  }
   
   
}
