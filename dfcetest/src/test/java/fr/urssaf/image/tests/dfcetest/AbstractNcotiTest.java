package fr.urssaf.image.tests.dfcetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.docubase.toolkit.model.ToolkitFactory;
import net.docubase.toolkit.model.document.Document;
import net.docubase.toolkit.service.ServiceProvider;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.urssaf.image.tests.dfcetest.helpers.DocubaseHelper;
import fr.urssaf.image.tests.dfcetest.helpers.NcotiHelper;

public abstract class AbstractNcotiTest {

   /**
    * Chaque classe fille a son logger.
    */
   protected Logger log = LoggerFactory.getLogger(getClass());

   /**
    * Nom de la base GED pour ce test. Tronqué à 8 caractères dans Docubase. On
    * laisse une valeur en dur pour éviter de créer trop de base car il
    * semblerait que la version 0.9.1 n'apprécie pas.
    */
   protected static final String BASE_ID = "NCOTI_99";// + System.nanoTime();
   protected static ToolkitFactory toolkit;

   protected static ServiceProvider sp;
   public static final String ADM_LOGIN = "_ADMIN";
   public static final String ADM_PASSWORD = "DOCUBASE";
   public static final String HESSIAN_HOST = "http://cer69imageint9.cer69.recouv:8080/dfce-webapp/toolkit/";

   
   @BeforeClass
   public static void beforeClass() throws Exception {
      sp = ServiceProvider.newServiceProvider();
      sp.connect(ADM_LOGIN, ADM_PASSWORD, HESSIAN_HOST);
      NcotiHelper.createOrReplaceBase(BASE_ID);
      toolkit = ToolkitFactory.getInstance();
   }

   @AfterClass
   public static void afterClass() {
      DocubaseHelper.dropBase(BASE_ID);

      if (sp != null) {
         sp.disconnect();
      }
   }

   /**
    * Déclare égaux, deux documents ayant le même UUID et le même hash.
    * On pourrait aller plus loin en implémentant un equals dans une implémentation de Document.
    * @param expectedDoc
    * @param actualDoc
    */
   public static void assertDocumentEquals(Document expectedDoc, Document actualDoc) {
      assertEquals("Les UUID des documents sont différents", 
            expectedDoc.getUuid(), actualDoc.getUuid());
      assertEquals("Les hash des fichiers sont différents", 
            expectedDoc.getDigest(), actualDoc.getDigest());
      assertEquals("Les titres des documents sont différents", 
            expectedDoc.getTitle(), actualDoc.getTitle());   
   }
   

   /**
    * Deux collections de Document seront réputées égales si leurs UUID et les hash correspondants
    * sont identiques.
    * @param expectedDocs
    * @param actualDocs
    */
   public static void assertDocumentsEquals(Collection<Document> expectedDocs, Collection<Document> actualDocs) {
      Map<UUID, String>expectedDigests = new HashMap<UUID, String>();
      
      assertEquals("Le nombre de documents est différent", expectedDocs.size(), actualDocs.size());
      
      for (Document doc : expectedDocs) {
         expectedDigests.put(doc.getUuid(), doc.getDigest());
      }
      
      for (Document actualDoc : actualDocs) {
         String expectedDigest = expectedDigests.get(actualDoc.getUuid());
         
         if (expectedDigest == null) {
            fail("L'UUID " + actualDoc.getUuid() + " ne fait pas partie des documents attendus");
         }
         assertEquals("Les hash du document " + actualDoc.getUuid() + " ne correspondent pas", 
               expectedDigest, actualDoc.getDigest());
      }
   } 
}
