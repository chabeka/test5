package sae.client.demo.webservice;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.junit.Test;

import sae.client.demo.webservice.factory.Axis2ObjectFactory;
import sae.client.demo.webservice.factory.StubFactory;
import sae.client.demo.webservice.modele.SaeServiceStub;
import sae.client.demo.webservice.modele.SaeServiceStub.ArchivageMasse;

public class ArchivageMasseTest {

   
   /**
    * Exemple de consommation de l'opération archivageMasse du service web SaeService<br>
    * <br>
    * Cas sans erreur
    * 
    * @throws RemoteException 
    */
   @Test
   public void archivageMasse_success() throws RemoteException {
      
      // Pré-requis pour la capture de masse :
      //  - Un répertoire de traitement a été créé dans l'ECDE dans la bonne arborescence
      //    par l'application cliente.
      //    Dans cet exemple :
      //      [RacineEcdeDuMontageNfsCoteClient]/le_contrat_service/20120120/Traitement002_ArchivageMasse
      //  - Le fichier sommaire.xml a été déposé dans ce répertoire.
      //    Exemple :
      //      [RacineEcdeDuMontageNfsCoteClient]/le_contrat_service/20120120/Traitement002_ArchivageMasse/sommaire.xml
      //  - Les fichiers à archiver, référencés dans sommaire.xml, ont été déposés dans
      //    le sous-répertoire "documents" du répertoire de traitement.
      //    Dans cet exemple :
      //     [RacineEcdeDuMontageNfsCoteClient]/le_contrat_service/20120120/Traitement002_ArchivageMasse/documents/attestation1.pdf
      //     [RacineEcdeDuMontageNfsCoteClient]/le_contrat_service/20120120/Traitement002_ArchivageMasse/documents/attestation2.pdf
      // 
      // L'URL ECDE correspondant au sommaire.xml est :
      //  => ecde://cer69-ecdeint.cer69.recouv/le_contrat_service/20120120/Traitement002_ArchivageMasse/sommaire.xml 
      
      // URL ECDE du fichier sommaire.xml
      String urlEcdeSommaire = "ecde://cer69-ecdeint.cer69.recouv/le_contrat_service/20120120/Traitement002_ArchivageMasse/sommaire.xml";
      
      // Construction du Stub
      SaeServiceStub saeService = StubFactory.createStubAvecAuthentification();
      
      // Construction du paramètre d'entrée de l'opération archivageMasse, 
      //  avec les objets modèle générés par Axis2.
      ArchivageMasse paramsEntree = Axis2ObjectFactory.contruitParamsEntreeArchivageMasse(
            urlEcdeSommaire);
      
      // Appel de l'opération archivageMasse
      // => aucun retour attendu, le traitement de masse étant asynchrone, le service web
      //    renvoie la main immédiatement
      saeService.archivageMasse(paramsEntree);

      // sysout
      System.out.println("La demande de prise en compte de l'archivage de masse a été envoyée");
      System.out.println("URL ECDE du sommaire.xml : " + urlEcdeSommaire);
      
   }
   
   
   /**
    * Exemple de consommation de l'opération archivageMasse du service web SaeService<br>
    * <br>
    * Cas avec erreur : L'URL ECDE du sommaire.xml pointe vers un fichier inexistant<br>
    * <br>
    * Le SAE renvoie la SoapFault suivante :<br>
    * <ul>
    *    <li>Code : sae:CaptureUrlEcdeFichierIntrouvable</li>
    *    <li>Message : Le fichier pointé par l'URL ECDE est introuvable (ecde://cer69-ecdeint.cer69.recouv/le_contrat_service/20120120/TraitementInexistant/sommaire.xml)</li>
    * </ul>
    */
   @Test
   public void archivageMasse_failure() {
      
      // URL ECDE du fichier sommaire.xml
      // Elle pointe sur un fichier inexistant
      String urlEcdeSommaire = "ecde://cer69-ecdeint.cer69.recouv/le_contrat_service/20120120/TraitementInexistant/sommaire.xml";
      
      // Construction du Stub
      SaeServiceStub saeService = StubFactory.createStubAvecAuthentification();
      
      // Construction du paramètre d'entrée de l'opération archivageMasse, 
      //  avec les objets modèle générés par Axis2.
      ArchivageMasse paramsEntree = Axis2ObjectFactory.contruitParamsEntreeArchivageMasse(
            urlEcdeSommaire);
      
      // Appel de l'opération archivageMasse
      try {
      
         // Appel de l'opération archivageMasse
         saeService.archivageMasse(paramsEntree);
         
         // Si on a passé l'appel, le test est en échec
         fail("La SoapFault attendue n'a pas été renvoyée");
         
      } catch (AxisFault fault) {
      
         // sysout
         System.out.println("Une SoapFault a été obtenue");
         System.out.println("Code namespace : " + fault.getFaultCode().getNamespaceURI());
         System.out.println("Code préfixe : " + fault.getFaultCode().getPrefix());
         System.out.println("Code partie locale : " + fault.getFaultCode().getLocalPart());
         System.out.println("Message : " + fault.getReason());
         
         // Test le code de la SoapFault
         // Le namespace
         assertEquals(
               "Le namespace du code de la SoapFault est incorrect",
               "urn:sae:faultcodes",
               fault.getFaultCode().getNamespaceURI());
         // Le préfixe
         assertEquals(
               "Le préfixe du code de la SoapFault est incorrect",
               "sae",
               fault.getFaultCode().getPrefix());
         // La partie locale
         assertEquals(
               "La partie locale du code de la SoapFault est incorrecte",
               "CaptureUrlEcdeFichierIntrouvable",
               fault.getFaultCode().getLocalPart());
         
         // Test le message de la SoapFault
         assertEquals(
               "Le message de la SoapFault est incorrecte",
               "Le fichier pointé par l'URL ECDE est introuvable (ecde://cer69-ecdeint.cer69.recouv/le_contrat_service/20120120/TraitementInexistant/sommaire.xml)",
               fault.getReason());
       
      } catch (RemoteException exception) {
         
         fail("Une RemoteException a été levée, alors qu'on attendait une AxisFault");
         
      }
      
      
   }
   
}
