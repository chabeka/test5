package fr.urssaf.image.sae.ecde.service.impl;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.xml.bind.JAXBException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import fr.urssaf.image.sae.ecde.exception.EcdeXsdException;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.BatchModeType;
import fr.urssaf.image.sae.ecde.modele.sommaire.SommaireType;
import fr.urssaf.image.sae.ecde.service.SommaireXmlService;
/**
 * Classe permettant de tester l'implémentation
 * des méthodes de la classe SommaireXmlServiceImpl
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-sae-ecde.xml")
@SuppressWarnings({"PMD.MethodNamingConventions"})
public class SommaireXmlServiceImplTest {

   @Autowired
   private SommaireXmlService service;
   
   private static final String MESSAGE_INATTENDU = "message inattendu"; 
   
   
   // Recupération repertoire temp de l'os
   private static final String REPERTORY;
   static {
       REPERTORY = SystemUtils.getJavaIoTmpDir().getAbsolutePath();
       System.setProperty("file.encoding", "UTF-8");
   }
   // nom du répertoire creer dans le repertoire temp de l'os
   private static String sommaireEcde = "sommaireEcde";
   // separateur de fichier
   private static final String FILE_SEPARATOR = System.getProperty("file.separator");
   // declaration d'un répertoire dans le repertoire temp de l'os
   private static final String REPERTOIRE = REPERTORY + FILE_SEPARATOR + sommaireEcde;
   
   @BeforeClass
   public static void init() {
      File rep = new File(REPERTOIRE); 
      // creation d'un repertoire dans le rep temp de l'os
      rep.mkdir();
      
   }
   
   // Test avec succes -- lecture du fichier sommaire-test001.xml 
   // Ce fichier a été placé dans le répertoire temporaire de l'os
   // Il est impératif de ce créer un fichier pour que ce test fonctionne
   @Test
   public void readSommaireXml_success_file() throws EcdeXsdException, FileNotFoundException, JAXBException {
      File input = new File(FilenameUtils.concat(REPERTOIRE, "sommaire-test001.xml"));
      SommaireType sommaire = service.readSommaireXml(input);
      assertEquals(MESSAGE_INATTENDU, BatchModeType.TOUT_OU_RIEN, sommaire.getBatchMode());
      assertEquals(MESSAGE_INATTENDU, "La description du traitement", sommaire.getDescription());
      assertEquals(MESSAGE_INATTENDU, 2, sommaire.getDocuments().getDocument().size());
      assertEquals(MESSAGE_INATTENDU, 2, sommaire.getDocumentsVirtuels().getDocumentVirtuel().size());
   }
   
   // Test avec succes -- lecture du fichier sommaire-test001.xml 
   // Ce fichier a été placé dans le répertoire temporaire de l'os
   // Il est impératif de ce créer un fichier pour que ce test fonctionne
   @Test
   public void readSommaireXml_success_inputStream() throws EcdeXsdException, FileNotFoundException, JAXBException {
      File file = new File(FilenameUtils.concat(REPERTOIRE, "sommaire-test001.xml"));
      InputStream input= new FileInputStream(file);
      SommaireType sommaire = service.readSommaireXml(input);
      assertEquals(MESSAGE_INATTENDU, BatchModeType.TOUT_OU_RIEN, sommaire.getBatchMode());
      assertEquals(MESSAGE_INATTENDU, "La description du traitement", sommaire.getDescription());
      assertEquals(MESSAGE_INATTENDU, 2, sommaire.getDocuments().getDocument().size());
      assertEquals(MESSAGE_INATTENDU, 2, sommaire.getDocumentsVirtuels().getDocumentVirtuel().size());
   }
   
}
