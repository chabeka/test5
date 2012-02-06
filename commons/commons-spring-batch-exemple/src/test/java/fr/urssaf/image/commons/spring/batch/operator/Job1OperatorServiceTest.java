package fr.urssaf.image.commons.spring.batch.operator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.apache.commons.lang.SystemUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

import fr.urssaf.image.commons.spring.batch.support.stax.XSDValidator;
import fr.urssaf.image.commons.spring.batch.support.stax.XSDValidator.SAXParseExceptionType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-operator.xml",
      "/applicationContext-item.xml", "/applicationContext-jobs.xml",
      "/applicationContext-service-test.xml",
      "/applicationContext-datasource-test.xml" })
@SuppressWarnings("PMD.MethodNamingConventions")
@Ignore("doit installer les tables dans POSTGRES")
public class Job1OperatorServiceTest {

   @Autowired
   private Job1OperatorService operatorService;

   private static final File TMP_FILE;

   static {

      TMP_FILE = SystemUtils.getJavaIoTmpDir();

   }

   @Test
   public void validate_success() throws SAXException, IOException,
         ParserConfigurationException {

      int count_items = 100000;

      File xmlPath = new File(TMP_FILE, "batch-exemple/bibliotheque_"
            + count_items + ".xml");

      File xsdPath = new File("src/main/resources/schemas/bibliotheque.xsd");

      List<SAXParseExceptionType> exceptions = XSDValidator
            .validXMLFileWithSAX(xmlPath, xsdPath);

      XSDValidator.afficher(exceptions);

      Assert.assertTrue("le fichier " + xmlPath.getAbsolutePath()
            + " ne doit comporter aucune erreur", exceptions.isEmpty());
   }

   @Test
   public void start() {

      int count_items = 100000;

      File xmlPath = new File(TMP_FILE, "batch-exemple/bibliotheque_"
            + count_items + ".xml");
      File filePath = new File(TMP_FILE, "batch-exemple/bibliotheque_"
            + count_items + ".txt");

      Long executionId = operatorService.start(xmlPath, filePath);

      Assert.assertNotNull(
            "l'identifiant de contexte d'exécution doit être renseigné",
            executionId);
   }

   @Test
   public void stop() {

      long executionId = 23;

      boolean isStopped = operatorService.stop(executionId);

      Assert.assertTrue("Le job n°" + executionId + " doit être arrêté",
            isStopped);
   }

   @Test
   public void restart() {

      long executionId = 23;

      Long newExecutionId = operatorService.restart(executionId);

      Assert.assertNotNull("Le job n°" + executionId + " doit être redemarré",
            newExecutionId);

   }

}
