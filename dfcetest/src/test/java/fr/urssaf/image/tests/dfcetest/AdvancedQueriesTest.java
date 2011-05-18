/**
 * 
 */
package fr.urssaf.image.tests.dfcetest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.docubase.toolkit.model.base.Base;
import net.docubase.toolkit.model.base.BaseCategory;
import net.docubase.toolkit.model.document.Document;
import net.docubase.toolkit.model.search.SearchResult;
import net.docubase.toolkit.service.ServiceProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import fr.urssaf.image.tests.dfcetest.helpers.DocGen;
import fr.urssaf.image.tests.dfcetest.helpers.DocubaseHelper;

/**
 * Teste les requêtes Lucene complexes
 * 
 */
public class AdvancedQueriesTest extends AbstractNcotiTest {
   
   private Base base;
   private BaseCategory appliSourceCategory;
   private String appliSourceFName;
   private String intergerFName;
   private String boolFName;
   private BaseCategory integerCategory;
   private BaseCategory boolCategory;
   private DocGen docGen;
   private List<Document> docs;
   
   @Before
   public void createContext() {
      base = ServiceProvider.getBaseAdministrationService().getBase(BASE_ID);
      appliSourceCategory = base.getBaseCategory(Categories.APPLI_SOURCE.toString());
      appliSourceFName = appliSourceCategory.getFormattedName();
      
      integerCategory = base.getBaseCategory(Categories.INTEGER.toString());
      intergerFName = integerCategory.getFormattedName(); 
      
      boolCategory = base.getBaseCategory(Categories.BOOLEAN.toString());
      boolFName = boolCategory.getFormattedName(); 
   }

   @Before
   public void setup() {
      docs = new ArrayList<Document>();
      docGen = new DocGen(base);
   }
   
   @After
   public void teardown() {
      for (Document doc : DocGen.storedDocuments) {
         ServiceProvider.getStoreService().deleteDocument(doc);
      }
   }
   
   public Document retainDoc(Map<String, Object>  metadata) {
      Document doc = DocubaseHelper.storeOneDoc(base, metadata);
      docs.add(doc);
      return doc;
   }
   
   /**
    * Requête booléenne de recherche simple : c1=x AND c2=y
    */
   @Test
   public void AQ1() {
      // Document A
      String titleA = docGen.setRandomTitle("AQ1A").getTitle();
      docGen.put(Categories.INTEGER, 10);
      Document docA = docGen.store();
      
      // Document B
      docGen.setRandomTitle("AQ1B").store();
      
      String lucene = String.format("%s:%s", intergerFName, 10);
      SearchResult result = ServiceProvider.getSearchService().search(lucene, 100, base, null);
      assertEquals(2, result.getDocuments().size());
      
      lucene = String.format("%s:%s", appliSourceFName, titleA);
      result = ServiceProvider.getSearchService().search(lucene, 100, base, null);
      assertEquals(1, result.getDocuments().size());

      // SUT
      //lucene = String.format("%s:%s AND %s:%s", intergerFName, 10, appliSourceFName, titleA);
      lucene = String.format("%s:%s AND %s:%s OR 1:1", intergerFName, 10, appliSourceFName, titleA);
      System.out.println(lucene);
      result = ServiceProvider.getSearchService().search(lucene, 100, base, null);
      assertEquals(1, result.getDocuments().size());
      assertDocumentEquals(docA, result.getDocuments().get(0));      
   }
   

   @Test
   public void AQ2() {
      // Document A
      String titleA = docGen.setRandomTitle("AQ2A").getTitle();
      docGen.put(Categories.INTEGER, 10);
      docGen.put(Categories.BOOLEAN, false);
      Document docA = docGen.store();
      
      // Document B
      docGen.setRandomTitle("AQ2B");
      docGen.put(Categories.BOOLEAN, true);
      Document docB = docGen.store();                
      
      String lucene = String.format("%s:%s", intergerFName, 10);
      SearchResult result = ServiceProvider.getSearchService().search(lucene, 100, base, null);
      assertEquals(2, result.getDocuments().size());
      
      lucene = String.format("%s:%s", appliSourceFName, titleA);
      result = ServiceProvider.getSearchService().search(lucene, 100, base, null);
      assertEquals(1, result.getDocuments().size());      

      lucene = String.format("%s:%s OR %s:%s", intergerFName, 10, appliSourceFName, titleA);
      result = ServiceProvider.getSearchService().search(lucene, 100, base, null);
      assertEquals(2, result.getDocuments().size()); 
      
      // SUT
      lucene = String.format("(%s:%s OR %s:%s) AND %s:%s", 
            intergerFName, 10, appliSourceFName, titleA, boolFName, true);
      result = ServiceProvider.getSearchService().search(lucene, 100, base, null);
      assertEquals(1, result.getDocuments().size()); 
      assertDocumentEquals(docB, result.getDocuments().get(0));
      
      // SUT
      lucene = String.format("(%s:%s OR %s:%s) AND %s:%s", 
            intergerFName, 10, appliSourceFName, titleA, boolFName, false);
      result = ServiceProvider.getSearchService().search(lucene, 100, base, null);
      assertEquals(1, result.getDocuments().size());
      assertDocumentEquals(docA, result.getDocuments().get(0));
   }
   

   @Test @Ignore("TODO")
   public void AQ3() {
      
   }

   /**
    * Requête contenant un joker * avec une valeur de catégorie contenant un underscore. 
    * Le underscore est important car il est mal géré par Docubase.
    */
   @Test
   public void AQ4() {
      Document injectedDoc = docGen.setRandomTitle("AQ6").store();      
      log.debug("AQ4, Doc inséré -> " + injectedDoc.getUUID().toString());
      
      Document docbyUUID = ServiceProvider.getSearchService().getDocumentByUUIDMultiBase(injectedDoc.getUUID());
      assertDocumentEquals(injectedDoc, docbyUUID);
      
      String lucene = appliSourceFName + ":" + docGen.getTitle();
      SearchResult result = ServiceProvider.getSearchService().search(lucene, 100, base);
      assertEquals(1, result.getDocuments().size());
      assertDocumentEquals(injectedDoc, result.getDocuments().get(0));
      
      // SUT
      lucene = appliSourceFName + ":AQ6*";
      result = ServiceProvider.getSearchService().search(lucene, 100, base);
      assertEquals(1, result.getDocuments().size());
      
      // On obtient le bon nombre de documents mais est-ce ceux que l'on a inséré ?
      assertDocumentEquals(injectedDoc, result.getDocuments().get(0));
   }
   
   
   /**
    * Requête booléenne absurde : c1=x AND c1=y
    * Cela permet de tester la stabilité de Docubase et l'exactitude 
    * de l'implémentation ensembliste.
    */
   @Test
   public void AQ5() {
      String appliSourceA = "AQ5A" + System.nanoTime();
      int nbDocsA = 100;      
      List<Document> docsA = DocubaseHelper.insertManyDocs(nbDocsA, base, appliSourceA); 
      
      String appliSourceB = "AQ5B" + System.nanoTime();
      int nbDocsB = 100;      
      List<Document> docsB = DocubaseHelper.insertManyDocs(nbDocsB, base, appliSourceB); 
      
      String lucene = String.format("%s:%s AND %s:%s", 
            appliSourceFName, appliSourceA, appliSourceFName, appliSourceB);
      // On fixe une limite de recherche plus grande pour voir si on ne ramène pas plus de
      // résultats que prévu
      int searchLimit = 2 * (nbDocsA + nbDocsB);
      // SUT
      SearchResult result = ServiceProvider.getSearchService().search(lucene, searchLimit, base, null);
      assertEquals(0, result.getDocuments().size());
   }   
}
