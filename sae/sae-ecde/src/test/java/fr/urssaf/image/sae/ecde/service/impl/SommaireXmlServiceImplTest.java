package fr.urssaf.image.sae.ecde.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.ecde.exception.EcdeXsdException;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.BatchModeType;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.ListeDocumentsType;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.ListeDocumentsVirtuelsType;
import fr.urssaf.image.sae.ecde.modele.sommaire.SommaireType;
import fr.urssaf.image.sae.ecde.service.SommaireXmlService;


/**
 * Classe permettant de tester l'implémentation
 * des méthodes de la classe SommaireXmlServiceImpl
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-sae-ecde.xml")
@SuppressWarnings({"PMD.MethodNamingConventions","PMD.TooManyMethods", "PMD"})
@Ignore
public class SommaireXmlServiceImplTest {

   @Autowired
   private SommaireXmlService service;
   
   // Recupération repertoire temp
//   private static final String REPERTORY;
//   static {
//       REPERTORY = SystemUtils.getJavaIoTmpDir().getAbsolutePath();
//
//         System.setProperty("file.encoding", "UTF-8");
//
//   }
   
   private static final String MESSAGE_INATTENDU = "message inattendu"; 
   private static File input;
   
   @BeforeClass
   public static void init() {
      //input = new File(FilenameUtils.concat(REPERTORY, "sommaire.xml"));
      input = new File("src/test/resources/sommaire-test001.xml");
   }
   
   
   // Test avec succes -- lecture du fichier sommaire.xml
   @Test
   public void readSommaireXml_success() throws EcdeXsdException {
      //TODO a corriger
      SommaireType sommaire = service.readSommaireXml(input);
      assertEquals(MESSAGE_INATTENDU, BatchModeType.TOUT_OU_RIEN, sommaire.getBatchMode());
   }
   
   
   // test ne respectant pas la structure du sommaire.xsd
   @Test
   public void readSommaireXml_failure_nonRespectXSD() throws EcdeXsdException {
      //TODO assert
   }
   
   // test ne respectant pas la structure du commun_som_res.xsd 
   @Test
   public void readSommaireXml_failure_nonRespectCommunXSD() throws EcdeXsdException {
    //TODO assert
   }
   
   // test sans schema XSD
   @Test
   public void readSommaireXml_failure_sansXSD() throws EcdeXsdException {
    //TODO assert
   }
   
   
   //----------------------- GENERATION DUN FICHIER
   public void generationSchemaXsd() throws JAXBException, FileNotFoundException {
      
      SommaireType sommaireType = new SommaireType();
      
      ListeDocumentsType listeDocs = new ListeDocumentsType();
      ListeDocumentsVirtuelsType listeVirtDocs = new ListeDocumentsVirtuelsType();
      
     
      sommaireType.setBatchMode(BatchModeType.fromValue("PARTIEL"));
      sommaireType.setDocuments(listeDocs);
      sommaireType.setDateCreation(null);
      sommaireType.setDescription("Xml Test Lokmen");
      sommaireType.setDocumentsVirtuels(listeVirtDocs);
      
      
      
      // Création des objets nécessaires
      JAXBContext context = JAXBContext.newInstance(SommaireType.class.getPackage().getName());
      Marshaller marshaller = context.createMarshaller();
      
      // Option pour indenter le XML en sortie
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      
      // Déclenche le marshalling
      OutputStream output = new FileOutputStream("C:/sommaireLokmen.xml");
      marshaller.marshal(sommaireType, System.out);
      marshaller.marshal(sommaireType, output);
   }
   
}
