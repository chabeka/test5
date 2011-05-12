/**
 * 
 */
package fr.urssaf.image.tests.dfcetest;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.docubase.toolkit.model.base.Base;
import net.docubase.toolkit.model.base.BaseCategory;
import net.docubase.toolkit.model.document.Document;
import net.docubase.toolkit.model.search.SearchResult;
import net.docubase.toolkit.service.ServiceProvider;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import fr.urssaf.image.tests.dfcetest.helpers.DocubaseHelper;

/**
 * Teste les requêtes Lucene complexes
 * 
 */
public class AdvancedQueriesTest extends AbstractNcotiTest {
   
   private Base base;
   private BaseCategory appliSourceCategory;
   private String appliSourceFName;
   private Object intergerFName;
   private BaseCategory integerCategory;

   
   @Before
   public void createContext() {
      base = ServiceProvider.getBaseAdministrationService().getBase(BASE_ID);
      appliSourceCategory = base.getBaseCategory(Categories.APPLI_SOURCE.toString());
      appliSourceFName = appliSourceCategory.getFormattedName();
      
      integerCategory = base.getBaseCategory(Categories.INTEGER.toString());
      intergerFName = integerCategory.getFormattedName();      
   }

   @Before
   public void setup() {
   }
   
   /**
    * Requête booléenne de recherche simple : c1=x AND c2=y
    */
   @Test
   public void AQ1() {
      // Metadonnées du doc A
      Map<String, Object> metadataA = new HashMap<String, Object>();
      // Catégories obligatoires
      String titleA = "AQ1A" + System.nanoTime();
      metadataA.put(Categories.TITRE.toString(), titleA);
      metadataA.put(Categories.TYPE_DOC.toString(), Math.random() > 0.5 ? ".pdf" : ".doc");
      metadataA.put(Categories.DATETIME.toString(), new Date());
      metadataA.put(Categories.APPLI_SOURCE.toString(), titleA);
      // Catégories facultatives
      metadataA.put(Categories.DATE.toString(), new Date());
      metadataA.put(Categories.INTEGER.toString(), 10);
      metadataA.put(Categories.BOOLEAN.toString(), true);
      
      Document docA = DocubaseHelper.storeOneDoc(base, metadataA);
      
      // Metadonnées du doc B
      Map<String, Object> metadataB = new HashMap<String, Object>();
      // Catégories obligatoires
      String titleB = "AQ1B" + System.nanoTime();
      metadataB.put(Categories.TITRE.toString(), titleB);
      metadataB.put(Categories.TYPE_DOC.toString(), Math.random() > 0.5 ? ".pdf" : ".doc");
      metadataB.put(Categories.DATETIME.toString(), new Date());
      metadataB.put(Categories.APPLI_SOURCE.toString(), titleB);
      // Catégories facultatives
      metadataB.put(Categories.DATE.toString(), new Date());
      metadataB.put(Categories.INTEGER.toString(), 10);
      metadataB.put(Categories.BOOLEAN.toString(), true);
      
      DocubaseHelper.storeOneDoc(base, metadataB);      
      
      String lucene = String.format("%s:%s AND %s:%s", intergerFName, 10, appliSourceFName, titleA);

      // SUT
      SearchResult result = ServiceProvider.getSearchService().search(lucene, 100, base, null);
      assertEquals(1, result.getDocuments().size());
   }
   

   @Test @Ignore("TODO")
   public void AQ2() {
      
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
      String appliSource = "AQ4" + System.nanoTime();
      int nbDocs = 100;      
      List<Document> docs = DocubaseHelper.insertManyDocs(nbDocs, base, appliSource);
      
      // READ : on cherche par un index commun i.e. l'application source
      String lucene = appliSourceFName + ":AQ4*";
      // On fixe une limite de recherche plus grande pour voir si on ne ramène pas plus de
      // résultats que prévu
      int searchLimit = nbDocs * 2;
      // SUT
      SearchResult result = ServiceProvider.getSearchService().search(lucene, searchLimit, base);
      
      assertEquals(nbDocs, result.getDocuments().size());
      
      // On obtient le bon nombre de documents mais est-ce ceux que l'on a inséré ?
      assertDocumentsEquals(docs, result.getDocuments());
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
