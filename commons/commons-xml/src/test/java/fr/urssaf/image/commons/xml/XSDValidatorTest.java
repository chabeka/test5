package fr.urssaf.image.commons.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import fr.urssaf.image.commons.util.resource.ResourceUtil;
import fr.urssaf.image.commons.xml.XSDValidator.SAXParseExceptionType;

public class XSDValidatorTest {

   @Test
	public void validatorInfoByDOMSuccess() throws
	   URISyntaxException,
	   ParserConfigurationException,
	   IOException,
	   SAXException
	{
		String xmlFile = "src/test/resources/info_success.xml";
		String xsdFile = getXsdFile();
		assertSuccess(xmlFile, xsdFile);
	}

	@Test
	public void validatorInfoByDOMFailure() throws
	   URISyntaxException,
	   ParserConfigurationException,
	   IOException,
	   SAXException
	{
		String xmlFile = "src/test/resources/info_failure.xml";
		
			List<SAXParseExceptionType> exceptions = XSDValidator
					.validXMLWithDOM(xmlFile, getXsdFile());

			SAXParseExceptionType exception = exceptions.get(0);

			assertEquals("erreur de type", "ERROR", exception.type.getLabel());
			assertEquals("erreur de ligne", 10, exception.exception
					.getLineNumber());
			assertEquals(
					"erreur de message",
					"cvc-complex-type.2.4.d: Invalid content was found starting with element 'baliseinconnue'. No child element is expected at this point.",
					exception.exception.getMessage());

			assertEquals("Le nombre d'erreurs trouvées dans le XML n'est pas celui attendu",1, exceptions.size());

	}

	private void assertSuccess(String xmlFile, String xsdFile) throws
	   ParserConfigurationException,
	   IOException,
	   SAXException
	{
	   assertTrue(
	         "La validation du XML aurait due réussir",
	         XSDValidator.validXMLWithDOM(xmlFile, xsdFile).isEmpty());
	}

	
	private String getXsdFile() throws URISyntaxException
	{
	   return ResourceUtil.getResourceFullPath(this, "/info.xsd");
	}

}
