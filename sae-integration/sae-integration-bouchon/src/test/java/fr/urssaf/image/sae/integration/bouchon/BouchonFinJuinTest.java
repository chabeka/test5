package fr.urssaf.image.sae.integration.bouchon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.databinding.types.URI.MalformedURIException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ArchivageMasse;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ArchivageMasseRequestType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ArchivageMasseResponse;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ArchivageUnitaire;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ArchivageUnitaireRequestType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ArchivageUnitaireResponse;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ArchivageUnitaireResponseType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.Consultation;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ConsultationRequestType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ConsultationResponse;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ConsultationResponseType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.EcdeUrlType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ListeMetadonneeCodeType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ListeMetadonneeType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ListeResultatRechercheType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ObjetNumeriqueType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.Recherche;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.RechercheRequestType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.RechercheResponse;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.RechercheResponseType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.RequeteRechercheType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.ResultatRechercheType;
import fr.urssaf.image.sae.integration.bouchon.modele.SaeServiceStub.UuidType;
import fr.urssaf.image.sae.integration.bouchon.utils.SoapFaultUtils;
import fr.urssaf.image.sae.integration.bouchon.utils.TestUtils;
import fr.urssaf.image.sae.integration.bouchon.utils.WsTypeUtils;


/**
 * Tests du bouchon des 4 opérations livré fin Juin 2011<br>
 * <br>
 * Les tests sont :<br>
 * <br>
 * <ul>
 *    <li>Pour chacune des 4 opérations, on vérifie que la sécurité est activée</li>
 *    <li>Pour chacune des 4 opérations, on vérifie que les données bouchon renvoyées sont correctes</li>
 * </ul>
 */
public class BouchonFinJuinTest {

   private static Configuration config;
   
   // private static final Logger LOG = Logger.getLogger(BouchonFinJuinTest.class);
   
   private static final String FAIL_PAS_SOAPFAULT = 
      "Une SoapFault wsse:SecurityTokenUnavailable aurait dû être levée";
   
   
   @BeforeClass
   public static void beforeClass() throws ConfigurationException, AxisFault {
      
      config = new PropertiesConfiguration(
            "sae-webservices-test.properties");
    
   }
   
   
   private SaeServiceStub getServiceStubSansVi() throws AxisFault {
      
      return TestUtils.getServiceStub(
            config,
            "vi/vi_SoapFault_wsse_SecurityTokenUnavailable.xml");
      
   }

   
   private SaeServiceStub getServiceStubAvecViOk() throws AxisFault {
      
      return TestUtils.getServiceStub(
            config,
            "vi/vi_ok.xml");
      
   }
   
   
   private void checkSoapFaultSecuriteObligatoire(AxisFault fault) {
      SoapFaultUtils.checkSoapFault(
            fault,
            "La référence au jeton de sécurité est introuvable",
            new QName(SoapFaultUtils.NAMESPACE_WSSE,"SecurityTokenUnavailable","wsse"));
   }
   
   
   private ArchivageUnitaire getParamsEntree_archivageUnitaire()
   throws MalformedURIException {
      
      ArchivageUnitaire archivageUnitaire = new ArchivageUnitaire();
      
      ArchivageUnitaireRequestType archivageUnitaireReqType = 
         new ArchivageUnitaireRequestType();
      
      archivageUnitaire.setArchivageUnitaire(archivageUnitaireReqType);
      
      
      // L'objet numérique
      ObjetNumeriqueType objetNumerique = WsTypeUtils.buildObjetNumeriqueAvecUrlEcde(
            "ecde://ecdeX/numeroCS/20110527/idTraitement/documents/nom_du_fichier");
      archivageUnitaireReqType.setObjetNumerique(objetNumerique);
      
      
      // Les métadonnées
      Map<String,String> metadonnees = new HashMap<String, String>();
      metadonnees.put("CodeRND", "1.2.3.4.5");
      metadonnees.put("DenominationCompte","TEST TEST");
      ListeMetadonneeType listeMetadonneeType = WsTypeUtils.buildListeMetadonnes(
            metadonnees);
      archivageUnitaireReqType.setMetadonnees(listeMetadonneeType);
      
      
      // fin
      return archivageUnitaire;
      
   }
   
   
   /**
    * Cas de test : 011_archivageUnitaire_authentification
    */
   @Test
   public void cas_011_archivageUnitaire_authentification() throws RemoteException, MalformedURIException {
      
      SaeServiceStub service = getServiceStubSansVi();
      
      ArchivageUnitaire paramsService = getParamsEntree_archivageUnitaire();
      
      try {
         
         service.archivageUnitaire(paramsService);
         
         fail(FAIL_PAS_SOAPFAULT);
         
      } catch (AxisFault fault) {
         checkSoapFaultSecuriteObligatoire(fault);
      }
      
   }
   
   
   /**
    * Cas de test : 012_archivageUnitaire_bouchon
    */
   @Test
   public void cas_012_archivageUnitaire_bouchon()
   throws MalformedURIException, RemoteException {
      
      // --------------------------------------------------------------------------------
      // Appel du service web
      // --------------------------------------------------------------------------------
      
      SaeServiceStub service = getServiceStubAvecViOk();
      ArchivageUnitaire paramsService = getParamsEntree_archivageUnitaire();
      ArchivageUnitaireResponse response = service.archivageUnitaire(paramsService);
      
      // --------------------------------------------------------------------------------
      // Vérification de la valeur de retour
      // --------------------------------------------------------------------------------
      
      // Valeur de retour non null
      assertNotNull(
            "La valeur de retour ne doit pas être null",
            response);
      
      // Récupération de la "suite" de la valeur de retour
      ArchivageUnitaireResponseType responseType = 
         response.getArchivageUnitaireResponse();
      
      // Vérification du UUID
      // Valeur attendue : 110E8400-E29B-11D4-A716-446655440000
      
      String uuidObtenu = WsTypeUtils.extractUuid(responseType.getIdArchive());
      
      String uuidAttendu = "110E8400-E29B-11D4-A716-446655440000";
      
      assertEquals(
            "Vérification de l'UUID",
            uuidAttendu,
            uuidObtenu);
      
   }
   
   
   
   private ArchivageMasse getParamsEntree_archivageMasse()
   throws MalformedURIException {
      
      ArchivageMasse archivageMasse = new ArchivageMasse();
      
      ArchivageMasseRequestType archivageMasseReqType = 
         new ArchivageMasseRequestType();
      
      archivageMasse.setArchivageMasse(archivageMasseReqType);
      
      // URL de sommaire.xml
      EcdeUrlType urlEcdeSommaire = WsTypeUtils.buildEcdeUrl(
            "ecde://ecdeX/numeroCS/20110527/idTraitement/documents/sommaire.xml");
      archivageMasseReqType.setUrlSommaire(urlEcdeSommaire);
      
      // fin
      return archivageMasse;
      
   }
   
   
   /**
    * Cas de test : 013_archivageMasse_authentification
    */
   @Test
   public void cas_013_archivageMasse_authentification()
   throws RemoteException, MalformedURIException {
      
      SaeServiceStub service = getServiceStubSansVi();
      
      ArchivageMasse paramsService = getParamsEntree_archivageMasse();
      
      try {
         
         service.archivageMasse(paramsService);
         
         fail(FAIL_PAS_SOAPFAULT);
         
      } catch (AxisFault fault) {
         checkSoapFaultSecuriteObligatoire(fault);
      }
      
   }
   
   
   /**
    * Cas de test : 014_archivageMasse_bouchon
    */
   @Test
   public void cas_014_archivageMasse_bouchon()
   throws MalformedURIException, RemoteException {
      
      // --------------------------------------------------------------------------------
      // Appel du service web
      // --------------------------------------------------------------------------------
      
      SaeServiceStub service = getServiceStubAvecViOk();
      ArchivageMasse paramsService = getParamsEntree_archivageMasse();
      ArchivageMasseResponse response = service.archivageMasse(paramsService);
      
      // --------------------------------------------------------------------------------
      // Vérification de la valeur de retour
      // --------------------------------------------------------------------------------
      
      // On vérifie juste que la valeur n'est pas nulle
      assertNotNull(
            "Opération archivageMasse - La valeur de retour ne doit pas être null",
            response);
      
      // ArchivageMasseResponseType responseType = response.getArchivageMasseResponse();
      
   }
   
   
   
   private Recherche getParamsEntree_recherche() {
      
      Recherche recherche = new Recherche();
      
      RechercheRequestType rechercheReqType = 
         new RechercheRequestType();
      
      recherche.setRecherche(rechercheReqType);
      
      // La requête Lucene
      // (CodeRND="1.2.3.4.5") AND (DenominationCompte="TEST TEST")
      RequeteRechercheType requeteLucene = WsTypeUtils.buildRequeteLucene(
            "(CodeRND=\"1.2.3.4.5\") AND (NumeroCotisant=\"12345678\")");
      rechercheReqType.setRequete(requeteLucene);
      
      // Les codes des métadonnées souhaitées
      List<String> codesMetadonneesList = new ArrayList<String>();
      codesMetadonneesList.add("CodeRND");
      codesMetadonneesList.add("NumeroCotisant");
      codesMetadonneesList.add("Siret");
      codesMetadonneesList.add("DenominationCompte");
      codesMetadonneesList.add("CodeOrganisme");
      ListeMetadonneeCodeType codesMetadonnees = 
         WsTypeUtils.buildListeCodesMetadonnes(codesMetadonneesList);
      rechercheReqType.setMetadonnees(codesMetadonnees);

      // fin
      return recherche;
      
   }
   
   
   /**
    * Cas de test : 015_recherche_authentification
    */
   @Test
   public void cas_015_recherche_authentification() throws RemoteException {
      
      SaeServiceStub service = getServiceStubSansVi();
      
      Recherche paramsService = getParamsEntree_recherche();
      
      try {
         
         service.recherche(paramsService);
         
         fail(FAIL_PAS_SOAPFAULT);
         
      } catch (AxisFault fault) {
         checkSoapFaultSecuriteObligatoire(fault);
      }
      
   }
   
   
   /**
    * Cas de test : 016_recherche_bouchon
    */
   @Test
   public void cas_016_recherche_bouchon() throws RemoteException {
      
      // --------------------------------------------------------------------------------
      // Appel du service web
      // --------------------------------------------------------------------------------
      
      SaeServiceStub service = getServiceStubAvecViOk();
      Recherche paramsService = getParamsEntree_recherche();
      RechercheResponse response = service.recherche(paramsService);
      
      // --------------------------------------------------------------------------------
      // Vérification de la valeur de retour
      // --------------------------------------------------------------------------------
      
      // Valeur de retour non null
      assertNotNull(
            "La valeur de retour ne doit pas être null",
            response);
      
      // Récupération de la "suite" de la valeur de retour
      RechercheResponseType responseType = 
         response.getRechercheResponse();
      
      
      // Résultats attentus :
//      UUID : 110E8400-E29B-11D4-A716-446655440000 
//      CodeRND : 3.1.3.1.1
//      NumeroCotisant : 704815 
//      Siret : 49980055500017 
//      DenominationCompte : SPOHN ERWAN MARIE MAX 
//      CodeOrganisme : UR030 
//
//      UUID : 510E8200-E29B-18C4-A716-446677440120 
//      CodeRND : 1.A.X.X.X 
//      NumeroCotisant : 723804 
//      Siret : 07413151710009 
//      DenominationCompte CHEVENIER ANDRE 
//      CodeOrganisme UR030 
//
//      UUID : 48758200-A29B-18C4-B616-455677840120 
//      CodeRND 1.2.3.3.1 
//      NumeroCotisant 719900 
//      Siret 07412723410007 
//      DenominationCompte COUTURIER GINETTE 
//      CodeOrganisme UR030
      
      ResultatRechercheType resultatRecherche;
      ListeMetadonneeType metadonnees;
      
      // 3 résultats de recherche
      ListeResultatRechercheType resultatsRecherche = responseType.getResultats();
      WsTypeUtils.displayResultatRecherche(resultatsRecherche);
      TestUtils.checkResultatsRechercheCount(resultatsRecherche,3);
      
      // Récupère le résultat 110E8400-E29B-11D4-A716-446655440000
      resultatRecherche = 
         TestUtils.checkResultatRecherchePresenceUnique(
               resultatsRecherche, 
               "110E8400-E29B-11D4-A716-446655440000");
      // Vérifie les métadonnées
      metadonnees = resultatRecherche.getMetadonnees();
      // WsTypeUtils.displayMetadonnees(metadonnees);
      TestUtils.checkMetadonneesCount(metadonnees, 5);
      TestUtils.checkMetadonneesPresenceUnique(
            metadonnees, 
            "CodeRND", 
            "3.1.3.1.1");
      TestUtils.checkMetadonneesPresenceUnique(
            metadonnees, 
            "NumeroCotisant", 
            "704815");
      TestUtils.checkMetadonneesPresenceUnique(
            metadonnees, 
            "Siret", 
            "49980055500017");
      TestUtils.checkMetadonneesPresenceUnique(
            metadonnees, 
            "DenominationCompte", 
            "SPOHN ERWAN MARIE MAX");
      TestUtils.checkMetadonneesPresenceUnique(
            metadonnees, 
            "CodeOrganisme", 
            "UR030");
      
      // Récupère le résultat 510E8200-E29B-18C4-A716-446677440120
      resultatRecherche = 
         TestUtils.checkResultatRecherchePresenceUnique(
               resultatsRecherche, 
               "510E8200-E29B-18C4-A716-446677440120");
      // Vérifie les métadonnées
      metadonnees = resultatRecherche.getMetadonnees();
      // WsTypeUtils.displayMetadonnees(metadonnees);
      TestUtils.checkMetadonneesCount(metadonnees, 5);
      TestUtils.checkMetadonneesPresenceUnique(
            metadonnees, 
            "CodeRND", 
            "1.A.X.X.X");
      TestUtils.checkMetadonneesPresenceUnique(
            metadonnees, 
            "NumeroCotisant", 
            "723804");
      TestUtils.checkMetadonneesPresenceUnique(
            metadonnees, 
            "Siret", 
            "07413151710009");
      TestUtils.checkMetadonneesPresenceUnique(
            metadonnees, 
            "DenominationCompte", 
            "CHEVENIER ANDRE");
      TestUtils.checkMetadonneesPresenceUnique(
            metadonnees, 
            "CodeOrganisme", 
            "UR030");
      
      // Récupère le résultat 48758200-A29B-18C4-B616-455677840120
      resultatRecherche = 
         TestUtils.checkResultatRecherchePresenceUnique(
               resultatsRecherche, 
               "48758200-A29B-18C4-B616-455677840120");
      // Vérifie les métadonnées
      metadonnees = resultatRecherche.getMetadonnees();
      // WsTypeUtils.displayMetadonnees(metadonnees);
      TestUtils.checkMetadonneesCount(metadonnees, 5);
      TestUtils.checkMetadonneesPresenceUnique(
            metadonnees, 
            "CodeRND", 
            "1.2.3.3.1");
      TestUtils.checkMetadonneesPresenceUnique(
            metadonnees, 
            "NumeroCotisant", 
            "719900");
      TestUtils.checkMetadonneesPresenceUnique(
            metadonnees, 
            "Siret", 
            "07412723410007");
      TestUtils.checkMetadonneesPresenceUnique(
            metadonnees, 
            "DenominationCompte", 
            "COUTURIER GINETTE");
      TestUtils.checkMetadonneesPresenceUnique(
            metadonnees, 
            "CodeOrganisme", 
            "UR030");
      
   }
   
   
   private Consultation getParamsEntree_consultation() {
      
      Consultation consultation = new Consultation();
      
      ConsultationRequestType consultationReqType = 
         new ConsultationRequestType();
      
      consultation.setConsultation(consultationReqType);
      
      
      // UUID
      UuidType uuid = WsTypeUtils.buildUuid("F81D4FAE-7DEC-11D0-A765-00A0C91E6BF6");
      consultationReqType.setIdArchive(uuid);
      
      // URL de consultation directe ?
      consultationReqType.setUrlConsultationDirecte(false);
      
      // fin
      return consultation;
      
   }
   
   
   /**
    * Cas de test : 017_consultation_authentification
    */
   @Test
   public void cas_017_consultation_authentification() throws RemoteException {
      
      SaeServiceStub service = getServiceStubSansVi();
      
      Consultation paramsService = getParamsEntree_consultation();
      
      try {
         
         service.consultation(paramsService);
         
         fail(FAIL_PAS_SOAPFAULT);
         
      } catch (AxisFault fault) {
         checkSoapFaultSecuriteObligatoire(fault);
      }
      
   }
   
   
   /**
    * Cas de test : 018_consultation_bouchon
    */
   @Test
   public void cas_018_consultation_bouchon() throws IOException {
      
      // --------------------------------------------------------------------------------
      // Appel du service web
      // --------------------------------------------------------------------------------
      
      SaeServiceStub service = getServiceStubAvecViOk();
      Consultation paramsService = getParamsEntree_consultation();
      ConsultationResponse response = service.consultation(paramsService);
      
      // --------------------------------------------------------------------------------
      // Vérification de la valeur de retour
      // --------------------------------------------------------------------------------
      
      // Valeur de retour non null
      assertNotNull(
            "La valeur de retour ne doit pas être null",
            response);
      
      // Récupération de la "suite" de la valeur de retour
      ConsultationResponseType responseType = 
         response.getConsultationResponse();
    
      // Vérification du sha-1 de l'objet numérique récupéré
      DataHandler dataHandler = WsTypeUtils.getObjetNumeriqueAsContenu(responseType);
      String sha1obtenu = DigestUtils.shaHex(dataHandler.getInputStream());
      String sha1attendu = 
         "4bf2ddbd82d5fd38e821e6aae434ac989972a043";
      assertEquals(
            "Vérification de l'objet numérique renvoyé",
            sha1attendu,
            sha1obtenu);
      
      // Pour une vérification visuelle : écrit le fichier sur le disque dur
      // TestUtils.ecritDataHandlerSurDisque(dataHandler,"c:/divers/integr.pdf");
      
      // Vérification des métadonnées
//      CodeRND 1.2.3.3.1 
//      NumeroCotisant 719900 
//      Siret 07412723410007 
//      DenominationCompte COUTURIER GINETTE 
//      CodeOrganisme UR030
      ListeMetadonneeType metadonnees = responseType.getMetadonnees();
      // WsTypeUtils.displayMetadonnees(metadonnees);
      TestUtils.checkMetadonneesCount(metadonnees, 5);
      TestUtils.checkMetadonneesPresenceUnique(
            metadonnees, "CodeRND", "1.2.3.3.1");
      TestUtils.checkMetadonneesPresenceUnique(
            metadonnees, "NumeroCotisant", "719900");
      TestUtils.checkMetadonneesPresenceUnique(
            metadonnees, "Siret", "07412723410007");
      TestUtils.checkMetadonneesPresenceUnique(
            metadonnees, "DenominationCompte", "COUTURIER GINETTE");
      TestUtils.checkMetadonneesPresenceUnique(
            metadonnees, "CodeOrganisme", "UR030");
      
   }
   
   
}
