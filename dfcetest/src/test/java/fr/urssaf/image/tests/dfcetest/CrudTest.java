/**
 * 
 */
package fr.urssaf.image.tests.dfcetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.docubase.toolkit.model.ToolkitFactory;
import net.docubase.toolkit.model.base.Base;
import net.docubase.toolkit.model.base.BaseCategory;
import net.docubase.toolkit.model.document.Document;
import net.docubase.toolkit.model.search.SearchResult;

import org.junit.Before;
import org.junit.Test;

import fr.urssaf.image.tests.dfcetest.helpers.DocGen;
import fr.urssaf.image.tests.dfcetest.helpers.DocubaseHelper;

/**
 * Teste les fonctionnalités CRUD en contexte mono threadé.
 * 
 */
public class CrudTest extends AbstractNcotiTest {

   private Base base;
   private BaseCategory appliSourceCategory;
   private DocGen docGen;   
   String appliSourceFName;

   @Before
   public void createContext() {
      base = sp.getBaseAdministrationService().getBase(BASE_ID);
      docGen = new DocGen(base);  
      
      appliSourceCategory = base.getBaseCategory(Categories.APPLI_SOURCE.toString());
      appliSourceFName = appliSourceCategory.getFormattedName();
   }

   /**
    * Teste un scénario de type CRUD en une fois.
    * 
    * @throws Exception
    */
   @Test
   public void crud() throws Exception {
      String appliSource = "test_crud" + System.nanoTime();
      int nbDocs = 100;
      Set<UUID> uuids = new HashSet<UUID>();
      Set<UUID> uuidsFound = new HashSet<UUID>();
      Set<String> applisSourcesFound = new HashSet<String>();

      /**
       *  CREATE
       */
      List<Document> docs = DocubaseHelper.insertManyDocs(nbDocs, base, appliSource);
      assertNotNull("Aucun document ne semble avoir été inséré", docs);
      assertEquals(nbDocs, docs.size());

      for (Document doc : docs) {
         uuids.add(doc.getUuid());
      }
      // Là on est mal si ça ne passe pas
      assertEquals("Il existe des doublons dans les documents insérés", nbDocs, uuids.size());

      /**
       *  READ : on cherche par un index commun i.e. l'application source
       */
      String lucene = appliSourceFName + ":" + appliSource;
      // On fixe une limite plus grande pour voir si on ne ramène pas plus de
      // résultats que prévu
      int searchLimit = nbDocs * 2;
      // SUT
      SearchResult result = sp.getSearchService().search(lucene, searchLimit, base);
      assertEquals("Le nombre de documents trouvés est incorrect", nbDocs, result.getTotalHits());

      for (Document docFound : result.getDocuments()) {
         uuidsFound.add(docFound.getUuid());
         applisSourcesFound.add(docFound.getFirstCriterion(appliSourceCategory).getWord().toString());
      }
      assertEquals("Les documents trouvés ne sont pas les documents insérés", uuids, uuidsFound);
      assertEquals("Tous les documents doivent avoir la même application source", 1,
            applisSourcesFound.size());
      assertEquals("La catégorie a été mal alimentée", appliSource, applisSourcesFound.iterator()
            .next());

      /**
       *  UPDATE : on met à jour l'application source des documents et la date de création
       */
      DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE);
      Date newDate = null;
      String dateFr = "01/01/2011 01:02:03";
      try {
         newDate = df.parse(dateFr);
      } catch (ParseException e) {
         e.printStackTrace();
         fail("Problème dans le test, la date est mal formatée : " + dateFr);
      }

      String newAppliSource = "test_crud" + System.nanoTime();

      for (Document doc : docs) {
         doc.setCreationDate(newDate);
         // FIXME: est-ce bien comme cela que l'on est met à jour la valeur d'une catégorie ?
         doc.updateCriterion(appliSourceCategory, newAppliSource);
         doc = sp.getStoreService().updateDocument(doc);
      }
      
      sp.disconnect();
      sp.connect(ADM_LOGIN, ADM_PASSWORD, HESSIAN_HOST);

      // On recherche avec la nouvelle appli source
      lucene = appliSourceFName + ":" + newAppliSource;
      result = sp.getSearchService().search(lucene, searchLimit, base);
      assertEquals("Le nombre de documents trouvés est incorrect (hits total)", nbDocs, result.getTotalHits());
      assertEquals("Le nombre de documents trouvés est incorrect (documents size)", nbDocs, result.getDocuments().size());

      for (Document docFound : result.getDocuments()) {
         uuidsFound.add(docFound.getUuid());
         applisSourcesFound.add(docFound.getFirstCriterion(appliSourceCategory).getWord().toString());
      }
      assertEquals("Les documents trouvés ne sont pas les documents insérés", uuids, uuidsFound);
      assertEquals("Tous les documents doivent avoir la même application source", 1,
            applisSourcesFound.size());
      assertEquals("La catégorie a été mal alimentée", newAppliSource, applisSourcesFound
            .iterator().next());
   }
   
   /**
    * On teste que la suppression d'un document supprime le document (i.e. le fichier) 
    * ainsi que ses méta données.
    * @throws Exception
    */
   @Test
   public void deleteDocument() throws Exception {
      String appliSource = "deleteDocument" + System.nanoTime();

      Document doc = docGen.setTitle(appliSource).store();  
      assertNotNull(doc);
      UUID uuid = doc.getUuid();
      assertNotNull(uuid);
      
      // Requête lucene qui ramène le document
      String luceneQuery = appliSourceFName + ":" + appliSource;
      
      // On teste la requête lucene avant de supprimer le document
      SearchResult result = sp.getSearchService().search(luceneQuery, 100, base);
      List<Document> docs = result.getDocuments();
      assertEquals("La requête lucene aurait dû ramener le document", 1, docs.size());
      
      // SUT
      sp.getStoreService().deleteDocument(uuid);      
      
      // On s'assure que le document ne remonte plus via l'UUID
      Document ghostDoc = sp.getSearchService().getDocumentByUUID(base, uuid);
      assertNull(ghostDoc);
      
      // On s'assure que les méta données du document n'existe plus
      result = sp.getSearchService().search(luceneQuery, 100, base);
      docs = result.getDocuments();
      assertEquals("Le document est censé avoir été supprimé", 0, docs.size());
   }

   @Test
   public void UUID_inMemoryModification() throws Exception {
      String appliSource = "test_existingUUID" + System.nanoTime();

      Document doc = DocubaseHelper.insertOneDoc(base, appliSource);
      UUID uuid = doc.getUuid();
      UUID newUuid = UUID.randomUUID();

      // On s'assure que la mise à jour des metadonnées plante
      try {
         doc.setUuid(newUuid);
      } catch (Exception e) {
         Throwable cause = e.getCause();

         if (cause == null) {
            throw e;
         }

         // FIXME : Quelle exception doit-on catcher ?
         /*if (!(cause instanceof RheaToolkitSystemElementExistException)) {
            throw e;
         }*/
      }

      // On doit bien retrouver le document avec son UUID non modifié
      Document docFoundWithOldUUID = sp.getSearchService().getDocumentByUUIDMultiBase(
            uuid);
      assertNotNull(docFoundWithOldUUID);

      // Le nouvel UUID ne doit être affecté à aucun document
      Document docFoundWithNewUUID = sp.getSearchService().getDocumentByUUIDMultiBase(
            newUuid);
      assertNull(docFoundWithNewUUID);
   }

   /**
    * Test "border line" : on insère un document avec un UUID existant. 
    * DFCE ne râle pas, ce qui est normal selon Docubase car ils ne veulent pas vérifier
    * si l'UUID fixé est un doublon. 
    * 
    * Ce test est donc là pour mémoire.
    * 
    * @throws Exception
    */
   @Test
   public void UUID_duplicate() throws Exception {
      String appliSource1 = "test_existingUUID" + System.nanoTime();
      
      // On crée un document, puis on récupère l'UUID fourni par Docubase
      Document doc1 = DocubaseHelper.insertOneDoc(base, appliSource1);
      UUID existingUuid = doc1.getUuid();
      InputStream file1 = sp.getStoreService().getDocumentFile(doc1);
      String fileContent1 = file1.toString();
      log.debug("\nInsertion du document 1\nDocument UUID: {} => hash: {} (Algo={}), appliSource: {}", new Object[] { 
            doc1.getUuid(),
            doc1.getDigest(),
            doc1.getDigestAlgorithm(),
            doc1.getFirstCriterion(appliSourceCategory).getWord()
      });      
      log.debug("\nContenu du fichier 1\n{}\n\n", fileContent1);
      
      // On crée un second document avec un UUID fixé par avance qui correspond
      // à l'UUID du premier document
      Document doc2 = ToolkitFactory.getInstance().createDocumentTag(base);
      doc2.setUuid(existingUuid);
      doc2.setType("PDF");
      String appliSource2 = "test_existingUUID" + System.nanoTime();
      Map<String, Object> metadata2 = DocubaseHelper.getCategoriesRandomValues(appliSource2); 
      Document stored = DocubaseHelper.storeThisDoc(base, doc2, metadata2); 
      assertEquals(doc2.getUuid(), stored.getUuid());
      InputStream file2 = sp.getStoreService().getDocumentFile(stored);
      String fileContent2 = file2.toString();
      log.debug("\nContenu du fichier 2\n{}\n\n", fileContent2);
      
      // On crée un troisième document avec un UUID fixé par avance, mais aléatoire.
      // Cela permet d'éviter un test faux positif
      Document doc3 = ToolkitFactory.getInstance().createDocumentTag(base);
      doc3.setUuid(UUID.randomUUID());
      doc3.setType("PDF");
      String appliSource3 = "test_existingUUID" + System.nanoTime();
      Map<String, Object> metadata3 = DocubaseHelper.getCategoriesRandomValues(appliSource3);
      stored = DocubaseHelper.storeThisDoc(base, doc3, metadata3);
      assertNotNull("Le document aurait dû être enregistré car son UUID est aléatoire", stored);
      
      // On recherche l'UUID doublon
      Document docByUuid = sp.getSearchService().getDocumentByUUIDMultiBase(existingUuid);
      log.debug("getDocumentByUUID({}) => UUID: {}, appliSource: {}", new Object[] { 
            existingUuid,
            docByUuid.getUuid(),
            docByUuid.getFirstCriterion(appliSourceCategory).getWord()});
      
      // On cherche le premier document : on doit le retrouver
      String lucene = appliSourceFName + ":" + appliSource1;
      SearchResult result = sp.getSearchService().search(lucene, 5, base);
      assertEquals(1, result.getDocuments().size());
      Document docFound = result.getDocuments().get(0);
      assertEquals(existingUuid, docFound.getUuid());  
      InputStream fileFound = sp.getStoreService().getDocumentFile(docFound);
      String fileContentFound = fileFound.toString();
      log.debug("\nContenu du fichier trouvé\n{}\n\n", fileContentFound);
      assertEquals("L'application source a changée", 
            appliSource1, 
            docFound.getFirstCriterion(appliSourceCategory).getWord());
      
      log.debug("\nRecherche du document 1 {} \nDocument UUID: {} => appliSource: {}", new Object[] { 
            lucene,
            docFound.getUuid(),
            docFound.getFirstCriterion(appliSourceCategory).getWord()});
      
      // On cherche le deuxième document : il NE doit PAS exister
      lucene = appliSourceFName + ":" + appliSource2;
      result = sp.getSearchService().search(lucene, 5, base);
      // assertEquals("Le document ne devrait pas exister", 0, result.getDocuments().size());
      if (result.getDocuments().size() > 0) {
         Document evilDocument = result.getDocuments().get(0);
         assertEquals(existingUuid, evilDocument.getUuid());
         log.debug("\nRecherche du document 2 {} \nDocument UUID: {} => appliSource: {}", new Object[] { 
               lucene,
               evilDocument.getUuid(),
               evilDocument.getFirstCriterion(appliSourceCategory).getWord() 
         });
      }
      
      // On cherche le troisième document : on doit le retrouver
      lucene = appliSourceFName + ":" + appliSource3;
      result = sp.getSearchService().search(lucene, 5, base);
      assertEquals(1, result.getDocuments().size());
      docFound = result.getDocuments().get(0);
      assertEquals(doc3.getUuid(), docFound.getUuid());
   }   
   
   
   @Test
   public void UUID_inexistant() throws Exception {
      UUID uuid = UUID.randomUUID();
      Document doc = sp.getSearchService().getDocumentByUUID(this.base, uuid);
      assertNull(doc);
   }
 
}
