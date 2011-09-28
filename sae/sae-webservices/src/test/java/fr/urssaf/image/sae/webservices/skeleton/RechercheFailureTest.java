package fr.urssaf.image.sae.webservices.skeleton;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamReader;

import org.apache.axis2.AxisFault;
import org.apache.commons.lang.exception.NestableRuntimeException;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cirtil.www.saeservice.Recherche;
import fr.urssaf.image.sae.services.document.SAEDocumentService;
import fr.urssaf.image.sae.services.exception.search.MetaDataUnauthorizedToConsultEx;
import fr.urssaf.image.sae.services.exception.search.MetaDataUnauthorizedToSearchEx;
import fr.urssaf.image.sae.services.exception.search.SAESearchServiceEx;
import fr.urssaf.image.sae.services.exception.search.SyntaxLuceneEx;
import fr.urssaf.image.sae.services.exception.search.UnknownDesiredMetadataEx;
import fr.urssaf.image.sae.services.exception.search.UnknownLuceneMetadataEx;
import fr.urssaf.image.sae.webservices.exception.RechercheAxis2Fault;
import fr.urssaf.image.sae.webservices.util.XMLStreamUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-service-test.xml" })
@SuppressWarnings( { "PMD.MethodNamingConventions", "PMD.PreserveStackTrace" , "PMD.AvoidPrintStackTrace" })
public class RechercheFailureTest {
   @Autowired
   private SaeServiceSkeleton skeleton;
   @Autowired
   private SAEDocumentService documentService;
   
   private static final String VIDE = "";
   private static final String TITRE = "Titre";

   @After
   public void after() {
      EasyMock.reset(documentService);
   }

   private Recherche createSearchType(String filePath) {
      try {
         XMLStreamReader reader = XMLStreamUtils.createXMLStreamReader(filePath);
         return Recherche.Factory.parse(reader);

      } catch (Exception e) {
         throw new NestableRuntimeException(e);
      }

   }

   private static final String AXIS_FAULT = "AxisFault non attendue";

   private static void assertAxisFault(AxisFault axisFault, String expectedMsg,
         String expectedType, String expectedPrefix) {

      Assert.assertEquals(AXIS_FAULT, expectedMsg, axisFault.getMessage());
      Assert.assertEquals(AXIS_FAULT, expectedType, axisFault.getFaultCode().getLocalPart());
      Assert.assertEquals(AXIS_FAULT, expectedPrefix, axisFault.getFaultCode().getPrefix());
   }

   /**
    * Test qui echoue car la liste des metadonnées est non recherchable
    * @throws SAESearchServiceEx 
    * @throws SyntaxLuceneEx 
    * @throws UnknownLuceneMetadataEx 
    * @throws UnknownDesiredMetadataEx 
    * @throws MetaDataUnauthorizedToConsultEx 
    * @throws MetaDataUnauthorizedToSearchEx 
    */ 
   @Test
   public void searchFailureSAESearchServiceEx() throws SAESearchServiceEx, 
                                                        MetaDataUnauthorizedToSearchEx, 
                                                        MetaDataUnauthorizedToConsultEx, 
                                                        UnknownDesiredMetadataEx, 
                                                        UnknownLuceneMetadataEx, 
                                                        SyntaxLuceneEx {
            
      String requete = TITRE+":NOTIFICATIONS";
      List<String> listMetaDesired = new ArrayList<String>();
      listMetaDesired.add("TailleFichier");

      try {
         // valeur attendu est MetaDataUnauthorizedToSearchEx via andThrow
         EasyMock.expect(documentService.search(requete, listMetaDesired)).andThrow(new MetaDataUnauthorizedToSearchEx(
                                                                                    "Les métadonnées : " + listMetaDesired + " ne sont pas autorisées a la recherche."
                                                                                    ));
         
         EasyMock.replay(documentService);
         
         Recherche request = createSearchType("src/test/resources/recherche/recherche_failure_01.xml");

         skeleton.rechercheSecure(request).getRechercheResponse();
         
         Assert
               .fail("le test doit échouer à cause de la levée d'une exception de type " + SAESearchServiceEx.class);

      } catch (RechercheAxis2Fault fault) {
         assertAxisFault(fault, "Les métadonnées : " + listMetaDesired + " ne sont pas autorisées a la recherche.", "RechercheMetadonneesInterdite", "sae");
      } 
   }
     
   /**
    * Test qui echoue car requete vide 
    * @throws MetaDataUnauthorizedToConsultEx 
    * @throws MetaDataUnauthorizedToSearchEx 
    * @throws SAESearchServiceEx 
    * @throws SyntaxLuceneEx 
    * @throws UnknownLuceneMetadataEx 
    * @throws UnknownDesiredMetadataEx 
    * @throws MetaDataUnauthorizedToConsultEx 
    * @throws MetaDataUnauthorizedToSearchEx 
    */
   @Test
   public void searchFailureReqVide() throws SAESearchServiceEx, 
                                             MetaDataUnauthorizedToSearchEx, 
                                             MetaDataUnauthorizedToConsultEx, 
                                             UnknownDesiredMetadataEx, 
                                             UnknownLuceneMetadataEx, 
                                             SyntaxLuceneEx {
      
      String requete = VIDE;
      List<String> listMetaDesired = new ArrayList<String>();
      listMetaDesired.add(TITRE);
      
      try {
         // valeur attendu est SAESearchServiceEx via andThrow
         EasyMock.expect(documentService.search(requete, listMetaDesired)).andThrow(new SAESearchServiceEx("La recherche est interrompue. La requete lucene est vide ou nulle."));
         
         EasyMock.replay(documentService);
         
         Recherche request = createSearchType("src/test/resources/recherche/recherche_failure_03.xml");
         // recuperer le type de reponse de la recherche
         skeleton.rechercheSecure(request).getRechercheResponse();
         
         Assert
               .fail("le test doit échouer car la requete lucene est vide.");

      } catch (RechercheAxis2Fault fault) {
         assertAxisFault(fault, 
                         "La recherche est interrompue. La requete Lucene est vide ou nulle.",
                         "RequeteLuceneVideOuNull", "sae");
      } 
   }
   /**
    * Test qui echoue car format requete incorrecte 
    * @throws MetaDataUnauthorizedToConsultEx 
    * @throws MetaDataUnauthorizedToSearchEx 
    * @throws SAESearchServiceEx 
    * @throws SyntaxLuceneEx 
    * @throws UnknownLuceneMetadataEx 
    * @throws UnknownDesiredMetadataEx 
    * @throws MetaDataUnauthorizedToConsultEx 
    * @throws MetaDataUnauthorizedToSearchEx 
    */
   @Test
   public void searchFailureReqIncorrecte() throws SAESearchServiceEx, 
                                                   MetaDataUnauthorizedToSearchEx, 
                                                   MetaDataUnauthorizedToConsultEx, 
                                                   UnknownDesiredMetadataEx, 
                                                   UnknownLuceneMetadataEx, 
                                                   SyntaxLuceneEx {

      String requete = TITRE + ":NOTIFICATION DateCreation:01-012011";
      List<String> listMetaDesired = new ArrayList<String>();
      listMetaDesired.add(TITRE);
      
      try {
         // valeur attendu est SyntaxLuceneEx via andThrow
         EasyMock.expect(documentService.search(requete, listMetaDesired)).andThrow(new SyntaxLuceneEx("Erreur de syntaxe de la requete Lucene : "
                                                                                                          + requete));
         
         EasyMock.replay(documentService);
         
         Recherche request = createSearchType("src/test/resources/recherche/recherche_failure_05.xml");

         // recuperer le type de reponse de la recherche
         skeleton.rechercheSecure(request).getRechercheResponse();

         Assert
               .fail("Test qui echoue car format requete incorrecte.");

      } catch (RechercheAxis2Fault fault) {
         assertAxisFault(fault, 
                         "Erreur de syntaxe de la requete Lucene : " + requete,
                         "SyntaxeLuceneNonValide", "sae");
      } 
   }
    
}
