package fr.urssaf.image.commons.spring.batch.support;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.junit.Test;
import org.xml.sax.SAXException;

import fr.urssaf.image.commons.spring.batch.support.stax.XSDValidator;
import fr.urssaf.image.commons.spring.batch.support.stax.XSDValidator.SAXParseExceptionType;

@SuppressWarnings("PMD.MethodNamingConventions")
public class XSDValidatorTest {

   @Test
   public void validate_success() throws SAXException, IOException,
         ParserConfigurationException {

      File xmlPath = new File("src/test/resources/data/bibliotheque.xml");

      File xsdPath = new File("src/main/resources/schemas/bibliotheque.xsd");

      List<SAXParseExceptionType> exceptions = XSDValidator
            .validXMLFileWithSAX(xmlPath, xsdPath);

      XSDValidator.afficher(exceptions);

      Assert.assertTrue("le fichier " + xmlPath.getAbsolutePath()
            + " ne doit comporter aucune erreur", exceptions.isEmpty());
   }

   @Test
   public void validate_failure() throws SAXException, IOException,
         ParserConfigurationException {

      File xmlPath = new File(
            "src/test/resources/data/bibliotheque_failure.xml");

      File xsdPath = new File("src/main/resources/schemas/bibliotheque.xsd");

      List<SAXParseExceptionType> exceptions = XSDValidator
            .validXMLFileWithSAX(xmlPath, xsdPath);

      XSDValidator.afficher(exceptions);

      Assert.assertEquals("le fichier " + xmlPath.getAbsolutePath()
            + " doit comporter des erreurs", 1, exceptions.size());

   }
}
