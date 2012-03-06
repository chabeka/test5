package fr.urssaf.image.sae.webservices.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.webservices.configuration.SecurityConfiguration;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.ConsultationMTOMResponseType;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.ListeMetadonneeType;
import fr.urssaf.image.sae.webservices.modele.SaeServiceStub.MetadonneeType;
import fr.urssaf.image.sae.webservices.util.AuthenticateUtils;

/**
 * Tests de l'opération "consultationMTOM" pour lesquels on attend une réponse
 * correcte (pas de SoapFault)<br>
 * <br>
 * Il faut penser à modifier la variable privée UUID_EXISTANT, après avoir
 * archivé un document grâce au TU ConsultationUtilsTest.prepareData()
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-sae-webservices.xml" })
public class ConsultationMTOMTest {

   /**
    * TODO : remplacer ici l'UUID par un UUID existant
    * 
    * Pour archiver un document, utiliser le TU
    * ConsultationUtilsTest.prepareData()
    */
   private static final String UUID_EXISTANT = "448b8c83-c2e1-4c2b-8574-8c0255086208";

   private static final String[] DEFAULT_META = new String[] { "Titre",
         "DateCreation", "DateReception", "CodeOrganismeProprietaire",
         "CodeOrganismeGestionnaire", "CodeRND", "Hash", "NomFichier",
         "FormatFichier", "TailleFichier", "ContratDeService", "DateArchivage" };

   private static final String[] WANTED_META = new String[] { "TypeHash",
         "NbPages" };

   /**
    * map contenant les metadatas
    */
   private Map<String, Object> expectedMetadatas;

   @Autowired
   private ConsultationMTOMService consultationMTOMService;

   /**
    * Methode d'initialisation des meta datas.
    */
   @Before
   public final void init() {
      expectedMetadatas = ConsultationUtilsTest.getExpectedMetadatas();
   }

   /**
    * Test success avec uuid non null et liste de metadata null
    * 
    * @throws RemoteException
    *            remoteException
    */
   @Test
   @Ignore
   public final void consultationMTOM_success_ListMetaNull()
         throws RemoteException {

      AuthenticateUtils.authenticate("ROLE_TOUS");

      ConsultationMTOMResponseType responseType = consultationMTOMService
            .consultationMTOM(UUID_EXISTANT);
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

   /**
    * Test success avec uuid not null et list null
    * 
    * @throws RemoteException
    *            remoteException
    */
   @Test
   @Ignore
   public final void consultation_success_ListMetaVide() throws RemoteException {

      AuthenticateUtils.authenticate("ROLE_TOUS");

      ConsultationMTOMResponseType responseType = consultationMTOMService
            .consultationMTOM(UUID_EXISTANT, new ArrayList<String>());
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

   /**
    * Test success avec Uuid et list de metadata not null
    * 
    * @throws RemoteException
    *            remoteException
    */
   @Test
   @Ignore
   public final void testSuccessUidNotNullListWithMetaData()
         throws RemoteException {

      AuthenticateUtils.authenticate("ROLE_TOUS");

      List<String> listMdString = Arrays.asList(WANTED_META);

      ConsultationMTOMResponseType responseType = consultationMTOMService
            .consultationMTOM(UUID_EXISTANT, listMdString);
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

}
