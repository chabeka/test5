package fr.urssaf.image.sae.ecde.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
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
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.ComposantDocumentVirtuelType;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.DocumentType;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.DocumentVirtuelType;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.FichierType;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.ListeDocumentsType;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.ListeDocumentsVirtuelsType;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.ListeMetadonneeType;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.MetadonneeType;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.DocumentVirtuelType.Composants;
import fr.urssaf.image.sae.ecde.modele.resultats.ResultatsType;
import fr.urssaf.image.sae.ecde.service.ResultatsXmlService;
/**
 * Classe permettant de tester l'implémentation
 * des méthodes de la classe ResultatsXmlServiceImpl
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-sae-ecde.xml")
@SuppressWarnings({"PMD.MethodNamingConventions","PMD.NcssMethodCount","PMD.ExcessiveMethodLength", "PMD.ExcessiveImports"})
public class ResultatsXMLServiceImplTest {

   @Autowired
   private ResultatsXmlService service;
   
   private static ResultatsType resultats;
   private static ResultatsType resultats2;
    
   private static final String MESSAGE_INATTENDU = "message inattendu"; 
   
   // Recupération repertoire temp
   private static final String REPERTORY = SystemUtils.getJavaIoTmpDir().getAbsolutePath();

   // nom du répertoire creer dans le repertoire temp de l'os
   private static String resultatsEcde = "resultatsEcde";
   // declaration d'un répertoire dans le repertoire temp de l'os
   private static final String REPERTOIRE = FilenameUtils.concat(REPERTORY, resultatsEcde);
   
   private static final String SHA1 = "SHA-1";
   private static final String VALEUR = "La valeur";
   private static final String META1 = "CODE_METADONNEE_1";
   private static final String META2 = "CODE_METADONNEE_2";
   
   private static String hashValeur = "hashValeur";
   
   @BeforeClass
   public static void init() throws IOException {
      resultats = new ResultatsType();
      resultats2 = new ResultatsType();
      initialiseResultats();
      initialiseResultats2();
      File rep = new File(REPERTOIRE); 
      // creation d'un repertoire dans le rep temp de l'os
      FileUtils.forceMkdir(rep);
      //nettoyage du repertoire present dans le rep temp de l'os
      FileUtils.cleanDirectory(rep);
   }
   
   private static void initialiseResultats() {
      resultats.setBatchMode(BatchModeType.fromValue("PARTIEL"));
      resultats.setInitialDocumentsCount(4);
      resultats.setIntegratedDocumentsCount(2);
      resultats.setNonIntegratedDocumentsCount(2);
      resultats.setInitialVirtualDocumentsCount(5);
      resultats.setIntegratedVirtualDocumentsCount(4);
      resultats.setNonIntegratedVirtualDocumentsCount(1);
      
      // Non integratedDocuments -- document qui n'ont pas été archivés
      DocumentType doc1 = new DocumentType();
      // objet num
      FichierType objetNum = new FichierType();
      objetNum.setCheminEtNomDuFichier("repertoire/fichier1.pdf");
      objetNum.setHashValeur("541f9db389ff2d4b70dd25917277daafea1e7ba6");
      objetNum.setHashAlgo(SHA1);
      doc1.setObjetNumerique(objetNum);
      //meta donnees
      MetadonneeType meta1 = new MetadonneeType();
      meta1.setCode(META1);
      meta1.setValeur(VALEUR);
      MetadonneeType meta2 = new MetadonneeType();
      meta2.setCode(META2);
      meta2.setValeur(VALEUR);
      
      ListeMetadonneeType listeM = new ListeMetadonneeType();
      listeM.getMetadonnee().add(meta1);
      listeM.getMetadonnee().add(meta2);
      doc1.setMetadonnees(listeM);
      
      doc1.setNumeroPageDebut(1);
      doc1.setNombreDePages(1);
      //---------------------------------------      
      DocumentType doc2 = new DocumentType();
      // objet num
      FichierType objetNum2 = new FichierType();
      objetNum2.setCheminEtNomDuFichier("repertoire/fichier2.pdf");
      objetNum2.setHashValeur("541f9db389ff2d4b70dd25917277daafea1dfb98");
      objetNum2.setHashAlgo(SHA1);
      doc2.setObjetNumerique(objetNum2);
      //meta donnees
      MetadonneeType meta3 = new MetadonneeType();
      meta3.setCode(META1);
      meta3.setValeur(VALEUR);
      MetadonneeType meta4 = new MetadonneeType();
      meta4.setCode(META2);
      meta4.setValeur(VALEUR);
      
      ListeMetadonneeType listeM2 = new ListeMetadonneeType();
      listeM2.getMetadonnee().add(meta3);
      listeM2.getMetadonnee().add(meta4);
      doc2.setMetadonnees(listeM2);
            
      ListeDocumentsType listeD = new ListeDocumentsType();
      listeD.getDocument().add(doc1);
      listeD.getDocument().add(doc2);
      resultats.setNonIntegratedDocuments(listeD);
      
      // NonIntegratedVirtualDocument -- document virtuel qui n'ont pas été archivés
      DocumentVirtuelType docVirtuel = new DocumentVirtuelType();
      FichierType objNumVir = new FichierType();
      objNumVir.setCheminEtNomDuFichier("repertoire/fichier3.pdf");
      objNumVir.setHashValeur(hashValeur);
      objNumVir.setHashAlgo(SHA1);
      docVirtuel.setObjetNumerique(objNumVir);
      
      ComposantDocumentVirtuelType composant = new ComposantDocumentVirtuelType();
      MetadonneeType meta5 = new MetadonneeType();
      meta5.setCode(META1);
      meta5.setValeur(VALEUR);
      MetadonneeType meta6 = new MetadonneeType();
      meta6.setCode(META2);
      meta6.setValeur(VALEUR);
      composant.setNumeroPageDebut(1);
      composant.setNombreDePages(2);
      ListeMetadonneeType listeM3 = new ListeMetadonneeType();
      listeM3.getMetadonnee().add(meta5);
      listeM3.getMetadonnee().add(meta6);
      
      composant.setMetadonnees(listeM3);
            
      ComposantDocumentVirtuelType composant2 = new ComposantDocumentVirtuelType();
      MetadonneeType meta7 = new MetadonneeType();
      meta7.setCode(META1);
      meta7.setValeur(VALEUR);
      MetadonneeType meta8 = new MetadonneeType();
      meta8.setCode(META2);
      meta8.setValeur(VALEUR);
      composant2.setNumeroPageDebut(2);
      composant2.setNombreDePages(2);
      ListeMetadonneeType listeM4 = new ListeMetadonneeType();
      listeM4.getMetadonnee().add(meta7);
      listeM4.getMetadonnee().add(meta8);
      
      composant2.setMetadonnees(listeM4);
      
      Composants composants = new Composants();
      composants.getComposant().add(composant);
      composants.getComposant().add(composant2);
      docVirtuel.setComposants(composants);
      //-- un deuxieme document virtuel qui n'as pas pu être archivé
      DocumentVirtuelType docVirtuel2 = new DocumentVirtuelType();
      FichierType objNumVir2 = new FichierType();
      objNumVir2.setCheminEtNomDuFichier("repertoire/fichier3.pdf");
      objNumVir2.setHashValeur(hashValeur);
      objNumVir2.setHashAlgo(SHA1);
      docVirtuel2.setObjetNumerique(objNumVir2);
      
      ComposantDocumentVirtuelType composant3 = new ComposantDocumentVirtuelType();
      MetadonneeType meta9 = new MetadonneeType();
      meta9.setCode(META1);
      meta9.setValeur(VALEUR);
      MetadonneeType meta10 = new MetadonneeType();
      meta10.setCode(META2);
      meta10.setValeur(VALEUR);
      composant3.setNumeroPageDebut(1);
      composant3.setNombreDePages(2);
      ListeMetadonneeType listeM5 = new ListeMetadonneeType();
      listeM5.getMetadonnee().add(meta9);
      listeM5.getMetadonnee().add(meta10);
      
      composant3.setMetadonnees(listeM5);
            
      ComposantDocumentVirtuelType composant4 = new ComposantDocumentVirtuelType();
      MetadonneeType meta11 = new MetadonneeType();
      meta11.setCode(META1);
      meta11.setValeur(VALEUR);
      MetadonneeType meta12 = new MetadonneeType();
      meta12.setCode(META2);
      meta12.setValeur(VALEUR);
      composant4.setNumeroPageDebut(2);
      composant4.setNombreDePages(2);
      ListeMetadonneeType listeM6 = new ListeMetadonneeType();
      listeM6.getMetadonnee().add(meta11);
      listeM6.getMetadonnee().add(meta12);
      
      composant4.setMetadonnees(listeM6);
      
      Composants composants2 = new Composants();
      composants2.getComposant().add(composant3);
      composants2.getComposant().add(composant4);
      docVirtuel2.setComposants(composants2);
      
      //--------------------
      ListeDocumentsVirtuelsType listeDV = new ListeDocumentsVirtuelsType();
      listeDV.getDocumentVirtuel().add(docVirtuel);
      listeDV.getDocumentVirtuel().add(docVirtuel2);
      resultats.setNonIntegratedVirtualDocuments(listeDV);
   }

   private static void initialiseResultats2() {
      
      // Non integratedDocuments -- document qui n'ont pas été archivés
      DocumentType doc1 = new DocumentType();
      // objet num
      FichierType objetNum = new FichierType();
      objetNum.setCheminEtNomDuFichier("repertoire/fichier1.pdf");
      objetNum.setHashValeur("541f9db389ff2d4b70dd25917277daafea1e7ba6");
      objetNum.setHashAlgo(SHA1);
      doc1.setObjetNumerique(objetNum);
      //meta donnees
      MetadonneeType meta1 = new MetadonneeType();
      meta1.setCode(META1);
      meta1.setValeur(VALEUR);
      MetadonneeType meta2 = new MetadonneeType();
      meta2.setCode(META2);
      meta2.setValeur(VALEUR);
      
      ListeMetadonneeType listeM = new ListeMetadonneeType();
      listeM.getMetadonnee().add(meta1);
      listeM.getMetadonnee().add(meta2);
      doc1.setMetadonnees(listeM);
      
      doc1.setNumeroPageDebut(1);
      doc1.setNombreDePages(1);
            
      ListeDocumentsType listeD = new ListeDocumentsType();
      listeD.getDocument().add(doc1);
      resultats2.setNonIntegratedDocuments(listeD);
      
      // NonIntegratedVirtualDocument -- document virtuel qui n'ont pas été archivés
      DocumentVirtuelType docVirtuel = new DocumentVirtuelType();
      FichierType objNumVir = new FichierType();
      objNumVir.setCheminEtNomDuFichier("repertoire/fichier6.pdf");
      objNumVir.setHashValeur(hashValeur);
      objNumVir.setHashAlgo(SHA1);
      docVirtuel.setObjetNumerique(objNumVir);
      
      ComposantDocumentVirtuelType composant = new ComposantDocumentVirtuelType();
      MetadonneeType meta5 = new MetadonneeType();
      meta5.setCode(META1);
      meta5.setValeur(VALEUR);
      MetadonneeType meta6 = new MetadonneeType();
      meta6.setCode(META2);
      meta6.setValeur(VALEUR);
      composant.setNumeroPageDebut(1);
      composant.setNombreDePages(2);
      ListeMetadonneeType listeM3 = new ListeMetadonneeType();
      listeM3.getMetadonnee().add(meta5);
      listeM3.getMetadonnee().add(meta6);
      
      composant.setMetadonnees(listeM3);
            
      ComposantDocumentVirtuelType composant2 = new ComposantDocumentVirtuelType();
      MetadonneeType meta7 = new MetadonneeType();
      meta7.setCode(META1);
      meta7.setValeur(VALEUR);
      MetadonneeType meta8 = new MetadonneeType();
      meta8.setCode(META2);
      meta8.setValeur(VALEUR);
      composant2.setNumeroPageDebut(2);
      composant2.setNombreDePages(2);
      ListeMetadonneeType listeM4 = new ListeMetadonneeType();
      listeM4.getMetadonnee().add(meta7);
      listeM4.getMetadonnee().add(meta8);
      
      composant2.setMetadonnees(listeM4);
      
      Composants composants = new Composants();
      composants.getComposant().add(composant);
      composants.getComposant().add(composant2);
      docVirtuel.setComposants(composants);
      //-- un deuxieme document virtuel qui n'as pas pu être archivé
      DocumentVirtuelType docVirtuel2 = new DocumentVirtuelType();
      FichierType objNumVir2 = new FichierType();
      objNumVir2.setCheminEtNomDuFichier("repertoire/fichier6.pdf");
      objNumVir2.setHashValeur(hashValeur);
      objNumVir2.setHashAlgo(SHA1);
      docVirtuel2.setObjetNumerique(objNumVir2);
      
      ComposantDocumentVirtuelType composant3 = new ComposantDocumentVirtuelType();
      MetadonneeType meta9 = new MetadonneeType();
      meta9.setCode(META1);
      meta9.setValeur(VALEUR);
      MetadonneeType meta10 = new MetadonneeType();
      meta10.setCode(META2);
      meta10.setValeur(VALEUR);
      composant3.setNumeroPageDebut(1);
      composant3.setNombreDePages(2);
      ListeMetadonneeType listeM5 = new ListeMetadonneeType();
      listeM5.getMetadonnee().add(meta9);
      listeM5.getMetadonnee().add(meta10);
      
      composant3.setMetadonnees(listeM5);
            
      
      Composants composants2 = new Composants();
      composants2.getComposant().add(composant3);
      
      docVirtuel2.setComposants(composants2);
      
      //--------------------
      ListeDocumentsVirtuelsType listeDV = new ListeDocumentsVirtuelsType();
      listeDV.getDocumentVirtuel().add(docVirtuel);
      listeDV.getDocumentVirtuel().add(docVirtuel2);
      resultats2.setNonIntegratedVirtualDocuments(listeDV);
   }

   // Test avec un fichier en deuxieme argument
   @Test
   public void writeResutatsXml_file_success() throws EcdeXsdException, IOException {
      File outputFile = new File(FilenameUtils.concat(REPERTOIRE,"resultats_success_file.xml"));
      service.writeResultatsXml(resultats, outputFile);
      boolean exist = false;
      if(outputFile.exists()) {
         exist = true;
      }
      assertEquals("fichier non existant", true, exist);
      // Sha-1 du fichier resultats-test001.xml (c'est le fichier attendu), Sha-1 calculé via un logiciel externe
      String fsumAttendu = "2e8cb99e8f0b921ac26f55f5a29585f34f709bf6";
      String checksumObtenu = sha(FilenameUtils.concat(REPERTOIRE,"resultats_success_file.xml"));
      
      assertEquals(MESSAGE_INATTENDU, fsumAttendu, checksumObtenu);
   }
   // Test simple avec un outputStream
   @Test
   public void writeResultatsXml_outputstream_success() throws EcdeXsdException, JAXBException, IOException {
      File outputFile = new File(FilenameUtils.concat(REPERTOIRE,"resultats_success.xml"));
      OutputStream output = new FileOutputStream(outputFile.getPath());
      service.writeResultatsXml(resultats, output);
      
      // Sha-1 du fichier resultats-test001.xml (c'est le fichier attendu), Sha-1 calculé via un logiciel externe
      String fsumAttendu = "2e8cb99e8f0b921ac26f55f5a29585f34f709bf6";
      String checksumObtenu = sha(FilenameUtils.concat(REPERTOIRE,"resultats_success.xml"));
      
      assertEquals(MESSAGE_INATTENDU, fsumAttendu, checksumObtenu);
   }
   private static String sha(String path) throws IOException{
      InputStream data = new FileInputStream(path);
      return DigestUtils.shaHex(data);
   }
   //--------------------------------------------
   // Test failure avec resultats sans batchMode
   @Test
   public void writeResultatsXml_outputstream_failure() throws IOException {
      try{  
         File outputFile = new File(FilenameUtils.concat(REPERTOIRE,"resultats_success.xml"));
         OutputStream output = new FileOutputStream(outputFile.getPath());
         service.writeResultatsXml(resultats2, output);
         fail("Une exception était attendue! " + EcdeXsdException.class);
      }catch (EcdeXsdException e) {
         assertEquals(MESSAGE_INATTENDU,"Une erreur de structure a été détectée sur le 'resultats.xml'.",e.getMessage());
      }
   }
   // Test failure avec resultats sans batchMode
   @Test
   public void writeResultatsXml_file_failure() throws IOException {
      try{  
         File outputFile = new File(FilenameUtils.concat(REPERTOIRE,"resultats_success_file.xml"));
         service.writeResultatsXml(resultats2, outputFile);
         fail("Une exception était attendue! " + EcdeXsdException.class);
      }catch (EcdeXsdException e) {
         assertEquals(MESSAGE_INATTENDU,"Une erreur de structure a été détectée sur le 'resultats.xml'.",e.getMessage());
      }
   }
}