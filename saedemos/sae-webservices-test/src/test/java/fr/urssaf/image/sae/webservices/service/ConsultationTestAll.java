/**
 * 
 */
package fr.urssaf.image.sae.webservices.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis2.AxisFault;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.webservices.configuration.EcdeManager;
import fr.urssaf.image.sae.webservices.configuration.SecurityConfiguration;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.ArchivageUnitaireResponseType;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.ConsultationResponseType;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.ListeMetadonneeType;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.MetadonneeType;
import fr.urssaf.image.sae.webservices.service.factory.ObjectModelFactory;
import fr.urssaf.image.sae.webservices.service.model.Metadata;
import fr.urssaf.image.sae.webservices.util.AuthenticateUtils;
import fr.urssaf.image.sae.webservices.util.SoapTestUtils;

/**
 * Classe permettant de tester les opérations de succes de consultation de WS
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-sae-webservices.xml" })
public class ConsultationTestAll {

   /**
    * 
    */
   private static final String CONTRAT_SERVICE = "ATT_PROD_001";
   /**
    * 
    */
   private static final String FILE_NAME = "attestation.pdf";
   private static final String PDF_FILE_PATH = "src/test/resources/storage/attestation.pdf";
   private static final String DATE_DEB_CONSERV = "2011-09-02";
   private static final String DATE_RECEPT = "1999-11-25";
   private static final String TYPE_HASH = "SHA-1";
   private static final String TITRE = "Attestation de vigilance";
   private static final String DATE_CREATION = "2012-01-01";
   private static final String FORMAT_FICHIER = "fmt/1354";
   private static final String NB_PAGE = "2";
   private static final String VERSION_RND = "11.1";
   private static final String APPLICATION_PRODUCTRICE = "ADELAIDE";
   private static final String CODE_ORGA_PROPRIO = "CER69";
   private static final String CODE_ORGA_GESTION = "UR750";
   private static final String CODE_RND = "2.3.1.1.12";

   private static final String[] DEFAULT_META = new String[] { "Titre",
         "DateCreation", "DateReception", "CodeOrganismeProprietaire",
         "CodeOrganismeGestionnaire", "CodeRND", "Hash", "NomFichier",
         "FormatFichier", "TailleFichier", "ContratDeService", "DateArchivage" };

   private static final String[] WANTED_META = new String[] { "TypeHash",
         "NbPages" };
   private static final String[] NOT_EXISTS_META = new String[] { "TypeHash",
         "NbPages", "metadatainexistante" };

   private static final String[] NO_CONSULT_META = new String[] { "TypeHash",
         "NbPages", "StartPage" };

   /**
    * map contenant les metadatas
    */
   private Map<String, Object> expectedMetadatas;

   @Autowired
   private ArchivageUnitaireService archivage;

   @Autowired
   private ConsultationService consultationService;

   private static final Logger LOG = LoggerFactory
         .getLogger(ConsultationTestAll.class);

   @Before
   public void init() {
      expectedMetadatas = new HashMap<String, Object>();

      expectedMetadatas.put("ApplicationProductrice", APPLICATION_PRODUCTRICE);
      expectedMetadatas.put("CodeOrganismeProprietaire", CODE_ORGA_PROPRIO);
      expectedMetadatas.put("CodeOrganismeGestionnaire", CODE_ORGA_GESTION);
      expectedMetadatas.put("CodeRND", CODE_RND);
      expectedMetadatas.put("VersionRND", VERSION_RND);
      expectedMetadatas.put("NbPages", NB_PAGE);
      expectedMetadatas.put("FormatFichier", FORMAT_FICHIER);
      expectedMetadatas.put("DateCreation", DATE_CREATION);
      expectedMetadatas.put("Titre", TITRE);
      expectedMetadatas.put("TypeHash", TYPE_HASH);
      expectedMetadatas.put("DateReception", DATE_RECEPT);
      expectedMetadatas.put("DateDebutConservation", DATE_DEB_CONSERV);
      expectedMetadatas.put("NomFichier", FILE_NAME);
      expectedMetadatas.put("ContratDeService", CONTRAT_SERVICE);
   }

   /**
    * Préparation du jeu de données
    * 
    * @throws IOException
    *            exception levée si problème avec le document à envoyer
    */
   @Test
   @Ignore
   public void prepareDatas() throws IOException {
      EcdeManager.cleanEcde();

      AuthenticateUtils.authenticate("ROLE_TOUS");

      // enregistrement du fichier dans l'ECDE
      File srcFile = new File(PDF_FILE_PATH);
      EcdeManager.copyFile(srcFile,
            "DCL001/19991231/3/documents/attestation.pdf");

      // affichage du Hash du fichier
      String hash = DigestUtils.shaHex(new FileInputStream(srcFile));

      LOG
            .debug("le hash du document 'attestation.pdf' à archiver est: "
                  + hash);

      // appel du service archivage unitaire

      List<Metadata> metadatas = new ArrayList<Metadata>();

      metadatas.add(ObjectModelFactory.createMetadata("ApplicationProductrice",
            APPLICATION_PRODUCTRICE));
      metadatas.add(ObjectModelFactory.createMetadata(
            "CodeOrganismeProprietaire", CODE_ORGA_PROPRIO));
      metadatas.add(ObjectModelFactory.createMetadata(
            "CodeOrganismeGestionnaire", CODE_ORGA_GESTION));
      metadatas.add(ObjectModelFactory.createMetadata("CodeRND", CODE_RND));
      metadatas.add(ObjectModelFactory
            .createMetadata("VersionRND", VERSION_RND));
      metadatas.add(ObjectModelFactory.createMetadata("NbPages", NB_PAGE));
      metadatas.add(ObjectModelFactory.createMetadata("FormatFichier",
            FORMAT_FICHIER));
      metadatas.add(ObjectModelFactory.createMetadata("DateCreation",
            DATE_CREATION));
      metadatas.add(ObjectModelFactory.createMetadata("Titre", TITRE));
      metadatas.add(ObjectModelFactory.createMetadata("TypeHash", TYPE_HASH));
      metadatas.add(ObjectModelFactory.createMetadata("Hash", hash));
      metadatas.add(ObjectModelFactory.createMetadata("DateReception",
            DATE_RECEPT));
      metadatas.add(ObjectModelFactory.createMetadata("DateDebutConservation",
            DATE_DEB_CONSERV));

      URI urlEcde = URI
            .create("ecde://ecde.local.recouv/DCL001/19991231/3/documents/attestation.pdf");

      ArchivageUnitaireResponseType archivageResponse = archivage
            .archivageUnitaire(urlEcde, metadatas);

      // récupération de l'uuid d'archivage
      String idArchive = archivageResponse.getIdArchive().getUuidType();

      String date = DateFormatUtils.ISO_DATE_FORMAT.format(new Date());

      LOG.info("===================");
      LOG.info("UUID du document archivé: " + idArchive.toLowerCase());
      LOG.info("===================");
      LOG.info("hash du document : " + hash);
      LOG.info("===================");
      LOG.info("date archivage : " + date);
      LOG.info("===================");

      SecurityConfiguration.cleanSecurityContext();
   }

   private String initBeforeConsult() {
      String uuid = "50403213-d18a-464f-ab12-bfdd8a929a89";
      String hash = "4bf2ddbd82d5fd38e821e6aae434ac989972a043";
      String dateArchivage = "2012-02-02";

      File srcFile = new File(PDF_FILE_PATH);
      expectedMetadatas.put("Hash", hash);
      expectedMetadatas.put("TailleFichier", Long.toString(FileUtils
            .sizeOf(srcFile)));
      expectedMetadatas.put("DateArchivage", dateArchivage);

      return uuid;
   }

   @Test
   @Ignore
   public void testSuccessUidNotNullListNull() throws RemoteException {

      AuthenticateUtils.authenticate("ROLE_TOUS");

      String uuid = initBeforeConsult();

      ConsultationResponseType responseType = consultationService
            .consultation(uuid);
      ListeMetadonneeType listeMD = responseType.getMetadonnees();

      assertNotNull("L'objet contenant les metadonnees ne doit pas etre null",
            listeMD);

      MetadonneeType[] tabMD = listeMD.getMetadonnee();

      assertNotNull("la liste des metadonnées ne doit pas être null", tabMD);

      Collection<String> colResMD = new ArrayList<String>();
      for (MetadonneeType metaData : tabMD) {
         colResMD.add(metaData.getCode().getMetadonneeCodeType());
      }

      Collection<String> colDefaultMD = Arrays.asList(DEFAULT_META);

      assertTrue(
            "Toutes les metadatas contenues dans le résultat doivent appartenir au résultat par défaut",
            colResMD.containsAll(colDefaultMD));

      assertTrue(
            "Il ne doit y avoir que des metadatas rendues par défaut dans le résultat",
            colDefaultMD.containsAll(colResMD));

      String code;
      for (MetadonneeType metaData : tabMD) {
         code = metaData.getCode().getMetadonneeCodeType();
         assertEquals("Vérification de la valeur du champ " + code,
               expectedMetadatas.get(code), metaData.getValeur()
                     .getMetadonneeValeurType());
      }

      SecurityConfiguration.cleanSecurityContext();
   }

   @Test
   @Ignore
   public void testSuccessUidNotNullListEmpty() throws RemoteException {

      AuthenticateUtils.authenticate("ROLE_TOUS");

      String uuid = initBeforeConsult();

      ConsultationResponseType responseType = consultationService.consultation(
            uuid, new ArrayList<String>());
      ListeMetadonneeType listeMD = responseType.getMetadonnees();

      assertNotNull("L'objet contenant les metadonnees ne doit pas etre null",
            listeMD);

      MetadonneeType[] tabMD = listeMD.getMetadonnee();

      assertNotNull("la liste des metadonnées ne doit pas être null", tabMD);

      Collection<String> colResMD = new ArrayList<String>();
      for (MetadonneeType metaData : tabMD) {
         colResMD.add(metaData.getCode().getMetadonneeCodeType());
      }

      Collection<String> colDefaultMD = Arrays.asList(DEFAULT_META);

      assertTrue(
            "Toutes les metadatas contenues dans le résultat doivent appartenir au résultat par défaut",
            colResMD.containsAll(colDefaultMD));

      assertTrue(
            "Il ne doit y avoir que des metadatas rendues par défaut dans le résultat",
            colDefaultMD.containsAll(colResMD));

      String code;
      for (MetadonneeType metaData : tabMD) {
         code = metaData.getCode().getMetadonneeCodeType();
         assertEquals("Vérification de la valeur du champ " + code,
               expectedMetadatas.get(code), metaData.getValeur()
                     .getMetadonneeValeurType());
      }

      SecurityConfiguration.cleanSecurityContext();
   }

   @Test
   @Ignore
   public void testSuccessUidNotNullListWithMetaData() throws RemoteException {

      AuthenticateUtils.authenticate("ROLE_TOUS");

      String uuid = initBeforeConsult();

      List<String> listMdString = Arrays.asList(WANTED_META);

      ConsultationResponseType responseType = consultationService.consultation(
            uuid, listMdString);
      ListeMetadonneeType listeMD = responseType.getMetadonnees();

      assertNotNull("L'objet contenant les metadonnees ne doit pas etre null",
            listeMD);

      MetadonneeType[] tabMD = listeMD.getMetadonnee();

      assertNotNull("la liste des metadonnées ne doit pas être null", tabMD);

      Collection<String> colResMD = new ArrayList<String>();
      for (MetadonneeType metaData : tabMD) {
         colResMD.add(metaData.getCode().getMetadonneeCodeType());
      }

      Collection<String> colWantedMD = Arrays.asList(WANTED_META);

      assertTrue(
            "Toutes les metadatas contenues dans le résultat doivent appartenir au résultat voulu",
            colResMD.containsAll(colWantedMD));

      assertTrue(
            "Il ne doit y avoir que des metadatas désirées dans le résultat",
            colWantedMD.containsAll(colResMD));

      String code;
      for (MetadonneeType metaData : tabMD) {
         code = metaData.getCode().getMetadonneeCodeType();
         assertEquals("Vérification de la valeur du champ " + code,
               expectedMetadatas.get(code), metaData.getValeur()
                     .getMetadonneeValeurType());
      }

      SecurityConfiguration.cleanSecurityContext();
   }

   @Test
   @Ignore
   public void testFailInexistantMetadata() throws RemoteException {

      AuthenticateUtils.authenticate("ROLE_TOUS");

      String uuid = initBeforeConsult();

      List<String> listMdString = Arrays.asList(NOT_EXISTS_META);

      try {
         consultationService.consultation(uuid, listMdString);

         fail("une exception devrait être levée");

      } catch (AxisFault axisFault) {
         SoapTestUtils
               .assertAxisFault(
                     axisFault,
                     "La ou les métadonnées suivantes, demandées dans les critères de consultation, n'existent pas dans le référentiel des métadonnées : metadatainexistante",
                     "ConsultationMetadonneesInexistante",
                     SoapTestUtils.SAE_NAMESPACE, SoapTestUtils.SAE_PREFIX);
      }

      SecurityConfiguration.cleanSecurityContext();
   }

   @Test
   @Ignore
   public void testFailNoConsultMetadata() throws RemoteException {

      AuthenticateUtils.authenticate("ROLE_TOUS");

      String uuid = initBeforeConsult();

      List<String> listMdString = Arrays.asList(NO_CONSULT_META);

      try {
         consultationService.consultation(uuid, listMdString);

         fail("une exception devrait être levée");

      } catch (AxisFault axisFault) {
         SoapTestUtils
               .assertAxisFault(
                     axisFault,
                     "La ou les métadonnées suivantes, demandées dans les critères de consultation, ne sont pas consultables : StartPage",
                     "ConsultationMetadonneesNonAutorisees",
                     SoapTestUtils.SAE_NAMESPACE, SoapTestUtils.SAE_PREFIX);
      }

      SecurityConfiguration.cleanSecurityContext();
   }

}
