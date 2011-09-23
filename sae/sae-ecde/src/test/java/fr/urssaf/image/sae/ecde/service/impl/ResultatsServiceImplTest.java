package fr.urssaf.image.sae.ecde.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.bo.model.SAEError;
import fr.urssaf.image.sae.bo.model.untyped.UntypedDocumentOnError;
import fr.urssaf.image.sae.bo.model.untyped.UntypedMetadata;
import fr.urssaf.image.sae.ecde.exception.EcdeXsdException;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.DocumentVirtuelType;
import fr.urssaf.image.sae.ecde.modele.resultats.Resultats;
import fr.urssaf.image.sae.ecde.modele.source.EcdeSource;
import fr.urssaf.image.sae.ecde.modele.source.EcdeSources;
import fr.urssaf.image.sae.ecde.service.ResultatService;
/**
 * Classe permettant de tester l'implémentation
 * de la méthode persist du service ResultatsServiceImpl
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-sae-ecde-test.xml")
@SuppressWarnings({"PMD.MethodNamingConventions", "PMD.TooManyMethods", "PMD.NcssMethodCount","PMD.ExcessiveMethodLength", "PMD.ExcessiveImports"})
public class ResultatsServiceImplTest {

   @Autowired
   private ResultatService resultatService;
   
   public static Resultats resultat;
   private static final String FILE_TEMP = "/1/20110101/3/documents/repertoire/testunitaire.txt";
   
   private static String ecdeDirectory = "";
   private static final String RESULTAT = "resultats.xml";
   private static final String FILE_NOT_EXSISTS = "Fichier non existant!";
   
   private static final String SEPARATOR = System.getProperty("file.separator");
   
   @Autowired
   public EcdeSources ecdeSources;
   
   private static final String TEST_UNIT = "ecde.testunit.recouv";
   
   private static File repertoireTemp = new File("");
   private static File repertoireFinal = new File("");
   private static File somCopy1 = new File("");
   
   @BeforeClass
   public static void init() throws IOException {
      // resultat sans message document en erreur
      resultat = new Resultats();
   }
   
   private void createAbo() throws URISyntaxException, IOException {
      
      ecdeSources.getSources();
      for (EcdeSource ecde : ecdeSources.getSources()) {
         if (TEST_UNIT.equals(ecde.getHost())) {
            repertoireTemp = ecde.getBasePath();
         }
      }
      repertoireFinal = new File(repertoireTemp.getAbsolutePath() + "/1/20110101/3/");
      ecdeDirectory = repertoireFinal.getAbsolutePath();
      FileUtils.forceMkdir(repertoireFinal);
   }
   
   private static void createFileTemp() throws URISyntaxException, IOException {
      
      ClassPathResource classPath = new ClassPathResource(FILE_TEMP);
      File sommaire1 = classPath.getFile();
     
      File rep = new File(repertoireFinal.getAbsolutePath() + SEPARATOR + "documents" + SEPARATOR + "repertoire");
      FileUtils.forceMkdir(rep);
      
      
      somCopy1 = new File(rep.getAbsolutePath() + SEPARATOR + "testunitaire.txt");
      FileUtils.copyFile(sommaire1, somCopy1);
      
      somCopy1.createNewFile();      
   }
   
   
   private static void initialiseResultats() {
      
      resultat.setBatchMode("TOUT_OU_RIEN");
      resultat.setEcdeDirectory(ecdeDirectory);
      resultat.setInitialDocumentsCount(1);
      resultat.setInitialVirtualDocumentsCount(0);
      resultat.setIntegratedDocumentsCount(1);
      resultat.setIntegratedVirtualDocumentsCount(0);
      resultat.setNonIntegratedDocuments(new ArrayList<UntypedDocumentOnError>());
      resultat.setNonIntegratedVDocuments(new ArrayList<DocumentVirtuelType>());
      resultat.setNonIntegratedDocumentsCount(0);
      resultat.setNonIntegratedVirtualDocumentsCount(0);
      
   }
   
   private static void initialiseResultatsOneError() throws IOException {
      
      resultat.setBatchMode("TOUT_OU_RIEN");
      resultat.setEcdeDirectory(ecdeDirectory);
      resultat.setInitialDocumentsCount(1);
      resultat.setInitialVirtualDocumentsCount(0);
      resultat.setIntegratedDocumentsCount(0);
      resultat.setIntegratedVirtualDocumentsCount(0);
      
      byte[] content = FileUtils.readFileToByteArray(somCopy1);
      
      
      UntypedMetadata metadata = new UntypedMetadata("Hash", "456598754");

      UntypedMetadata metadata2 = new UntypedMetadata("TypeHash", "SHA-1");
      List<UntypedMetadata> metadatas = new ArrayList<UntypedMetadata>();
      metadatas.add(metadata);
      metadatas.add(metadata2);
      
      SAEError saeError = new SAEError("testError", "doc1 en error");
      List<SAEError> err = new ArrayList<SAEError>();
      err.add(saeError);
      
      UntypedDocumentOnError error = new UntypedDocumentOnError(content, metadatas, err);
      error.setFilePath(somCopy1.getAbsolutePath());
      List<UntypedDocumentOnError> listError = new ArrayList<UntypedDocumentOnError>();
      listError.add(error);
      
      
      resultat.setNonIntegratedDocuments(listError);
      resultat.setNonIntegratedVDocuments(new ArrayList<DocumentVirtuelType>());
      resultat.setNonIntegratedDocumentsCount(1);
      resultat.setNonIntegratedVirtualDocumentsCount(0);
      
      
   }
   
   private static void initialiseResultatsPlsError() throws IOException {
      resultat.setBatchMode("TOUT_OU_RIEN");
      resultat.setEcdeDirectory(ecdeDirectory);
      resultat.setInitialDocumentsCount(2);
      resultat.setInitialVirtualDocumentsCount(0);
      resultat.setIntegratedDocumentsCount(0);
      resultat.setIntegratedVirtualDocumentsCount(0);
      
      byte[] content = FileUtils.readFileToByteArray(somCopy1);
      
      
      UntypedMetadata metadata = new UntypedMetadata("Hash", "456598754");
      UntypedMetadata metadata2 = new UntypedMetadata("TypeHash", "SHA-1");
      List<UntypedMetadata> metadatas = new ArrayList<UntypedMetadata>();
      metadatas.add(metadata);
      metadatas.add(metadata2);
      
      SAEError saeError = new SAEError("testError", "doc1 en error");
      List<SAEError> err = new ArrayList<SAEError>();
      err.add(saeError);
      
      SAEError saeError2 = new SAEError("testError2", "doc2 en error");
      err.add(saeError2);
      
      UntypedDocumentOnError error = new UntypedDocumentOnError(content, metadatas, err);
      error.setFilePath(somCopy1.getAbsolutePath());
      List<UntypedDocumentOnError> listError = new ArrayList<UntypedDocumentOnError>();
      listError.add(error);
      
      
      resultat.setNonIntegratedDocuments(listError);
      resultat.setNonIntegratedVDocuments(new ArrayList<DocumentVirtuelType>());
      resultat.setNonIntegratedDocumentsCount(1);
      resultat.setNonIntegratedVirtualDocumentsCount(0);
   }
   

   private static void initialiseResultatsBatchModeError() {
      
      resultat.setBatchMode("TOUT");
      resultat.setEcdeDirectory(ecdeDirectory);
      resultat.setInitialDocumentsCount(1);
      resultat.setInitialVirtualDocumentsCount(0);
      resultat.setIntegratedDocumentsCount(1);
      resultat.setIntegratedVirtualDocumentsCount(0);
      resultat.setNonIntegratedDocuments(new ArrayList<UntypedDocumentOnError>());
      resultat.setNonIntegratedVDocuments(new ArrayList<DocumentVirtuelType>());
      resultat.setNonIntegratedDocumentsCount(0);
      resultat.setNonIntegratedVirtualDocumentsCount(0);
      
   }
   
   
   // Test avec resultat sans document en erreur
   @Test
   public void persistResultatSuccess() throws EcdeXsdException, IOException, URISyntaxException {
      createAbo();
      initialiseResultats();
      resultatService.persistResultat(resultat);
      File resultatXml = new File(repertoireFinal + SEPARATOR + RESULTAT);
      assertEquals(FILE_NOT_EXSISTS, true, resultatXml.exists());
   }
   // Test avec creation d'un document en erreur
   @Test
   public void persistFailureOneDocOnError() throws URISyntaxException, IOException, EcdeXsdException {
      createAbo();
      createFileTemp();
      
      initialiseResultatsOneError();
      resultatService.persistResultat(resultat);
      
      File resultatXml = new File(repertoireFinal + SEPARATOR + RESULTAT);
      assertEquals(FILE_NOT_EXSISTS, true, resultatXml.exists());
   }
   
   // Test avec creation de plusieurs documents en erreur
   @Test
   public void persistFailurePlsDocOnError() throws URISyntaxException, IOException, EcdeXsdException {
      createAbo();
      createFileTemp();
      
      initialiseResultatsPlsError();
      resultatService.persistResultat(resultat);
      
      File resultatXml = new File(repertoireFinal + SEPARATOR + RESULTAT);
      assertEquals(FILE_NOT_EXSISTS, true, resultatXml.exists());
   }
   
   // Test avec bacthMode non TOUT_OU_RIEN
   // No enum const BatchModeType TOUT --> doit etre egal à TOUT_OU_RIEN
   @Test(expected = IllegalArgumentException.class)
   public void persistFailureBacthModeError() throws URISyntaxException, IOException, EcdeXsdException {
      createAbo();
      createFileTemp();
      
      initialiseResultatsBatchModeError();
      resultatService.persistResultat(resultat);
      
      File resultatXml = new File(repertoireFinal + SEPARATOR + RESULTAT);
      assertEquals(FILE_NOT_EXSISTS, true, resultatXml.exists());
   }
   
   
}
